<template>
  <div class="page-container">
    <div class="page-toolbar">
      <el-input v-model="searchKeyword" placeholder="搜索用例..." class="search-input">
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <div class="filter-tags">
        <span class="filter-tag" :class="{ active: filterStatus === '设计中' }" @click="filterStatus = '设计中'">设计中</span>
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
import { getCaseDesigns } from '@/api/caseDesign'

const searchKeyword = ref('')
const filterStatus = ref('')
const caseDesigns = ref([])

const filteredData = computed(() => {
  return caseDesigns.value.filter(item => {
    const matchKeyword = !searchKeyword.value ||
      item.name.toLowerCase().includes(searchKeyword.value.toLowerCase()) ||
      item.id.toLowerCase().includes(searchKeyword.value.toLowerCase())
    const matchStatus = !filterStatus.value || item.status === filterStatus.value
    return matchKeyword && matchStatus
  })
})

const fetchData = async () => {
  try {
    caseDesigns.value = await getCaseDesigns()
  } catch (e) {
    console.error('Failed to load case designs:', e)
  }
}

onMounted(fetchData)

const { showApprovalDrawer, approvalScene, approvalFlowData, openApproval, closeApproval } = useApprovalFlow('case')

const handleView = (row) => {}

const handleEdit = (row) => {}

const handleDelete = (row) => {}
</script>

<style lang="scss" scoped>
@use '@/styles/mixins.scss' as *;
</style>