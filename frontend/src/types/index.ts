export type VideoType = 'FILM' | 'SERIES' | 'DOCUMENTARY'

export interface User {
    id: number
    username: string
    email: string
    avatarUrl?: string
    createdAt: string
}

export interface Category {
    name: string
    label: string
    slug: string
}

export interface Video {
    id: number
    title: string
    description: string
    thumbnailUrl: string
    trailerUrl: string
    duration: number
    releaseYear: number
    type: VideoType
    category: string
    rating: number
    director: string
    cast: string[]
    views: number
    featured?: boolean
    createdAt?: string
    updatedAt?: string
}

export interface WatchlistItem {
    id: number
    userId: number
    videoId: number
    addedAt: string
    video?: Video
}

export interface WatchHistoryItem {
    id: number
    userId: number
    videoId: number
    watchedAt: string
    progressTime: number
    completed: boolean
    video?: Video
}

export interface UserRating {
    id: number
    userId: number
    videoId: number
    rating: number
    ratedAt: string
}

export interface WatchStats {
    totalWatched: number
    completed: number
    totalMinutes: number
    watchlistCount: number
}

export interface AuthState {
    user: User | null
    isAuthenticated: boolean
}

export interface FilterState {
    type: VideoType | 'ALL'
    category: string
    sortBy: 'recent' | 'rating' | 'views'
    searchQuery: string
}

export type Theme = 'dark' | 'light'
