import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { User, Film, Clock, List, Calendar, Trash2, Play } from 'lucide-react'
import { useAuthStore, useWatchlistStore } from '../stores'
import { Button, LoadingSpinner } from '../components/ui'
import type { WatchHistoryItem, WatchStats } from '../types'

export function Profile() {
    const user = useAuthStore(state => state.currentUser)
    const { getWatchStats, getUserHistory, clearHistory } = useWatchlistStore()

    const [stats, setStats] = useState<WatchStats>({ totalWatched: 0, completed: 0, totalMinutes: 0, watchlistCount: 0 })
    const [history, setHistory] = useState<WatchHistoryItem[]>([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        if (!user) return
        setLoading(true)
        Promise.all([
            getWatchStats(user.id),
            getUserHistory(user.id)
        ]).then(([s, h]) => {
            setStats(s)
            setHistory(h)
        }).finally(() => setLoading(false))
    }, [user, getWatchStats, getUserHistory])

    if (!user) return null

    if (loading) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <LoadingSpinner size="lg" />
            </div>
        )
    }

    const recentHistory = history
        .sort((a, b) => new Date(b.watchedAt).getTime() - new Date(a.watchedAt).getTime())
        .slice(0, 5)

    const memberSince = new Date(user.createdAt).toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    })

    const handleClearHistory = async () => {
        await clearHistory(user.id)
        setHistory([])
        setStats(prev => ({ ...prev, totalWatched: 0, completed: 0, totalMinutes: 0 }))
    }

    return (
        <div className="container mx-auto px-4 lg:px-8 py-8">
            <div className="max-w-4xl mx-auto">
                <div className="bg-gradient-to-r from-red-600 to-orange-500 rounded-2xl p-8 mb-8">
                    <div className="flex items-center gap-6">
                        <div className="w-20 h-20 rounded-full bg-white/20 flex items-center justify-center">
                            <span className="text-3xl font-bold text-white">
                                {user.username.charAt(0).toUpperCase()}
                            </span>
                        </div>
                        <div>
                            <h1 className="text-2xl font-bold text-white">{user.username}</h1>
                            <p className="text-white/80">{user.email}</p>
                            <p className="text-white/60 text-sm mt-1 flex items-center gap-1">
                                <Calendar className="w-4 h-4" />
                                Member since {memberSince}
                            </p>
                        </div>
                    </div>
                </div>

                <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
                    <div className="bg-zinc-800/50 rounded-xl p-5">
                        <div className="flex items-center gap-3 mb-2">
                            <div className="p-2 bg-red-500/20 rounded-lg">
                                <Film className="w-5 h-5 text-red-500" />
                            </div>
                            <span className="text-sm text-zinc-400">Videos Watched</span>
                        </div>
                        <p className="text-2xl font-bold text-white">{stats.totalWatched}</p>
                    </div>

                    <div className="bg-zinc-800/50 rounded-xl p-5">
                        <div className="flex items-center gap-3 mb-2">
                            <div className="p-2 bg-green-500/20 rounded-lg">
                                <Play className="w-5 h-5 text-green-500" />
                            </div>
                            <span className="text-sm text-zinc-400">Completed</span>
                        </div>
                        <p className="text-2xl font-bold text-white">{stats.completed}</p>
                    </div>

                    <div className="bg-zinc-800/50 rounded-xl p-5">
                        <div className="flex items-center gap-3 mb-2">
                            <div className="p-2 bg-blue-500/20 rounded-lg">
                                <Clock className="w-5 h-5 text-blue-500" />
                            </div>
                            <span className="text-sm text-zinc-400">Watch Time</span>
                        </div>
                        <p className="text-2xl font-bold text-white">{stats.totalMinutes} min</p>
                    </div>

                    <div className="bg-zinc-800/50 rounded-xl p-5">
                        <div className="flex items-center gap-3 mb-2">
                            <div className="p-2 bg-purple-500/20 rounded-lg">
                                <List className="w-5 h-5 text-purple-500" />
                            </div>
                            <span className="text-sm text-zinc-400">In Watchlist</span>
                        </div>
                        <p className="text-2xl font-bold text-white">{stats.watchlistCount}</p>
                    </div>
                </div>

                <div className="bg-zinc-800/50 rounded-xl p-6">
                    <div className="flex items-center justify-between mb-6">
                        <h2 className="text-lg font-semibold text-white">Watch History</h2>
                        {history.length > 0 && (
                            <Button
                                variant="ghost"
                                size="sm"
                                onClick={handleClearHistory}
                                className="text-zinc-400 hover:text-red-400 gap-1"
                            >
                                <Trash2 className="w-4 h-4" />
                                Clear History
                            </Button>
                        )}
                    </div>

                    {recentHistory.length === 0 ? (
                        <div className="text-center py-8">
                            <User className="w-12 h-12 text-zinc-600 mx-auto mb-3" />
                            <p className="text-zinc-400">No watch history yet</p>
                            <Link to="/browse" className="text-red-500 text-sm hover:underline mt-2 inline-block">
                                Start watching
                            </Link>
                        </div>
                    ) : (
                        <div className="space-y-3">
                            {recentHistory.map((item) => {
                                const video = item.video
                                if (!video) return null
                                return (
                                    <Link
                                        key={video.id}
                                        to={`/video/${video.id}`}
                                        className="flex items-center gap-4 p-3 rounded-lg hover:bg-zinc-700/50 transition-colors"
                                    >
                                        <img
                                            src={video.thumbnailUrl}
                                            alt={video.title}
                                            className="w-16 h-24 object-cover rounded"
                                        />
                                        <div className="flex-1 min-w-0">
                                            <h3 className="font-medium text-white truncate">{video.title}</h3>
                                            <p className="text-sm text-zinc-400">{video.type} • {video.releaseYear}</p>
                                            <p className="text-xs text-zinc-500 mt-1">
                                                Watched {new Date(item.watchedAt).toLocaleDateString()}
                                                {item.completed && ' • Completed'}
                                            </p>
                                        </div>
                                    </Link>
                                )
                            })}
                        </div>
                    )}
                </div>
            </div>
        </div>
    )
}
