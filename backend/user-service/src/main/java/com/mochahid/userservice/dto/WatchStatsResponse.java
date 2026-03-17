package com.mochahid.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchStatsResponse {

    private long totalWatched;
    private long completed;
    private int totalMinutes;
    private long watchlistCount;
}
