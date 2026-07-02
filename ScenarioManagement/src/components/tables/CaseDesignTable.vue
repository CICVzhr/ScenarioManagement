<template>
  <DataTable :data="tableData" class="full-width-table">
    <el-table-column prop="id" label="用例ID" min-width="100" />
    <el-table-column prop="name" label="用例名称" min-width="200" />
    <el-table-column prop="type" label="用例类型" min-width="120" />
    <el-table-column prop="level" label="危险等级" min-width="100">
      <template #default="scope">
        <span :class="getLevelClass(scope.row.level)">{{ scope.row.level }}</span>
      </template>
    </el-table-column>
    <el-table-column prop="createTime" label="创建时间" min-width="120" />
    <el-table-column prop="caseStatus" label="状态" min-width="100">
      <template #default="scope">
        <StatusBadge :status="scope.row.caseStatus" />
      </template>
    </el-table-column>
    <el-table-column label="操作" min-width="260">
      <template #default="scope">
        <ActionButton text="查看" type="default" size="small" @click="emit('view', scope.row)" />
        <ActionButton text="编辑" type="primary" size="small" @click="emit('edit', scope.row)" />
        <ActionButton text="审批记录" type="default" size="small" @click="emit('approval', scope.row)" />
        <ActionButton text="删除" type="danger" size="small" @click="emit('delete', scope.row)" />
      </template>
    </el-table-column>
  </DataTable>
</template>

<script setup>
import DataTable from '@/components/common/DataTable.vue'
import StatusBadge from '@/components/common/StatusBadge.vue'
import ActionButton from '@/components/common/ActionButton.vue'
import { getLevelClass } from '@/utils'

defineProps({
  tableData: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['view', 'edit', 'delete', 'approval'])
</script>

<style lang="scss" scoped>
@use '@/styles/mixins.scss' as *;
</style>