import { useMemo, useCallback, useEffect } from 'react'
import { useVideoStore } from '../stores'
import type { Video, FilterState, VideoType } from '../types'

export function useVideos() {
    const { videos, categories, loading, error, fetchAll } = useVideoStore()

    useEffect(() => {
        fetchAll()
    }, [fetchAll])

    const getVideoById = useCallback((id: number): Video | undefined => {
        return videos.find(v => v.id === id)
    }, [videos])

    const getFeaturedVideos = useMemo(() => {
        return videos.filter(v => v.featured).slice(0, 5)
    }, [videos])

    const filterVideos = useCallback((filters: FilterState): Video[] => {
        let filtered = [...videos]

        if (filters.type !== 'ALL') {
            filtered = filtered.filter(v => v.type === filters.type)
        }

        if (filters.category !== 'ALL') {
            filtered = filtered.filter(v => v.category === filters.category)
        }

        if (filters.searchQuery) {
            const query = filters.searchQuery.toLowerCase()
            filtered = filtered.filter(v =>
                v.title.toLowerCase().includes(query) ||
                v.description.toLowerCase().includes(query) ||
                v.director.toLowerCase().includes(query) ||
                v.cast.some(c => c.toLowerCase().includes(query))
            )
        }

        switch (filters.sortBy) {
            case 'recent':
                filtered.sort((a, b) => b.releaseYear - a.releaseYear)
                break
            case 'rating':
                filtered.sort((a, b) => b.rating - a.rating)
                break
            case 'views':
                filtered.sort((a, b) => b.views - a.views)
                break
        }

        return filtered
    }, [videos])

    const getSimilarVideos = useCallback((video: Video, limit = 6): Video[] => {
        return videos
            .filter(v => v.id !== video.id && (v.category === video.category || v.type === video.type))
            .sort((a, b) => b.rating - a.rating)
            .slice(0, limit)
    }, [videos])

    const getVideosByType = useCallback((type: VideoType): Video[] => {
        return videos.filter(v => v.type === type)
    }, [videos])

    const searchSuggestions = useCallback((query: string, limit = 5): Video[] => {
        if (!query.trim()) return []
        const q = query.toLowerCase()
        return videos
            .filter(v => v.title.toLowerCase().includes(q))
            .slice(0, limit)
    }, [videos])

    return {
        videos,
        categories,
        loading,
        error,
        getVideoById,
        getFeaturedVideos,
        filterVideos,
        getSimilarVideos,
        getVideosByType,
        searchSuggestions
    }
}
