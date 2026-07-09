import { get, patch } from './index'

export const getProcessTasks = () => get('/process-tasks')
export const updateProcessTaskStatus = (id, status) => patch(`/process-tasks/${id}/status`, { status })
