package com.mochahid.userservice.client;

import com.mochahid.common.dto.ApiResponse;
import com.mochahid.userservice.dto.VideoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "video-service", fallback = VideoClientFallback.class)
public interface VideoClient {

    @GetMapping("/api/videos/{id}")
    ApiResponse<VideoDto> getVideoById(@PathVariable("id") Long id);
}
