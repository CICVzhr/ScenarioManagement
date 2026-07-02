<template>
  <div class="track-changes">
    <div
      v-for="(change, index) in textChanges"
      :key="index"
      class="change-item"
    >
      <div class="change-field">
        <el-icon :size="14" class="field-icon"><EditPen /></el-icon>
        <span class="field-name">{{ change.field }}</span>
      </div>
      <div class="change-diff">
        <span
          v-for="(seg, sIdx) in change.segments"
          :key="sIdx"
          :class="['diff-segment', `diff-${seg.type}`]"
          :title="getSegmentTitle(seg)"
        >{{ seg.text }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { EditPen } from '@element-plus/icons-vue'

defineProps({
  textChanges: {
    type: Array,
    default: () => []
  }
})

const getSegmentTitle = (seg) => {
  if (seg.type === 'delete') return `${seg.author || ''} 删除于 ${seg.time || ''}`
  if (seg.type === 'add') return `${seg.author || ''} 新增于 ${seg.time || ''}`
  return ''
}
</script>

<style lang="scss" scoped>
.track-changes {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.change-item {
  background: #fff;
  border-radius: 8px;
  padding: 10px 12px;
  border: 1px solid #F3F4F6;
}

.change-field {
  display: flex;
  align-items: center;
  gap: 6px;
  margin-bottom: 6px;

  .field-icon {
    color: #F59E0B;
  }

  .field-name {
    font-size: 12px;
    font-weight: 600;
    color: #92400E;
    background: #FEF3C7;
    padding: 2px 8px;
    border-radius: 4px;
  }
}

.change-diff {
  font-size: 13px;
  line-height: 1.8;
  color: #374151;
  word-break: break-all;
}

.diff-segment {
  &.diff-equal {
    color: #374151;
  }

  &.diff-delete {
    background: #FECACA;
    color: #991B1B;
    text-decoration: line-through;
    text-decoration-color: #DC2626;
    text-decoration-thickness: 2px;
    border-radius: 2px;
    padding: 0 1px;
  }

  &.diff-add {
    background: #BBF7D0;
    color: #166534;
    text-decoration: underline;
    text-decoration-color: #16A34A;
    text-decoration-thickness: 2px;
    text-underline-offset: 3px;
    border-radius: 2px;
    padding: 0 1px;
  }
}
</style>