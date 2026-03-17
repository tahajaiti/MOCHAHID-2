import type { FilterState, VideoType } from '../../types'
import { useVideoStore } from '../../stores'

interface FilterBarProps {
    filters: FilterState
    onChange: (filters: FilterState) => void
}

const videoTypes: { value: VideoType | 'ALL'; label: string }[] = [
    { value: 'ALL', label: 'All Types' },
    { value: 'FILM', label: 'Films' },
    { value: 'SERIES', label: 'Series' },
    { value: 'DOCUMENTARY', label: 'Documentaries' }
]

const sortOptions: { value: FilterState['sortBy']; label: string }[] = [
    { value: 'views', label: 'Most Popular' },
    { value: 'rating', label: 'Top Rated' },
    { value: 'recent', label: 'Most Recent' }
]

export function FilterBar({ filters, onChange }: FilterBarProps) {
    const categories = useVideoStore(state => state.categories)

    return (
        <div className="flex flex-wrap items-center gap-3">
            <select
                value={filters.type}
                onChange={e => onChange({ ...filters, type: e.target.value as VideoType | 'ALL' })}
                className="px-4 py-2 bg-zinc-800 border border-zinc-700 rounded-lg text-sm text-white focus:outline-none focus:border-red-500 cursor-pointer"
            >
                {videoTypes.map(type => (
                    <option key={type.value} value={type.value}>{type.label}</option>
                ))}
            </select>

            <select
                value={filters.category}
                onChange={e => onChange({ ...filters, category: e.target.value })}
                className="px-4 py-2 bg-zinc-800 border border-zinc-700 rounded-lg text-sm text-white focus:outline-none focus:border-red-500 cursor-pointer"
            >
                <option value="ALL">All Categories</option>
                {categories.map(cat => (
                    <option key={cat.name} value={cat.name}>{cat.label}</option>
                ))}
            </select>

            <select
                value={filters.sortBy}
                onChange={e => onChange({ ...filters, sortBy: e.target.value as FilterState['sortBy'] })}
                className="px-4 py-2 bg-zinc-800 border border-zinc-700 rounded-lg text-sm text-white focus:outline-none focus:border-red-500 cursor-pointer"
            >
                {sortOptions.map(opt => (
                    <option key={opt.value} value={opt.value}>{opt.label}</option>
                ))}
            </select>
        </div>
    )
}
