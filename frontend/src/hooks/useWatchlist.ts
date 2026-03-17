import { useCallback } from 'react'
import { useWatchlistStore } from '../stores'

// thin wrapper around the watchlist store for convenience
export function useWatchlist(userId: number | null) {
    const store = useWatchlistStore()

    const isInWatchlist = useCallback((videoId: number): boolean => {
        if (!userId) return false
        return store.isInWatchlist(userId, videoId)
    }, [store, userId])

    const toggleWatchlist = useCallback((videoId: number) => {
        if (!userId) return
        store.toggleWatchlist(userId, videoId)
    }, [store, userId])

    return {
        isInWatchlist,
        toggleWatchlist
    }
}
