<template>
  <div class="page-container">
    <div class="page-toolbar">
      <el-input v-model="searchKeyword" placeholder="搜索场景..." class="search-input">
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <div class="filter-tags">
        <span class="filter-tag" :class="{ active: filterStatus === 'pending' }" @click="filterStatus = 'pending'">待验证</span>
        <span class="filter-tag" :class="{ active: filterStatus === 'processed' }" @click="filterStatus = 'processed'">验证通过</span>
        <span class="filter-tag" :class="{ active: filterStatus === 'error' }" @click="filterStatus = 'error'">验证失败</span>
      </div>
    </div>

    <div class="scenario-list">
      <div class="list-header">
        <h3>场景验证列表</h3>
      </div>
      <ScenarioValidationTable :table-data="filteredData" @view="handleView" @validate="handleValidate" @edit="handleEdit" @approval="openApproval" />
    </div>

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
import { Search } from '@element-plus/icons-vue'
import ScenarioValidationTable from '@/components/tables/ScenarioValidationTable.vue'
import ApprovalFlowDrawer from '@/components/drawers/ApprovalFlowDrawer.vue'
import { usePageFilter } from '@/composables/usePageFilter'
import { useApprovalFlow } from '@/composables/useApprovalFlow'
import { approvalFlowData as approvalFlowDataSource } from '@/data/mockData'

const statusMap = {
  'pending': '待验证',
  'processed': '验证通过',
  'error': '验证失败'
}

const { searchKeyword, filterStatus, filteredData } = usePageFilter({
  stage: 'dynamic',
  statusMap
})

const { showApprovalDrawer, approvalScene, approvalFlowData, openApproval, closeApproval } = useApprovalFlow(approvalFlowDataSource)

const handleView = (row) => {}

const handleValidate = (row) => {}

const handleEdit = (row) => {}
</script>

<style lang="scss" scoped>
@use '@/styles/mixins.scss' as *;
</style>