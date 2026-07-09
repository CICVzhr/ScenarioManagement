import { get, post } from './index'

export const getApprovalFlowBySceneId = (sceneId, flowType) => {
  const params = flowType ? `?type=${flowType}` : ''
  return get(`/approval-flows/${sceneId}${params}`)
}
export const createApprovalFlow = (data) => post('/approval-flows', data)
