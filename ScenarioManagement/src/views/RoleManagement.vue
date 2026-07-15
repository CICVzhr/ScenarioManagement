<template>
  <div class="page-container">
    <div class="page-header">
      <h2>角色管理</h2>
      <el-button type="primary" v-permission="'user_management:edit'" @click="handleAdd">新增角色</el-button>
    </div>

    <el-table :data="roleList" border stripe v-loading="loading">
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="roleName" label="角色名称" width="150" />
      <el-table-column prop="roleCode" label="角色编码" width="150" />
      <el-table-column prop="description" label="描述" min-width="200" />
      <el-table-column label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'" size="small">
            {{ row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="170" />
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" size="small" v-permission="'user_management:edit'" @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" size="small" v-permission="'user_management:delete'" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="formRules" label-width="80px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" />
        </el-form-item>
        <el-form-item label="角色编码" prop="roleCode">
          <el-input v-model="form.roleCode" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" active-text="启用" inactive-text="禁用" />
        </el-form-item>
        <el-form-item label="权限分配">
          <el-tree ref="permTreeRef" :data="permissionTree" show-checkbox node-key="id"
            :default-checked-keys="checkedPermissionIds" :props="{ label: 'permissionName', children: 'children' }"
            :check-strictly="false" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getRoleList, createRole, updateRole, deleteRole, getRolePermissions } from '@/api/role'
import { getPermissionTree } from '@/api/permission'

const loading = ref(false)
const roleList = ref([])
const permissionTree = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('新增角色')
const isEdit = ref(false)
const formRef = ref(null)
const permTreeRef = ref(null)
const checkedPermissionIds = ref([])

const form = reactive({
  id: null,
  roleName: '',
  roleCode: '',
  description: '',
  status: 1,
  permissionIds: []
})

const formRules = {
  roleName: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
  roleCode: [{ required: true, message: '请输入角色编码', trigger: 'blur' }]
}

const loadRoles = async () => {
  loading.value = true
  try {
    roleList.value = await getRoleList()
  } catch {
    ElMessage.error('加载角色列表失败')
  } finally {
    loading.value = false
  }
}

const loadPermissionTree = async () => {
  try {
    permissionTree.value = await getPermissionTree()
  } catch { }
}

const resetForm = () => {
  form.id = null
  form.roleName = ''
  form.roleCode = ''
  form.description = ''
  form.status = 1
  form.permissionIds = []
  checkedPermissionIds.value = []
}

const getLeafPermissionIds = (tree) => {
  const ids = []
  const walk = (nodes) => {
    for (const node of nodes) {
      if (node.children && node.children.length > 0) {
        walk(node.children)
      } else {
        ids.push(node.id)
      }
    }
  }
  walk(tree)
  return ids
}

const handleAdd = () => {
  resetForm()
  isEdit.value = false
  dialogTitle.value = '新增角色'
  dialogVisible.value = true
}

const handleEdit = async (row) => {
  resetForm()
  isEdit.value = true
  dialogTitle.value = '编辑角色'
  form.id = row.id
  form.roleName = row.roleName
  form.roleCode = row.roleCode
  form.description = row.description
  form.status = row.status
  try {
    const perms = await getRolePermissions(row.id)
    checkedPermissionIds.value = perms.map(p => p.id)
  } catch {
    checkedPermissionIds.value = []
  }
  dialogVisible.value = true
}

const handleSave = async () => {
  if (!formRef.value) return
  try {
    await formRef.value.validate()
  } catch {
    return
  }
  const checkedKeys = permTreeRef.value ? permTreeRef.value.getCheckedKeys() : []
  const halfCheckedKeys = permTreeRef.value ? permTreeRef.value.getHalfCheckedKeys() : []
  const allPermIds = [...checkedKeys, ...halfCheckedKeys]

  try {
    const data = { ...form, permissionIds: allPermIds }
    if (isEdit.value) {
      await updateRole(form.id, data)
      ElMessage.success('更新成功')
    } else {
      await createRole(data)
      ElMessage.success('创建成功')
    }
    dialogVisible.value = false
    loadRoles()
  } catch (e) {
    ElMessage.error('操作失败: ' + (e.message || '未知错误'))
  }
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该角色吗？', '确认删除', { type: 'warning' }).then(async () => {
    try {
      await deleteRole(row.id)
      ElMessage.success('删除成功')
      loadRoles()
    } catch (e) {
      ElMessage.error('删除失败')
    }
  }).catch(() => { })
}

onMounted(() => {
  loadRoles()
  loadPermissionTree()
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