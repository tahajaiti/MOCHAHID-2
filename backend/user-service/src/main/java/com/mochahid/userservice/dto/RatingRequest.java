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
public class RatingRequest {

    @NotNull(message = "video id is required")
    private Long videoId;

    @NotNull(message = "rating is required")
    @Min(value = 0, message = "rating must be >= 0")
    @Max(value = 10, message = "rating must be <= 10")
    private Double rating;
}
