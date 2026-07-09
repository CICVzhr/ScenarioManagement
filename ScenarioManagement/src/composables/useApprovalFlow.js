import { ref } from 'vue'
import { getApprovalFlowBySceneId } from '@/api/approvalFlow'

export function useApprovalFlow(flowType) {
  const showApprovalDrawer = ref(false)
  const approvalScene = ref(null)
  const approvalFlowData = ref(null)

  const openApproval = async (row) => {
    approvalScene.value = row
    showApprovalDrawer.value = true
    try {
      const response = await getApprovalFlowBySceneId(row.id, flowType)
      // response contains { id, sceneId, flowType, currentStep, flowData (JSON string) }
      // Parse the flowData to get the steps array
      const flow = JSON.parse(response.flowData || '{}')
      approvalFlowData.value = {
        flowType: response.flowType,
        currentStep: response.currentStep,
        steps: flow.steps || []
      }
    } catch (e) {
      console.error('Failed to load approval flow:', e)
      approvalFlowData.value = null
    }
  }

  const closeApproval = () => {
    approvalScene.value = null
    approvalFlowData.value = null
  }

  return {
    showApprovalDrawer,
    approvalScene,
    approvalFlowData,
    openApproval,
    closeApproval
  }
}
