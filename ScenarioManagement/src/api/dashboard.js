import { get } from './index'

export const getDashboardStats = () => get('/dashboard/stats')
export const getProcessDistribution = () => get('/dashboard/process-distribution')
export const getDangerCategory = () => get('/dashboard/danger-category')
export const getRecentTasks = () => get('/dashboard/recent-tasks')
