import { get, post, put, del } from './index'

export const getRoleList = () => {
  return get('/roles')
}

export const getEnabledRoleList = () => {
  return get('/roles/enabled')
}

export const getRoleById = (id) => {
  return get(`/roles/${id}`)
}

export const createRole = (data) => {
  return post('/roles', data)
}

export const updateRole = (id, data) => {
  return put(`/roles/${id}`, data)
}

export const deleteRole = (id) => {
  return del(`/roles/${id}`)
}

export const getRolePermissions = (id) => {
  return get(`/roles/${id}/permissions`)
}