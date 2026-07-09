import { get, put } from './index'

export const getSystemSetting = (key) => get(`/system-settings/${key}`)
export const updateSystemSetting = (key, value) => put(`/system-settings/${key}`, { value })
