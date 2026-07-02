<template>
  <span class="status-badge" :class="statusClass">{{ status }}</span>
</template>

<script setup>
import { computed } from 'vue'
import { getStatusClass } from '@/utils'

const props = defineProps({
  status: {
    type: String,
    required: true
  }
})

const statusClass = computed(() => getStatusClass(props.status))
</script>

<style lang="scss" scoped>
.status-badge {
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;

  &.status-pending {
    background: #FEF3C7;
    color: #D97706;
  }

  &.status-success {
    background: #D1FAE5;
    color: #059669;
  }

  &.status-error {
    background: #FEE2E2;
    color: #DC2626;
  }

  &.status-processing {
    background: #DBEAFE;
    color: #2563EB;
  }

  &.status-warning {
    background: #FEF3C7;
    color: #D97706;
  }

  &.status-review {
    background: #EDE9FE;
    color: #7C3AED;
  }

  &.status-modifying {
    background: #FFEDD5;
    color: #EA580C;
  }

  &.status-developing {
    background: #E0F2FE;
    color: #0284C7;
  }

  &.status-default {
    background: #F3F4F6;
    color: #6B7280;
  }
}
</style>