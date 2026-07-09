import { get, post, put, del, patch } from './index'

export const getAccidentDataList = () => get('/accident-data')
export const getAccidentDataById = (id) => get(`/accident-data/${id}`)
export const createAccidentData = (data) => post('/accident-data', data)
export const updateAccidentData = (id, data) => put(`/accident-data/${id}`, data)
export const deleteAccidentData = (id) => del(`/accident-data/${id}`)
export const updateAccidentDataStatus = (id, status) => patch(`/accident-data/${id}/status`, { status })