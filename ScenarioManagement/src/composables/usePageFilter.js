import { ref, computed, onMounted } from 'vue'
import { getSceneDesigns } from '@/api/sceneDesign'

export function usePageFilter(options = {}) {
  const { stage, statusMap } = options

  const searchKeyword = ref('')
  const filterStatus = ref('')
  const scenarios = ref([])
  const loading = ref(false)

  const fetchData = async () => {
    loading.value = true
    try {
      scenarios.value = await getSceneDesigns()
    } catch (e) {
      console.error('Failed to load scene designs:', e)
    } finally {
      loading.value = false
    }
  }

  onMounted(fetchData)

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
    filteredData,
    loading,
    fetchData
  }
}
