import { ref } from 'vue'

export function useApprovalFlow(flowDataSource) {
  const showApprovalDrawer = ref(false)
  const approvalScene = ref(null)
  const approvalFlowData = ref(null)

  const openApproval = (row) => {
    approvalScene.value = row
    approvalFlowData.value = flowDataSource[row.id] || null
    showApprovalDrawer.value = true
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