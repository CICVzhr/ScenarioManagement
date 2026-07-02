<template>
  <el-drawer
    v-model="drawerVisible"
    title="案例详情"
    direction="rtl"
    size="40%"
    :close-on-click-modal="false"
    :modal="false"
    :append-to-body="true"
    class="case-detail-drawer"
    @close="handleClose"
  >
    <div class="case-detail-content">
      <div class="detail-card">
        <div class="card-header">
          <el-icon :size="18" color="#3B82F6"><Document /></el-icon>
          <h3>基本信息</h3>
        </div>
        <div class="info-grid">
          <div class="info-item">
            <span class="info-label">案例ID</span>
            <span class="info-value">{{ caseData.id || '-' }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">案例标题</span>
            <span class="info-value">{{ caseData.name || '-' }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">创建人</span>
            <span class="info-value">{{ caseData.reporter || '-' }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">创建时间</span>
            <span class="info-value">{{ caseData.reportTime || '-' }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">事故/事件发生时间</span>
            <span class="info-value">{{ caseData.occurTime || '-' }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">事故/事件地点</span>
            <span class="info-value">{{ caseData.location || '-' }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">事故/事件类型</span>
            <span class="info-value">{{ caseData.accidentType || '-' }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">事故等级</span>
            <span class="info-value">
              <span v-if="caseData.accidentLevel" :class="getLevelClass(caseData.accidentLevel)">{{ caseData.accidentLevel }}</span>
              <span v-else>-</span>
            </span>
          </div>
          <div class="info-item">
            <span class="info-label">审核状态</span>
            <span class="info-value">
              <StatusBadge v-if="caseData.status" :status="caseData.status" />
              <span v-else>-</span>
            </span>
          </div>
        </div>
      </div>

      <div class="detail-card">
        <div class="card-header">
          <el-icon :size="18" color="#10B981"><Location /></el-icon>
          <h3>场景信息</h3>
        </div>
        <div class="info-grid">
          <div class="info-item">
            <span class="info-label">道路描述</span>
            <span class="info-value">{{ caseData.roadType || '-' }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">天气条件</span>
            <span class="info-value">{{ caseData.weather || '-' }}</span>
          </div>
        </div>
      </div>

      <div class="detail-card">
        <div class="card-header">
          <el-icon :size="18" color="#F59E0B"><Van /></el-icon>
          <h3>车辆与系统</h3>
        </div>
        <div class="info-grid">
          <div class="info-item">
            <span class="info-label">事故车型</span>
            <span class="info-value">{{ caseData.vehicleType || '-' }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">系统版本</span>
            <span class="info-value">{{ caseData.systemVersion || '-' }}</span>
          </div>
        </div>
      </div>

      <div class="detail-card">
        <div class="card-header">
          <el-icon :size="18" color="#8B5CF6"><Connection /></el-icon>
          <h3>关联场景</h3>
        </div>
        <div class="related-scenes">
          <div v-if="relatedScenes.length > 0" class="scene-list">
            <div
              v-for="scene in relatedScenes"
              :key="scene.id"
              class="scene-item"
            >
              <span class="scene-id">{{ scene.id }}</span>
              <span class="scene-name">{{ scene.name }}</span>
              <StatusBadge :status="scene.status" />
            </div>
          </div>
          <div v-else class="empty-tip">暂无关联场景</div>
        </div>
      </div>
    </div>

    <template #footer>
      <div class="drawer-footer">
        <el-button @click="handleClose">关闭</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<script setup>
import { computed } from 'vue'
import { Document, Location, Van, Connection } from '@element-plus/icons-vue'
import StatusBadge from '@/components/common/StatusBadge.vue'
import { scenarioList } from '@/data/mockData'

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  caseData: {
    type: Object,
    default: () => ({})
  }
})

const emit = defineEmits(['update:visible', 'close'])

const drawerVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val)
})

const relatedScenes = computed(() => {
  if (!props.caseData || !props.caseData.id) return []
  return scenarioList.filter(item => item.dcid === props.caseData.id)
})

const getLevelClass = (level) => {
  const map = {
    '一般': 'level-normal',
    '较大': 'level-medium',
    '重大': 'level-major',
    '特别重大': 'level-critical'
  }
  return map[level] || 'level-normal'
}

const handleClose = () => {
  emit('close')
}
</script>

<style lang="scss">
.case-detail-drawer {
  right: 0 !important;
  width: 40% !important;
  z-index: 2000 !important;
}

.case-detail-drawer.el-overlay {
  pointer-events: none !important;
}

.case-detail-drawer .el-drawer {
  pointer-events: auto !important;
}

.scene-edit-drawer {
  z-index: 2001 !important;
}
</style>

<style lang="scss" scoped>
.case-detail-content {
  padding: 8px 0;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.detail-card {
  background: #F9FAFB;
  border-radius: 12px;
  padding: 20px;
  border: 1px solid #F3F4F6;

  .card-header {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: 16px;
    padding-bottom: 12px;
    border-bottom: 1px solid #E5E7EB;

    h3 {
      font-size: 15px;
      font-weight: 600;
      color: #1F2937;
      margin: 0;
    }
  }
}

.info-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.info-item {
  display: flex;
  flex-direction: column;
  gap: 4px;

  .info-label {
    font-size: 12px;
    font-weight: 500;
    color: #6B7280;
  }

  .info-value {
    font-size: 14px;
    color: #1F2937;
    word-break: break-all;
  }
}

.level-normal {
  background: #D1FAE5;
  color: #059669;
  padding: 2px 10px;
  border-radius: 6px;
  font-size: 12px;
  display: inline-block;
}

.level-medium {
  background: #FEF3C7;
  color: #D97706;
  padding: 2px 10px;
  border-radius: 6px;
  font-size: 12px;
  display: inline-block;
}

.level-major {
  background: #FECACA;
  color: #DC2626;
  padding: 2px 10px;
  border-radius: 6px;
  font-size: 12px;
  display: inline-block;
}

.level-critical {
  background: #FEE2E2;
  color: #B91C1C;
  padding: 2px 10px;
  border-radius: 6px;
  font-size: 12px;
  display: inline-block;
}

.related-scenes {
  .scene-list {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }

  .scene-item {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 10px 14px;
    background: #fff;
    border-radius: 8px;
    border: 1px solid #E5E7EB;

    .scene-id {
      font-size: 13px;
      font-weight: 600;
      color: #3B82F6;
      white-space: nowrap;
    }

    .scene-name {
      font-size: 13px;
      color: #374151;
      flex: 1;
    }
  }

  .empty-tip {
    font-size: 13px;
    color: #9CA3AF;
    text-align: center;
    padding: 20px 0;
  }
}

.drawer-footer {
  display: flex;
  justify-content: flex-end;
  padding: 12px 0;
}
</style>