package com.mochahid.videoservice.service;

import com.mochahid.common.exception.ResourceNotFoundException;
import com.mochahid.videoservice.dto.VideoRequest;
import com.mochahid.videoservice.dto.VideoResponse;
import com.mochahid.videoservice.entity.Category;
import com.mochahid.videoservice.entity.Video;
import com.mochahid.videoservice.entity.VideoType;
import com.mochahid.videoservice.mapper.VideoMapper;
import com.mochahid.videoservice.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoMapper videoMapper;

    @InjectMocks
    private VideoService videoService;

    private Video video;
    private VideoResponse videoResponse;
    private VideoRequest videoRequest;

    @BeforeEach
    void setUp() {
        video = Video.builder()
                .title("Inception")
                .description("A thief who steals corporate secrets")
                .type(VideoType.FILM)
                .category(Category.SCIENCE_FICTION)
                .rating(8.8)
                .director("Christopher Nolan")
                .cast(List.of("Leonardo DiCaprio"))
                .views(15420)
                .featured(true)
                .build();
        video.setId(1L);

        videoResponse = VideoResponse.builder()
                .id(1L)
                .title("Inception")
                .description("A thief who steals corporate secrets")
                .type(VideoType.FILM)
                .category(Category.SCIENCE_FICTION)
                .rating(8.8)
                .director("Christopher Nolan")
                .cast(List.of("Leonardo DiCaprio"))
                .views(15420)
                .featured(true)
                .build();

        videoRequest = VideoRequest.builder()
                .title("Inception")
                .description("A thief who steals corporate secrets")
                .type(VideoType.FILM)
                .category(Category.SCIENCE_FICTION)
                .rating(8.8)
                .director("Christopher Nolan")
                .cast(List.of("Leonardo DiCaprio"))
                .build();
    }

    @Test
    void getAllVideos_shouldReturnAllVideos() {
        when(videoRepository.findAll()).thenReturn(List.of(video));
        when(videoMapper.toResponseList(any())).thenReturn(List.of(videoResponse));

        List<VideoResponse> result = videoService.getAllVideos(null, null, null, null);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getTitle()).isEqualTo("Inception");
    }

    @Test
    void getVideoById_shouldReturnVideo() {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        when(videoMapper.toResponse(video)).thenReturn(videoResponse);

        VideoResponse result = videoService.getVideoById(1L);

        assertThat(result.getTitle()).isEqualTo("Inception");
    }

    @Test
    void getVideoById_shouldThrowWhenNotFound() {
        when(videoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> videoService.getVideoById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createVideo_shouldSaveAndReturn() {
        when(videoMapper.toEntity(videoRequest)).thenReturn(video);
        when(videoRepository.save(video)).thenReturn(video);
        when(videoMapper.toResponse(video)).thenReturn(videoResponse);

        VideoResponse result = videoService.createVideo(videoRequest);

        assertThat(result.getTitle()).isEqualTo("Inception");
        verify(videoRepository).save(video);
    }

    @Test
    void deleteVideo_shouldDeleteWhenExists() {
        when(videoRepository.existsById(1L)).thenReturn(true);

        videoService.deleteVideo(1L);

        verify(videoRepository).deleteById(1L);
    }

    @Test
    void deleteVideo_shouldThrowWhenNotFound() {
        when(videoRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> videoService.deleteVideo(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getFeaturedVideos_shouldReturnFeatured() {
        when(videoRepository.findByFeaturedTrue()).thenReturn(List.of(video));
        when(videoMapper.toResponseList(any())).thenReturn(List.of(videoResponse));

        List<VideoResponse> result = videoService.getFeaturedVideos();

        assertThat(result).hasSize(1);
    }

    @Test
    void searchVideos_shouldReturnMatches() {
        when(videoRepository.search("inception")).thenReturn(List.of(video));
        when(videoMapper.toResponseList(any())).thenReturn(List.of(videoResponse));

        List<VideoResponse> result = videoService.searchVideos("inception");

        assertThat(result).hasSize(1);
    }

    @Test
    void getAllVideos_withTypeFilter_shouldFilter() {
        when(videoRepository.findByType(VideoType.FILM)).thenReturn(List.of(video));
        when(videoMapper.toResponseList(any())).thenReturn(List.of(videoResponse));

        List<VideoResponse> result = videoService.getAllVideos(VideoType.FILM, null, null, null);

        assertThat(result).hasSize(1);
        verify(videoRepository).findByType(VideoType.FILM);
    }

    @Test
    void getAllVideos_withCategoryFilter_shouldFilter() {
        when(videoRepository.findByCategory(Category.SCIENCE_FICTION)).thenReturn(List.of(video));
        when(videoMapper.toResponseList(any())).thenReturn(List.of(videoResponse));

        List<VideoResponse> result = videoService.getAllVideos(null, Category.SCIENCE_FICTION, null, null);

        assertThat(result).hasSize(1);
        verify(videoRepository).findByCategory(Category.SCIENCE_FICTION);
    }
}
