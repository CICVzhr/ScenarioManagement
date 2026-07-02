<template>
  <div class="test-management">
    <div class="page-header">
      <h2>测试管理</h2>
      <ActionButton text="新建测试" type="primary" @click="showCreateModal = true" />
    </div>

    <div class="test-stats">
      <div class="stat-card">
        <div class="stat-icon">📊</div>
        <div class="stat-info">
          <div class="stat-value">{{ totalTests }}</div>
          <div class="stat-label">总测试数</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon">✅</div>
        <div class="stat-info">
          <div class="stat-value">{{ completedTests }}</div>
          <div class="stat-label">已完成</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon">⏳</div>
        <div class="stat-info">
          <div class="stat-value">{{ runningTests }}</div>
          <div class="stat-label">进行中</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon">⚠️</div>
        <div class="stat-info">
          <div class="stat-value">{{ failedTests }}</div>
          <div class="stat-label">失败</div>
        </div>
      </div>
    </div>

    <div class="test-list">
      <div v-for="test in testList" :key="test.id" class="test-item">
        <div class="test-header">
          <div class="test-info">
            <span class="test-id">{{ test.id }}</span>
            <span class="test-name">{{ test.name }}</span>
          </div>
          <span :class="['test-status', test.statusClass]">{{ test.status }}</span>
        </div>
        <div class="test-details">
          <div class="detail-item">
            <span class="detail-label">关联场景:</span>
            <span class="detail-value">{{ test.sceneId }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">执行时间:</span>
            <span class="detail-value">{{ test.executeTime }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">执行人:</span>
            <span class="detail-value">{{ test.executor }}</span>
          </div>
        </div>
        <div class="test-actions">
          <ActionButton text="查看详情" type="default" size="small" @click="viewTest(test)" />
          <ActionButton text="重新执行" type="primary" size="small" @click="rerunTest(test)" />
          <ActionButton text="下载报告" type="success" size="small" @click="downloadReport(test)" />
        </div>
      </div>
    </div>

    <el-dialog v-model="showCreateModal" title="新建测试" width="600px">
      <el-form :model="formData" label-width="120px">
        <el-form-item label="测试名称">
          <el-input v-model="formData.name" placeholder="请输入测试名称" />
        </el-form-item>
        <el-form-item label="关联场景">
          <el-select v-model="formData.sceneId" placeholder="选择场景">
            <el-option v-for="scene in sceneOptions" :key="scene.value" :label="scene.label" :value="scene.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="执行人">
          <el-select v-model="formData.executor" placeholder="选择执行人">
            <el-option v-for="user in userOptions" :key="user.value" :label="user.label" :value="user.value" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateModal = false">取消</el-button>
        <el-button type="primary" @click="handleCreate">创建</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import ActionButton from '@/components/common/ActionButton.vue'

const showCreateModal = ref(false)

const testList = ref([
  { id: 'TM001', name: '高速公路场景综合测试', sceneId: 'SC001', status: '已完成', statusClass: 'status-completed', executeTime: '2024-01-15 14:30', executor: '张晓明' },
  { id: 'TM002', name: '城市路口安全测试', sceneId: 'SC002', status: '进行中', statusClass: 'status-running', executeTime: '2024-01-16 09:00', executor: '李思琪' },
  { id: 'TM003', name: '夜间环境测试', sceneId: 'SC003', status: '失败', statusClass: 'status-failed', executeTime: '2024-01-17 16:45', executor: '王建国' },
  { id: 'TM004', name: '恶劣天气适应性测试', sceneId: 'SC004', status: '已完成', statusClass: 'status-completed', executeTime: '2024-01-18 11:20', executor: '赵海洋' },
  { id: 'TM005', name: '多车交互场景测试', sceneId: 'SC005', status: '进行中', statusClass: 'status-running', executeTime: '2024-01-19 15:00', executor: '孙东方' }
])

const sceneOptions = [
  { label: 'SC001 - 高速公路场景', value: 'SC001' },
  { label: 'SC002 - 城市路口场景', value: 'SC002' },
  { label: 'SC003 - 夜间场景', value: 'SC003' },
  { label: 'SC004 - 恶劣天气场景', value: 'SC004' },
  { label: 'SC005 - 多车交互场景', value: 'SC005' }
]

const userOptions = [
  { label: '张晓明', value: '张晓明' },
  { label: '李思琪', value: '李思琪' },
  { label: '王建国', value: '王建国' },
  { label: '赵海洋', value: '赵海洋' },
  { label: '孙东方', value: '孙东方' }
]

const formData = ref({
  name: '',
  sceneId: '',
  executor: ''
})

const totalTests = computed(() => testList.value.length)
const completedTests = computed(() => testList.value.filter(t => t.status === '已完成').length)
const runningTests = computed(() => testList.value.filter(t => t.status === '进行中').length)
const failedTests = computed(() => testList.value.filter(t => t.status === '失败').length)

const viewTest = (test) => console.log('查看测试:', test)
const rerunTest = (test) => console.log('重新执行测试:', test)
const downloadReport = (test) => console.log('下载报告:', test)

const handleCreate = () => {
  const newId = `TM${String(testList.value.length + 1).padStart(3, '0')}`
  testList.value.unshift({
    ...formData.value,
    id: newId,
    status: '进行中',
    statusClass: 'status-running',
    executeTime: new Date().toLocaleString()
  })
  showCreateModal.value = false
  formData.value = { name: '', sceneId: '', executor: '' }
}
</script>

<style lang="scss" scoped>
.test-management {
  padding: 16px;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;
  overflow: hidden;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-shrink: 0;

  h2 {
    font-size: 20px;
    font-weight: 600;
    color: #1f2937;
    margin: 0;
  }
}

.test-stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 16px;
  flex-shrink: 0;
}

.stat-card {
  background: #fff;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  display: flex;
  align-items: center;
  gap: 12px;
}

.stat-icon {
  font-size: 32px;
}

.stat-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-value {
  font-size: 24px;
  font-weight: 600;
  color: #1f2937;
}

.stat-label {
  font-size: 13px;
  color: #6b7280;
}

.test-list {
  flex: 1;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.test-item {
  background: #fff;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  transition: all 0.2s;

  &:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }
}

.test-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.test-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.test-id {
  font-size: 12px;
  color: #6b7280;
  font-weight: 500;
}

.test-name {
  font-size: 14px;
  color: #1f2937;
  font-weight: 600;
}

.test-status {
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;

  &.status-completed {
    background: #D1FAE5;
    color: #059669;
  }

  &.status-running {
    background: #DBEAFE;
    color: #1D4ED8;
  }

  &.status-failed {
    background: #FEE2E2;
    color: #B91C1C;
  }
}

.test-details {
  display: flex;
  gap: 24px;
  margin-bottom: 12px;
  padding: 8px 0;
  border-top: 1px solid #f3f4f6;
  border-bottom: 1px solid #f3f4f6;
}

.detail-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.detail-label {
  font-size: 12px;
  color: #6b7280;
}

.detail-value {
  font-size: 13px;
  color: #1f2937;
  font-weight: 500;
}

.test-actions {
  display: flex;
  gap: 8px;
}
</style>