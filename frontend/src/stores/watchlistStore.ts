import { create } from 'zustand'
import { api } from '../api'
import type { WatchlistItem, WatchHistoryItem, WatchStats } from '../types'

interface WatchlistState {
    watchlistIds: number[]
    initialized: boolean

    init: (userId: number) => Promise<void>
    reset: () => void
    isInWatchlist: (userId: number, videoId: number) => boolean
    addToWatchlist: (userId: number, videoId: number) => Promise<void>
    removeFromWatchlist: (userId: number, videoId: number) => Promise<void>
    toggleWatchlist: (userId: number, videoId: number) => Promise<void>
    getUserWatchlist: (userId: number) => Promise<WatchlistItem[]>
    getUserHistory: (userId: number) => Promise<WatchHistoryItem[]>
    addToHistory: (userId: number, videoId: number, progressTime?: number, completed?: boolean) => Promise<void>
    clearHistory: (userId: number) => Promise<void>
    getWatchStats: (userId: number) => Promise<WatchStats>
    rateVideo: (userId: number, videoId: number, rating: number) => Promise<void>
    getUserRating: (userId: number, videoId: number) => Promise<number>
}

export const useWatchlistStore = create<WatchlistState>()((set, get) => ({
    watchlistIds: [],
    initialized: false,

    init: async (userId) => {
        try {
            const items = await api.get<WatchlistItem[]>(`/api/users/${userId}/watchlist`)
            set({ watchlistIds: items.map(i => i.videoId), initialized: true })
        } catch {
            set({ watchlistIds: [], initialized: true })
        }
    },

    reset: () => {
        set({ watchlistIds: [], initialized: false })
    },

    isInWatchlist: (_userId, videoId) => {
        return get().watchlistIds.includes(videoId)
    },

    addToWatchlist: async (userId, videoId) => {
        set(state => ({ watchlistIds: [...state.watchlistIds, videoId] }))
        try {
            await api.post(`/api/users/${userId}/watchlist/${videoId}`)
        } catch {
            set(state => ({ watchlistIds: state.watchlistIds.filter(id => id !== videoId) }))
        }
    },

    removeFromWatchlist: async (userId, videoId) => {
        const prev = get().watchlistIds
        set({ watchlistIds: prev.filter(id => id !== videoId) })
        try {
            await api.delete(`/api/users/${userId}/watchlist/${videoId}`)
        } catch {
            set({ watchlistIds: prev })
        }
    },

    toggleWatchlist: async (userId, videoId) => {
        if (get().isInWatchlist(userId, videoId)) {
            await get().removeFromWatchlist(userId, videoId)
        } else {
            await get().addToWatchlist(userId, videoId)
        }
    },

    getUserWatchlist: async (userId) => {
        return api.get<WatchlistItem[]>(`/api/users/${userId}/watchlist`)
    },

    getUserHistory: async (userId) => {
        return api.get<WatchHistoryItem[]>(`/api/users/${userId}/history`)
    },

    addToHistory: async (userId, videoId, progressTime = 0, completed = false) => {
        try {
            await api.post(`/api/users/${userId}/history`, { videoId, progressTime, completed })
        } catch {
            // silent fail for history tracking
        }
    },

    clearHistory: async (userId) => {
        await api.delete(`/api/users/${userId}/history`)
    },

    getWatchStats: async (userId) => {
        return api.get<WatchStats>(`/api/users/${userId}/stats`)
    },

    rateVideo: async (userId, videoId, rating) => {
        await api.post(`/api/users/${userId}/ratings`, { videoId, rating })
    },

    getUserRating: async (userId, videoId) => {
        return api.get<number>(`/api/users/${userId}/ratings/${videoId}`)
    }
}))
