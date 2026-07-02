<template>
  <div class="approval-flow-timeline">
    <div
      v-for="(step, index) in steps"
      :key="step.stepKey"
      class="flow-step"
      :class="[
        `flow-step--${step.status}`,
        { 'flow-step--last': index === steps.length - 1 }
      ]"
    >
      <div class="flow-step__rail">
        <div class="flow-step__node">
          <el-icon v-if="step.status === 'completed'" :size="16"><Select /></el-icon>
          <el-icon v-else-if="step.status === 'active'" :size="14"><Loading /></el-icon>
          <span v-else class="flow-step__dot"></span>
        </div>
        <div v-if="index < steps.length - 1" class="flow-step__line"></div>
      </div>

      <div class="flow-step__content">
        <div class="flow-step__header">
          <span class="flow-step__name">{{ step.stepName }}</span>
          <span v-if="step.status === 'completed'" class="flow-step__badge flow-step__badge--completed">已完成</span>
          <span v-else-if="step.status === 'active'" class="flow-step__badge flow-step__badge--active">进行中</span>
          <span v-else class="flow-step__badge flow-step__badge--pending">待处理</span>
        </div>

        <div v-if="step.operator" class="flow-step__info">
          <div class="flow-step__row">
            <span class="flow-step__label">操作人</span>
            <span class="flow-step__value">{{ step.operator }}</span>
          </div>
          <div class="flow-step__row">
            <span class="flow-step__label">操作时间</span>
            <span class="flow-step__value">{{ step.operateTime }}</span>
          </div>
          <div v-if="step.action" class="flow-step__row">
            <span class="flow-step__label">操作</span>
            <span class="flow-step__value flow-step__action">{{ step.action }}</span>
          </div>
          <div v-if="step.comment" class="flow-step__row">
            <span class="flow-step__label">备注</span>
            <span class="flow-step__value flow-step__comment">{{ step.comment }}</span>
          </div>
        </div>

        <div v-if="step.modifications && step.modifications.length > 0" class="flow-step__modifications">
          <div class="modification-title">
            <el-icon :size="14"><EditPen /></el-icon>
            <span>修改记录 ({{ step.modifications.length }})</span>
          </div>
          <div
            v-for="(mod, mIndex) in step.modifications"
            :key="mIndex"
            class="modification-item"
          >
            <div class="modification-item__header">
              <span class="modification-item__modifier">{{ mod.modifier }}</span>
              <span class="modification-item__time">{{ mod.modifyTime }}</span>
              <el-button
                v-if="mod.textChanges && mod.textChanges.length > 0"
                type="primary"
                link
                size="small"
                class="modification-item__toggle"
                @click="toggleTrackChanges(`${index}-${mIndex}`)"
              >
                {{ expandedChanges[`${index}-${mIndex}`] ? '收起修订' : '查看修订' }}
              </el-button>
            </div>
            <div class="modification-item__content">{{ mod.modifyContent }}</div>
            <div v-if="mod.textChanges && mod.textChanges.length > 0 && expandedChanges[`${index}-${mIndex}`]" class="modification-item__track-changes">
              <TrackChanges :textChanges="mod.textChanges" />
            </div>
          </div>
        </div>

        <div v-if="step.attachments && step.attachments.length > 0" class="flow-step__attachments">
          <div class="attachment-title">
            <el-icon :size="14"><Document /></el-icon>
            <span>附件记录 ({{ step.attachments.length }})</span>
          </div>
          <div
            v-for="(att, aIndex) in step.attachments"
            :key="aIndex"
            class="attachment-item"
          >
            <div class="attachment-item__icon">
              <el-icon :size="16"><Document /></el-icon>
            </div>
            <div class="attachment-item__info">
              <span class="attachment-item__name">{{ att.fileName }}</span>
              <div class="attachment-item__meta">
                <span>上传人: {{ att.uploader }}</span>
                <span>上传时间: {{ att.uploadTime }}</span>
                <span>版本: {{ att.version }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Select, Loading, EditPen, Document } from '@element-plus/icons-vue'
import TrackChanges from '@/components/common/TrackChanges.vue'

defineProps({
  steps: {
    type: Array,
    default: () => []
  }
})

const expandedChanges = ref({})

const toggleTrackChanges = (key) => {
  expandedChanges.value[key] = !expandedChanges.value[key]
}
</script>

<style lang="scss" scoped>
.approval-flow-timeline {
  padding: 8px 0;
}

.flow-step {
  display: flex;
  gap: 16px;
  min-height: 60px;

  &--last {
    .flow-step__content {
      padding-bottom: 0;
    }
  }
}

.flow-step__rail {
  display: flex;
  flex-direction: column;
  align-items: center;
  flex-shrink: 0;
  width: 28px;
}

.flow-step__node {
  width: 28px;
  height: 28px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  z-index: 1;

  .flow-step--completed & {
    background: #10B981;
    color: #fff;
  }

  .flow-step--active & {
    background: #3B82F6;
    color: #fff;
    animation: pulse 2s infinite;
  }

  .flow-step--pending & {
    background: #F3F4F6;
    border: 2px solid #D1D5DB;
  }
}

.flow-step__dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: #D1D5DB;
}

.flow-step__line {
  width: 2px;
  flex: 1;
  min-height: 20px;

  .flow-step--completed & {
    background: #10B981;
  }

  .flow-step--active & {
    background: linear-gradient(to bottom, #3B82F6, #E5E7EB);
  }

  .flow-step--pending & {
    background: #E5E7EB;
  }
}

.flow-step__content {
  flex: 1;
  padding-bottom: 24px;
}

.flow-step__header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 8px;
}

.flow-step__name {
  font-size: 15px;
  font-weight: 600;
  color: #1F2937;

  .flow-step--pending & {
    color: #9CA3AF;
  }
}

.flow-step__badge {
  font-size: 11px;
  padding: 2px 8px;
  border-radius: 10px;
  font-weight: 500;

  &--completed {
    background: #D1FAE5;
    color: #059669;
  }

  &--active {
    background: #DBEAFE;
    color: #2563EB;
  }

  &--pending {
    background: #F3F4F6;
    color: #9CA3AF;
  }
}

.flow-step__info {
  background: #F9FAFB;
  border-radius: 8px;
  padding: 12px;
  margin-bottom: 8px;
}

.flow-step__row {
  display: flex;
  align-items: flex-start;
  gap: 8px;
  margin-bottom: 6px;

  &:last-child {
    margin-bottom: 0;
  }
}

.flow-step__label {
  font-size: 12px;
  color: #9CA3AF;
  flex-shrink: 0;
  width: 56px;
}

.flow-step__value {
  font-size: 12px;
  color: #4B5563;
}

.flow-step__action {
  color: #3B82F6;
  font-weight: 500;
}

.flow-step__comment {
  color: #6B7280;
  line-height: 1.5;
}

.flow-step__modifications {
  margin-top: 8px;
}

.modification-title {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  font-weight: 500;
  color: #6B7280;
  margin-bottom: 8px;
  padding: 6px 10px;
  background: #FFF7ED;
  border-radius: 6px;
  border-left: 3px solid #F59E0B;
}

.modification-item {
  background: #FFFBEB;
  border-radius: 6px;
  padding: 10px 12px;
  margin-bottom: 6px;
  border: 1px solid #FEF3C7;

  &:last-child {
    margin-bottom: 0;
  }
}

.modification-item__header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 4px;
  gap: 8px;
}

.modification-item__toggle {
  flex-shrink: 0;
  font-size: 12px !important;
  padding: 0 !important;
  margin-left: auto;
}

.modification-item__modifier {
  font-size: 12px;
  font-weight: 600;
  color: #92400E;
}

.modification-item__time {
  font-size: 11px;
  color: #B45309;
}

.modification-item__content {
  font-size: 12px;
  color: #78350F;
  line-height: 1.5;
}

.modification-item__track-changes {
  margin-top: 8px;
  padding-top: 8px;
  border-top: 1px dashed #FDE68A;
}

.flow-step__attachments {
  margin-top: 8px;
}

.attachment-title {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  font-weight: 500;
  color: #6B7280;
  margin-bottom: 8px;
  padding: 6px 10px;
  background: #EFF6FF;
  border-radius: 6px;
  border-left: 3px solid #3B82F6;
}

.attachment-item {
  display: flex;
  gap: 10px;
  background: #F0F9FF;
  border-radius: 6px;
  padding: 10px 12px;
  margin-bottom: 6px;
  border: 1px solid #DBEAFE;

  &:last-child {
    margin-bottom: 0;
  }
}

.attachment-item__icon {
  width: 32px;
  height: 32px;
  background: #DBEAFE;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #3B82F6;
  flex-shrink: 0;
}

.attachment-item__info {
  flex: 1;
  min-width: 0;
}

.attachment-item__name {
  font-size: 13px;
  font-weight: 500;
  color: #1E40AF;
  display: block;
  margin-bottom: 4px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.attachment-item__meta {
  display: flex;
  gap: 12px;
  font-size: 11px;
  color: #6B7280;
  flex-wrap: wrap;
}

@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(59, 130, 246, 0.4);
  }
  70% {
    box-shadow: 0 0 0 8px rgba(59, 130, 246, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(59, 130, 246, 0);
  }
}
</style>