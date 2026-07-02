<template>
  <div class="progress-bar-wrapper">
    <div class="progress-header">
      <span class="progress-label">{{ label }}</span>
      <span class="progress-value">{{ value }}%</span>
    </div>
    <div class="progress-bar">
      <div class="progress-fill" :style="{ width: value + '%' }"></div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  value: {
    type: Number,
    default: 0
  },
  label: {
    type: String,
    default: '进度'
  }
})
</script>

<style lang="scss" scoped>
.progress-bar-wrapper {
  width: 100%;
}

.progress-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;

  .progress-label {
    font-size: 13px;
    color: #6B7280;
  }

  .progress-value {
    font-size: 13px;
    font-weight: 600;
    color: #3B82F6;
  }
}

.progress-bar {
  height: 8px;
  background: #E5E7EB;
  border-radius: 4px;
  overflow: hidden;

  .progress-fill {
    height: 100%;
    background: linear-gradient(90deg, #3B82F6 0%, #60A5FA 100%);
    border-radius: 4px;
    transition: width 0.3s ease;
  }
}
</style>
