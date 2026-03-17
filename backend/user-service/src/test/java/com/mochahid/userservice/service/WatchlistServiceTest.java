package com.mochahid.userservice.service;

import com.mochahid.common.exception.BadRequestException;
import com.mochahid.common.exception.ResourceNotFoundException;
import com.mochahid.userservice.client.VideoClient;
import com.mochahid.userservice.dto.WatchlistResponse;
import com.mochahid.userservice.entity.WatchlistItem;
import com.mochahid.userservice.repository.UserRepository;
import com.mochahid.userservice.repository.WatchlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WatchlistServiceTest {

    @Mock
    private WatchlistRepository watchlistRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VideoClient videoClient;

    @InjectMocks
    private WatchlistService watchlistService;

    @Test
    void getUserWatchlist_shouldReturnItems() {
        when(userRepository.existsById(1L)).thenReturn(true);

        WatchlistItem item = WatchlistItem.builder()
                .userId(1L).videoId(1L).addedAt(LocalDateTime.now()).build();
        item.setId(1L);

        when(watchlistRepository.findByUserIdOrderByAddedAtDesc(1L)).thenReturn(List.of(item));

        List<WatchlistResponse> result = watchlistService.getUserWatchlist(1L);

        assertThat(result).hasSize(1);
    }

    @Test
    void getUserWatchlist_shouldThrowWhenUserNotFound() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> watchlistService.getUserWatchlist(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void addToWatchlist_shouldAdd() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(watchlistRepository.existsByUserIdAndVideoId(1L, 1L)).thenReturn(false);

        WatchlistItem saved = WatchlistItem.builder()
                .userId(1L).videoId(1L).addedAt(LocalDateTime.now()).build();
        saved.setId(1L);

        when(watchlistRepository.save(any())).thenReturn(saved);

        WatchlistResponse result = watchlistService.addToWatchlist(1L, 1L);

        assertThat(result.getVideoId()).isEqualTo(1L);
    }

    @Test
    void addToWatchlist_shouldThrowWhenAlreadyExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(watchlistRepository.existsByUserIdAndVideoId(1L, 1L)).thenReturn(true);

        assertThatThrownBy(() -> watchlistService.addToWatchlist(1L, 1L))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void removeFromWatchlist_shouldDelete() {
        when(userRepository.existsById(1L)).thenReturn(true);

        watchlistService.removeFromWatchlist(1L, 1L);

        verify(watchlistRepository).deleteByUserIdAndVideoId(1L, 1L);
    }

    @Test
    void isInWatchlist_shouldReturnTrue() {
        when(watchlistRepository.existsByUserIdAndVideoId(1L, 1L)).thenReturn(true);

        assertThat(watchlistService.isInWatchlist(1L, 1L)).isTrue();
    }

    @Test
    void isInWatchlist_shouldReturnFalse() {
        when(watchlistRepository.existsByUserIdAndVideoId(1L, 2L)).thenReturn(false);

        assertThat(watchlistService.isInWatchlist(1L, 2L)).isFalse();
    }
}
