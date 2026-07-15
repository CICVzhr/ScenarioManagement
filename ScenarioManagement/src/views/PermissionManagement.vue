<template>
  <div class="page-container">
    <div class="page-header">
      <h2>权限管理</h2>
      <el-tag type="info">权限数据由系统预设，仅做查看</el-tag>
    </div>

    <el-table :data="permissionList" border stripe v-loading="loading" row-key="id" default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="permissionName" label="权限名称" width="180" />
      <el-table-column prop="permissionCode" label="权限编码" width="220" />
      <el-table-column label="类型" width="80">
        <template #default="{ row }">
          <el-tag :type="row.permissionType === 'menu' ? 'primary' : 'success'" size="small">
            {{ row.permissionType === 'menu' ? '菜单' : '按钮' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="path" label="路由路径" width="200" />
      <el-table-column prop="sortOrder" label="排序" width="70" />
      <el-table-column label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'" size="small">
            {{ row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="170" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getPermissionTree } from '@/api/permission'

const loading = ref(false)
const permissionList = ref([])

const loadPermissions = async () => {
  loading.value = true
  try {
    permissionList.value = await getPermissionTree()
  } catch (e) {
    ElMessage.error('加载权限列表失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadPermissions()
})
</script>

<style lang="scss" scoped>
.page-container {
  padding: 20px;
  background: #fff;
  margin: 20px;
  border-radius: 8px;
  min-height: calc(100vh - 100px);
}
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  h2 { margin: 0; font-size: 18px; }
}
</style>