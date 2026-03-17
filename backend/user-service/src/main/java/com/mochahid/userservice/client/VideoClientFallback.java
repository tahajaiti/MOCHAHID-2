package com.mochahid.userservice.client;

import com.mochahid.common.dto.ApiResponse;
import com.mochahid.userservice.dto.VideoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VideoClientFallback implements VideoClient {

    @Override
    public ApiResponse<VideoDto> getVideoById(Long id) {
        log.warn("fallback: video-service unavailable for video id {}", id);
        return ApiResponse.success(null);
    }
}
