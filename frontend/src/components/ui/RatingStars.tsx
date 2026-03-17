import { Star } from 'lucide-react'

interface RatingStarsProps {
    rating: number
    maxRating?: number
    size?: 'sm' | 'md' | 'lg'
    interactive?: boolean
    onChange?: (rating: number) => void
}

export function RatingStars({
    rating,
    maxRating = 5,
    size = 'md',
    interactive = false,
    onChange
}: RatingStarsProps) {
    const normalizedRating = (rating / 10) * maxRating

    const sizes = {
        sm: 'w-3 h-3',
        md: 'w-4 h-4',
        lg: 'w-5 h-5'
    }

    const handleClick = (value: number) => {
        if (interactive && onChange) {
            onChange(value * 2)
        }
    }

    return (
        <div className="flex items-center gap-0.5">
            {Array.from({ length: maxRating }).map((_, i) => {
                const value = i + 1
                const filled = normalizedRating >= value
                const halfFilled = normalizedRating >= value - 0.5 && normalizedRating < value

                return (
                    <button
                        key={i}
                        type="button"
                        onClick={() => handleClick(value)}
                        disabled={!interactive}
                        className={`${interactive ? 'cursor-pointer hover:scale-110 transition-transform' : 'cursor-default'}`}
                    >
                        <Star
                            className={`${sizes[size]} ${filled
                                    ? 'text-yellow-500'
                                    : halfFilled
                                        ? 'text-yellow-500/50'
                                        : 'text-zinc-600'
                                }`}
                            fill={filled || halfFilled ? 'currentColor' : 'none'}
                        />
                    </button>
                )
            })}
        </div>
    )
}
