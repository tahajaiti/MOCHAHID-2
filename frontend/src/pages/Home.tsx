import { useMemo, useState, useEffect } from 'react'
import { useVideos } from '../hooks'
import { Hero, VideoRow, LoadingSpinner } from '../components/ui'

export function Home() {
    const { getFeaturedVideos, getVideosByType, videos, loading } = useVideos()
    const [currentFeatured, setCurrentFeatured] = useState(0)

    const featured = getFeaturedVideos
    const films = useMemo(() => getVideosByType('FILM').slice(0, 10), [getVideosByType])
    const series = useMemo(() => getVideosByType('SERIES').slice(0, 10), [getVideosByType])
    const docs = useMemo(() => getVideosByType('DOCUMENTARY').slice(0, 10), [getVideosByType])
    const topRated = useMemo(() => [...videos].sort((a, b) => b.rating - a.rating).slice(0, 10), [videos])
    const trending = useMemo(() => [...videos].sort((a, b) => b.views - a.views).slice(0, 10), [videos])

    useEffect(() => {
        if (featured.length <= 1) return
        const interval = setInterval(() => {
            setCurrentFeatured(prev => (prev + 1) % featured.length)
        }, 8000)
        return () => clearInterval(interval)
    }, [featured.length])

    const currentHero = featured[currentFeatured]

    if (loading) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <LoadingSpinner size="lg" />
            </div>
        )
    }

    return (
        <div className="pb-12">
            {currentHero && <Hero video={currentHero} />}

            <div className="container mx-auto px-4 lg:px-8 -mt-20 relative z-10 space-y-8">
                <VideoRow title="Trending Now" videos={trending} />
                <VideoRow title="Top Rated" videos={topRated} />
                <VideoRow title="Films" videos={films} />
                <VideoRow title="Series" videos={series} />
                <VideoRow title="Documentaries" videos={docs} />
            </div>
        </div>
    )
}
