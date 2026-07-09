const BASE_URL = '/api'

async function request(url, options = {}) {
  const config = {
    headers: { 'Content-Type': 'application/json' },
    ...options
  }

  const response = await fetch(`${BASE_URL}${url}`, config)

  if (response.status === 204) return null

  const data = await response.json()

  if (!response.ok) {
    throw new Error(data.error || data.message || `HTTP ${response.status}`)
  }

  return data
}

export function get(url) {
  return request(url)
}

export function post(url, body) {
  return request(url, {
    method: 'POST',
    body: JSON.stringify(body)
  })
}

export function put(url, body) {
  return request(url, {
    method: 'PUT',
    body: JSON.stringify(body)
  })
}

export function patch(url, body) {
  return request(url, {
    method: 'PATCH',
    body: JSON.stringify(body)
  })
}

export function del(url) {
  return request(url, {
    method: 'DELETE'
  })
}
