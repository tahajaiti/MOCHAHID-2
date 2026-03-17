import { create } from 'zustand'
import { api } from '../api'
import type { Video, Category } from '../types'

interface VideoState {
    videos: Video[]
    categories: Category[]
    loading: boolean
    error: string | null

    fetchAll: () => Promise<void>
}

export const useVideoStore = create<VideoState>()((set, get) => ({
    videos: [],
    categories: [],
    loading: false,
    error: null,

    fetchAll: async () => {
        if (get().loading || get().videos.length > 0) return
        set({ loading: true, error: null })
        try {
            const [videos, categories] = await Promise.all([
                api.get<Video[]>('/api/videos'),
                api.get<Category[]>('/api/categories')
            ])
            set({ videos, categories, loading: false })
        } catch (err) {
            set({ error: (err as Error).message, loading: false })
        }
    }
}))
