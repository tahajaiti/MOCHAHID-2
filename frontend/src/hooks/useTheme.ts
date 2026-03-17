import { useState, useEffect, useCallback } from 'react'
import { useLocalStorage } from './useLocalStorage'
import type { Theme } from '../types'

const THEME_KEY = 'streamix_theme'

export function useTheme() {
    const [storedTheme, setStoredTheme] = useLocalStorage<Theme>(THEME_KEY, 'dark')
    const [theme, setTheme] = useState<Theme>(storedTheme)

    useEffect(() => {
        const root = document.documentElement
        root.classList.remove('light', 'dark')
        root.classList.add(theme)
        setStoredTheme(theme)
    }, [theme, setStoredTheme])

    const toggleTheme = useCallback(() => {
        setTheme(prev => prev === 'dark' ? 'light' : 'dark')
    }, [])

    return { theme, setTheme, toggleTheme }
}
