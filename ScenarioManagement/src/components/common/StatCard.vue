<template>
  <div class="stat-card" :class="cardClass">
    <div class="stat-icon" :class="iconClass">{{ icon }}</div>
    <div class="stat-info">
      <div class="stat-value">{{ value }}</div>
      <div class="stat-label">{{ label }}</div>
    </div>
    <div class="stat-change" :class="changeClass">{{ change }}</div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  icon: {
    type: String,
    default: '📊'
  },
  value: {
    type: [String, Number],
    default: '0'
  },
  label: {
    type: String,
    default: ''
  },
  change: {
    type: String,
    default: ''
  },
  type: {
    type: String,
    default: 'blue'
  }
})

const cardClass = computed(() => `stat-card-${props.type}`)
const iconClass = computed(() => `icon-${props.type}`)
const changeClass = computed(() => props.change.includes('+') ? 'positive' : 'negative')
</script>

<style lang="scss" scoped>
.stat-card {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);

  .stat-icon {
    width: 56px;
    height: 56px;
    border-radius: 14px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 28px;
    margin-right: 16px;

    &.icon-blue { background: linear-gradient(135deg, #DBEAFE 0%, #BFDBFE 100%); }
    &.icon-green { background: linear-gradient(135deg, #D1FAE5 0%, #A7F3D0 100%); }
    &.icon-orange { background: linear-gradient(135deg, #FEF3C7 0%, #FDE68A 100%); }
    &.icon-purple { background: linear-gradient(135deg, #EDE9FE 0%, #DDD6FE 100%); }
  }

  .stat-info {
    flex: 1;

    .stat-value {
      font-size: 28px;
      font-weight: 700;
      color: #1f2937;
    }

    .stat-label {
      font-size: 14px;
      color: #6b7280;
      margin-top: 4px;
    }
  }

  .stat-change {
    font-size: 12px;
    padding: 4px 8px;
    border-radius: 12px;

    &.positive {
      background: #ECFDF5;
      color: #059669;
    }

    &.negative {
      background: #FEF2F2;
      color: #DC2626;
    }
  }
}
</style>
