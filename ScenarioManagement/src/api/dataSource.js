import { get, post, del, patch } from './index'

export const getDataSources = () => get('/data-sources')
export const createDataSource = (data) => post('/data-sources', data)
export const deleteDataSource = (id) => del(`/data-sources/${id}`)
export const updateDataSourceStatus = (id, status) => patch(`/data-sources/${id}/status`, { status })
