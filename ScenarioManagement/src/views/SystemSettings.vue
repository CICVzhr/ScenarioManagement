<template>
  <div class="system-settings">
    <div class="page-header">
      <h2>系统设置</h2>
    </div>

    <div class="settings-tabs">
      <el-tabs v-model="activeTab" type="card">
        <el-tab-pane label="数据来源管理" name="data-source">
          <div class="source-list">
            <div v-for="source in dataSources" :key="source.id" class="source-card">
              <div class="source-info">
                <span class="source-name">{{ source.name }}</span>
              </div>
              <div class="source-status">
                <span :class="source.status === '启用' ? 'status-active' : 'status-inactive'">
                  {{ source.status }}
                </span>
                <button class="toggle-btn" @click="toggleSource(source)">
                  {{ source.status === '启用' ? '禁用' : '启用' }}
                </button>
              </div>
            </div>
          </div>
        </el-tab-pane>

        <el-tab-pane label="角色权限管理" name="roles">
          <div class="roles-panel">
            <div class="roles-header">
              <h3>角色列表</h3>
              <button class="add-role-btn" @click="showRoleModal = true">
                <el-icon><component :is="icons.Plus" /></el-icon>
                添加角色
              </button>
            </div>
            <el-table :data="roles" border>
              <el-table-column prop="name" label="角色名称" width="150" />
              <el-table-column prop="permissions" label="权限列表" width="400">
                <template #default="scope">
                  <span 
                    v-for="(perm, index) in scope.row.permissions" 
                    :key="index" 
                    class="perm-tag"
                  >{{ perm }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="150">
                <template #default="scope">
                  <button class="btn-edit" @click="editRole(scope.row)">编辑</button>
                  <button class="btn-delete" @click="deleteRole(scope.row)">删除</button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>

        <el-tab-pane label="导出设置" name="export">
          <div class="export-panel">
            <div class="export-option">
              <div class="option-info">
                <h4>Word导出</h4>
                <p>支持将场景文档导出为Word格式</p>
              </div>
              <el-switch v-model="exportSettings.wordExport" />
            </div>
            <div class="export-option">
              <div class="option-info">
                <h4>PDF导出</h4>
                <p>支持将场景文档导出为PDF格式</p>
              </div>
              <el-switch v-model="exportSettings.pdfExport" />
            </div>
            <div class="export-option">
              <div class="option-info">
                <h4>Excel导出</h4>
                <p>支持将测试用例表导出为Excel格式</p>
              </div>
              <el-switch v-model="exportSettings.excelExport" />
            </div>
            <button class="save-settings-btn" @click="saveExportSettings">保存设置</button>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>

    <el-dialog title="添加角色" :visible.sync="showRoleModal" width="500px">
      <el-form :model="roleForm" label-width="100px">
        <el-form-item label="角色名称">
          <el-input v-model="roleForm.name" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="权限列表">
          <el-select v-model="roleForm.permissions" multiple placeholder="选择权限">
            <el-option label="场景设计" value="场景设计" />
            <el-option label="用例设计" value="用例设计" />
            <el-option label="审核" value="审核" />
            <el-option label="审批" value="审批" />
            <el-option label="用例补充" value="用例补充" />
            <el-option label="用例转化" value="用例转化" />
            <el-option label="评价指标" value="评价指标" />
            <el-option label="全部" value="全部" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showRoleModal = false">取消</el-button>
        <el-button type="primary" @click="handleAddRole">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import * as icons from '@element-plus/icons-vue'
import { systemSettings } from '@/data/mockData'

const activeTab = ref('data-source')
const showRoleModal = ref(false)

const dataSources = ref([...systemSettings.dataSources])
const roles = ref([...systemSettings.roles])
const exportSettings = ref({ ...systemSettings.exportSettings })

const roleForm = ref({
  name: '',
  permissions: []
})

const toggleSource = (source) => {
  source.status = source.status === '启用' ? '禁用' : '启用'
}

const editRole = (role) => {
  console.log('编辑角色:', role)
}

const deleteRole = (role) => {
  console.log('删除角色:', role)
}

const handleAddRole = () => {
  if (roleForm.value.name) {
    roles.value.push({
      id: roles.value.length + 1,
      name: roleForm.value.name,
      permissions: [...roleForm.value.permissions]
    })
    showRoleModal.value = false
    roleForm.value = { name: '', permissions: [] }
  }
}

const saveExportSettings = () => {
  console.log('保存导出设置:', exportSettings.value)
}
</script>

<style lang="scss" scoped>
.system-settings {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;

  h2 {
    font-size: 20px;
    font-weight: 600;
    color: #1f2937;
    margin: 0;
  }
}

.settings-tabs {
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.source-list {
  padding: 20px;
}

.source-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  background: #f9fafb;
  border-radius: 8px;
  margin-bottom: 12px;

  &:last-child {
    margin-bottom: 0;
  }

  .source-name {
    font-size: 14px;
    color: #1f2937;
    font-weight: 500;
  }

  .source-status {
    display: flex;
    align-items: center;
    gap: 12px;

    .status-active {
      background: #D1FAE5;
      color: #059669;
      padding: 4px 12px;
      border-radius: 12px;
      font-size: 12px;
    }

    .status-inactive {
      background: #F3F4F6;
      color: #6B7280;
      padding: 4px 12px;
      border-radius: 12px;
      font-size: 12px;
    }

    .toggle-btn {
      background: #fff;
      border: 1px solid #D1D5DB;
      padding: 6px 16px;
      border-radius: 6px;
      font-size: 12px;
      color: #6B7280;
      cursor: pointer;

      &:hover {
        border-color: #3B82F6;
        color: #3B82F6;
      }
    }
  }
}

.roles-panel {
  padding: 20px;
}

.roles-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;

  h3 {
    font-size: 16px;
    font-weight: 600;
    color: #1f2937;
    margin: 0;
  }

  .add-role-btn {
    display: flex;
    align-items: center;
    gap: 6px;
    background: #3B82F6;
    color: #fff;
    border: none;
    padding: 8px 16px;
    border-radius: 6px;
    font-size: 13px;
    cursor: pointer;

    &:hover {
      background: #2563EB;
    }
  }
}

.el-table {
  --el-table-header-text-color: #6b7280;
  --el-table-row-hover-bg-color: #f9fafb;
}

.perm-tag {
  display: inline-block;
  background: #DBEAFE;
  color: #2563EB;
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 12px;
  margin-right: 6px;
}

.btn-edit {
  background: #DBEAFE;
  color: #2563EB;
  border: none;
  padding: 4px 12px;
  border-radius: 4px;
  font-size: 12px;
  cursor: pointer;
  margin-right: 8px;

  &:hover {
    background: #BFDBFE;
  }
}

.btn-delete {
  background: #FEE2E2;
  color: #DC2626;
  border: none;
  padding: 4px 12px;
  border-radius: 4px;
  font-size: 12px;
  cursor: pointer;

  &:hover {
    background: #FECACA;
  }
}

.export-panel {
  padding: 20px;
}

.export-option {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background: #f9fafb;
  border-radius: 8px;
  margin-bottom: 16px;

  .option-info {
    h4 {
      font-size: 14px;
      font-weight: 600;
      color: #1f2937;
      margin: 0 0 4px 0;
    }

    p {
      font-size: 13px;
      color: #6B7280;
      margin: 0;
    }
  }
}

.save-settings-btn {
  width: 100%;
  background: #3B82F6;
  color: #fff;
  border: none;
  padding: 12px;
  border-radius: 8px;
  font-size: 14px;
  cursor: pointer;

  &:hover {
    background: #2563EB;
  }
}
</style>
