package com.mochahid.userservice.controller;

import com.mochahid.common.dto.ApiResponse;
import com.mochahid.userservice.dto.RatingRequest;
import com.mochahid.userservice.dto.UserResponse;
import com.mochahid.userservice.dto.UserUpdateRequest;
import com.mochahid.userservice.dto.WatchHistoryRequest;
import com.mochahid.userservice.dto.WatchHistoryResponse;
import com.mochahid.userservice.dto.WatchStatsResponse;
import com.mochahid.userservice.dto.WatchlistResponse;
import com.mochahid.userservice.service.RatingService;
import com.mochahid.userservice.service.UserService;
import com.mochahid.userservice.service.WatchHistoryService;
import com.mochahid.userservice.service.WatchlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final WatchlistService watchlistService;
    private final WatchHistoryService watchHistoryService;
    private final RatingService ratingService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success(userService.getAllUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.getUserById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("user updated", userService.updateUser(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("user deleted", null));
    }

    @GetMapping("/{userId}/watchlist")
    public ResponseEntity<ApiResponse<List<WatchlistResponse>>> getWatchlist(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(watchlistService.getUserWatchlist(userId)));
    }

    @GetMapping("/{userId}/watchlist/check")
    public ResponseEntity<ApiResponse<Boolean>> isInWatchlist(
            @PathVariable Long userId,
            @RequestParam Long videoId) {
        return ResponseEntity.ok(ApiResponse.success(watchlistService.isInWatchlist(userId, videoId)));
    }

    @PostMapping("/{userId}/watchlist/{videoId}")
    public ResponseEntity<ApiResponse<WatchlistResponse>> addToWatchlist(
            @PathVariable Long userId,
            @PathVariable Long videoId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("added to watchlist", watchlistService.addToWatchlist(userId, videoId)));
    }

    @DeleteMapping("/{userId}/watchlist/{videoId}")
    public ResponseEntity<ApiResponse<Void>> removeFromWatchlist(
            @PathVariable Long userId,
            @PathVariable Long videoId) {
        watchlistService.removeFromWatchlist(userId, videoId);
        return ResponseEntity.ok(ApiResponse.success("removed from watchlist", null));
    }

    @PostMapping("/{userId}/watchlist/{videoId}/toggle")
    public ResponseEntity<ApiResponse<WatchlistResponse>> toggleWatchlist(
            @PathVariable Long userId,
            @PathVariable Long videoId) {
        WatchlistResponse response = watchlistService.toggleWatchlist(userId, videoId);
        String message = response != null ? "added to watchlist" : "removed from watchlist";
        return ResponseEntity.ok(ApiResponse.success(message, response));
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<ApiResponse<List<WatchHistoryResponse>>> getHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(watchHistoryService.getUserHistory(userId)));
    }

    @PostMapping("/{userId}/history")
    public ResponseEntity<ApiResponse<WatchHistoryResponse>> addToHistory(
            @PathVariable Long userId,
            @Valid @RequestBody WatchHistoryRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("added to history", watchHistoryService.addToHistory(userId, request)));
    }

    @DeleteMapping("/{userId}/history")
    public ResponseEntity<ApiResponse<Void>> clearHistory(@PathVariable Long userId) {
        watchHistoryService.clearHistory(userId);
        return ResponseEntity.ok(ApiResponse.success("history cleared", null));
    }

    @GetMapping("/{userId}/stats")
    public ResponseEntity<ApiResponse<WatchStatsResponse>> getWatchStats(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(watchHistoryService.getWatchStats(userId)));
    }

    @PostMapping("/{userId}/ratings")
    public ResponseEntity<ApiResponse<Double>> rateVideo(
            @PathVariable Long userId,
            @Valid @RequestBody RatingRequest request) {
        return ResponseEntity.ok(ApiResponse.success("video rated", ratingService.rateVideo(userId, request)));
    }

    @GetMapping("/{userId}/ratings/{videoId}")
    public ResponseEntity<ApiResponse<Double>> getUserRating(
            @PathVariable Long userId,
            @PathVariable Long videoId) {
        return ResponseEntity.ok(ApiResponse.success(ratingService.getUserRating(userId, videoId)));
    }
}
