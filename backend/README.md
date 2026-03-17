# mochahid backend

microservices backend for the mochahid video streaming platform. built with spring boot 4.0.3, spring cloud 2025.0.1, java 21, and gradle kotlin dsl.

## architecture

```
                    ┌──────────────┐
                    │   frontend   │
                    │  (react/ts)  │
                    └──────┬───────┘
                           │
                    ┌──────▼───────┐
                    │   gateway    │
                    │   :8080      │
                    └──────┬───────┘
                           │
              ┌────────────┼────────────┐
              │                         │
       ┌──────▼───────┐         ┌──────▼───────┐
       │ video-service │         │ user-service  │
       │    :8081      │         │    :8082      │
       └──────┬───────┘         └──────┬───────┘
              │                         │
       ┌──────▼───────┐         ┌──────▼───────┐
       │   video-db    │         │   user-db     │
       │    :5432      │         │    :5433      │
       └──────────────┘         └──────────────┘

       ┌──────────────┐         ┌──────────────┐
       │  discovery    │         │    config     │
       │   :8761       │         │    :8888      │
       └──────────────┘         └──────────────┘
```

## modules

| module | port | description |
|---|---|---|
| **common** | - | shared entities, exceptions, api response wrappers |
| **discovery-service** | 8761 | eureka server for service registration |
| **config-service** | 8888 | centralized config via spring cloud config server |
| **gateway-service** | 8080 | api gateway with route definitions and cors |
| **video-service** | 8081 | video crud, categories, search, featured content |
| **user-service** | 8082 | auth, watchlist, watch history, ratings |

## tech stack

- java 21 (eclipse temurin)
- spring boot 4.0.3
- spring cloud 2025.0.1 (eureka, config, gateway, openfeign)
- postgresql 17
- mapstruct 1.6.3
- lombok
- gradle 9.4 (kotlin dsl)
- docker + docker compose

## prerequisites

- java 21
- gradle 9.x (or use the included wrapper)
- docker & docker compose

## running with docker compose

from the project root (where `docker-compose.yml` lives):

```bash
docker compose up --build
```

this starts everything: two postgres databases, eureka, config server, gateway, video-service, user-service, and the frontend.

| service | url |
|---|---|
| frontend | http://localhost |
| gateway api | http://localhost:8080 |
| eureka dashboard | http://localhost:8761 |
| config server | http://localhost:8888 |
| video-service (direct) | http://localhost:8081 |
| user-service (direct) | http://localhost:8082 |

## running locally (without docker)

### 1. start postgres databases

you need two postgres instances (or one with two databases):
- `mochahid_videos` on port 5432
- `mochahid_users` on port 5433

### 2. start services in order

```bash
# from backend/
./gradlew :discovery-service:bootRun
./gradlew :config-service:bootRun
./gradlew :gateway-service:bootRun
./gradlew :video-service:bootRun
./gradlew :user-service:bootRun
```

start discovery and config first, wait for them to be healthy, then start the rest.

## building

```bash
./gradlew build
```

runs compilation and tests for all modules.

```bash
./gradlew :video-service:test
./gradlew :user-service:test
```

## api overview

all endpoints are accessible through the gateway at `http://localhost:8080`.

### videos

| method | endpoint | description |
|---|---|---|
| GET | `/api/videos` | list videos (supports `type`, `category`, `sort` query params) |
| GET | `/api/videos/{id}` | get video by id |
| GET | `/api/videos/featured` | get featured videos |
| GET | `/api/videos/{id}/similar` | get similar videos |
| GET | `/api/videos/search?q=` | search videos |
| POST | `/api/videos` | create video |
| PUT | `/api/videos/{id}` | update video |
| DELETE | `/api/videos/{id}` | delete video |
| GET | `/api/categories` | list all categories |

### auth

| method | endpoint | description |
|---|---|---|
| POST | `/api/auth/register` | register new user |
| POST | `/api/auth/login` | login |

### users

| method | endpoint | description |
|---|---|---|
| GET | `/api/users/{id}` | get user profile |
| PUT | `/api/users/{id}` | update user profile |
| DELETE | `/api/users/{id}` | delete user |
| GET | `/api/users/{userId}/watchlist` | get user watchlist |
| POST | `/api/users/{userId}/watchlist/{videoId}` | add to watchlist |
| DELETE | `/api/users/{userId}/watchlist/{videoId}` | remove from watchlist |
| POST | `/api/users/{userId}/watchlist/{videoId}/toggle` | toggle watchlist item |
| GET | `/api/users/{userId}/watchlist/{videoId}/check` | check if in watchlist |
| GET | `/api/users/{userId}/history` | get watch history |
| POST | `/api/users/{userId}/history` | add to watch history |
| DELETE | `/api/users/{userId}/history` | clear watch history |
| GET | `/api/users/{userId}/stats` | get watch stats |
| POST | `/api/users/{userId}/ratings/{videoId}` | rate a video |
| GET | `/api/users/{userId}/ratings/{videoId}` | get user rating for video |

## project structure

```
backend/
├── build.gradle.kts          # root build config
├── settings.gradle.kts        # module includes
├── gradle.properties          # shared dependency versions
├── common/                    # shared library (entities, exceptions, responses)
├── config-repo/               # externalized yaml configs per service
├── config-service/            # spring cloud config server
├── discovery-service/         # eureka server
├── gateway-service/           # spring cloud gateway
├── video-service/             # video domain service
└── user-service/              # user domain service
```

## environment variables

configured via `.env` at the project root for docker compose:

| variable | default | description |
|---|---|---|
| `POSTGRES_USER` | postgres | database username |
| `POSTGRES_PASSWORD` | postgres | database password |
| `VIDEO_DB_NAME` | mochahid_videos | video service database name |
| `USER_DB_NAME` | mochahid_users | user service database name |
| `EUREKA_URI` | http://discovery-service:8761/eureka | eureka server url |
| `CONFIG_URI` | http://config-service:8888 | config server url |
