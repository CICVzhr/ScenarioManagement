const BASE_URL = '/api'

async function request(url, options = {}) {
  const token = localStorage.getItem('token')
  const { params, ...fetchOptions } = options

  let fullUrl = `${BASE_URL}${url}`
  if (params) {
    const searchParams = new URLSearchParams(params)
    fullUrl += `?${searchParams.toString()}`
  }

  const config = {
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { 'Authorization': `Bearer ${token}` } : {})
    },
    ...fetchOptions
  }

  if (config.headers) {
    config.headers = { ...config.headers }
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
  }

  const response = await fetch(fullUrl, config)

  if (response.status === 204) return null

  const text = await response.text()
  if (!text) {
    if (!response.ok) {
      throw new Error(`HTTP ${response.status}`)
    }
    return null
  }

  let data
  try {
    data = JSON.parse(text)
  } catch {
    throw new Error(text || `HTTP ${response.status}`)
  }

  if (!response.ok) {
    throw new Error(data.error || data.message || `HTTP ${response.status}`)
  }

  return data
}

export function get(url, options = {}) {
  return request(url, options)
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

export default request