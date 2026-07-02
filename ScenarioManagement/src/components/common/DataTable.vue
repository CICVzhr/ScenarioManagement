<template>
  <div class="data-table">
    <el-table 
      :data="data" 
      :show-header="showHeader"
      :loading="loading"
      :fit="true"
      style="width: 100%"
    >
      <slot></slot>
    </el-table>
  </div>
</template>

<script setup>
defineProps({
  data: {
    type: Array,
    default: () => []
  },
  showHeader: {
    type: Boolean,
    default: true
  },
  loading: {
    type: Boolean,
    default: false
  }
})
</script>

<style lang="scss" scoped>
.data-table {
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  overflow: hidden;
  width: 100%;
  flex: 1;
  display: flex;
  flex-direction: column;

  .el-table {
    --el-table-header-text-color: #6b7280;
    --el-table-row-hover-bg-color: #f9fafb;
    width: 100%;
    font-size: 12px;
    flex: 1;
  }

  .el-table__header-wrapper,
  .el-table__body-wrapper {
    overflow-x: auto;
    overflow-y: auto;
  }

  :deep(.el-table__cell) {
    padding: 8px 10px;
    font-size: 11px;
    white-space: nowrap;
  }

  :deep(.el-table th.el-table__cell) {
    font-size: 12px;
    font-weight: 600;
    white-space: nowrap;
  }
}
</style>
