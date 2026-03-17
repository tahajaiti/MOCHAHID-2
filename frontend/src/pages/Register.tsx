import { useState, type FormEvent } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { Eye, EyeOff } from 'lucide-react'
import { useAuthStore } from '../stores'
import { Button, Input } from '../components/ui'

interface FormErrors {
    username?: string
    email?: string
    password?: string
    confirmPassword?: string
    general?: string
}

export function Register() {
    const [username, setUsername] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const [showPassword, setShowPassword] = useState(false)
    const [errors, setErrors] = useState<FormErrors>({})
    const [isLoading, setIsLoading] = useState(false)

    const register = useAuthStore(state => state.register)
    const navigate = useNavigate()

    const validate = (): boolean => {
        const newErrors: FormErrors = {}

        if (!username.trim()) {
            newErrors.username = 'Username is required'
        } else if (username.length < 3) {
            newErrors.username = 'Username must be at least 3 characters'
        }

        if (!email.trim()) {
            newErrors.email = 'Email is required'
        } else if (!/\S+@\S+\.\S+/.test(email)) {
            newErrors.email = 'Invalid email format'
        }

        if (!password) {
            newErrors.password = 'Password is required'
        } else if (password.length < 6) {
            newErrors.password = 'Password must be at least 6 characters'
        }

        if (password !== confirmPassword) {
            newErrors.confirmPassword = 'Passwords do not match'
        }

        setErrors(newErrors)
        return Object.keys(newErrors).length === 0
    }

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault()
        if (!validate()) return

        setIsLoading(true)
        setErrors({})

        const result = await register(username, email, password)
        if (result.success) {
            navigate('/')
        } else {
            setErrors({ general: result.error })
        }
        setIsLoading(false)
    }

    return (
        <div className="min-h-screen flex items-center justify-center px-4 py-12 bg-zinc-900 dark:bg-zinc-900">
            <div className="w-full max-w-md">
                <div className="bg-zinc-800/50 border border-zinc-700 rounded-2xl p-8">
                    <div className="text-center mb-8">
                        <Link to="/" className="text-3xl font-bold text-red-500">MOCHAHID</Link>
                        <h1 className="text-xl font-semibold text-white mt-4">Create an account</h1>
                        <p className="text-zinc-400 mt-1">Start your streaming journey</p>
                    </div>

                    <form onSubmit={handleSubmit} className="space-y-5">
                        {errors.general && (
                            <div className="p-3 bg-red-500/10 border border-red-500/50 rounded-lg text-sm text-red-400">
                                {errors.general}
                            </div>
                        )}

                        <Input
                            type="text"
                            label="Username"
                            value={username}
                            onChange={e => setUsername(e.target.value)}
                            error={errors.username}
                            placeholder="Choose a username"
                            autoComplete="username"
                        />

                        <Input
                            type="email"
                            label="Email"
                            value={email}
                            onChange={e => setEmail(e.target.value)}
                            error={errors.email}
                            placeholder="Enter your email"
                            autoComplete="email"
                        />

                        <div className="relative">
                            <Input
                                type={showPassword ? 'text' : 'password'}
                                label="Password"
                                value={password}
                                onChange={e => setPassword(e.target.value)}
                                error={errors.password}
                                placeholder="Create a password"
                                autoComplete="new-password"
                            />
                            <button
                                type="button"
                                onClick={() => setShowPassword(!showPassword)}
                                className="absolute right-3 top-9 text-zinc-400 hover:text-white"
                            >
                                {showPassword ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
                            </button>
                        </div>

                        <Input
                            type={showPassword ? 'text' : 'password'}
                            label="Confirm Password"
                            value={confirmPassword}
                            onChange={e => setConfirmPassword(e.target.value)}
                            error={errors.confirmPassword}
                            placeholder="Confirm your password"
                            autoComplete="new-password"
                        />

                        <Button
                            type="submit"
                            disabled={isLoading}
                            className="w-full"
                            size="lg"
                        >
                            {isLoading ? 'Creating account...' : 'Create Account'}
                        </Button>
                    </form>

                    <p className="text-center text-sm text-zinc-400 mt-6">
                        Already have an account?{' '}
                        <Link to="/login" className="text-red-500 hover:text-red-400 font-medium">
                            Sign in
                        </Link>
                    </p>
                </div>
            </div>
        </div>
    )
}
