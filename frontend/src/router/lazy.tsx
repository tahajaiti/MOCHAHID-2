import { lazy } from 'react'

export const Home = lazy(() => import('../pages/Home').then(m => ({ default: m.Home })))
export const Browse = lazy(() => import('../pages/Browse').then(m => ({ default: m.Browse })))
export const Login = lazy(() => import('../pages/Login').then(m => ({ default: m.Login })))
export const Register = lazy(() => import('../pages/Register').then(m => ({ default: m.Register })))
export const VideoDetails = lazy(() => import('../pages/VideoDetails').then(m => ({ default: m.VideoDetails })))
export const Watchlist = lazy(() => import('../pages/Watchlist').then(m => ({ default: m.Watchlist })))
export const Profile = lazy(() => import('../pages/Profile').then(m => ({ default: m.Profile })))
