import { useEffect } from 'react'
import { Outlet } from 'react-router-dom'
import { Navbar } from './Navbar'
import { useAuthStore, useWatchlistStore } from '../../stores'

export function Layout() {
    const currentUser = useAuthStore(state => state.currentUser)
    const isAuthenticated = useAuthStore(state => state.isAuthenticated)
    const { init, reset } = useWatchlistStore()

    useEffect(() => {
        if (isAuthenticated && currentUser) {
            init(currentUser.id)
        } else {
            reset()
        }
    }, [isAuthenticated, currentUser, init, reset])

    return (
        <div className="min-h-screen bg-zinc-900 dark:bg-zinc-900">
            <Navbar />
            <main className="pt-16">
                <Outlet />
            </main>
            <footer className="border-t border-zinc-800 py-8 mt-12">
                <div className="container mx-auto px-4 lg:px-8">
                    <div className="flex flex-col md:flex-row items-center justify-between gap-4">
                        <p className="text-sm text-zinc-500">
                            © {new Date().getFullYear()} MOCHAHID. All rights reserved.
                        </p>
                        <div className="flex items-center gap-6 text-sm text-zinc-500">
                            <a href="#" className="hover:text-white transition-colors">Privacy Policy</a>
                            <a href="#" className="hover:text-white transition-colors">Terms of Service</a>
                            <a href="#" className="hover:text-white transition-colors">Contact</a>
                        </div>
                    </div>
                </div>
            </footer>
        </div>
    )
}
