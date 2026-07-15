import { get, put } from './index'

export const getSceneDesignDetail = (id) => get(`/scene-design-detail/${id}`)
export const saveSceneDesignDetail = (id, data) => put(`/scene-design-detail/${id}`, data)