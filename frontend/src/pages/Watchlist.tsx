import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { Trash2, Play, Clock, Plus } from 'lucide-react'
import { useAuthStore, useWatchlistStore } from '../stores'
import { Button, LoadingSpinner } from '../components/ui'
import type { WatchlistItem, WatchHistoryItem } from '../types'

export function Watchlist() {
    const user = useAuthStore(state => state.currentUser)
    const { removeFromWatchlist, getUserWatchlist, getUserHistory } = useWatchlistStore()
    const [watchlist, setWatchlist] = useState<WatchlistItem[]>([])
    const [history, setHistory] = useState<WatchHistoryItem[]>([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        if (!user) return
        setLoading(true)
        Promise.all([
            getUserWatchlist(user.id),
            getUserHistory(user.id)
        ]).then(([wl, hist]) => {
            setWatchlist(wl)
            setHistory(hist)
        }).finally(() => setLoading(false))
    }, [user, getUserWatchlist, getUserHistory])

    const handleRemove = async (videoId: number) => {
        if (!user) return
        await removeFromWatchlist(user.id, videoId)
        setWatchlist(prev => prev.filter(item => item.videoId !== videoId))
    }

    const getProgress = (videoId: number) => history.find(h => h.videoId === videoId)

    if (loading) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <LoadingSpinner size="lg" />
            </div>
        )
    }

    if (watchlist.length === 0) {
        return (
            <div className="container mx-auto px-4 lg:px-8 py-12">
                <h1 className="text-2xl font-bold text-white mb-8">My List</h1>
                <div className="flex flex-col items-center justify-center py-16 text-center">
                    <div className="w-16 h-16 rounded-full bg-zinc-800 flex items-center justify-center mb-4">
                        <Plus className="w-8 h-8 text-zinc-600" />
                    </div>
                    <h2 className="text-xl font-semibold text-white mb-2">Your list is empty</h2>
                    <p className="text-zinc-400 mb-6 max-w-md">
                        Start adding movies and series you want to watch later
                    </p>
                    <Link to="/browse">
                        <Button>Browse Content</Button>
                    </Link>
                </div>
            </div>
        )
    }

    return (
        <div className="container mx-auto px-4 lg:px-8 py-8">
            <div className="flex items-center justify-between mb-8">
                <h1 className="text-2xl font-bold text-white">My List</h1>
                <span className="text-sm text-zinc-400">{watchlist.length} items</span>
            </div>

            <div className="grid gap-4">
                {watchlist.map((item) => {
                    const video = item.video
                    if (!video || !user) return null
                    const progress = getProgress(video.id)

                    return (
                        <div
                            key={video.id}
                            className="flex items-center gap-4 p-4 bg-zinc-800/50 rounded-xl hover:bg-zinc-800 transition-colors"
                        >
                            <Link to={`/video/${video.id}`} className="flex-shrink-0">
                                <img
                                    src={video.thumbnailUrl}
                                    alt={video.title}
                                    className="w-24 h-36 object-cover rounded-lg"
                                />
                            </Link>

                            <div className="flex-1 min-w-0">
                                <Link to={`/video/${video.id}`}>
                                    <h3 className="text-lg font-medium text-white hover:text-red-400 transition-colors truncate">
                                        {video.title}
                                    </h3>
                                </Link>
                                <div className="flex items-center gap-3 mt-1 text-sm text-zinc-400">
                                    <span>{video.type}</span>
                                    <span>•</span>
                                    <span>{video.releaseYear}</span>
                                    <span>•</span>
                                    <span>{video.duration} min</span>
                                </div>
                                <p className="text-sm text-zinc-500 mt-2 line-clamp-2">
                                    {video.description}
                                </p>
                                <div className="flex items-center gap-4 mt-3">
                                    {progress && (
                                        <span className="flex items-center gap-1 text-xs text-zinc-400">
                                            <Clock className="w-3 h-3" />
                                            {progress.completed ? 'Watched' : `${progress.progressTime} min watched`}
                                        </span>
                                    )}
                                    <span className="text-xs text-zinc-500">
                                        Added {new Date(item.addedAt).toLocaleDateString()}
                                    </span>
                                </div>
                            </div>

                            <div className="flex items-center gap-2">
                                <Link to={`/video/${video.id}`}>
                                    <Button size="sm" className="gap-1">
                                        <Play className="w-4 h-4" fill="currentColor" />
                                        Watch
                                    </Button>
                                </Link>
                                <Button
                                    variant="ghost"
                                    size="sm"
                                    onClick={() => handleRemove(video.id)}
                                    className="text-zinc-400 hover:text-red-400"
                                >
                                    <Trash2 className="w-4 h-4" />
                                </Button>
                            </div>
                        </div>
                    )
                })}
            </div>
        </div>
    )
}
