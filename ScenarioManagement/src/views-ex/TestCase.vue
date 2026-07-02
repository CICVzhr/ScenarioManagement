<template>
  <div class="test-case">
    <div class="page-header">
      <h2>测试用例库</h2>
      <ActionButton text="创建用例" type="primary" @click="showCreateModal = true" />
    </div>

    <div class="case-table">
      <el-table :data="filteredCases" border>
        <el-table-column prop="id" label="用例ID" width="100" />
        <el-table-column prop="sceneId" label="关联场景ID" width="120" />
        <el-table-column prop="name" label="用例名称" width="250" />
        <el-table-column prop="type" label="测试类型" width="100">
          <template #default="scope">
            <span :class="getTypeClass(scope.row.type)">{{ scope.row.type }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <span :class="getStatusClass(scope.row.status)">{{ scope.row.status }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="version" label="版本" width="100" />
        <el-table-column prop="createTime" label="创建时间" width="140" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <button class="btn-action btn-view" @click="viewCase(scope.row)">查看</button>
            <button class="btn-action btn-edit" @click="editCase(scope.row)">编辑</button>
            <button class="btn-action btn-export" @click="exportCase(scope.row)">导出</button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog v-model="showCreateModal" title="创建测试用例" width="600px">
      <el-form :model="formData" label-width="120px">
        <el-form-item label="用例名称">
          <el-input v-model="formData.name" placeholder="请输入用例名称" />
        </el-form-item>
        <el-form-item label="关联场景ID">
          <el-select v-model="formData.sceneId" placeholder="选择场景">
            <el-option v-for="scene in sceneOptions" :key="scene.value" :label="scene.label" :value="scene.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="测试类型">
          <el-select v-model="formData.type" placeholder="选择测试类型">
            <el-option label="功能测试" value="功能测试" />
            <el-option label="性能测试" value="性能测试" />
            <el-option label="安全测试" value="安全测试" />
            <el-option label="兼容性测试" value="兼容性测试" />
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

const searchKeyword = ref('')
const showCreateModal = ref(false)

const testCases = ref([
  { id: 'TC001', sceneId: 'SC001', name: '高速公路跟车距离测试', type: '功能测试', status: '开发中', version: 'v1.0', createTime: '2024-01-15' },
  { id: 'TC002', sceneId: 'SC002', name: '城市路口红绿灯识别测试', type: '功能测试', status: '已完成', version: 'v1.2', createTime: '2024-01-16' },
  { id: 'TC003', sceneId: 'SC003', name: '夜间行人检测测试', type: '安全测试', status: '测试中', version: 'v1.0', createTime: '2024-01-17' },
  { id: 'TC004', sceneId: 'SC004', name: '恶劣天气响应测试', type: '性能测试', status: '已发布', version: 'v2.0', createTime: '2024-01-18' },
  { id: 'TC005', sceneId: 'SC005', name: '多车交互场景测试', type: '兼容性测试', status: '开发中', version: 'v1.0', createTime: '2024-01-19' }
])

const sceneOptions = [
  { label: 'SC001 - 高速公路场景', value: 'SC001' },
  { label: 'SC002 - 城市路口场景', value: 'SC002' },
  { label: 'SC003 - 夜间场景', value: 'SC003' },
  { label: 'SC004 - 恶劣天气场景', value: 'SC004' },
  { label: 'SC005 - 多车交互场景', value: 'SC005' }
]

const formData = ref({
  name: '',
  sceneId: '',
  type: ''
})

const filteredCases = computed(() => {
  return testCases.value.filter(item => {
    return !searchKeyword.value || 
      item.name.toLowerCase().includes(searchKeyword.value.toLowerCase()) ||
      item.id.toLowerCase().includes(searchKeyword.value.toLowerCase())
  })
})

const getTypeClass = (type) => {
  const typeMap = { 
    '功能测试': 'type-functional', 
    '性能测试': 'type-performance', 
    '安全测试': 'type-security', 
    '兼容性测试': 'type-compatibility' 
  }
  return typeMap[type] || 'type-functional'
}

const getStatusClass = (status) => {
  const statusMap = { 
    '开发中': 'status-developing', 
    '已完成': 'status-completed', 
    '测试中': 'status-testing', 
    '已发布': 'status-released' 
  }
  return statusMap[status] || 'status-developing'
}

const viewCase = (row) => console.log('查看用例:', row)
const editCase = (row) => console.log('编辑用例:', row)
const exportCase = (row) => console.log('导出用例:', row)

const handleCreate = () => {
  const newId = `TC${String(testCases.value.length + 1).padStart(3, '0')}`
  testCases.value.unshift({
    ...formData.value,
    id: newId,
    status: '开发中',
    version: 'v1.0',
    createTime: new Date().toISOString().split('T')[0]
  })
  showCreateModal.value = false
  formData.value = { name: '', sceneId: '', type: '' }
}
</script>

<style lang="scss" scoped>
.test-case {
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

.case-table {
  background: #fff;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  flex: 1;
  overflow: hidden;
}

.btn-action {
  padding: 6px 12px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 13px;
  margin-right: 8px;
  transition: all 0.2s;

  &:hover {
    opacity: 0.8;
  }
}

.btn-view {
  background: #3b82f6;
  color: white;
}

.btn-edit {
  background: #10b981;
  color: white;
}

.btn-export {
  background: #f59e0b;
  color: white;
}

.type-functional { background: #DBEAFE; color: #1D4ED8; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.type-performance { background: #FCE7F3; color: #BE185D; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.type-security { background: #FEE2E2; color: #B91C1C; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.type-compatibility { background: #D1FAE5; color: #059669; padding: 4px 10px; border-radius: 8px; font-size: 12px; }

.status-developing { background: #FEF3C7; color: #D97706; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.status-completed { background: #D1FAE5; color: #059669; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.status-testing { background: #DBEAFE; color: #1D4ED8; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.status-released { background: #E0E7FF; color: #4338CA; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
</style>