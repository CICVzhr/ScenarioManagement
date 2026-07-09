import { get, patch } from './index'

export const getCaseDesigns = () => get('/case-design')
export const updateCaseDesignProgress = (id, progress) => patch(`/case-design/${id}/progress`, { progress })
