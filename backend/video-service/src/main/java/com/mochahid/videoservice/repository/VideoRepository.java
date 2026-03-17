package com.mochahid.videoservice.repository;

import com.mochahid.videoservice.entity.Category;
import com.mochahid.videoservice.entity.Video;
import com.mochahid.videoservice.entity.VideoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByType(VideoType type);

    List<Video> findByCategory(Category category);

    List<Video> findByFeaturedTrue();

    List<Video> findByTypeAndCategory(VideoType type, Category category);

    @Query("select v from Video v where " +
            "lower(v.title) like lower(concat('%', :query, '%')) or " +
            "lower(v.description) like lower(concat('%', :query, '%')) or " +
            "lower(v.director) like lower(concat('%', :query, '%'))")
    List<Video> search(@Param("query") String query);

    @Query("select v from Video v where v.id <> :videoId and (v.category = :category or v.type = :type) order by v.rating desc")
    List<Video> findSimilar(@Param("videoId") Long videoId, @Param("category") Category category, @Param("type") VideoType type);
}
