<template>
  <div class="page-container">
    <div class="page-toolbar">
      <el-input v-model="searchKeyword" placeholder="搜索用例..." class="search-input">
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <div class="filter-tags">
        <span class="filter-tag" :class="{ active: filterStatus === 'pending' }" @click="filterStatus = 'pending'">开发中</span>
        <span class="filter-tag" :class="{ active: filterStatus === 'processed' }" @click="filterStatus = 'processed'">已完成</span>
        <span class="filter-tag" :class="{ active: filterStatus === 'urgent' }" @click="filterStatus = 'urgent'">测试中</span>
        <span class="filter-tag" :class="{ active: filterStatus === 'error' }" @click="filterStatus = 'error'">已发布</span>
      </div>
    </div>

    <div class="scenario-list">
      <div class="list-header">
        <h3>用例开发列表</h3>
      </div>
      <CaseDevelopmentTable :table-data="filteredData" @view="handleView" @edit="handleEdit" @execute="handleExecute" @export="handleExport" @approval="openApproval" />
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
import CaseDevelopmentTable from '@/components/tables/CaseDevelopmentTable.vue'
import ApprovalFlowDrawer from '@/components/drawers/ApprovalFlowDrawer.vue'
import { usePageFilter } from '@/composables/usePageFilter'
import { useApprovalFlow } from '@/composables/useApprovalFlow'

const statusMap = {
  'pending': '开发中',
  'processed': '已完成',
  'urgent': '测试中',
  'error': '已发布'
}

const { searchKeyword, filterStatus, filteredData } = usePageFilter({
  stage: 'validate',
  statusMap
})

const { showApprovalDrawer, approvalScene, approvalFlowData, openApproval, closeApproval } = useApprovalFlow('case')

const handleView = (row) => {}

const handleEdit = (row) => {}

const handleExecute = (row) => {}

const handleExport = (row) => {}
</script>

<style lang="scss" scoped>
@use '@/styles/mixins.scss' as *;
</style>