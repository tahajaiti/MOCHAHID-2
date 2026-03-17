import { useState, useMemo } from 'react'
import { useVideos } from '../hooks'
import { VideoGrid, FilterBar, LoadingSpinner } from '../components/ui'
import type { FilterState } from '../types'

export function Browse() {
    const { filterVideos, loading } = useVideos()
    const [filters, setFilters] = useState<FilterState>({
        type: 'ALL',
        category: 'ALL',
        sortBy: 'views',
        searchQuery: ''
    })

    const filteredVideos = useMemo(() => filterVideos(filters), [filterVideos, filters])

    if (loading) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <LoadingSpinner size="lg" />
            </div>
        )
    }

    return (
        <div className="container mx-auto px-4 lg:px-8 py-8">
            <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 mb-8">
                <h1 className="text-2xl font-bold text-white">Browse</h1>
                <FilterBar filters={filters} onChange={setFilters} />
            </div>

            <VideoGrid
                videos={filteredVideos}
                emptyMessage="No videos match your filters"
            />
        </div>
    )
}
