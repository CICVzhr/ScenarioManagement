<template>
  <div class="page-container">
    <div class="page-toolbar">
      <el-input v-model="searchKeyword" placeholder="搜索场景..." class="search-input">
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <div class="filter-tags">
        <span class="filter-tag" :class="{ active: filterStatus === 'pending' }" @click="filterStatus = 'pending'">设计中</span>
        <span class="filter-tag" :class="{ active: filterStatus === 'processed' }" @click="filterStatus = 'processed'">审核中</span>
        <span class="filter-tag" :class="{ active: filterStatus === 'urgent' }" @click="filterStatus = 'urgent'">待验证</span>
        <span class="filter-tag" :class="{ active: filterStatus === 'error' }" @click="filterStatus = 'error'">修改中</span>
        <span class="filter-tag" :class="{ active: filterStatus === 'completed' }" @click="filterStatus = 'completed'">已完成</span>
      </div>
    </div>

    <div class="scenario-list">
      <div class="list-header">
        <h3>场景设计列表</h3>
      </div>
      <ScenarioDesignTable :table-data="filteredData" @edit="handleEdit" @approval="openApproval" />
    </div>

    <SceneEditDrawer
      :visible="showEditDrawer"
      :scene-data="selectedScene"
      :data-drawer-visible="showDataDrawer"
      @update:visible="showEditDrawer = $event"
      @close="handleEditClose"
      @save="handleEditSave"
      @approve="handleApprove"
      @view-data="handleViewData"
    />

    <SceneDetailDrawer
      :visible="showDetailDrawer"
      :scene-data="detailScene"
      @update:visible="showDetailDrawer = $event"
      @close="handleDetailClose"
    />

    <CaseDetailDrawer
      :visible="showDataDrawer"
      :case-data="dataDrawerRow"
      @update:visible="showDataDrawer = $event"
      @close="handleDataDrawerClose"
    />

    <ApprovalFlowDrawer
      :visible="showApprovalDrawer"
      :scene-data="approvalScene"
      :flow-data="approvalFlowData"
      @update:visible="showApprovalDrawer = $event"
      @close="closeApproval"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import ScenarioDesignTable from '@/components/tables/ScenarioDesignTable.vue'
import SceneEditDrawer from '@/components/drawers/SceneEditDrawer.vue'
import SceneDetailDrawer from '@/components/drawers/SceneDetailDrawer.vue'
import CaseDetailDrawer from '@/components/drawers/CaseDetailDrawer.vue'
import ApprovalFlowDrawer from '@/components/drawers/ApprovalFlowDrawer.vue'
import { usePageFilter } from '@/composables/usePageFilter'
import { useApprovalFlow } from '@/composables/useApprovalFlow'
import { approvalFlowData as approvalFlowDataSource } from '@/data/mockData'

const statusMap = {
  'pending': '设计中',
  'processed': '审核中',
  'urgent': '待验证',
  'error': '修改中',
  'completed': '已完成'
}

const { searchKeyword, filterStatus, scenarios, filteredData } = usePageFilter({
  stage: 'scenariocase',
  statusMap
})

const { showApprovalDrawer, approvalScene, approvalFlowData, openApproval, closeApproval } = useApprovalFlow(approvalFlowDataSource)

const showEditDrawer = ref(false)
const showDetailDrawer = ref(false)
const showDataDrawer = ref(false)
const selectedScene = ref(null)
const detailScene = ref(null)
const dataDrawerRow = ref(null)

const handleEdit = (row) => {
  selectedScene.value = row
  showDataDrawer.value = false
  dataDrawerRow.value = null
  showEditDrawer.value = true
}

const handleEditClose = () => {
  selectedScene.value = null
}

const handleDetailClose = () => {
  detailScene.value = null
}

const handleViewData = (data) => {
  dataDrawerRow.value = data
  showDataDrawer.value = true
}

const handleDataDrawerClose = () => {
  dataDrawerRow.value = null
}

const handleEditSave = (data) => {
  const index = scenarios.value.findIndex(item => item.id === data.id)
  if (index !== -1) {
    scenarios.value[index] = { ...scenarios.value[index], ...data }
  }
  selectedScene.value = null
}

const handleApprove = (data) => {
  const index = scenarios.value.findIndex(item => item.id === data.id)
  if (index !== -1) {
    scenarios.value[index] = { ...scenarios.value[index], ...data, status: '已完成' }
  }
  selectedScene.value = null
  showEditDrawer.value = false
}
</script>

<style lang="scss" scoped>
@use '@/styles/mixins.scss' as *;
</style>