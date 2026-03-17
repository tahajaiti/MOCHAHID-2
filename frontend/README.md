# MOCHAHID - Video Streaming Platform

A modern video streaming platform frontend built with React, TypeScript, Tailwind CSS v4, and Zustand.

## Features

- **Authentication** - User registration and login with form validation
- **Catalog** - Browse movies, series, and documentaries with filtering and sorting
- **Search** - Real-time search with suggestions
- **Video Details** - YouTube trailer player, ratings, and similar content
- **Watchlist** - Save videos to your personal list
- **User Profile** - View watch statistics and history
- **Dark/Light Mode** - Theme toggle with persistence
- **Responsive Design** - Mobile-first responsive layout

## Tech Stack

- React 19
- TypeScript
- Tailwind CSS v4
- Zustand (State Management)
- React Router v7
- Lucide Icons
- Vite

## Getting Started

### Prerequisites

- Node.js 18+ or Bun
- npm, yarn, or bun

### Installation

```bash
# Clone the repository
git clone https://github.com/yourusername/mochahid.git
cd mochahid

# Install dependencies
bun install

# Start development server
bun run dev
```

Open [http://localhost:5173](http://localhost:5173) in your browser.

### Build

```bash
bun run build
```

## Project Structure

```
src/
├── components/
│   ├── auth/          # Authentication components
│   ├── layout/        # Layout components (Navbar, Layout)
│   └── ui/            # Reusable UI components
├── data/              # Mock data (videos, categories)
├── hooks/             # Custom hooks
├── pages/             # Page components
├── router/            # Route configuration with lazy loading
├── stores/            # Zustand stores (auth, theme, watchlist)
└── types/             # TypeScript type definitions
```

## State Management

Using Zustand with persist middleware for:
- `authStore` - User authentication (login, register, logout)
- `themeStore` - Dark/light theme preference
- `watchlistStore` - Watchlist, history, and ratings

All data is persisted to localStorage with keys:
- `mochahid_auth`
- `mochahid_theme`
- `mochahid_watchlist`

## License

MIT
