import { Link, useLocation, useNavigate } from 'react-router-dom'
import { Menu, X, User, LogOut } from 'lucide-react'
import { useState } from 'react'
import { useAuthStore } from '../../stores'
import { SearchBar, ThemeToggle } from '../ui'

export function Navbar() {
    const [isMenuOpen, setIsMenuOpen] = useState(false)
    const { currentUser, isAuthenticated, logout } = useAuthStore()
    const location = useLocation()
    const navigate = useNavigate()

    const handleLogout = () => {
        logout()
        navigate('/')
    }

    const navLinks = [
        { to: '/', label: 'Home' },
        { to: '/browse', label: 'Browse' },
        ...(isAuthenticated ? [{ to: '/watchlist', label: 'My List' }] : [])
    ]

    return (
        <nav className="fixed top-0 left-0 right-0 z-50 bg-zinc-900/95 backdrop-blur-sm border-b border-zinc-800 dark:bg-zinc-900/95 dark:border-zinc-800">
            <div className="container mx-auto px-4 lg:px-8">
                <div className="flex items-center justify-between h-16">
                    <div className="flex items-center gap-8">
                        <Link to="/" className="text-2xl font-bold text-red-500">
                            MOCHAHID
                        </Link>
                        <div className="hidden md:flex items-center gap-6">
                            {navLinks.map(link => (
                                <Link
                                    key={link.to}
                                    to={link.to}
                                    className={`text-sm font-medium transition-colors ${location.pathname === link.to
                                        ? 'text-white'
                                        : 'text-zinc-400 hover:text-white'
                                        }`}
                                >
                                    {link.label}
                                </Link>
                            ))}
                        </div>
                    </div>

                    <div className="hidden md:flex items-center gap-4">
                        <SearchBar />
                        <ThemeToggle />
                        {isAuthenticated ? (
                            <div className="flex items-center gap-3">
                                <Link
                                    to="/profile"
                                    className="flex items-center gap-2 px-3 py-1.5 rounded-lg hover:bg-zinc-800 transition-colors"
                                >
                                    <div className="w-8 h-8 rounded-full bg-gradient-to-br from-red-500 to-orange-500 flex items-center justify-center">
                                        <span className="text-sm font-medium text-white">
                                            {currentUser?.username.charAt(0).toUpperCase()}
                                        </span>
                                    </div>
                                    <span className="text-sm text-zinc-300">{currentUser?.username}</span>
                                </Link>
                                <button
                                    onClick={handleLogout}
                                    className="p-2 text-zinc-400 hover:text-white transition-colors"
                                >
                                    <LogOut className="w-5 h-5" />
                                </button>
                            </div>
                        ) : (
                            <div className="flex items-center gap-4">
                                <Link
                                    to="/login"
                                    className="px-5 py-2.5 text-sm font-medium text-zinc-300 hover:text-white transition-colors whitespace-nowrap"
                                >
                                    Sign In
                                </Link>
                                <Link
                                    to="/register"
                                    className="px-5 py-2.5 text-sm font-medium text-white bg-red-600 hover:bg-red-700 rounded-lg transition-colors whitespace-nowrap"
                                >
                                    Sign Up
                                </Link>
                            </div>
                        )}

                    </div>

                    <button
                        onClick={() => setIsMenuOpen(!isMenuOpen)}
                        className="md:hidden p-2 text-zinc-400 hover:text-white"
                    >
                        {isMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
                    </button>
                </div>

                {isMenuOpen && (
                    <div className="md:hidden py-4 border-t border-zinc-800">
                        <div className="flex flex-col gap-4">
                            <SearchBar />
                            {navLinks.map(link => (
                                <Link
                                    key={link.to}
                                    to={link.to}
                                    onClick={() => setIsMenuOpen(false)}
                                    className={`text-sm font-medium ${location.pathname === link.to
                                        ? 'text-white'
                                        : 'text-zinc-400'
                                        }`}
                                >
                                    {link.label}
                                </Link>
                            ))}
                            <div className="flex items-center gap-4 pt-4 border-t border-zinc-800">
                                <ThemeToggle />
                                {isAuthenticated ? (
                                    <>
                                        <Link
                                            to="/profile"
                                            onClick={() => setIsMenuOpen(false)}
                                            className="flex items-center gap-2 text-zinc-300"
                                        >
                                            <User className="w-5 h-5" />
                                            Profile
                                        </Link>
                                        <button
                                            onClick={() => {
                                                handleLogout()
                                                setIsMenuOpen(false)
                                            }}
                                            className="flex items-center gap-2 text-zinc-400"
                                        >
                                            <LogOut className="w-5 h-5" />
                                            Sign Out
                                        </button>
                                    </>
                                ) : (
                                    <>
                                        <Link
                                            to="/login"
                                            onClick={() => setIsMenuOpen(false)}
                                            className="text-zinc-300"
                                        >
                                            Sign In
                                        </Link>
                                        <Link
                                            to="/register"
                                            onClick={() => setIsMenuOpen(false)}
                                            className="text-red-500"
                                        >
                                            Sign Up
                                        </Link>
                                    </>
                                )}
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </nav>
    )
}
