import { useState, useRef, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { Search, X } from 'lucide-react'
import { useDebounce, useVideos } from '../../hooks'

export function SearchBar() {
    const [query, setQuery] = useState('')
    const [isOpen, setIsOpen] = useState(false)
    const debouncedQuery = useDebounce(query, 300)
    const { searchSuggestions } = useVideos()
    const suggestions = searchSuggestions(debouncedQuery)
    const containerRef = useRef<HTMLDivElement>(null)

    useEffect(() => {
        const handleClickOutside = (e: MouseEvent) => {
            if (containerRef.current && !containerRef.current.contains(e.target as Node)) {
                setIsOpen(false)
            }
        }
        document.addEventListener('mousedown', handleClickOutside)
        return () => document.removeEventListener('mousedown', handleClickOutside)
    }, [])

    const handleClear = () => {
        setQuery('')
        setIsOpen(false)
    }

    return (
        <div ref={containerRef} className="relative w-full max-w-md">
            <div className="relative">
                <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-zinc-400" />
                <input
                    type="text"
                    value={query}
                    onChange={e => {
                        setQuery(e.target.value)
                        setIsOpen(true)
                    }}
                    onFocus={() => setIsOpen(true)}
                    placeholder="Search movies, series..."
                    className="w-full pl-10 pr-10 py-2 bg-zinc-800/50 border border-zinc-700 rounded-full text-sm text-white placeholder-zinc-500 focus:outline-none focus:border-red-500 focus:bg-zinc-800 transition-colors"
                />
                {query && (
                    <button
                        onClick={handleClear}
                        className="absolute right-3 top-1/2 -translate-y-1/2 text-zinc-400 hover:text-white"
                    >
                        <X className="w-4 h-4" />
                    </button>
                )}
            </div>

            {isOpen && suggestions.length > 0 && (
                <div className="absolute top-full left-0 right-0 mt-2 bg-zinc-800 border border-zinc-700 rounded-lg overflow-hidden shadow-xl z-50">
                    {suggestions.map(video => (
                        <Link
                            key={video.id}
                            to={`/video/${video.id}`}
                            onClick={() => {
                                setIsOpen(false)
                                setQuery('')
                            }}
                            className="flex items-center gap-3 p-3 hover:bg-zinc-700 transition-colors"
                        >
                            <img
                                src={video.thumbnailUrl}
                                alt={video.title}
                                className="w-10 h-14 object-cover rounded"
                            />
                            <div>
                                <p className="text-sm font-medium text-white">{video.title}</p>
                                <p className="text-xs text-zinc-400">{video.type} • {video.releaseYear}</p>
                            </div>
                        </Link>
                    ))}
                </div>
            )}
        </div>
    )
}
