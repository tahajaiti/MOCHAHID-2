# MOCHAHID 2.0

Full-stack video streaming platform built with a Spring Boot microservices backend and a React + TypeScript frontend.

## Overview

This repository contains:

- `backend/`: Java 21 microservices (Spring Boot + Spring Cloud)
- `frontend/`: React 19 app (Vite + TypeScript + Tailwind + Zustand)
- `docker-compose.yml`: one-command local environment for all services and databases

## Architecture

```text
Frontend (React, Nginx)
        |
        v
Gateway Service (:8080)
   |                |
   v                v
Video Service    User Service
   |                |
   v                v
Video DB         User DB

+ Discovery Service (Eureka)
+ Config Service (Spring Cloud Config)
```

## Services and Ports

| Service | Port | Purpose |
|---|---|---|
| Frontend | `80` | Web UI |
| Gateway Service | `8080` | API entrypoint |
| Video Service | `8081` | Video catalog APIs |
| User Service | `8082` | Auth, watchlist, history, ratings |
| Config Service | `8888` | Centralized config |
| Discovery Service | `8761` | Service registry |
| Video DB (Postgres) | `5432` | Video data |
| User DB (Postgres) | `5433` | User data |

## Tech Stack

### Backend

- Java 21
- Spring Boot 4
- Spring Cloud (Gateway, Eureka, Config, OpenFeign)
- Gradle Kotlin DSL
- PostgreSQL 17

### Frontend

- React 19
- TypeScript
- Vite
- Tailwind CSS v4
- Zustand
- React Router v7

## Prerequisites

- Docker + Docker Compose
- (Optional for local dev without Docker)
  - Java 21
  - Node.js 18+ or Bun

## Quick Start (Recommended)

Run everything with Docker:

```bash
docker compose up --build
```

Then open:

- Frontend: `http://localhost`
- API Gateway: `http://localhost:8080`
- Eureka: `http://localhost:8761`
- Config Server: `http://localhost:8888`

## Environment Variables

Root `.env` file is used by Docker Compose:

- `POSTGRES_USER`
- `POSTGRES_PASSWORD`
- `VIDEO_DB_NAME`
- `USER_DB_NAME`
- `EUREKA_URI`
- `CONFIG_URI`
- `GATEWAY_HOST`
- `GATEWAY_PORT`

## Local Development (Without Docker)

### 1. Backend

From `backend/`, start services in this order:

```bash
./gradlew :discovery-service:bootRun
./gradlew :config-service:bootRun
./gradlew :gateway-service:bootRun
./gradlew :video-service:bootRun
./gradlew :user-service:bootRun
```

### 2. Frontend

From `frontend/`:

```bash
bun install
bun run dev
```

Frontend dev URL: `http://localhost:5173`

## Repository Structure

```text
.
├── backend/
│   ├── common/
│   ├── discovery-service/
│   ├── config-service/
│   ├── gateway-service/
│   ├── video-service/
│   ├── user-service/
│   └── config-repo/
├── frontend/
└── docker-compose.yml
```

## Notes

- Backend and frontend each include their own detailed README:
  - `backend/README.md`
  - `frontend/README.md`
- API should be consumed through the gateway (`:8080`) in normal usage.
