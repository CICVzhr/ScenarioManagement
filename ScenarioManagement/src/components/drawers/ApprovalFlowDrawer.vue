<template>
  <el-drawer
    v-model="drawerVisible"
    title="设计审批流程记录"
    direction="rtl"
    size="480px"
    :close-on-click-modal="true"
    @close="handleClose"
  >
    <div class="approval-flow-drawer">
      <div class="flow-scene-info">
        <div class="scene-info__header">
          <span class="scene-info__id">{{ sceneData?.id }}</span>
          <StatusBadge :status="sceneData?.status" />
        </div>
        <h3 class="scene-info__name">{{ sceneData?.name }}</h3>
        <div v-if="flowData" class="scene-info__current">
          <span class="current-label">当前步骤</span>
          <span class="current-step">{{ flowData.currentStep }}</span>
        </div>
      </div>

      <div class="flow-progress" v-if="flowData">
        <div class="progress-bar">
          <div
            v-for="(step, index) in flowData.steps"
            :key="step.stepKey"
            class="progress-step"
            :class="{
              'progress-step--completed': step.status === 'completed',
              'progress-step--active': step.status === 'active'
            }"
          >
            <div class="progress-step__dot"></div>
            <span class="progress-step__label">{{ step.stepName }}</span>
            <div v-if="index < flowData.steps.length - 1" class="progress-step__connector"></div>
          </div>
        </div>
      </div>

      <div class="flow-timeline-section">
        <h4 class="section-title">流程记录</h4>
        <ApprovalFlowTimeline v-if="flowData" :steps="flowData.steps" />
        <div v-else class="no-data">
          <el-icon :size="48" color="#D1D5DB"><Document /></el-icon>
          <p>暂无审批流程记录</p>
        </div>
      </div>
    </div>
  </el-drawer>
</template>

<script setup>
import { computed } from 'vue'
import { Document } from '@element-plus/icons-vue'
import StatusBadge from '@/components/common/StatusBadge.vue'
import ApprovalFlowTimeline from '@/components/common/ApprovalFlowTimeline.vue'

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  sceneData: {
    type: Object,
    default: () => null
  },
  flowData: {
    type: Object,
    default: () => null
  }
})

const emit = defineEmits(['update:visible', 'close'])

const drawerVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val)
})

const handleClose = () => {
  emit('close')
}
</script>

<style lang="scss" scoped>
.approval-flow-drawer {
  padding: 0 4px;
  height: calc(100vh - 60px);
  overflow-y: auto;
}

.flow-scene-info {
  background: linear-gradient(135deg, #EFF6FF 0%, #DBEAFE 100%);
  border-radius: 12px;
  padding: 16px;
  margin-bottom: 20px;
}

.scene-info__header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 8px;
}

.scene-info__id {
  font-size: 13px;
  font-weight: 600;
  color: #3B82F6;
  background: #fff;
  padding: 2px 10px;
  border-radius: 12px;
}

.scene-info__name {
  font-size: 16px;
  font-weight: 600;
  color: #1E40AF;
  margin: 0 0 10px 0;
}

.scene-info__current {
  display: flex;
  align-items: center;
  gap: 8px;
}

.current-label {
  font-size: 12px;
  color: #6B7280;
}

.current-step {
  font-size: 13px;
  font-weight: 600;
  color: #2563EB;
  background: #fff;
  padding: 2px 10px;
  border-radius: 10px;
}

.flow-progress {
  margin-bottom: 20px;
}

.progress-bar {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  position: relative;
  padding: 0 4px;
}

.progress-step {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  flex: 1;
}

.progress-step__dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: #E5E7EB;
  border: 2px solid #D1D5DB;
  z-index: 1;
  margin-bottom: 6px;

  .progress-step--completed & {
    background: #10B981;
    border-color: #10B981;
  }

  .progress-step--active & {
    background: #3B82F6;
    border-color: #3B82F6;
    box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
  }
}

.progress-step__label {
  font-size: 11px;
  color: #9CA3AF;
  text-align: center;
  white-space: nowrap;

  .progress-step--completed & {
    color: #059669;
    font-weight: 500;
  }

  .progress-step--active & {
    color: #2563EB;
    font-weight: 600;
  }
}

.progress-step__connector {
  position: absolute;
  top: 5px;
  left: calc(50% + 8px);
  right: calc(-50% + 8px);
  height: 2px;
  background: #E5E7EB;

  .progress-step--completed & {
    background: #10B981;
  }
}

.flow-timeline-section {
  background: #fff;
  border-radius: 12px;
  padding: 16px;
  border: 1px solid #F3F4F6;
}

.section-title {
  font-size: 14px;
  font-weight: 600;
  color: #1F2937;
  margin: 0 0 12px 0;
  padding-bottom: 8px;
  border-bottom: 1px solid #F3F4F6;
}

.no-data {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 0;
  gap: 12px;

  p {
    font-size: 14px;
    color: #9CA3AF;
    margin: 0;
  }
}
</style>