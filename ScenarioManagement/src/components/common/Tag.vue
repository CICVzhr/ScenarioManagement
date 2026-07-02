<template>
  <span 
    class="tag" 
    :class="[tagClass, { removable: removable }]"
    :style="{ background: bgColor, color: textColor }"
  >
    {{ label }}
    <button v-if="removable" class="remove-tag" @click.stop="handleRemove">
      <el-icon :size="14"><component :is="icons.Close" /></el-icon>
    </button>
  </span>
</template>

<script setup>
import { computed } from 'vue'
import * as icons from '@element-plus/icons-vue'

const props = defineProps({
  label: {
    type: String,
    required: true
  },
  type: {
    type: String,
    default: 'default'
  },
  removable: {
    type: Boolean,
    default: false
  },
  bgColor: {
    type: String,
    default: ''
  },
  textColor: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['remove'])

const tagClass = computed(() => `tag-${props.type}`)

const handleRemove = () => {
  emit('remove', props.label)
}
</script>

<style lang="scss" scoped>
.tag {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 13px;

  &.tag-default {
    background: #DBEAFE;
    color: #2563EB;
  }

  &.tag-success {
    background: #D1FAE5;
    color: #059669;
  }

  &.tag-warning {
    background: #FEF3C7;
    color: #D97706;
  }

  &.tag-error {
    background: #FEE2E2;
    color: #DC2626;
  }

  &.tag-info {
    background: #EDE9FE;
    color: #7C3AED;
  }

  &.removable {
    padding-right: 8px;
  }

  .remove-tag {
    background: none;
    border: none;
    color: inherit;
    cursor: pointer;
    padding: 2px;
    border-radius: 50%;

    &:hover {
      background: rgba(0, 0, 0, 0.1);
    }
  }
}
</style>
