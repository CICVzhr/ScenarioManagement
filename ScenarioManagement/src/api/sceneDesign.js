import { get, post, put, patch } from './index'

export const getSceneDesigns = () => get('/scene-design')
export const getSceneDesignById = (id) => get(`/scene-design/${id}`)
export const createSceneDesign = (data) => post('/scene-design', data)
export const updateSceneDesign = (id, data) => put(`/scene-design/${id}`, data)
export const updateSceneDesignStatus = (id, status) => patch(`/scene-design/${id}/status`, { status })
export const updateSceneDesignModifyCount = (id, modifyCount) => patch(`/scene-design/${id}/modify-count`, { modifyCount })
export const submitSceneDesign = (id, status) => patch(`/scene-design/${id}/submit`, { status })