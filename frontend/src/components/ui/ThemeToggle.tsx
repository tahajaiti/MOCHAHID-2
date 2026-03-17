import { Moon, Sun } from 'lucide-react'
import { useThemeStore } from '../../stores'

export function ThemeToggle() {
    const { theme, toggleTheme } = useThemeStore()

    return (
        <button
            onClick={toggleTheme}
            className="p-2 rounded-lg bg-zinc-800 hover:bg-zinc-700 transition-colors dark:bg-zinc-800 dark:hover:bg-zinc-700"
            aria-label={`Switch to ${theme === 'dark' ? 'light' : 'dark'} mode`}
        >
            {theme === 'dark' ? (
                <Sun className="w-5 h-5 text-yellow-500" />
            ) : (
                <Moon className="w-5 h-5 text-zinc-600" />
            )}
        </button>
    )
}
