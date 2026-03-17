import { useRef } from 'react'
import { ChevronLeft, ChevronRight } from 'lucide-react'
import { VideoCard } from './VideoCard'
import type { Video } from '../../types'

interface VideoRowProps {
    title: string
    videos: Video[]
}

export function VideoRow({ title, videos }: VideoRowProps) {
    const scrollRef = useRef<HTMLDivElement>(null)

    const scroll = (direction: 'left' | 'right') => {
        if (!scrollRef.current) return
        const scrollAmount = scrollRef.current.offsetWidth * 0.75
        scrollRef.current.scrollBy({
            left: direction === 'left' ? -scrollAmount : scrollAmount,
            behavior: 'smooth'
        })
    }

    if (videos.length === 0) return null

    return (
        <div className="relative group/row">
            <h2 className="text-lg font-semibold text-white mb-3 px-1">{title}</h2>

            <button
                onClick={() => scroll('left')}
                className="absolute left-0 top-1/2 -translate-y-1/2 z-10 w-10 h-32 bg-zinc-900/80 flex items-center justify-center opacity-0 group-hover/row:opacity-100 transition-opacity"
            >
                <ChevronLeft className="w-6 h-6 text-white" />
            </button>

            <div
                ref={scrollRef}
                className="flex gap-3 overflow-x-auto scrollbar-hide scroll-smooth pb-2"
            >
                {videos.map(video => (
                    <VideoCard key={video.id} video={video} size="md" />
                ))}
            </div>

            <button
                onClick={() => scroll('right')}
                className="absolute right-0 top-1/2 -translate-y-1/2 z-10 w-10 h-32 bg-zinc-900/80 flex items-center justify-center opacity-0 group-hover/row:opacity-100 transition-opacity"
            >
                <ChevronRight className="w-6 h-6 text-white" />
            </button>
        </div>
    )
}
