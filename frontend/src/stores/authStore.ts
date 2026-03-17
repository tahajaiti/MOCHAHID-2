import { create } from 'zustand'
import { persist } from 'zustand/middleware'
import { api } from '../api'
import type { User } from '../types'

interface AuthState {
    currentUser: User | null
    isAuthenticated: boolean
    login: (email: string, password: string) => Promise<{ success: boolean; error?: string }>
    register: (username: string, email: string, password: string) => Promise<{ success: boolean; error?: string }>
    logout: () => void
    updateUser: (updates: Partial<User>) => Promise<void>
}

export const useAuthStore = create<AuthState>()(
    persist(
        (set, get) => ({
            currentUser: null,
            isAuthenticated: false,

            login: async (email, password) => {
                try {
                    const user = await api.post<User>('/api/auth/login', { email, password })
                    set({ currentUser: user, isAuthenticated: true })
                    return { success: true }
                } catch (err) {
                    return { success: false, error: (err as Error).message }
                }
            },

            register: async (username, email, password) => {
                try {
                    const user = await api.post<User>('/api/auth/register', { username, email, password })
                    set({ currentUser: user, isAuthenticated: true })
                    return { success: true }
                } catch (err) {
                    return { success: false, error: (err as Error).message }
                }
            },

            logout: () => {
                set({ currentUser: null, isAuthenticated: false })
            },

            updateUser: async (updates) => {
                const { currentUser } = get()
                if (!currentUser) return
                try {
                    const updated = await api.put<User>(`/api/users/${currentUser.id}`, updates)
                    set({ currentUser: updated })
                } catch (err) {
                    console.error('failed to update user:', err)
                }
            }
        }),
        {
            name: 'mochahid_auth'
        }
    )
)
