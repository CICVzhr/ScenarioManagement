<template>
  <DataTable :data="tableData" class="full-width-table">
    <el-table-column prop="id" label="用例ID" min-width="100" />
    <el-table-column prop="name" label="用例名称" min-width="200" />
    <el-table-column prop="type" label="测试类型" min-width="120">
      <template #default="scope">
        <span :class="getTypeClass(scope.row.type)">{{ scope.row.type }}</span>
      </template>
    </el-table-column>
    <el-table-column prop="developer" label="开发人员" min-width="100" />
    <el-table-column prop="version" label="版本" min-width="80" />
    <el-table-column prop="createTime" label="创建时间" min-width="120" />
    <el-table-column prop="status" label="状态" min-width="100">
      <template #default="scope">
        <span :class="getDevStatusClass(scope.row.status)">{{ scope.row.status }}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作" min-width="300">
      <template #default="scope">
        <ActionButton text="查看" type="default" size="small" @click="emit('view', scope.row)" />
        <ActionButton text="编辑" type="primary" size="small" @click="emit('edit', scope.row)" />
        <ActionButton text="执行" type="success" size="small" @click="emit('execute', scope.row)" />
        <ActionButton text="审批记录" type="default" size="small" @click="emit('approval', scope.row)" />
        <ActionButton text="导出" type="warning" size="small" @click="emit('export', scope.row)" />
      </template>
    </el-table-column>
  </DataTable>
</template>

<script setup>
import DataTable from '@/components/common/DataTable.vue'
import ActionButton from '@/components/common/ActionButton.vue'
import { getTypeClass, getDevStatusClass } from '@/utils'

defineProps({
  tableData: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['view', 'edit', 'execute', 'export', 'approval'])
</script>

<style lang="scss" scoped>
@use '@/styles/mixins.scss' as *;

.type-functional { background: #DBEAFE; color: #1D4ED8; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.type-performance { background: #FCE7F3; color: #BE185D; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.type-security { background: #FEE2E2; color: #B91C1C; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.type-compatibility { background: #D1FAE5; color: #059669; padding: 4px 10px; border-radius: 8px; font-size: 12px; }

.status-developing { background: #FEF3C7; color: #D97706; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.status-completed { background: #D1FAE5; color: #059669; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.status-testing { background: #DBEAFE; color: #1D4ED8; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
.status-released { background: #E0E7FF; color: #4338CA; padding: 4px 10px; border-radius: 8px; font-size: 12px; }
</style>