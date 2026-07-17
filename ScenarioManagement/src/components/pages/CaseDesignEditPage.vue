<template>
  <div class="page-container">
    <div class="page-toolbar">
      <el-input v-model="searchKeyword" placeholder="搜索用例设计..." class="search-input">
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <div class="filter-tags">
        <span class="filter-tag" :class="{ active: filterStatus === '设计中' }" @click="filterStatus = '设计中'">设计中</span>
        <span class="filter-tag" :class="{ active: filterStatus === '审批中' }" @click="filterStatus = '审批中'">审批中</span>
        <span class="filter-tag" :class="{ active: filterStatus === '已完成' }" @click="filterStatus = '已完成'">已完成</span>
      </div>
    </div>

    <div class="scenario-list">
      <div class="list-header">
        <h3>用例设计列表</h3>
        <span class="list-count">共 {{ filteredData.length }} 条记录</span>
      </div>
      <CaseDesignTableForDesign
        v-if="!loading"
        :table-data="filteredData"
        @view="handleView"
        @approval="openApproval"
      />
      <div v-else class="loading-container">
        <el-icon class="is-loading" :size="24"><Loading /></el-icon>
        <span>加载中...</span>
      </div>
    </div>

    <CaseDesignEditDrawer
      :visible="showEditDrawer"
      :case-data="selectedCase"
      @update:visible="showEditDrawer = $event"
      @close="handleEditClose"
      @save="handleSave"
      @submit="handleSubmit"
    />

    <ApprovalFlowDrawer
      :visible="showApprovalDrawer"
      :scene-data="approvalCase"
      :flow-data="approvalFlowData"
      @update:visible="showApprovalDrawer = $event"
      @close="closeApproval"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { Search, Loading } from '@element-plus/icons-vue'
import CaseDesignTableForDesign from '@/components/tables/CaseDesignTableForDesign.vue'
import CaseDesignEditDrawer from '@/components/drawers/CaseDesignEditDrawer.vue'
import ApprovalFlowDrawer from '@/components/drawers/ApprovalFlowDrawer.vue'
import { getSceneDesignsByPhase, advanceInPhase } from '@/api/sceneDesign'
import { useApprovalFlow } from '@/composables/useApprovalFlow'
import { ElMessage } from 'element-plus'

const searchKeyword = ref('')
const filterStatus = ref('')
const caseDesigns = ref([])
const loading = ref(false)

const showEditDrawer = ref(false)
const showApprovalDrawer = ref(false)
const selectedCase = ref(null)

const {
  approvalCase,
  approvalFlowData,
  openApproval,
  closeApproval
} = useApprovalFlow('case-design')

const filteredData = computed(() => {
  return caseDesigns.value.filter(item => {
    const matchKeyword = !searchKeyword.value ||
      item.name?.toLowerCase().includes(searchKeyword.value.toLowerCase()) ||
      item.id?.toLowerCase().includes(searchKeyword.value.toLowerCase())
    const matchStatus = !filterStatus.value || item.status === filterStatus.value
    return matchKeyword && matchStatus
  })
})

const fetchData = async () => {
  loading.value = true
  try {
    const data = await getSceneDesignsByPhase('case_design')
    caseDesigns.value = data || []
  } catch (e) {
    console.error('Failed to load case designs:', e)
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const handleView = (row) => {
  selectedCase.value = row
  showEditDrawer.value = true
}

const handleEditClose = () => {
  selectedCase.value = null
}

const handleSave = (data) => {
  fetchData()
}

const handleSubmit = (result) => {
  fetchData()
}

onMounted(fetchData)

defineExpose({ fetchData })
</script>

<style lang="scss" scoped>
.page-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;
  overflow: hidden;
}

.page-toolbar {
  display: flex;
  gap: 16px;
  align-items: center;
  flex-shrink: 0;

  .search-input {
    width: 280px;
  }

  .filter-tags {
    display: flex;
    gap: 8px;
    flex-wrap: wrap;
  }

  .filter-tag {
    padding: 6px 14px;
    border-radius: 6px;
    font-size: 13px;
    cursor: pointer;
    background: #f3f4f6;
    color: #6b7280;
    transition: all 0.2s;

    &:hover {
      background: #e5e7eb;
    }

    &.active {
      background: #3b82f6;
      color: #fff;
    }
  }
}

.scenario-list {
  flex: 1;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  padding: 20px;

  .list-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;

    h3 {
      font-size: 16px;
      font-weight: 600;
      color: #1f2937;
      margin: 0;
    }

    .list-count {
      font-size: 13px;
      color: #9ca3af;
    }
  }
}

.loading-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 200px;
  gap: 12px;
  color: #9ca3af;
}
</style>