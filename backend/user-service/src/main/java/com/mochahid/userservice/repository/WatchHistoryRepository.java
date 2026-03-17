package com.mochahid.userservice.repository;

import com.mochahid.userservice.entity.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {

    List<WatchHistory> findByUserIdOrderByWatchedAtDesc(Long userId);

    Optional<WatchHistory> findByUserIdAndVideoId(Long userId, Long videoId);

    long countByUserId(Long userId);

    long countByUserIdAndCompletedTrue(Long userId);

    @Query("select coalesce(sum(h.progressTime), 0) from WatchHistory h where h.userId = :userId")
    int sumProgressTimeByUserId(@Param("userId") Long userId);

    void deleteByUserId(Long userId);
}
