package com.mochahid.userservice.service;

import com.mochahid.common.exception.ResourceNotFoundException;
import com.mochahid.userservice.client.VideoClient;
import com.mochahid.userservice.dto.VideoDto;
import com.mochahid.userservice.dto.WatchHistoryRequest;
import com.mochahid.userservice.dto.WatchHistoryResponse;
import com.mochahid.userservice.dto.WatchStatsResponse;
import com.mochahid.userservice.entity.WatchHistory;
import com.mochahid.userservice.repository.UserRepository;
import com.mochahid.userservice.repository.WatchHistoryRepository;
import com.mochahid.userservice.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;
    private final WatchlistRepository watchlistRepository;
    private final UserRepository userRepository;
    private final VideoClient videoClient;

    public List<WatchHistoryResponse> getUserHistory(Long userId) {
        validateUserExists(userId);
        return watchHistoryRepository.findByUserIdOrderByWatchedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public WatchHistoryResponse addToHistory(Long userId, WatchHistoryRequest request) {
        validateUserExists(userId);

        Optional<WatchHistory> existing = watchHistoryRepository.findByUserIdAndVideoId(userId, request.getVideoId());

        WatchHistory history;
        if (existing.isPresent()) {
            history = existing.get();
            history.setWatchedAt(LocalDateTime.now());
            history.setProgressTime(request.getProgressTime() != null ? request.getProgressTime() : 0);
            history.setCompleted(request.getCompleted() != null ? request.getCompleted() : false);
        } else {
            history = WatchHistory.builder()
                    .userId(userId)
                    .videoId(request.getVideoId())
                    .watchedAt(LocalDateTime.now())
                    .progressTime(request.getProgressTime() != null ? request.getProgressTime() : 0)
                    .completed(request.getCompleted() != null ? request.getCompleted() : false)
                    .build();
        }

        return toResponse(watchHistoryRepository.save(history));
    }

    public WatchStatsResponse getWatchStats(Long userId) {
        validateUserExists(userId);

        return WatchStatsResponse.builder()
                .totalWatched(watchHistoryRepository.countByUserId(userId))
                .completed(watchHistoryRepository.countByUserIdAndCompletedTrue(userId))
                .totalMinutes(watchHistoryRepository.sumProgressTimeByUserId(userId))
                .watchlistCount(watchlistRepository.countByUserId(userId))
                .build();
    }

    @Transactional
    public void clearHistory(Long userId) {
        validateUserExists(userId);
        watchHistoryRepository.deleteByUserId(userId);
    }

    private WatchHistoryResponse toResponse(WatchHistory history) {
        VideoDto video = fetchVideo(history.getVideoId());
        return WatchHistoryResponse.builder()
                .id(history.getId())
                .userId(history.getUserId())
                .videoId(history.getVideoId())
                .watchedAt(history.getWatchedAt())
                .progressTime(history.getProgressTime())
                .completed(history.getCompleted())
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
