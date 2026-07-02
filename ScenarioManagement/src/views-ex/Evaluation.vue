<template>
  <div class="evaluation">
    <div class="page-header">
      <h2>评价管理</h2>
      <ActionButton text="新建评价" type="primary" @click="showCreateModal = true" />
    </div>

    <div class="evaluation-list">
      <div v-for="evaluation in evaluationList" :key="evaluation.id" class="evaluation-card">
        <div class="card-header">
          <div class="evaluation-info">
            <span class="evaluation-id">{{ evaluation.id }}</span>
            <span class="evaluation-name">{{ evaluation.name }}</span>
          </div>
          <div class="evaluation-score">
            <span class="score-label">评分:</span>
            <span class="score-value" :class="getScoreClass(evaluation.score)">{{ evaluation.score }}</span>
          </div>
        </div>
        <div class="card-body">
          <div class="info-row">
            <span class="info-label">关联场景:</span>
            <span class="info-value">{{ evaluation.sceneId }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">评价时间:</span>
            <span class="info-value">{{ evaluation.evaluationTime }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">评价人:</span>
            <span class="info-value">{{ evaluation.evaluator }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">评价标准:</span>
            <span class="info-value">{{ evaluation.standard }}</span>
          </div>
        </div>
        <div class="card-actions">
          <ActionButton text="查看详情" type="default" size="small" @click="viewEvaluation(evaluation)" />
          <ActionButton text="导出报告" type="primary" size="small" @click="exportReport(evaluation)" />
        </div>
      </div>
    </div>

    <el-dialog v-model="showCreateModal" title="新建评价" width="600px">
      <el-form :model="formData" label-width="120px">
        <el-form-item label="评价名称">
          <el-input v-model="formData.name" placeholder="请输入评价名称" />
        </el-form-item>
        <el-form-item label="关联场景">
          <el-select v-model="formData.sceneId" placeholder="选择场景">
            <el-option v-for="scene in sceneOptions" :key="scene.value" :label="scene.label" :value="scene.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="评价标准">
          <el-select v-model="formData.standard" placeholder="选择评价标准">
            <el-option label="ISO 26262" value="ISO 26262" />
            <el-option label="ASPICE" value="ASPICE" />
            <el-option label="自定义标准" value="自定义标准" />
          </el-select>
        </el-form-item>
        <el-form-item label="评价人">
          <el-select v-model="formData.evaluator" placeholder="选择评价人">
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
import { ref } from 'vue'
import ActionButton from '@/components/common/ActionButton.vue'

const showCreateModal = ref(false)

const evaluationList = ref([
  { id: 'EV001', name: '高速公路场景安全性评价', sceneId: 'SC001', score: 92, evaluationTime: '2024-01-15', evaluator: '张晓明', standard: 'ISO 26262' },
  { id: 'EV002', name: '城市路口场景功能性评价', sceneId: 'SC002', score: 88, evaluationTime: '2024-01-16', evaluator: '李思琪', standard: 'ASPICE' },
  { id: 'EV003', name: '夜间场景可靠性评价', sceneId: 'SC003', score: 95, evaluationTime: '2024-01-17', evaluator: '王建国', standard: 'ISO 26262' },
  { id: 'EV004', name: '恶劣天气适应性评价', sceneId: 'SC004', score: 78, evaluationTime: '2024-01-18', evaluator: '赵海洋', standard: '自定义标准' },
  { id: 'EV005', name: '多车交互场景综合评价', sceneId: 'SC005', score: 85, evaluationTime: '2024-01-19', evaluator: '孙东方', standard: 'ASPICE' }
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
  standard: '',
  evaluator: ''
})

const getScoreClass = (score) => {
  if (score >= 90) return 'score-excellent'
  if (score >= 80) return 'score-good'
  if (score >= 70) return 'score-average'
  return 'score-poor'
}

const viewEvaluation = (evaluation) => console.log('查看评价:', evaluation)
const exportReport = (evaluation) => console.log('导出报告:', evaluation)

const handleCreate = () => {
  const newId = `EV${String(evaluationList.value.length + 1).padStart(3, '0')}`
  evaluationList.value.unshift({
    ...formData.value,
    id: newId,
    score: 0,
    evaluationTime: new Date().toISOString().split('T')[0]
  })
  showCreateModal.value = false
  formData.value = { name: '', sceneId: '', standard: '', evaluator: '' }
}
</script>

<style lang="scss" scoped>
.evaluation {
  padding: 16px;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;

  h2 {
    font-size: 20px;
    font-weight: 600;
    color: #1f2937;
    margin: 0;
  }
}

.evaluation-list {
  flex: 1;
  overflow-y: auto;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 16px;
}

.evaluation-card {
  background: #fff;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  transition: all 0.2s;

  &:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.evaluation-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.evaluation-id {
  font-size: 12px;
  color: #6b7280;
  font-weight: 500;
}

.evaluation-name {
  font-size: 14px;
  color: #1f2937;
  font-weight: 600;
}

.evaluation-score {
  display: flex;
  align-items: center;
  gap: 8px;
}

.score-label {
  font-size: 12px;
  color: #6b7280;
}

.score-value {
  font-size: 18px;
  font-weight: 600;
  padding: 4px 12px;
  border-radius: 8px;

  &.score-excellent {
    background: #D1FAE5;
    color: #059669;
  }

  &.score-good {
    background: #DBEAFE;
    color: #1D4ED8;
  }

  &.score-average {
    background: #FEF3C7;
    color: #D97706;
  }

  &.score-poor {
    background: #FEE2E2;
    color: #B91C1C;
  }
}

.card-body {
  margin-bottom: 12px;
  padding: 8px 0;
  border-top: 1px solid #f3f4f6;
  border-bottom: 1px solid #f3f4f6;
}

.info-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;

  &:last-child {
    margin-bottom: 0;
  }
}

.info-label {
  font-size: 12px;
  color: #6b7280;
  min-width: 80px;
}

.info-value {
  font-size: 13px;
  color: #1f2937;
  font-weight: 500;
}

.card-actions {
  display: flex;
  gap: 8px;
}
</style>