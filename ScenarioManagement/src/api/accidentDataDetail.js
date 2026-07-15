import { get, put } from './index'

export const getAccidentDataDetail = (id) => get(`/accident-data-detail/${id}`)
export const saveAccidentDataDetail = (id, data) => put(`/accident-data-detail/${id}`, data)