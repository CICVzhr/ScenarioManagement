import { get, post, put, del } from './index'

export const getDangerScenes = () => get('/danger-scenes')
export const getDangerSceneById = (id) => get(`/danger-scenes/${id}`)
export const createDangerScene = (data) => post('/danger-scenes', data)
export const updateDangerScene = (id, data) => put(`/danger-scenes/${id}`, data)
export const deleteDangerScene = (id) => del(`/danger-scenes/${id}`)
