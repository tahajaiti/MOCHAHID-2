import { useState, useEffect } from 'react'
import { useParams, Link } from 'react-router-dom'
import { Play, Plus, Check, Star, Clock, Calendar, User, ArrowLeft } from 'lucide-react'
import { useVideos } from '../hooks'
import { useAuthStore, useWatchlistStore } from '../stores'
import { YouTubePlayer, RatingStars, VideoRow, Button, LoadingSpinner } from '../components/ui'

export function VideoDetails() {
    const { id } = useParams<{ id: string }>()
    const { getVideoById, getSimilarVideos, categories, loading } = useVideos()
    const user = useAuthStore(state => state.currentUser)
    const { isInWatchlist, toggleWatchlist, addToHistory, rateVideo, getUserRating } = useWatchlistStore()

    const [userRating, setUserRating] = useState<number>(0)

    const videoId = id ? Number(id) : undefined
    const video = videoId ? getVideoById(videoId) : undefined
    const similar = video ? getSimilarVideos(video) : []
    const inList = user && video ? isInWatchlist(user.id, video.id) : false

    useEffect(() => {
        if (user && videoId) {
            getUserRating(user.id, videoId).then(setUserRating).catch(() => setUserRating(0))
        }
    }, [user, videoId, getUserRating])

    if (loading) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <LoadingSpinner size="lg" />
            </div>
        )
    }

    if (!video) {
        return (
            <div className="container mx-auto px-4 lg:px-8 py-12">
                <div className="text-center">
                    <h1 className="text-2xl font-bold text-white mb-4">Video not found</h1>
                    <Link to="/browse" className="text-red-500 hover:underline">
                        Back to Browse
                    </Link>
                </div>
            </div>
        )
    }

    const category = categories.find(c => c.name === video.category)

    const handlePlay = () => {
        if (user) {
            addToHistory(user.id, video.id, 0, false)
        }
    }

    const handleRate = (rating: number) => {
        if (user) {
            rateVideo(user.id, video.id, rating)
            setUserRating(rating)
        }
    }

    return (
        <div className="pb-12">
            <div className="relative">
                <div className="absolute top-4 left-4 z-10">
                    <Link
                        to="/browse"
                        className="flex items-center gap-2 px-3 py-2 bg-zinc-900/80 rounded-lg text-zinc-300 hover:text-white transition-colors"
                    >
                        <ArrowLeft className="w-4 h-4" />
                        Back
                    </Link>
                </div>

                <div className="w-full" onClick={handlePlay}>
                    <YouTubePlayer url={video.trailerUrl} title={video.title} />
                </div>
            </div>

            <div className="container mx-auto px-4 lg:px-8 py-8">
                <div className="grid lg:grid-cols-3 gap-8">
                    <div className="lg:col-span-2 space-y-6">
                        <div>
                            <div className="flex items-center gap-3 mb-2">
                                <span className="px-2 py-1 bg-red-600 text-white text-xs font-medium rounded">
                                    {video.type}
                                </span>
                                {category && (
                                    <span className="px-2 py-1 bg-zinc-700 text-zinc-300 text-xs rounded">
                                        {category.label}
                                    </span>
                                )}
                            </div>
                            <h1 className="text-3xl md:text-4xl font-bold text-white">{video.title}</h1>
                        </div>

                        <div className="flex flex-wrap items-center gap-4 text-sm text-zinc-400">
                            <span className="flex items-center gap-1">
                                <Star className="w-4 h-4 text-yellow-500" fill="currentColor" />
                                {video.rating}/10
                            </span>
                            <span className="flex items-center gap-1">
                                <Calendar className="w-4 h-4" />
                                {video.releaseYear}
                            </span>
                            <span className="flex items-center gap-1">
                                <Clock className="w-4 h-4" />
                                {video.duration} min
                            </span>
                            <span className="flex items-center gap-1">
                                <Play className="w-4 h-4" />
                                {video.views.toLocaleString()} views
                            </span>
                        </div>

                        <div className="flex flex-wrap gap-3">
                            <Button onClick={handlePlay} className="gap-2">
                                <Play className="w-5 h-5" fill="currentColor" />
                                Watch Trailer
                            </Button>
                            {user && (
                                <Button
                                    variant="secondary"
                                    onClick={() => toggleWatchlist(user.id, video.id)}
                                    className="gap-2"
                                >
                                    {inList ? (
                                        <>
                                            <Check className="w-5 h-5 text-green-400" />
                                            In My List
                                        </>
                                    ) : (
                                        <>
                                            <Plus className="w-5 h-5" />
                                            Add to List
                                        </>
                                    )}
                                </Button>
                            )}
                        </div>

                        <div>
                            <h2 className="text-lg font-semibold text-white mb-2">Description</h2>
                            <p className="text-zinc-300 leading-relaxed">{video.description}</p>
                        </div>

                        {user && (
                            <div>
                                <h2 className="text-lg font-semibold text-white mb-2">Your Rating</h2>
                                <div className="flex items-center gap-3">
                                    <RatingStars
                                        rating={userRating || 0}
                                        size="lg"
                                        interactive
                                        onChange={handleRate}
                                    />
                                    <span className="text-sm text-zinc-400">
                                        {userRating ? `${userRating}/10` : 'Click to rate'}
                                    </span>
                                </div>
                            </div>
                        )}
                    </div>

                    <div className="space-y-6">
                        <div className="bg-zinc-800/50 rounded-xl p-5">
                            <h3 className="text-sm font-medium text-zinc-400 mb-3">Director</h3>
                            <div className="flex items-center gap-2">
                                <User className="w-4 h-4 text-zinc-500" />
                                <span className="text-white">{video.director}</span>
                            </div>
                        </div>

                        <div className="bg-zinc-800/50 rounded-xl p-5">
                            <h3 className="text-sm font-medium text-zinc-400 mb-3">Cast</h3>
                            <div className="flex flex-wrap gap-2">
                                {video.cast.map(actor => (
                                    <span
                                        key={actor}
                                        className="px-3 py-1 bg-zinc-700 text-zinc-300 text-sm rounded-full"
                                    >
                                        {actor}
                                    </span>
                                ))}
                            </div>
                        </div>
                    </div>
                </div>

                {similar.length > 0 && (
                    <div className="mt-12">
                        <VideoRow title="Similar Content" videos={similar} />
                    </div>
                )}
            </div>
        </div>
    )
}
