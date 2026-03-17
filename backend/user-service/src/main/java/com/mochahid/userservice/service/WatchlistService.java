package com.mochahid.userservice.service;

import com.mochahid.common.exception.BadRequestException;
import com.mochahid.common.exception.ResourceNotFoundException;
import com.mochahid.userservice.client.VideoClient;
import com.mochahid.userservice.dto.VideoDto;
import com.mochahid.userservice.dto.WatchlistResponse;
import com.mochahid.userservice.entity.WatchlistItem;
import com.mochahid.userservice.repository.UserRepository;
import com.mochahid.userservice.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final UserRepository userRepository;
    private final VideoClient videoClient;

    public List<WatchlistResponse> getUserWatchlist(Long userId) {
        validateUserExists(userId);
        return watchlistRepository.findByUserIdOrderByAddedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public boolean isInWatchlist(Long userId, Long videoId) {
        return watchlistRepository.existsByUserIdAndVideoId(userId, videoId);
    }

    @Transactional
    public WatchlistResponse addToWatchlist(Long userId, Long videoId) {
        validateUserExists(userId);

        if (watchlistRepository.existsByUserIdAndVideoId(userId, videoId)) {
            throw new BadRequestException("video already in watchlist");
        }

        WatchlistItem item = WatchlistItem.builder()
                .userId(userId)
                .videoId(videoId)
                .addedAt(LocalDateTime.now())
                .build();

        return toResponse(watchlistRepository.save(item));
    }

    @Transactional
    public void removeFromWatchlist(Long userId, Long videoId) {
        validateUserExists(userId);
        watchlistRepository.deleteByUserIdAndVideoId(userId, videoId);
    }

    @Transactional
    public WatchlistResponse toggleWatchlist(Long userId, Long videoId) {
        validateUserExists(userId);

        if (watchlistRepository.existsByUserIdAndVideoId(userId, videoId)) {
            watchlistRepository.deleteByUserIdAndVideoId(userId, videoId);
            return null;
        }

        WatchlistItem item = WatchlistItem.builder()
                .userId(userId)
                .videoId(videoId)
                .addedAt(LocalDateTime.now())
                .build();
        return toResponse(watchlistRepository.save(item));
    }

    private WatchlistResponse toResponse(WatchlistItem item) {
        VideoDto video = fetchVideo(item.getVideoId());
        return WatchlistResponse.builder()
                .id(item.getId())
                .userId(item.getUserId())
                .videoId(item.getVideoId())
                .addedAt(item.getAddedAt())
                .video(video)
                .build();
    }

    private VideoDto fetchVideo(Long videoId) {
        try {
            var response = videoClient.getVideoById(videoId);
            return response != null ? response.getData() : null;
        } catch (Exception e) {
            log.warn("failed to fetch video {}: {}", videoId, e.getMessage());
            return null;
        }
    }

    private void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("user", userId);
        }
    }
}
