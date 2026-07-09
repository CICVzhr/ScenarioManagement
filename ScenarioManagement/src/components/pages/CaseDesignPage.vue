<template>
  <div class="page-container">
    <div class="page-toolbar">
      <el-input v-model="searchKeyword" placeholder="搜索用例..." class="search-input">
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <div class="filter-tags">
        <span class="filter-tag" :class="{ active: filterStatus === '开发中' }" @click="filterStatus = '开发中'">开发中</span>
        <span class="filter-tag" :class="{ active: filterStatus === '已完成' }" @click="filterStatus = '已完成'">已完成</span>
      </div>
    </div>

    <div class="scenario-list">
      <div class="list-header">
        <h3>用例开发列表</h3>
      </div>
      <CaseDesignTable :table-data="filteredData" @view="handleView" @edit="handleEdit" @delete="handleDelete" @approval="openApproval" />
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
import { ref, computed, onMounted } from 'vue'
import { Search } from '@element-plus/icons-vue'
import CaseDesignTable from '@/components/tables/CaseDesignTable.vue'
import ApprovalFlowDrawer from '@/components/drawers/ApprovalFlowDrawer.vue'
import { useApprovalFlow } from '@/composables/useApprovalFlow'
import { getSceneDesigns } from '@/api/sceneDesign'

const sceneToCaseStatusMap = {
  '已完成': '开发中',
  '设计中': '待设计',
  '审核中': '待设计',
  '待验证': '待设计',
  '修改中': '待设计'
}

const searchKeyword = ref('')
const filterStatus = ref('')
const sceneDesigns = ref([])

const caseDesignList = computed(() => {
  return sceneDesigns.value
    .filter(item => item.stage === 'scenariocase')
    .map(item => ({
      ...item,
      caseStatus: sceneToCaseStatusMap[item.status] || '待设计'
    }))
    .filter(item => item.caseStatus !== '待设计')
})

const filteredData = computed(() => {
  return caseDesignList.value.filter(item => {
    const matchKeyword = !searchKeyword.value ||
      item.name.toLowerCase().includes(searchKeyword.value.toLowerCase()) ||
      item.id.toLowerCase().includes(searchKeyword.value.toLowerCase())
    const matchStatus = !filterStatus.value || item.caseStatus === filterStatus.value
    return matchKeyword && matchStatus
  })
})

onMounted(() => {
  getSceneDesigns().then(data => {
    sceneDesigns.value = data
  })
})

const { showApprovalDrawer, approvalScene, approvalFlowData, openApproval, closeApproval } = useApprovalFlow('case')

const handleView = (row) => {}

const handleEdit = (row) => {}

const handleDelete = (row) => {}
</script>

<style lang="scss" scoped>
@use '@/styles/mixins.scss' as *;
</style>