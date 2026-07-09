﻿﻿﻿﻿﻿﻿<template>
  <el-drawer
    title="案例详情"
    :model-value="visible"
    direction="rtl"
    size="40%"
    :before-close="handleClose"
    @update:model-value="handleVisibleChange"
    :close-on-click-modal="true"
    :modal="true"
    :append-to-body="true"
    class="detail-drawer-right"
  >
    <div class="detail-drawer-content">
      <div class="detail-section">
        <div class="section-header">
          <span class="section-icon">📋</span>
          <h3>基本信息</h3>
        </div>
        <div class="detail-row">
          <span class="detail-label">创建人</span>
          <el-input v-if="isEditing" v-model="data.creator" placeholder="请输入创建人" />
          <span v-else class="detail-value">{{ data.creator || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">创建时间</span>
          <el-input v-if="isEditing" v-model="data.createTime" placeholder="请输入创建时间" />
          <span v-else class="detail-value">{{ data.createTime || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">事故/事件发生时间</span>
          <el-input v-if="isEditing" v-model="data.occurTime" placeholder="请输入发生时间" />
          <span v-else class="detail-value">{{ data.occurTime || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">事故/事件地点</span>
          <el-input v-if="isEditing" v-model="data.location" placeholder="请输入地点" />
          <span v-else class="detail-value">{{ data.location || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">案例类型</span>
          <el-input v-if="isEditing" v-model="data.caseType" placeholder="请输入案例类型" />
          <span v-else class="detail-value">{{ data.caseType || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">案例标题</span>
          <el-input v-if="isEditing" v-model="data.title" placeholder="请输入案例标题" />
          <span v-else class="detail-value">{{ data.title || '-' }}</span>
        </div>
      </div>

      <div class="detail-section">
        <div class="section-header">
          <span class="section-icon">🌄</span>
          <h3>场景描述</h3>
        </div>
        <div class="detail-row">
          <span class="detail-label">道路描述</span>
          <el-input v-if="isEditing" v-model="data.roadDescription" placeholder="请输入道路描述" />
          <span v-else class="detail-value">{{ data.roadDescription || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">照明条件</span>
          <el-input v-if="isEditing" v-model="data.lightingCondition" placeholder="请输入照明条件" />
          <span v-else class="detail-value">{{ data.lightingCondition || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">天气条件</span>
          <el-input v-if="isEditing" v-model="data.weatherCondition" placeholder="请输入天气条件" />
          <span v-else class="detail-value">{{ data.weatherCondition || '-' }}</span>
        </div>
      </div>

      <div class="detail-section">
        <div class="section-header">
          <span class="section-icon">🚗</span>
          <h3>车辆与智驾</h3>
        </div>
        <div class="detail-row">
          <span class="detail-label">感知方案</span>
          <el-input v-if="isEditing" v-model="data.perceptionScheme" placeholder="请输入感知方案" />
          <span v-else class="detail-value">{{ data.perceptionScheme || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">事故车型</span>
          <el-input v-if="isEditing" v-model="data.vehicleType" placeholder="请输入事故车型" />
          <span v-else class="detail-value">{{ data.vehicleType || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">组合辅助驾驶功能</span>
          <el-input v-if="isEditing" v-model="data.adaptiveFunction" placeholder="请输入组合辅助驾驶功能" />
          <span v-else class="detail-value">{{ data.adaptiveFunction || '-' }}</span>
        </div>
      </div>

      <div class="detail-section">
        <div class="section-header">
          <span class="section-icon">📝</span>
          <h3>事故事件详情</h3>
        </div>
        <div class="detail-row">
          <span class="detail-label">过程描述</span>
          <el-input v-if="isEditing" v-model="data.processDescription" type="textarea" placeholder="请输入过程描述" :rows="3" />
          <span v-else class="detail-value">{{ data.processDescription || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">致因人工初判</span>
          <el-input v-if="isEditing" v-model="data.causeInitialJudgment" type="textarea" placeholder="请输入致因人工初判" :rows="2" />
          <span v-else class="detail-value">{{ data.causeInitialJudgment || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">事故/事件</span>
          <el-input v-if="isEditing" v-model="data.accidentType" placeholder="请输入事故/事件" />
          <span v-else class="detail-value">{{ data.accidentType || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">致因标签</span>
          <el-input v-if="isEditing" v-model="data.causeTag" placeholder="请输入致因标签" />
          <span v-else class="detail-value">{{ data.causeTag || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">等级</span>
          <el-input v-if="isEditing" v-model="data.level" placeholder="请输入等级" />
          <span v-else class="detail-value">{{ data.level || '-' }}</span>
        </div>
      </div>

      <div class="detail-section">
        <div class="section-header">
          <span class="section-icon">📎</span>
          <h3>媒体与附件</h3>
        </div>
        <div class="detail-row">
          <span class="detail-label">媒体链接</span>
          <el-input v-if="isEditing" v-model="data.mediaLink" placeholder="请输入媒体链接" />
          <span v-else class="detail-value">{{ data.mediaLink || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">图片/视频</span>
          <el-input v-if="isEditing" v-model="data.mediaFile" placeholder="请输入图片/视频路径" />
          <span v-else class="detail-value">{{ data.mediaFile || '-' }}</span>
        </div>
      </div>
    </div>
    <template #footer>
      <template v-if="!isEditing">
        <el-button @click="handleClose">关闭</el-button>
        <el-button type="primary" @click="handleEdit">编辑</el-button>
      </template>
      <template v-else>
        <el-button @click="handleCancel">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </template>
  </el-drawer>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  rowData: {
    type: Object,
    default: () => ({})
  },
  readonly: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['update:visible', 'close', 'save'])

const defaultData = {
  id: '',
  creator: '',
  createTime: '',
  occurTime: '',
  location: '',
  title: '',
  roadDescription: '',
  lightingCondition: '',
  weatherCondition: '',
  perceptionScheme: '',
  vehicleType: '',
  adaptiveFunction: '',
  processDescription: '',
  causeInitialJudgment: '',
  accidentType: '',
  caseType: '',
  causeTag: '',
  level: '',
  mediaLink: '',
  mediaFile: ''
}

const data = ref({ ...defaultData })
const isEditing = ref(false)
const backupData = ref(null)

watch(() => props.rowData, (newData) => {
  if (newData) {
    data.value = {
      id: newData.id || '',
      creator: newData.reporter || '',
      createTime: newData.reportTime || '',
      occurTime: newData.occurTime || '',
      location: newData.location || '',
      title: newData.name || '',
      roadDescription: newData.roadType || '',
      lightingCondition: '',
      weatherCondition: newData.weather || '',
      perceptionScheme: '',
      vehicleType: newData.vehicleType || '',
      adaptiveFunction: '',
      processDescription: '',
      causeInitialJudgment: '',
      accidentType: newData.accidentType || '',
      caseType: newData.caseType || '',
      causeTag: '',
      level: newData.accidentLevel || '',
      mediaLink: '',
      mediaFile: ''
    }
  }
}, { immediate: true })


const handleClose = () => {
  isEditing.value = false
  emit('update:visible', false)
  emit('close')
}

const handleVisibleChange = (val) => {
  emit('update:visible', val)
}

const handleEdit = () => {
  backupData.value = { ...data.value }
  isEditing.value = true
}

const handleCancel = () => {
  if (backupData.value) {
    data.value = { ...backupData.value }
    backupData.value = null
  }
  isEditing.value = false
}

const handleSave = () => {
  isEditing.value = false
  backupData.value = null
  emit('save', { ...data.value })
  emit('update:visible', false)
}
</script>

<style lang="scss">
.detail-drawer-right {
  right: 0 !important;
  width: 40% !important;
  z-index: 2000 !important;
}
</style>

<style lang="scss" scoped>
.detail-drawer-content {
  padding: 8px 0;
  max-height: calc(100vh - 140px);
  overflow-y: auto;
}

.detail-section {
  background: #f9fafb;
  border-radius: 12px;
  padding: 16px;
  margin-bottom: 16px;

  .section-header {
    display: flex;
    align-items: center;
    margin-bottom: 14px;
    padding-bottom: 10px;
    border-bottom: 1px solid #e5e7eb;

    .section-icon {
      font-size: 16px;
      margin-right: 8px;
    }

    h3 {
      font-size: 14px;
      font-weight: 600;
      color: #1f2937;
      margin: 0;
    }
  }

  .detail-row {
    display: flex;
    flex-direction: column;
    gap: 6px;
    margin-bottom: 12px;

    &:last-child {
      margin-bottom: 0;
    }

    .detail-label {
      font-size: 12px;
      font-weight: 500;
      color: #4b5563;
    }

    .detail-value {
      font-size: 13px;
      color: #1f2937;
      line-height: 1.6;
      padding: 4px 0;
      word-break: break-all;
    }

    .el-input,
    .el-textarea {
      font-size: 12px;
    }
  }
}
</style>