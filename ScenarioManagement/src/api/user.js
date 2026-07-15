import { get, post, put, del } from './index'

export const login = (data) => {
  return post('/auth/login', data)
}

export const getUserList = () => {
  return get('/users')
}

export const getUserById = (id) => {
  return get(`/users/${id}`)
}

export const createUser = (data) => {
  return post('/users', data)
}

export const updateUser = (id, data) => {
  return put(`/users/${id}`, data)
}

export const deleteUser = (id) => {
  return del(`/users/${id}`)
}

export const getUserRoles = (id) => {
  return get(`/users/${id}/roles`)
}

export const checkUserPermission = (id, code) => {
  return get(`/users/${id}/check-permission`, { params: { code } })
}