<template>
  <div class="case-design">
    <div class="page-header">
      <h2>用例设计</h2>
      <ActionButton text="创建用例" type="primary" @click="showCreateModal = true" />
    </div>

    <div class="case-list">
      <div v-for="caseItem in caseList" :key="caseItem.id" class="case-card">
        <div class="card-header">
          <div class="case-info">
            <span class="case-id">{{ caseItem.id }}</span>
            <span class="case-name">{{ caseItem.name }}</span>
          </div>
          <span class="case-date">{{ caseItem.createTime }}</span>
        </div>
        <div class="card-body">
          <ProgressBar :value="caseItem.progress" label="完成进度" />
          <div class="responsible-section">
            <span class="responsible-label">负责人</span>
            <span class="responsible-value">{{ caseItem.responsible }}</span>
          </div>
        </div>
        <div class="card-actions">
          <ActionButton text="查看详情" type="default" @click="viewCase(caseItem)" />
          <ActionButton text="补充信息" type="primary" @click="editCase(caseItem)" />
        </div>
      </div>
    </div>

    <el-dialog v-model="showCreateModal" title="创建用例" width="600px">
      <el-form :model="formData" label-width="120px">
        <el-form-item label="用例名称">
          <el-input v-model="formData.name" placeholder="请输入用例名称" />
        </el-form-item>
        <el-form-item label="关联场景">
          <el-select v-model="formData.sceneId" placeholder="选择场景">
            <el-option v-for="scene in sceneOptions" :key="scene.value" :label="scene.label" :value="scene.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="负责人">
          <el-select v-model="formData.responsible" placeholder="选择负责人">
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
import ProgressBar from '@/components/common/ProgressBar.vue'

const showCreateModal = ref(false)

const caseList = ref([
  { id: 'CD001', name: '高速公路紧急制动测试用例', progress: 75, responsible: '张晓明', createTime: '2024-01-15' },
  { id: 'CD002', name: '城市路口行人检测测试用例', progress: 60, responsible: '李思琪', createTime: '2024-01-16' },
  { id: 'CD003', name: '夜间车道保持测试用例', progress: 45, responsible: '王建国', createTime: '2024-01-17' },
  { id: 'CD004', name: '恶劣天气适应性测试用例', progress: 30, responsible: '赵海洋', createTime: '2024-01-18' },
  { id: 'CD005', name: '多车交互避让测试用例', progress: 90, responsible: '孙东方', createTime: '2024-01-19' }
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
  responsible: ''
})

const viewCase = (item) => console.log('查看用例:', item)
const editCase = (item) => console.log('编辑用例:', item)

const handleCreate = () => {
  const newId = `CD${String(caseList.value.length + 1).padStart(3, '0')}`
  caseList.value.unshift({
    ...formData.value,
    id: newId,
    progress: 0,
    createTime: new Date().toISOString().split('T')[0]
  })
  showCreateModal.value = false
  formData.value = { name: '', sceneId: '', responsible: '' }
}
</script>

<style lang="scss" scoped>
.case-design {
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

.case-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 16px;
  overflow-y: auto;
  flex: 1;
}

.case-card {
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
  align-items: flex-start;
  margin-bottom: 12px;
}

.case-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.case-id {
  font-size: 12px;
  color: #6b7280;
  font-weight: 500;
}

.case-name {
  font-size: 14px;
  color: #1f2937;
  font-weight: 600;
}

.case-date {
  font-size: 12px;
  color: #9ca3af;
}

.card-body {
  margin-bottom: 12px;
}

.responsible-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 8px;
  padding-top: 8px;
  border-top: 1px solid #f3f4f6;
}

.responsible-label {
  font-size: 12px;
  color: #6b7280;
}

.responsible-value {
  font-size: 13px;
  color: #1f2937;
  font-weight: 500;
}

.card-actions {
  display: flex;
  gap: 8px;
}
</style>