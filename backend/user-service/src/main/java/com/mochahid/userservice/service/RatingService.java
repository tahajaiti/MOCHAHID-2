package com.mochahid.userservice.service;

import com.mochahid.common.exception.ResourceNotFoundException;
import com.mochahid.userservice.dto.RatingRequest;
import com.mochahid.userservice.entity.UserRating;
import com.mochahid.userservice.repository.UserRatingRepository;
import com.mochahid.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RatingService {

    private final UserRatingRepository userRatingRepository;
    private final UserRepository userRepository;

    public Double getUserRating(Long userId, Long videoId) {
        return userRatingRepository.findByUserIdAndVideoId(userId, videoId)
                .map(UserRating::getRating)
                .orElse(null);
    }

    @Transactional
    public Double rateVideo(Long userId, RatingRequest request) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("user", userId);
        }

        Optional<UserRating> existing = userRatingRepository.findByUserIdAndVideoId(userId, request.getVideoId());

        UserRating rating;
        if (existing.isPresent()) {
            rating = existing.get();
            rating.setRating(request.getRating());
            rating.setRatedAt(LocalDateTime.now());
        } else {
            rating = UserRating.builder()
                    .userId(userId)
                    .videoId(request.getVideoId())
                    .rating(request.getRating())
                    .ratedAt(LocalDateTime.now())
                    .build();
        }

        return userRatingRepository.save(rating).getRating();
    }
}
