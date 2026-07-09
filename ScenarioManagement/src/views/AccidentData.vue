<template>
  <div class="accident-data">
    <div class="page-header">
      <h2>主机厂内部事故事件</h2>
      <ActionButton text="添加数据" type="primary" :icon="icons.Plus" @click="showAddModal = true" />
    </div>

    <div class="tabs">
      <el-tabs v-model="activeTab" type="card">
        <el-tab-pane label="案例池" name="pool">
          <SearchBar v-model="searchKeyword" placeholder="搜索案例...">
            <template #filters>
              <el-select v-model="statusFilter" placeholder="全部状态" class="status-select">
                <el-option label="全部状态" value="" />
                <el-option label="待审核" value="待审核" />
                <el-option label="已通过" value="已通过" />
                <el-option label="已驳回" value="已驳回" />
                <el-option label="设计完成" value="设计完成" />
              </el-select>
            </template>
          </SearchBar>

          <DataTable :data="filteredData">
            <el-table-column prop="id" label="案例ID" width="80" fixed="left" />
            <el-table-column prop="name" label="案例标题" width="250" fixed="left" />
            <el-table-column prop="reporter" label="创建人" width="100" />
            <el-table-column prop="reportTime" label="创建时间" width="120" />
            <el-table-column prop="caseType" label="案例类型" width="200" />
            <el-table-column prop="occurTime" label="发生时间" width="120" />
            <el-table-column prop="location" label="地点" width="150" />
            <el-table-column prop="roadType" label="道路描述" width="100" />
            <el-table-column prop="weather" label="天气条件" width="80" />
            <el-table-column prop="vehicleType" label="事故车型" width="100" />
            <el-table-column prop="systemVersion" label="系统版本" width="120" />
            <el-table-column prop="accidentType" label="事故/事件" width="100" />
            <el-table-column prop="accidentLevel" label="等级" width="100">
              <template #default="scope">
                <span :class="getLevelClass(scope.row.accidentLevel)">{{ scope.row.accidentLevel }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="150" fixed="right">
              <template #default="scope">
                <StatusBadge :status="scope.row.status" />
              </template>
            </el-table-column>
            <el-table-column label="操作" width="200" fixed="right">
              <template #default="scope">
                <div class="action-group">
                  <ActionButton text="查看" type="default" size="small" @click="viewDetail(scope.row)" />
                  <!-- <ActionButton text="编辑" type="primary" size="small" @click="editRow(scope.row)" /> -->
                  <ActionButton text="删除" type="danger" size="small" @click="deleteRow(scope.row)" />
                </div>
              </template>
            </el-table-column>
          </DataTable>
        </el-tab-pane>
        <el-tab-pane label="案例审核" name="review">
          <SearchBar v-model="reviewSearchKeyword" placeholder="搜索待审核案例...">
            <template #filters>
              <el-select v-model="reviewStatusFilter" placeholder="全部状态" class="status-select">
                <el-option label="全部状态" value="" />
                <el-option label="待审核" value="待审核" />
                <el-option label="已通过" value="已通过" />
                <el-option label="已驳回" value="已驳回" />
              </el-select>
            </template>
          </SearchBar>

          <DataTable :data="filteredReviewData">
            <el-table-column type="expand">
              <template #default="scope">
                <div class="expand-content">
                  <h4>系统审核</h4>
                  <div class="audit-card">
                    <div class="audit-item">
                      <span class="audit-label">数据完整性</span>
                      <span :class="['audit-result', scope.row.auditData.dataCompleteness.type]">{{ scope.row.auditData.dataCompleteness.text }}</span>
                    </div>
                    <div class="audit-item">
                      <span class="audit-label">场景价值判断</span>
                      <span :class="['audit-result', scope.row.auditData.sceneValue.type]">{{ scope.row.auditData.sceneValue.text }}</span>
                    </div>
                    <div class="audit-item">
                      <span class="audit-label">可行性判断</span>
                      <span :class="['audit-result', scope.row.auditData.feasibility.type]">{{ scope.row.auditData.feasibility.text }}</span>
                    </div>
                    <div class="audit-item">
                      <span class="audit-label">重复检测</span>
                      <span :class="['audit-result', scope.row.auditData.duplicateCheck.type]">{{ scope.row.auditData.duplicateCheck.text }}</span>
                    </div>
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="id" label="案例ID" width="80" />
            <el-table-column prop="name" label="案例标题" min-width="200" />
            <el-table-column prop="reporter" label="创建人" width="100" />
            <el-table-column prop="reportTime" label="创建时间" width="120" />
            <el-table-column prop="caseType" label="案例类型" width="200" />
            <el-table-column label="操作" width="320">
              <template #default="scope">
                <div class="action-group">
                  <ActionButton text="查看" type="default" size="small" @click="viewDetail(scope.row)" />
                  <ActionButton text="通过" type="success" size="small" @click="handleApprove(scope.row)" />
                  <ActionButton text="驳回" type="danger" size="small" @click="handleReject(scope.row)" />
                  <ActionButton text="关联已有场景" type="default" size="small" @click="handleRelate(scope.row)" />
                </div>
              </template>
            </el-table-column>
          </DataTable>
        </el-tab-pane>
      </el-tabs>
    </div>

    <el-dialog title="添加事故事件" :visible.sync="showAddModal" width="600px">
      <el-form :model="formData" label-width="120px">
        <el-form-item label="案例标题">
          <el-input v-model="formData.name" placeholder="请输入案例标题" />
        </el-form-item>
        <el-form-item label="创建人">
          <el-input v-model="formData.reporter" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="发生时间">
          <el-date-picker v-model="formData.occurTime" type="datetime" placeholder="选择发生时间" />
        </el-form-item>
        <el-form-item label="地点">
          <el-input v-model="formData.location" placeholder="请输入地点" />
        </el-form-item>
        <el-form-item label="道路描述">
          <el-select v-model="formData.roadType" placeholder="选择道路类型">
            <el-option label="高速公路" value="高速公路" />
            <el-option label="城市道路" value="城市道路" />
            <el-option label="山区道路" value="山区道路" />
            <el-option label="隧道" value="隧道" />
            <el-option label="交叉口" value="交叉口" />
          </el-select>
        </el-form-item>
        <el-form-item label="天气条件">
          <el-select v-model="formData.weather" placeholder="选择天气条件">
            <el-option label="晴天" value="晴天" />
            <el-option label="雨天" value="雨天" />
            <el-option label="雪天" value="雪天" />
            <el-option label="雾天" value="雾天" />
            <el-option label="阴天" value="阴天" />
          </el-select>
        </el-form-item>
        <el-form-item label="事故车型">
          <el-input v-model="formData.vehicleType" placeholder="请输入事故车型" />
        </el-form-item>
        <el-form-item label="系统版本">
          <el-input v-model="formData.systemVersion" placeholder="请输入系统版本" />
        </el-form-item>
        <el-form-item label="案例类型">
          <el-select v-model="formData.caseType" placeholder="选择案例类型">
            <el-option label="主机厂内部事故事件" value="主机厂内部事故事件" />
            <el-option label="外部事故事件" value="外部事故事件" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="事故/事件">
          <el-select v-model="formData.accidentType">
            <el-option label="事故" value="事故" />
            <el-option label="事件" value="事件" />
          </el-select>
        </el-form-item>
        <el-form-item label="等级">
          <el-select v-model="formData.accidentLevel">
            <el-option label="一般" value="一般" />
            <el-option label="较大" value="较大" />
            <el-option label="重大" value="重大" />
            <el-option label="特别重大" value="特别重大" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddModal = false">取消</el-button>
        <el-button type="primary" @click="handleAdd">确定</el-button>
      </template>
    </el-dialog>

    <DetailDrawer
      v-model:visible="showDetailDrawer" 
      :row-data="selectedRow"
      @close="handleDrawerClose"
      @save="handleSaveDetail"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import * as icons from '@element-plus/icons-vue'
import SearchBar from '@/components/common/SearchBar.vue'
import DataTable from '@/components/common/DataTable.vue'
import StatusBadge from '@/components/common/StatusBadge.vue'
import ActionButton from '@/components/common/ActionButton.vue'
import DetailDrawer from '@/components/common/DetailDrawer.vue'
import { getAccidentDataList, createAccidentData, updateAccidentData, deleteAccidentData, updateAccidentDataStatus } from '@/api/accidentData'

const activeTab = ref('pool')
const searchKeyword = ref('')
const statusFilter = ref('')
const reviewSearchKeyword = ref('')
const reviewStatusFilter = ref('')
const showAddModal = ref(false)
const showDetailDrawer = ref(false)
const selectedRow = ref(null)

const dataList = ref([])

const fetchData = async () => {
  try {
    dataList.value = await getAccidentDataList()
  } catch (e) {
    console.error('Failed to load accident data:', e)
  }
}

onMounted(fetchData)

const reviewDataList = computed(() => {
  return dataList.value.map(item => ({
    ...item,
    auditData: {
      dataCompleteness: { type: 'success', text: '✓ 完整' },
      sceneValue: { type: 'success', text: '✓ 元素为诱因' },
      feasibility: { type: 'warning', text: '⚠ 建议简化' },
      duplicateCheck: { type: 'info', text: '关联场景：SC001' }
    }
  }))
})

const formData = ref({
  name: '',
  reporter: '',
  reportTime: new Date().toISOString().split('T')[0],
  occurTime: '',
  location: '',
  roadType: '',
  weather: '',
  vehicleType: '',
  systemVersion: '',
  caseType: '',
  accidentType: '事故',
  accidentLevel: '一般'
})

const filteredData = computed(() => {
  return dataList.value.filter(item => {
    const matchKeyword = !searchKeyword.value || 
      item.name.toLowerCase().includes(searchKeyword.value.toLowerCase()) ||
      item.id.toLowerCase().includes(searchKeyword.value.toLowerCase())
    const matchStatus = !statusFilter.value || item.status === statusFilter.value
    return matchKeyword && matchStatus
  })
})

const filteredReviewData = computed(() => {
  return reviewDataList.value.filter(item => {
    const matchKeyword = !reviewSearchKeyword.value ||
      item.name.toLowerCase().includes(reviewSearchKeyword.value.toLowerCase()) ||
      item.id.toLowerCase().includes(reviewSearchKeyword.value.toLowerCase())
    const matchStatus = !reviewStatusFilter.value || item.status === reviewStatusFilter.value
    return matchKeyword && matchStatus
  })
})

const getLevelClass = (level) => {
  const levelMap = {
    '一般': 'level-normal',
    '较大': 'level-medium',
    '重大': 'level-major',
    '特别重大': 'level-critical'
  }
  return levelMap[level] || 'level-normal'
}

const viewDetail = (row) => {
  selectedRow.value = row
  showDetailDrawer.value = true
}
/*const editRow = (row) => console.log('编辑:', row)*/
const deleteRow = async (row) => {
  try {
    await deleteAccidentData(row.id)
    await fetchData()
  } catch (e) {
    console.error('Failed to delete accident data:', e)
  }
}
const handleDrawerClose = () => {
  showDetailDrawer.value = false
}
const handleSaveDetail = async (drawerData) => {
  try {
    const payload = {
      id: drawerData.id,
      name: drawerData.title || '',
      reporter: drawerData.creator || '',
      reportTime: drawerData.createTime || null,
      occurTime: drawerData.occurTime || null,
      location: drawerData.location || '',
      roadType: drawerData.roadDescription || '',
      weather: drawerData.weatherCondition || '',
      vehicleType: drawerData.vehicleType || '',
      systemVersion: drawerData.adaptiveFunction || '',
      caseType: drawerData.caseType || '',
      accidentType: drawerData.accidentType || '',
      accidentLevel: drawerData.level || '',
      status: selectedRow.value?.status || ''
    }
    await updateAccidentData(drawerData.id, payload)
    await fetchData()
  } catch (e) {
    console.error('Failed to save detail:', e)
  }
}

const handleAdd = async () => {
  const newId = `DC${Date.now().toString(36).toUpperCase()}`
  const formPayload = {
    ...formData.value,
    id: newId,
    reportTime: new Date().toISOString().split('T')[0],
    status: '待审核'
  }
  try {
    await createAccidentData(formPayload)
    await fetchData()
  } catch (e) {
    console.error('Failed to create accident data:', e)
  }
  showAddModal.value = false
  formData.value = {
    name: '', reporter: '', reportTime: new Date().toISOString().split('T')[0],
    occurTime: '', location: '', roadType: '', weather: '',
    vehicleType: '', systemVersion: '', caseType: '', accidentType: '事故', accidentLevel: '一般'
  }
}

const handleApprove = async (row) => {
  try {
    await updateAccidentDataStatus(row.id, '已通过')
    await fetchData()
  } catch (e) {
    console.error('Failed to approve accident data:', e)
  }
}
const handleReject = async (row) => {
  try {
    await updateAccidentDataStatus(row.id, '已驳回')
    await fetchData()
  } catch (e) {
    console.error('Failed to reject accident data:', e)
  }
}
const handleRelate = (row) => console.log('关联已有场景:', row)
</script>

<style lang="scss" scoped>
.accident-data {
  padding: 16px;
  min-height: 100%;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;

  h2 {
    font-size: 16px;
    font-weight: 600;
    color: #1f2937;
    margin: 0;
  }
}

.tabs {
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  overflow: visible;
  display: flex;
  flex-direction: column;
}

.status-select {
  width: 300px;
}

.level-normal { background: #D1FAE5; color: #059669; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.level-medium { background: #FEF3C7; color: #D97706; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.level-major { background: #FECACA; color: #DC2626; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.level-critical { background: #FEE2E2; color: #B91C1C; padding: 4px 10px; border-radius: 8px; font-size: 12px; }

.action-group {
  display: flex;
  gap: 6px;
}

.expand-content {
  padding: 16px 24px 16px 48px;

  h4 {
    font-size: 15px;
    font-weight: 600;
    color: #1f2937;
    margin: 0 0 12px 0;
  }

  .audit-card {
    background: #f9fafb;
    border-radius: 12px;
    padding: 20px;
  }

  .audit-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 0;
    border-bottom: 1px solid #e5e7eb;

    &:last-child { border-bottom: none; }

    .audit-label { font-size: 14px; color: #374151; }
    .audit-result { font-size: 14px; font-weight: 500; }
    .audit-result.success { color: #059669; }
    .audit-result.warning { color: #D97706; }
    .audit-result.info { color: #2563EB; }
  }
}
</style>