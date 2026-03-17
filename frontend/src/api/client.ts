interface ApiResponse<T> {
    success: boolean
    message?: string
    data: T
}

async function request<T>(path: string, options?: RequestInit): Promise<T> {
    const res = await fetch(path, {
        headers: { 'Content-Type': 'application/json' },
        ...options
    })

    const json: ApiResponse<T> = await res.json().catch(() => ({
        success: false,
        message: 'network error',
        data: null as T
    }))

    if (!json.success) throw new Error(json.message || 'request failed')
    return json.data
}

export const api = {
    get: <T>(path: string) => request<T>(path),

    post: <T>(path: string, body?: unknown) =>
        request<T>(path, {
            method: 'POST',
            body: body ? JSON.stringify(body) : undefined
        }),

    put: <T>(path: string, body?: unknown) =>
        request<T>(path, {
            method: 'PUT',
            body: body ? JSON.stringify(body) : undefined
        }),

    delete: <T>(path: string) =>
        request<T>(path, { method: 'DELETE' })
}
