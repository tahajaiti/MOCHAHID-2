import { Link } from 'react-router-dom'
import { Play, Plus, Check, Star } from 'lucide-react'
import type { Video } from '../../types'
import { useAuthStore, useWatchlistStore } from '../../stores'

interface VideoCardProps {
    video: Video
    size?: 'sm' | 'md' | 'lg'
}

export function VideoCard({ video, size = 'md' }: VideoCardProps) {
    const user = useAuthStore(state => state.currentUser)
    const { isInWatchlist, toggleWatchlist } = useWatchlistStore()
    const inList = user ? isInWatchlist(user.id, video.id) : false

    const sizeClasses = {
        sm: 'w-40',
        md: 'w-48',
        lg: 'w-56'
    }

    const handleWatchlistClick = (e: React.MouseEvent) => {
        e.preventDefault()
        e.stopPropagation()
        if (user) toggleWatchlist(user.id, video.id)
    }

    return (
        <Link
            to={`/video/${video.id}`}
            className={`group relative flex-shrink-0 ${sizeClasses[size]}`}
        >
            <div className="relative aspect-[2/3] rounded-lg overflow-hidden bg-zinc-800">
                <img
                    src={video.thumbnailUrl}
                    alt={video.title}
                    className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105"
                    loading="lazy"
                />
                <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-transparent to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300" />

                <div className="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                    <div className="w-12 h-12 rounded-full bg-white/90 flex items-center justify-center">
                        <Play className="w-5 h-5 text-zinc-900 ml-0.5" fill="currentColor" />
                    </div>
                </div>

                {user && (
                    <button
                        onClick={handleWatchlistClick}
                        className="absolute top-2 right-2 w-8 h-8 rounded-full bg-zinc-900/80 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-all duration-300 hover:bg-zinc-800"
                    >
                        {inList ? (
                            <Check className="w-4 h-4 text-green-400" />
                        ) : (
                            <Plus className="w-4 h-4 text-white" />
                        )}
                    </button>
                )}

                <div className="absolute bottom-0 left-0 right-0 p-3 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                    <div className="flex items-center gap-1 text-xs text-zinc-300">
                        <Star className="w-3 h-3 text-yellow-500" fill="currentColor" />
                        <span>{video.rating}</span>
                        <span className="mx-1">•</span>
                        <span>{video.releaseYear}</span>
                    </div>
                </div>
            </div>

            <div className="mt-2">
                <h3 className="text-sm font-medium text-white truncate group-hover:text-red-400 transition-colors dark:text-white">
                    {video.title}
                </h3>
                <p className="text-xs text-zinc-500 mt-0.5">{video.type}</p>
            </div>
        </Link>
    )
}
