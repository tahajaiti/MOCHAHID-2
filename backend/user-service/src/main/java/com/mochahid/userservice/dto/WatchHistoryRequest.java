package com.mochahid.userservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchHistoryRequest {

    @NotNull(message = "video id is required")
    private Long videoId;

    @Min(0)
    private Integer progressTime;

    private Boolean completed;
}
