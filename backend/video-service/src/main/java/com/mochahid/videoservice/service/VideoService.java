package com.mochahid.videoservice.service;

import com.mochahid.common.exception.ResourceNotFoundException;
import com.mochahid.videoservice.dto.VideoRequest;
import com.mochahid.videoservice.dto.VideoResponse;
import com.mochahid.videoservice.entity.Category;
import com.mochahid.videoservice.entity.Video;
import com.mochahid.videoservice.entity.VideoType;
import com.mochahid.videoservice.mapper.VideoMapper;
import com.mochahid.videoservice.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    public List<VideoResponse> getAllVideos(VideoType type, Category category, String search, String sortBy) {
        List<Video> videos;

        if (search != null && !search.isBlank()) {
            videos = videoRepository.search(search.trim());
        } else if (type != null && category != null) {
            videos = videoRepository.findByTypeAndCategory(type, category);
        } else if (type != null) {
            videos = videoRepository.findByType(type);
        } else if (category != null) {
            videos = videoRepository.findByCategory(category);
        } else {
            videos = videoRepository.findAll();
        }

        if (sortBy != null) {
            switch (sortBy) {
                case "rating" -> videos.sort(Comparator.comparing(Video::getRating).reversed());
                case "views" -> videos.sort(Comparator.comparing(Video::getViews).reversed());
                case "recent" -> videos.sort(Comparator.comparing(Video::getReleaseYear).reversed());
                case "title" -> videos.sort(Comparator.comparing(Video::getTitle));
            }
        }

        return videoMapper.toResponseList(videos);
    }

    public VideoResponse getVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("video", id));
        return videoMapper.toResponse(video);
    }

    public List<VideoResponse> getFeaturedVideos() {
        return videoMapper.toResponseList(videoRepository.findByFeaturedTrue());
    }

    public List<VideoResponse> getSimilarVideos(Long videoId, int limit) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("video", videoId));

        List<Video> similar = videoRepository.findSimilar(videoId, video.getCategory(), video.getType());
        return videoMapper.toResponseList(similar.stream().limit(limit).toList());
    }

    public List<VideoResponse> searchVideos(String query) {
        return videoMapper.toResponseList(videoRepository.search(query));
    }

    @Transactional
    public VideoResponse createVideo(VideoRequest request) {
        Video video = videoMapper.toEntity(request);
        return videoMapper.toResponse(videoRepository.save(video));
    }

    @Transactional
    public VideoResponse updateVideo(Long id, VideoRequest request) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("video", id));

        videoMapper.updateEntity(request, video);
        return videoMapper.toResponse(videoRepository.save(video));
    }

    @Transactional
    public void deleteVideo(Long id) {
        if (!videoRepository.existsById(id)) {
            throw new ResourceNotFoundException("video", id);
        }
        videoRepository.deleteById(id);
    }
}
