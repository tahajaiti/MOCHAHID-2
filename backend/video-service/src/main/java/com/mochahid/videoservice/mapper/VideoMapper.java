package com.mochahid.videoservice.mapper;

import com.mochahid.videoservice.dto.VideoRequest;
import com.mochahid.videoservice.dto.VideoResponse;
import com.mochahid.videoservice.entity.Video;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    Video toEntity(VideoRequest request);

    VideoResponse toResponse(Video video);

    List<VideoResponse> toResponseList(List<Video> videos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(VideoRequest request, @MappingTarget Video video);
}
