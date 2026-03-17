interface YouTubePlayerProps {
    url: string
    title?: string
}

export function YouTubePlayer({ url, title = 'Video player' }: YouTubePlayerProps) {
    return (
        <div className="relative w-full aspect-video rounded-lg overflow-hidden bg-zinc-900">
            <iframe
                src={`${url}?autoplay=0&rel=0&modestbranding=1`}
                title={title}
                className="absolute inset-0 w-full h-full"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowFullScreen
            />
        </div>
    )
}
