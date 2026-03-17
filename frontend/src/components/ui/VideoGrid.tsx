import { VideoCard } from './VideoCard'
import type { Video } from '../../types'

interface VideoGridProps {
    videos: Video[]
    title?: string
    emptyMessage?: string
}

export function VideoGrid({ videos, title, emptyMessage = 'No videos found' }: VideoGridProps) {
    return (
        <div className="w-full">
            {title && (
                <h2 className="text-xl font-semibold text-white mb-4">{title}</h2>
            )}
            {videos.length === 0 ? (
                <div className="flex items-center justify-center py-12 text-zinc-500">
                    {emptyMessage}
                </div>
            ) : (
                <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 xl:grid-cols-6 gap-4">
                    {videos.map(video => (
                        <VideoCard key={video.id} video={video} />
                    ))}
                </div>
            )}
        </div>
    )
}
