package com.mochahid.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistResponse {

    private Long id;
    private Long userId;
    private Long videoId;
    private LocalDateTime addedAt;
    private VideoDto video;
}
