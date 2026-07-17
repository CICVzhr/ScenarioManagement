import { get, post, put, patch } from './index'

export const getSceneDesigns = () => get('/scene-design')
export const getSceneDesignsByPhase = (phase) => get(`/scene-design/phase/${phase}`)
export const getSceneDesignById = (id) => get(`/scene-design/${id}`)
export const getSceneDesignFullById = (id) => get(`/scene-design/${id}/full`)
export const getAllSceneDesignsFull = () => get('/scene-design/full')
export const createSceneDesign = (data) => post('/scene-design', data)
export const updateSceneDesign = (id, data) => put(`/scene-design/${id}`, data)
export const updateSceneDesignStatus = (id, status) => patch(`/scene-design/${id}/status`, { status })
export const updateSceneDesignModifyCount = (id, modifyCount) => patch(`/scene-design/${id}/modify-count`, { modifyCount })
export const submitSceneDesign = (id, status) => patch(`/scene-design/${id}/submit`, { status })
export const advanceInPhase = (id, action) => patch(`/scene-design/${id}/advance`, { action })