<template>
  <button 
    class="action-btn" 
    :class="[buttonClass, { disabled: disabled }]"
    @click="handleClick"
  >
    <el-icon v-if="icon" :size="iconSize"><component :is="icon" /></el-icon>
    {{ text }}
  </button>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  text: {
    type: String,
    required: true
  },
  type: {
    type: String,
    default: 'default'
  },
  icon: {
    type: Object,
    default: null
  },
  iconSize: {
    type: Number,
    default: 16
  },
  disabled: {
    type: Boolean,
    default: false
  },
  size: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['click'])

const buttonClass = computed(() => {
  const classes = [`btn-${props.type}`]
  if (props.size === 'small' || props.size === 'sm') classes.push('btn-sm')
  if (props.size === 'large' || props.size === 'lg') classes.push('btn-lg')
  return classes
})

const handleClick = () => {
  if (!props.disabled) {
    emit('click')
  }
}
</script>

<style lang="scss" scoped>
.action-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  padding: 6px 12px;
  border: none;
  border-radius: 6px;
  font-size: 12px;
  cursor: pointer;
  transition: all 0.2s;

  &.btn-default {
    background: #F3F4F6;
    color: #6B7280;

    &:hover {
      background: #E5E7EB;
    }
  }

  &.btn-primary {
    background: #3B82F6;
    color: #fff;

    &:hover {
      background: #2563EB;
    }
  }

  &.btn-success {
    background: #059669;
    color: #fff;

    &:hover {
      background: #047857;
    }
  }

  &.btn-danger {
    background: #DC2626;
    color: #fff;

    &:hover {
      background: #B91C1C;
    }
  }

  &.btn-warning {
    background: #D97706;
    color: #fff;

    &:hover {
      background: #B45309;
    }
  }

  &.btn-info {
    background: #7C3AED;
    color: #fff;

    &:hover {
      background: #6D28D9;
    }
  }

  &.btn-sm {
    padding: 3px 8px;
    font-size: 11px;
  }

  &.btn-lg {
    padding: 10px 20px;
    font-size: 14px;
  }

  &.disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
}
</style>