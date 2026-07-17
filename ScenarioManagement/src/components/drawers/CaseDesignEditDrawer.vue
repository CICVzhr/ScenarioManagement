<template>
  <el-drawer
    v-model="drawerVisible"
    direction="rtl"
    :size="drawerSize"
    :close-on-click-modal="false"
    class="case-design-edit-drawer"
  >
    <template #header>
      <div class="drawer-header-custom">
        <span class="drawer-title">用例设计 - {{ editData.name }}</span>
        <div class="header-actions">
          <el-button type="default" size="small" @click="toggleDataPanel">{{ showDataPanel ? '隐藏数据' : '查看数据' }}</el-button>
        </div>
      </div>
    </template>
    <div class="split-panel-container">
      <div class="split-panel-left" :class="{ 'has-right-panel': showDataPanel }">
        <div class="detail-content" v-loading="detailLoading">
          <div v-if="!detailLoading && editData.id" class="detail-form">
            <div class="detail-header">
              <div class="header-left">
                <span class="scene-id-lg">{{ editData.id }}</span>
                <span class="scene-version-lg">{{ editData.version || 'v1.0' }}</span>
              </div>
              <span class="storage-badge">创建时间: {{ editData.createTime || editData.storageTime }}</span>
            </div>

            <el-input v-model="editData.name" class="scene-title-input" placeholder="用例名称" disabled />

            <div class="detail-section readonly-section">
              <h3>（1）场景描述</h3>
              <div class="readonly-notice"><el-icon><InfoFilled /></el-icon> 以下内容为场景设计阶段已完成，仅可查看</div>

              <h4>场景标签</h4>
              <el-input v-model="editData.sceneTags" type="textarea" :rows="2" placeholder="场景标签" disabled />

              <h4>数据来源</h4>
              <el-input v-model="editData.dataSource" type="textarea" :rows="2" placeholder="数据来源" disabled />

              <h4>设计原因</h4>
              <el-input v-model="editData.designReason" type="textarea" :rows="4" placeholder="设计原因" disabled />

              <h4>测试场景</h4>
              <el-input v-model="editData.testScene" type="textarea" :rows="6" placeholder="测试场景描述" disabled />
              <img :src="imageSrc" alt="测试场景示意图" class="section-image" />
              <el-input v-model="editData.generalization" type="textarea" :rows="2" placeholder="场景泛化要求" disabled />
            </div>

            <div class="detail-section readonly-section">
              <h3>（2）场景测试目标</h3>
              <el-input v-model="editData.testObjective" type="textarea" :rows="3" placeholder="场景测试目标" disabled />
            </div>

            <div class="detail-section readonly-section">
              <h3>（3）试验场地及试验环境</h3>
              <h4>1)封闭场地测试及模拟仿真测试试验道路环境应满足以下要求：</h4>
              <el-input v-model="editData.environmentRoad" type="textarea" :rows="5" placeholder="道路环境要求" disabled />
              <h4>2)模拟仿真试验环境应满足以下要求：</h4>
              <el-input v-model="editData.environmentSimulation" type="textarea" :rows="4" placeholder="模拟仿真环境要求" disabled />
              <h4>3)封闭场地试验环境应满足以下要求：</h4>
              <el-input v-model="editData.environmentField" type="textarea" :rows="6" placeholder="封闭场地环境要求" disabled />
              <h4>4)具备试验车辆组合驾驶辅助系统正常激活的必要数据和设施条件。</h4>
            </div>

            <div class="detail-section readonly-section">
              <h3>（4）试验设备及数据采集</h3>
              <h4>1)目标物</h4>
              <el-input v-model="editData.targetObject" type="textarea" :rows="2" placeholder="目标物描述" disabled />
              <h4>2)试验设备要求</h4>
              <h4>a)模拟仿真测试试验设备满足以下要求：</h4>
              <el-input v-model="editData.equipmentSimulation" type="textarea" :rows="3" placeholder="模拟仿真设备要求" disabled />
              <h4>b)封闭场地测试试验设备满足以下要求：</h4>
              <el-input v-model="editData.equipmentField" type="textarea" :rows="5" placeholder="封闭场地设备要求" disabled />
              <h4>3)试验记录内容</h4>
              <el-input v-model="editData.recordContent" type="textarea" :rows="10" placeholder="试验记录内容" disabled />
            </div>

            <div class="detail-section readonly-section">
              <h3>（5）试验车辆要求</h3>
              <h4>1)试验车辆满足以下人机交互要求：</h4>
              <el-input v-model="editData.vehicleInteraction" type="textarea" :rows="2" placeholder="人机交互要求" disabled />
              <h4>2)试验车辆满足以下载荷要求：</h4>
              <el-input v-model="editData.vehicleLoad" type="textarea" :rows="2" placeholder="载荷要求" disabled />
              <h4>3)试验车辆设置满足以下要求：</h4>
              <el-input v-model="editData.vehicleSettings" type="textarea" :rows="4" placeholder="车辆设置要求" disabled />
            </div>

            <div class="detail-section editable-section">
              <h3>（6）试验方法</h3>
              <div class="editable-notice"><el-icon><Edit /></el-icon> 以下为用例设计编辑区域</div>
              <el-input v-model="editData.testProcedure" type="textarea" :rows="3" placeholder="请输入试验方法" :disabled="isReadonly" />
            </div>

            <div class="detail-section editable-section">
              <h3>（7）通过要求</h3>
              <el-input v-model="editData.passCriteria" type="textarea" :rows="2" placeholder="请输入通过要求" :disabled="isReadonly" />
            </div>

            <div class="detail-section editable-section">
              <h3>（8）结束条件</h3>
              <el-input v-model="editData.endConditions" type="textarea" :rows="4" placeholder="请输入结束条件" :disabled="isReadonly" />
            </div>
          </div>
        </div>
      </div>

      <div v-if="showDataPanel" class="split-panel-right">
        <div class="case-detail-content">
          <div v-if="caseLoading" class="case-loading">
            <el-icon class="is-loading" :size="24"><Loading /></el-icon>
            <span>加载案例数据中...</span>
          </div>
          <template v-else-if="caseData">
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
                  <span class="info-label">事故等级</span>
                  <span class="info-value">{{ caseData.accidentLevel || '-' }}</span>
                </div>
              </div>
            </div>
          </template>
          <div v-else class="case-empty">暂无案例数据</div>
        </div>
      </div>
    </div>

    <template #footer>
      <div class="drawer-footer">
        <el-button @click="handleClose">关闭</el-button>
        <el-button v-if="!isReadonly" type="primary" @click="handleSave">保存修改</el-button>
        <el-button v-if="canSubmit" type="success" @click="handleSubmit">设计完成</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { Document, Loading, InfoFilled, Edit } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import StatusBadge from '@/components/common/StatusBadge.vue'
import { getSceneDesignFullById, updateSceneDesign, advanceInPhase } from '@/api/sceneDesign'
import { getAccidentDataById } from '@/api/accidentData'

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  caseData: {
    type: Object,
    default: () => null
  }
})

const emit = defineEmits(['update:visible', 'close', 'save', 'submit'])

const drawerVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val)
})

const showDataPanel = ref(false)
const detailLoading = ref(false)
const caseLoading = ref(false)
const accidentData = ref(null)

const imageSrc = ref('')

const defaultEditData = {
  id: '',
  version: 'v1.0',
  storageTime: '',
  name: '',
  tags: '',
  sceneTags: '',
  dataSource: '',
  designReason: '',
  testScene: '',
  generalization: '',
  generalizationItems: '',
  testMethod: '',
  testObjective: '',
  environmentRoad: '',
  environmentSimulation: '',
  environmentField: '',
  targetObject: '',
  equipmentSimulation: '',
  equipmentField: '',
  recordContent: '',
  vehicleInteraction: '',
  vehicleLoad: '',
  vehicleSettings: '',
  testProcedure: '',
  passCriteria: '',
  endConditions: ''
}

const editData = ref({ ...defaultEditData })

const drawerSize = computed(() => showDataPanel.value ? '85%' : '60%')

const isReadonly = computed(() => {
  return props.caseData?.status === '已完成' || props.caseData?.status === '审批中'
})

const canSubmit = computed(() => {
  return props.caseData?.status === '设计中'
})

const toggleDataPanel = () => {
  showDataPanel.value = !showDataPanel.value
  if (showDataPanel.value && props.caseData?.dcid) {
    loadAccidentData()
  }
}

const loadDetail = async () => {
  if (!props.caseData?.id) return

  detailLoading.value = true
  try {
    const fullData = await getSceneDesignFullById(props.caseData.id)
    if (fullData) {
      editData.value = { ...defaultEditData, ...fullData }
    } else {
      editData.value = { ...defaultEditData, id: props.caseData.id, name: props.caseData.name }
    }
  } catch (e) {
    console.error('Failed to load detail:', e)
    ElMessage.error('加载详情失败')
  } finally {
    detailLoading.value = false
  }
}

const loadAccidentData = async () => {
  if (!props.caseData?.dcid) return

  caseLoading.value = true
  try {
    const data = await getAccidentDataById(props.caseData.dcid)
    accidentData.value = data
  } catch (e) {
    console.error('Failed to load accident data:', e)
  } finally {
    caseLoading.value = false
  }
}

const handleSave = async () => {
  try {
    await updateSceneDesign(props.caseData.id, editData.value)
    ElMessage.success('保存成功')
    emit('save', editData.value)
  } catch (e) {
    ElMessage.error('保存失败: ' + e.message)
  }
}

const handleSubmit = async () => {
  try {
    const result = await advanceInPhase(props.caseData.id, 'submit_review')
    ElMessage.success('提交成功，状态已更新')
    emit('submit', result)
    emit('update:visible', false)
  } catch (e) {
    ElMessage.error('提交失败: ' + e.message)
  }
}

const handleClose = () => {
  emit('update:visible', false)
  emit('close')
}

watch(() => props.visible, (val) => {
  if (val) {
    showDataPanel.value = false
    loadDetail()
  }
}, { immediate: true })
</script>

<style lang="scss" scoped>
.case-design-edit-drawer {
  .split-panel-container {
    display: flex;
    height: calc(100vh - 120px);
    gap: 16px;
    padding: 0 20px;
  }

  .split-panel-left {
    flex: 1;
    overflow-y: auto;
    padding-right: 16px;

    &.has-right-panel {
      flex: 1.2;
    }
  }

  .split-panel-right {
    width: 400px;
    min-width: 350px;
    border-left: 1px solid #e5e7eb;
    padding-left: 16px;
    overflow-y: auto;
  }

  .detail-content {
    height: 100%;
  }

  .detail-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;
    padding-bottom: 12px;
    border-bottom: 1px solid #e5e7eb;

    .header-left {
      display: flex;
      gap: 12px;
      align-items: center;
    }

    .scene-id-lg {
      font-size: 18px;
      font-weight: 600;
      color: #1f2937;
    }

    .scene-version-lg {
      font-size: 14px;
      color: #6b7280;
      background: #f3f4f6;
      padding: 2px 8px;
      border-radius: 4px;
    }

    .storage-badge {
      font-size: 13px;
      color: #9ca3af;
    }
  }

  .scene-title-input {
    margin-bottom: 20px;

    :deep(.el-input__inner) {
      font-size: 18px;
      font-weight: 500;
    }
  }

  .detail-section {
    margin-bottom: 24px;

    h3 {
      font-size: 15px;
      font-weight: 600;
      color: #374151;
      margin-bottom: 12px;
      padding-bottom: 8px;
      border-bottom: 1px solid #e5e7eb;
    }

    h4 {
      font-size: 13px;
      font-weight: 500;
      color: #4b5563;
      margin: 12px 0 8px;
    }

    &.readonly-section {
      background: #f9fafb;
      padding: 16px;
      border-radius: 8px;
      border: 1px solid #e5e7eb;
    }

    &.editable-section {
      background: #eff6ff;
      padding: 16px;
      border-radius: 8px;
      border: 1px solid #bfdbfe;
    }
  }

  .readonly-notice,
  .editable-notice {
    display: flex;
    align-items: center;
    gap: 6px;
    font-size: 12px;
    padding: 8px 12px;
    border-radius: 6px;
    margin-bottom: 12px;
  }

  .readonly-notice {
    background: #fef3c7;
    color: #92400e;
  }

  .editable-notice {
    background: #d1fae5;
    color: #065f46;
  }

  .section-image {
    max-width: 100%;
    max-height: 200px;
    object-fit: contain;
    border-radius: 8px;
    margin: 12px 0;
    border: 1px solid #e5e7eb;
  }

  .case-detail-content {
    .case-loading,
    .case-empty {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 200px;
      color: #9ca3af;
      gap: 12px;
    }

    .detail-card {
      background: #fff;
      border-radius: 10px;
      padding: 16px;
      margin-bottom: 16px;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);

      .card-header {
        display: flex;
        align-items: center;
        gap: 8px;
        margin-bottom: 14px;
        padding-bottom: 10px;
        border-bottom: 1px solid #f3f4f6;

        h3 {
          font-size: 14px;
          font-weight: 600;
          color: #374151;
          margin: 0;
        }
      }

      .info-grid {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 10px;
      }

      .info-item {
        display: flex;
        flex-direction: column;
        gap: 2px;
      }

      .info-label {
        font-size: 11px;
        color: #9ca3af;
      }

      .info-value {
        font-size: 13px;
        color: #374151;
        font-weight: 500;
      }
    }
  }

  .drawer-footer {
    display: flex;
    justify-content: flex-end;
    gap: 12px;
    padding: 16px 20px;
    border-top: 1px solid #e5e7eb;
  }
}
</style>