package com.mochahid.userservice.repository;

import com.mochahid.userservice.entity.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating, Long> {

    Optional<UserRating> findByUserIdAndVideoId(Long userId, Long videoId);
}
