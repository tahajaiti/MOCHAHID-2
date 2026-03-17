package com.mochahid.videoservice.entity;

import com.mochahid.common.entity.BaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "videos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "trailer_url")
    private String trailerUrl;

    private Integer duration;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VideoType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    private Double rating;

    private String director;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "video_cast", joinColumns = @JoinColumn(name = "video_id"))
    @Column(name = "actor")
    @Builder.Default
    private List<String> cast = new ArrayList<>();

    @Builder.Default
    private Integer views = 0;

    @Builder.Default
    private Boolean featured = false;
}
