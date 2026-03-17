package com.mochahid.videoservice.controller;

import com.mochahid.common.dto.ApiResponse;
import com.mochahid.videoservice.dto.VideoRequest;
import com.mochahid.videoservice.dto.VideoResponse;
import com.mochahid.videoservice.entity.Category;
import com.mochahid.videoservice.entity.VideoType;
import com.mochahid.videoservice.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<VideoResponse>>> getAllVideos(
            @RequestParam(required = false) VideoType type,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "recent") String sortBy) {
        return ResponseEntity.ok(ApiResponse.success(videoService.getAllVideos(type, category, search, sortBy)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VideoResponse>> getVideoById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(videoService.getVideoById(id)));
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<VideoResponse>>> getFeaturedVideos() {
        return ResponseEntity.ok(ApiResponse.success(videoService.getFeaturedVideos()));
    }

    @GetMapping("/{id}/similar")
    public ResponseEntity<ApiResponse<List<VideoResponse>>> getSimilarVideos(
            @PathVariable Long id,
            @RequestParam(defaultValue = "6") int limit) {
        return ResponseEntity.ok(ApiResponse.success(videoService.getSimilarVideos(id, limit)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<VideoResponse>>> searchVideos(@RequestParam String q) {
        return ResponseEntity.ok(ApiResponse.success(videoService.searchVideos(q)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VideoResponse>> createVideo(@Valid @RequestBody VideoRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("video created", videoService.createVideo(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VideoResponse>> updateVideo(
            @PathVariable Long id,
            @Valid @RequestBody VideoRequest request) {
        return ResponseEntity.ok(ApiResponse.success("video updated", videoService.updateVideo(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.ok(ApiResponse.success("video deleted", null));
    }
}
