import { ref, computed } from 'vue'
import { scenarioList } from '@/data/mockData'

export function usePageFilter(options = {}) {
  const { stage, statusMap } = options

  const searchKeyword = ref('')
  const filterStatus = ref('')
  const scenarios = ref([...scenarioList])

  const filteredData = computed(() => {
    return scenarios.value.filter(item => {
      const matchKeyword = !searchKeyword.value ||
        item.name.toLowerCase().includes(searchKeyword.value.toLowerCase()) ||
        item.id.toLowerCase().includes(searchKeyword.value.toLowerCase())

      const matchStage = item.stage === stage
      const matchStatus = !filterStatus.value || item.status === statusMap[filterStatus.value]

      return matchKeyword && matchStage && matchStatus
    })
  })

  return {
    searchKeyword,
    filterStatus,
    scenarios,
    filteredData
  }
}