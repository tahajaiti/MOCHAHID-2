package com.mochahid.videoservice.dto;

import com.mochahid.videoservice.entity.Category;
import com.mochahid.videoservice.entity.VideoType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoRequest {

    @NotBlank(message = "title is required")
    private String title;

    private String description;

    private String thumbnailUrl;

    private String trailerUrl;

    private Integer duration;

    private Integer releaseYear;

    @NotNull(message = "type is required")
    private VideoType type;

    @NotNull(message = "category is required")
    private Category category;

    @Min(value = 0, message = "rating must be >= 0")
    @Max(value = 10, message = "rating must be <= 10")
    private Double rating;

    private String director;

    private List<String> cast;

    private Integer views;

    private Boolean featured;
}
