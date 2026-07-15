import { get } from './index'

export const getPermissionList = () => {
  return get('/permissions')
}

export const getPermissionTree = () => {
  return get('/permissions/tree')
}

export const getEnabledPermissions = () => {
  return get('/permissions/enabled')
}

export const getPermissionById = (id) => {
  return get(`/permissions/${id}`)
}