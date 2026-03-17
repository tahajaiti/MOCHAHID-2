import { Link } from 'react-router-dom'
import { Play, Plus, Check, Star, Info } from 'lucide-react'
import type { Video } from '../../types'
import { useAuthStore, useWatchlistStore } from '../../stores'
import { Button } from './Button'

interface HeroProps {
    video: Video
}

export function Hero({ video }: HeroProps) {
    const user = useAuthStore(state => state.currentUser)
    const { isInWatchlist, toggleWatchlist } = useWatchlistStore()
    const inList = user ? isInWatchlist(user.id, video.id) : false

    return (
        <div className="relative w-full h-[70vh] min-h-[500px] overflow-hidden">
            <div className="absolute inset-0">
                <img
                    src={video.thumbnailUrl}
                    alt={video.title}
                    className="w-full h-full object-cover"
                />
                <div className="absolute inset-0 bg-gradient-to-r from-zinc-900 via-zinc-900/80 to-transparent" />
                <div className="absolute inset-0 bg-gradient-to-t from-zinc-900 via-transparent to-zinc-900/30" />
            </div>

            <div className="relative h-full flex items-center">
                <div className="container mx-auto px-6 lg:px-8">
                    <div className="max-w-2xl">
                        <span className="inline-block px-3 py-1 bg-red-600 text-white text-xs font-medium rounded-full mb-4">
                            Featured
                        </span>
                        <h1 className="text-4xl md:text-5xl lg:text-6xl font-bold text-white mb-4">
                            {video.title}
                        </h1>
                        <div className="flex items-center gap-4 text-sm text-zinc-300 mb-4">
                            <span className="flex items-center gap-1">
                                <Star className="w-4 h-4 text-yellow-500" fill="currentColor" />
                                {video.rating}
                            </span>
                            <span>{video.releaseYear}</span>
                            <span>{video.duration} min</span>
                            <span className="px-2 py-0.5 bg-zinc-700 rounded text-xs">{video.type}</span>
                        </div>
                        <p className="text-zinc-300 text-base md:text-lg mb-6 line-clamp-3">
                            {video.description}
                        </p>
                        <div className="flex items-center gap-3">
                            <Link to={`/video/${video.id}`}>
                                <Button size="lg" className="gap-2">
                                    <Play className="w-5 h-5" fill="currentColor" />
                                    Watch Trailer
                                </Button>
                            </Link>
                            <Link to={`/video/${video.id}`}>
                                <Button variant="secondary" size="lg" className="gap-2">
                                    <Info className="w-5 h-5" />
                                    More Info
                                </Button>
                            </Link>
                            {user && (
                                <Button
                                    variant="ghost"
                                    size="lg"
                                    onClick={() => toggleWatchlist(user.id, video.id)}
                                    className="gap-2"
                                >
                                    {inList ? (
                                        <>
                                            <Check className="w-5 h-5 text-green-400" />
                                            In List
                                        </>
                                    ) : (
                                        <>
                                            <Plus className="w-5 h-5" />
                                            My List
                                        </>
                                    )}
                                </Button>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
