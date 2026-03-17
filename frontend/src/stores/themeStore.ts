import { create } from 'zustand'
import { persist } from 'zustand/middleware'
import type { Theme } from '../types'

interface ThemeState {
    theme: Theme
    setTheme: (theme: Theme) => void
    toggleTheme: () => void
}

export const useThemeStore = create<ThemeState>()(
    persist(
        (set) => ({
            theme: 'dark',

            setTheme: (theme) => {
                set({ theme })
                applyTheme(theme)
            },

            toggleTheme: () => {
                set(state => {
                    const newTheme = state.theme === 'dark' ? 'light' : 'dark'
                    applyTheme(newTheme)
                    return { theme: newTheme }
                })
            }
        }),
        {
            name: 'mochahid_theme',
            onRehydrateStorage: () => (state) => {
                if (state) {
                    applyTheme(state.theme)
                }
            }
        }
    )
)

function applyTheme(theme: Theme) {
    const root = document.documentElement
    root.classList.remove('light', 'dark')
    root.classList.add(theme)
}
