import { get, post } from './index'

export const getApprovalFlowBySceneId = (sceneId, flowType) => {
  const params = flowType ? `?type=${flowType}` : ''
  return get(`/approval-flows/${sceneId}${params}`)
}
export const createApprovalFlow = (data) => post('/approval-flows', data)

export const getApprovalModifications = (sceneId, flowType = 'scene') => {
  return get(`/approval-flows/${sceneId}/modifications?type=${flowType}`)
}

export const saveApprovalModification = (sceneId, data) => {
  return post(`/approval-flows/${sceneId}/modifications`, data)
}

export const advanceApprovalStep = (sceneId, data) => {
  return post(`/approval-flows/${sceneId}/advance-step`, data)
}