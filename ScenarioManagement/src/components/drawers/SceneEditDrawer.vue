<template>
  <el-drawer
    v-model="drawerVisible"
    direction="rtl"
    :size="drawerSize"
    :close-on-click-modal="false"
    :modal="false"
    :append-to-body="true"
    @close="handleClose"
    class="scene-edit-drawer"
  >
    <template #header>
      <div class="drawer-header-custom">
        <span class="drawer-title">{{ drawerTitle }}</span>
        <div class="header-actions">
          <el-button
            v-if="showTrackModeBtn"
            :type="trackMode ? 'warning' : 'default'"
            size="small"
            @click="trackMode = !trackMode"
          >
            {{ trackMode ? '退出修订模式' : '修订模式' }}
          </el-button>
          <el-button type="default" size="small" @click="toggleDataPanel">{{ showDataPanel ? '隐藏数据' : '查看数据' }}</el-button>
        </div>
      </div>
    </template>
    <div class="split-panel-container">
      <div class="split-panel-left" :class="{ 'has-right-panel': showDataPanel }">
        <div class="detail-content">
          <div class="detail-header">
            <div class="header-left">
              <span class="scene-id-lg">{{ editData.id }}</span>
              <span class="scene-version-lg">{{ editData.version }}</span>
            </div>
            <span class="storage-badge">入库时间: {{ editData.storageTime }}</span>
          </div>

          <el-input v-model="editData.name" class="scene-title-input" placeholder="请输入场景名称" :disabled="isReadonly" />

          <div class="detail-tags">
            <el-input v-if="!isReadonly" v-model="tagInput" class="tag-input" placeholder="输入标签后按回车添加" @keyup.enter="addTag" />
            <span v-for="(tag, index) in editData.tags" :key="index" class="tag-lg">
              {{ tag }}
              <span v-if="!isReadonly" class="tag-close" @click="removeTag(index)"><Close /></span>
            </span>
          </div>

          <div class="detail-section">
            <h3>（1）场景描述</h3>
          </div>

          <div class="detail-section">
            <h4>场景标签</h4>
            <el-input v-model="editData.sceneTags" type="textarea" :rows="2" placeholder="请输入场景标签" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('sceneTags').length" :textChanges="getFieldChanges('sceneTags')" class="field-track-changes" />
          </div>

          <div class="detail-section">
            <h4>数据来源</h4>
            <el-input v-model="editData.dataSource" type="textarea" :rows="2" placeholder="请输入数据来源" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('dataSource').length" :textChanges="getFieldChanges('dataSource')" class="field-track-changes" />
          </div>

          <div class="detail-section">
            <h4>设计原因</h4>
            <el-input v-model="editData.designReason" type="textarea" :rows="4" placeholder="请输入设计原因" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('designReason').length" :textChanges="getFieldChanges('designReason')" class="field-track-changes" />
          </div>

          <div class="detail-section">
            <h4>测试场景</h4>
            <el-input v-model="editData.testScene" type="textarea" :rows="6" placeholder="请输入测试场景描述" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('testScene').length" :textChanges="getFieldChanges('testScene')" class="field-track-changes" />
            <img :src="imageSrc" alt="测试场景示意图" class="section-image" />
            <el-input v-model="editData.generalization" type="textarea" :rows="2" placeholder="请输入场景泛化要求" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('generalization').length" :textChanges="getFieldChanges('generalization')" class="field-track-changes" />
          </div>

          <div class="detail-section">
            <h4>泛化要素</h4>
            <div class="table-container">
              <div class="table-row table-header">
                <div class="table-cell">泛化要素</div>
                <div class="table-cell">要素参数</div>
                <div class="table-cell">操作</div>
              </div>
              <div v-for="(item, index) in editData.generalizationItems" :key="index" class="table-row">
                <el-input v-model="item.name" class="table-input" placeholder="要素名称" :disabled="isReadonly" />
                <el-input v-model="item.params" class="table-input" placeholder="要素参数" :disabled="isReadonly" />
                <el-button v-if="!isReadonly" type="primary" link @click="removeGeneralizationItem(index)">删除</el-button>
              </div>
              <el-button v-if="!isReadonly" type="primary" link @click="addGeneralizationItem">添加要素</el-button>
            </div>
          </div>

          <div class="detail-section">
            <h4>测试方法</h4>
            <el-input v-model="editData.testMethod" type="textarea" :rows="2" placeholder="请输入测试方法" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('testMethod').length" :textChanges="getFieldChanges('testMethod')" class="field-track-changes" />
          </div>

          <div class="detail-section">
            <h3>（2）场景测试目标</h3>
            <el-input v-model="editData.testObjective" type="textarea" :rows="3" placeholder="请输入场景测试目标" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('testObjective').length" :textChanges="getFieldChanges('testObjective')" class="field-track-changes" />
          </div>

          <div class="detail-section">
            <h3>（3）试验场地及试验环境</h3>
            <h4>1)封闭场地测试及模拟仿真测试试验道路环境应满足以下要求：</h4>
            <el-input v-model="editData.environmentRoad" type="textarea" :rows="5" placeholder="请输入道路环境要求" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('environmentRoad').length" :textChanges="getFieldChanges('environmentRoad')" class="field-track-changes" />
            <h4>2)模拟仿真试验环境应满足以下要求：</h4>
            <el-input v-model="editData.environmentSimulation" type="textarea" :rows="4" placeholder="请输入模拟仿真环境要求" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('environmentSimulation').length" :textChanges="getFieldChanges('environmentSimulation')" class="field-track-changes" />
            <h4>3)封闭场地试验环境应满足以下要求：</h4>
            <el-input v-model="editData.environmentField" type="textarea" :rows="6" placeholder="请输入封闭场地环境要求" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('environmentField').length" :textChanges="getFieldChanges('environmentField')" class="field-track-changes" />
            <h4>4)具备试验车辆组合驾驶辅助系统正常激活的必要数据和设施条件。</h4>
          </div>

          <div class="detail-section">
            <h3>（4）试验设备及数据采集</h3>
            <h4>1)目标物</h4>
            <el-input v-model="editData.targetObject" type="textarea" :rows="2" placeholder="请输入目标物描述" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('targetObject').length" :textChanges="getFieldChanges('targetObject')" class="field-track-changes" />
            <h4>2)试验设备要求</h4>
            <h4>a)模拟仿真测试试验设备满足以下要求：</h4>
            <el-input v-model="editData.equipmentSimulation" type="textarea" :rows="3" placeholder="请输入模拟仿真设备要求" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('equipmentSimulation').length" :textChanges="getFieldChanges('equipmentSimulation')" class="field-track-changes" />
            <h4>b)封闭场地测试试验设备满足以下要求：</h4>
            <el-input v-model="editData.equipmentField" type="textarea" :rows="5" placeholder="请输入封闭场地设备要求" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('equipmentField').length" :textChanges="getFieldChanges('equipmentField')" class="field-track-changes" />
            <h4>3)试验记录内容</h4>
            <el-input v-model="editData.recordContent" type="textarea" :rows="10" placeholder="请输入试验记录内容" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('recordContent').length" :textChanges="getFieldChanges('recordContent')" class="field-track-changes" />
          </div>

          <div class="detail-section">
            <h3>（5）试验车辆要求</h3>
            <h4>1)试验车辆满足以下人机交互要求：</h4>
            <el-input v-model="editData.vehicleInteraction" type="textarea" :rows="2" placeholder="请输入人机交互要求" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('vehicleInteraction').length" :textChanges="getFieldChanges('vehicleInteraction')" class="field-track-changes" />
            <h4>2)试验车辆满足以下载荷要求：</h4>
            <el-input v-model="editData.vehicleLoad" type="textarea" :rows="2" placeholder="请输入载荷要求" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('vehicleLoad').length" :textChanges="getFieldChanges('vehicleLoad')" class="field-track-changes" />
            <h4>3)试验车辆设置满足以下要求：</h4>
            <el-input v-model="editData.vehicleSettings" type="textarea" :rows="4" placeholder="请输入车辆设置要求" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('vehicleSettings').length" :textChanges="getFieldChanges('vehicleSettings')" class="field-track-changes" />
          </div>

          <div class="detail-section">
            <h3>（6）试验方法</h3>
            <el-input v-model="editData.testProcedure" type="textarea" :rows="3" placeholder="请输入试验方法" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('testProcedure').length" :textChanges="getFieldChanges('testProcedure')" class="field-track-changes" />
          </div>

          <div class="detail-section">
            <h3>（7）通过要求</h3>
            <el-input v-model="editData.passCriteria" type="textarea" :rows="2" placeholder="请输入通过要求" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('passCriteria').length" :textChanges="getFieldChanges('passCriteria')" class="field-track-changes" />
          </div>

          <div class="detail-section">
            <h3>（8）结束条件</h3>
            <el-input v-model="editData.endConditions" type="textarea" :rows="4" placeholder="请输入结束条件" :disabled="trackMode || isReadonly" />
            <TrackChanges v-if="trackMode && getFieldChanges('endConditions').length" :textChanges="getFieldChanges('endConditions')" class="field-track-changes" />
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
          </template>
          <div v-else class="case-empty">暂无案例数据</div>
        </div>
      </div>
    </div>

    <template #footer>
      <div class="drawer-footer">
        <el-button v-if="showCancelBtn" @click="handleCancel">取消</el-button>
        <el-button v-if="showSaveBtn" type="primary" @click="handleSave">保存修改</el-button>
        <el-button v-if="showSubmitBtn" type="primary" @click="handleSubmit">{{ submitBtnText }}</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { Close, Document, Location, Van, Connection, Loading } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import TrackChanges from '@/components/common/TrackChanges.vue'
import StatusBadge from '@/components/common/StatusBadge.vue'
import { getApprovalFlowBySceneId, saveApprovalModification, advanceApprovalStep } from '@/api/approvalFlow'
import { submitSceneDesign } from '@/api/sceneDesign'
import { getSceneDesignDetail, saveSceneDesignDetail } from '@/api/sceneDesignDetail'
import { getAccidentDataById } from '@/api/accidentData'
import { getSceneDesigns } from '@/api/sceneDesign'

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  sceneData: {
    type: Object,
    default: () => null
  }
})

const emit = defineEmits(['update:visible', 'close', 'save', 'approve'])

const trackMode = ref(false)
const sceneFlowData = ref(null)

const sceneStatus = computed(() => {
  return props.sceneData?.status || ''
})

const isReadonly = computed(() => {
  return sceneStatus.value === '已完成'
})

const showTrackModeBtn = computed(() => {
  return sceneStatus.value === '修改中' || sceneStatus.value === '审核中' || sceneStatus.value === '待验证'
})

const showCancelBtn = computed(() => {
  return sceneStatus.value !== '已完成'
})

const showSaveBtn = computed(() => {
  return sceneStatus.value !== '已完成'
})

const showSubmitBtn = computed(() => {
  return sceneStatus.value !== '已完成'
})

const submitBtnText = computed(() => {
  if (sceneStatus.value === '设计中') {
    return '确认提交'
  }
  return '审批通过'
})

const fieldKeyMap = {
  '测试场景': 'testScene',
  '场景描述': 'testScene',
  '场景标签': 'sceneTags',
  '数据来源': 'dataSource',
  '设计原因': 'designReason',
  '场景泛化要求': 'generalization',
  '泛化要素-行人速度': 'generalization',
  '泛化要素-行人类型': 'generalization',
  '泛化要素-横穿距离': 'generalization',
  '测试方法': 'testMethod',
  '场景测试目标': 'testObjective',
  '封闭场地环境': 'environmentField',
  '道路环境': 'environmentRoad',
  '模拟仿真环境': 'environmentSimulation',
  '目标物': 'targetObject',
  '模拟仿真设备': 'equipmentSimulation',
  '封闭场地设备': 'equipmentField',
  '试验记录内容': 'recordContent',
  '人机交互': 'vehicleInteraction',
  '载荷要求': 'vehicleLoad',
  '车辆设置': 'vehicleSettings',
  '试验方法': 'testProcedure',
  '通过要求': 'passCriteria',
  '结束条件': 'endConditions'
}

const getFieldChanges = (fieldKey) => {
  if (!props.sceneData || !props.sceneData.id || !sceneFlowData.value) return []
  const flowData = sceneFlowData.value
  const allChanges = []
  for (const step of flowData.steps) {
    if (step.modifications) {
      for (const mod of step.modifications) {
        if (mod.textChanges) {
          for (const tc of mod.textChanges) {
            const mappedKey = fieldKeyMap[tc.field]
            if (mappedKey === fieldKey) {
              allChanges.push({
                field: tc.field,
                segments: tc.segments.map(seg => ({
                  ...seg,
                  author: seg.author || mod.modifier,
                  time: seg.time || mod.modifyTime
                }))
              })
            }
          }
        }
      }
    }
  }
  return allChanges
}

const drawerVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val)
})

const drawerTitle = computed(() => {
  return '编辑场景'
})

const drawerSize = computed(() => {
  return showDataPanel.value ? '80%' : '55%'
})

const showDataPanel = ref(false)
const caseData = ref(null)
const caseLoading = ref(false)
const allScenes = ref([])

const toggleDataPanel = async () => {
  showDataPanel.value = !showDataPanel.value
  if (showDataPanel.value) {
    const dcid = props.sceneData?.dcid
    if (dcid) {
      caseLoading.value = true
      try {
        caseData.value = await getAccidentDataById(dcid)
      } catch {
        caseData.value = null
      } finally {
        caseLoading.value = false
      }
      try {
        allScenes.value = await getSceneDesigns()
      } catch {
        allScenes.value = []
      }
    }
  } else {
    caseData.value = null
  }
}

const relatedScenes = computed(() => {
  if (!caseData.value || !caseData.value.id) return []
  return allScenes.value.filter(item => item.dcid === caseData.value.id)
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

const tagInput = ref('')

const imageSrc = computed(() => {
  return new URL('@/image/图片1.png', import.meta.url).href
})

const defaultEditData = {
  id: '',
  version: 'v1.0',
  storageTime: '',
  name: '',
  tags: [],
  sceneTags: '',
  dataSource: '',
  designReason: '',
  testScene: '',
  generalization: '',
  generalizationItems: [
    { name: '', params: '' }
  ],
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
const originalEditData = ref(null)

const fieldLabelMap = {
  name: '场景名称',
  sceneTags: '场景标签',
  dataSource: '数据来源',
  designReason: '设计原因',
  testScene: '测试场景',
  generalization: '场景泛化要求',
  testMethod: '测试方法',
  testObjective: '场景测试目标',
  environmentRoad: '道路环境',
  environmentSimulation: '模拟仿真环境',
  environmentField: '封闭场地环境',
  targetObject: '目标物',
  equipmentSimulation: '模拟仿真设备',
  equipmentField: '封闭场地设备',
  recordContent: '试验记录内容',
  vehicleInteraction: '人机交互',
  vehicleLoad: '载荷要求',
  vehicleSettings: '车辆设置',
  testProcedure: '试验方法',
  passCriteria: '通过要求',
  endConditions: '结束条件'
}

const textFieldKeys = [
  'name', 'sceneTags', 'dataSource', 'designReason', 'testScene',
  'generalization', 'testMethod', 'testObjective', 'environmentRoad',
  'environmentSimulation', 'environmentField', 'targetObject', 'equipmentSimulation',
  'equipmentField', 'recordContent', 'vehicleInteraction', 'vehicleLoad',
  'vehicleSettings', 'testProcedure', 'passCriteria', 'endConditions'
]

function computeTextDiff(oldText, newText) {
  if (!oldText && !newText) return []
  oldText = oldText || ''
  newText = newText || ''
  if (oldText === newText) return []

  const segments = []
  let i = 0, j = 0
  while (i < oldText.length || j < newText.length) {
    if (i < oldText.length && j < newText.length && oldText[i] === newText[j]) {
      let start = i
      while (i < oldText.length && j < newText.length && oldText[i] === newText[j]) {
        i++; j++
      }
      segments.push({ type: 'equal', text: oldText.substring(start, i) })
    } else {
      let delStart = i
      while (i < oldText.length && (j >= newText.length || oldText[i] !== newText[j])) {
        i++
      }
      if (i > delStart) {
        segments.push({ type: 'delete', text: oldText.substring(delStart, i) })
      }
      let addStart = j
      while (j < newText.length && (i >= oldText.length || oldText[i] !== newText[j])) {
        j++
      }
      if (j > addStart) {
        segments.push({ type: 'add', text: newText.substring(addStart, j) })
      }
    }
  }
  return segments
}

function computeFieldChanges() {
  if (!originalEditData.value) return []
  const changes = []
  for (const key of textFieldKeys) {
    const oldVal = originalEditData.value[key] || ''
    const newVal = editData.value[key] || ''
    if (oldVal !== newVal) {
      const segments = computeTextDiff(String(oldVal), String(newVal))
      if (segments.length > 0) {
        changes.push({
          field: fieldLabelMap[key] || key,
          segments
        })
      }
    }
  }
  return changes
}

async function refreshFlowData() {
  if (props.sceneData?.id) {
    try {
      const response = await getApprovalFlowBySceneId(props.sceneData.id, 'scene')
      const flowDataObj = JSON.parse(response.flowData || '{}')
      sceneFlowData.value = {
        flowType: response.flowType,
        currentStep: response.currentStep,
        steps: flowDataObj.steps || []
      }
    } catch (e) {
      sceneFlowData.value = null
    }
  }
}

watch(() => props.sceneData, async (newData) => {
  showDataPanel.value = false
  caseData.value = null
  if (newData && newData.id) {
    try {
      const detail = await getSceneDesignDetail(newData.id)
      editData.value = {
        ...defaultEditData,
        id: newData.id || '',
        version: detail.version || 'v1.0',
        storageTime: detail.storageTime || '',
        name: detail.name || newData.name || '',
        tags: detail.tags ? detail.tags.split(',').filter(Boolean) : (newData.tags ? [...newData.tags] : []),
        sceneTags: detail.sceneTags || '',
        dataSource: detail.dataSource || '',
        designReason: detail.designReason || '',
        testScene: detail.testScene || '',
        generalization: detail.generalization || '',
        generalizationItems: detail.generalizationItems ? JSON.parse(detail.generalizationItems) : [{ name: '', params: '' }],
        testMethod: detail.testMethod || '',
        testObjective: detail.testObjective || '',
        environmentRoad: detail.environmentRoad || '',
        environmentSimulation: detail.environmentSimulation || '',
        environmentField: detail.environmentField || '',
        targetObject: detail.targetObject || '',
        equipmentSimulation: detail.equipmentSimulation || '',
        equipmentField: detail.equipmentField || '',
        recordContent: detail.recordContent || '',
        vehicleInteraction: detail.vehicleInteraction || '',
        vehicleLoad: detail.vehicleLoad || '',
        vehicleSettings: detail.vehicleSettings || '',
        testProcedure: detail.testProcedure || '',
        passCriteria: detail.passCriteria || '',
        endConditions: detail.endConditions || ''
      }
      originalEditData.value = JSON.parse(JSON.stringify(editData.value))
    } catch {
      editData.value = { ...defaultEditData }
      originalEditData.value = null
    }
  } else {
    editData.value = { ...defaultEditData }
    originalEditData.value = null
  }
}, { immediate: true, deep: true })

watch(() => props.sceneData?.id, async (newId) => {
  if (newId) {
    try {
      const response = await getApprovalFlowBySceneId(newId, 'scene')
      const flowDataObj = JSON.parse(response.flowData || '{}')
      sceneFlowData.value = {
        flowType: response.flowType,
        currentStep: response.currentStep,
        steps: flowDataObj.steps || []
      }
    } catch (e) {
      sceneFlowData.value = null
    }
  } else {
    sceneFlowData.value = null
  }
}, { immediate: true })

const addTag = () => {
  if (tagInput.value.trim() && !editData.value.tags.includes(tagInput.value.trim())) {
    editData.value.tags.push(tagInput.value.trim())
    tagInput.value = ''
  }
}

const removeTag = (index) => {
  editData.value.tags.splice(index, 1)
}

const addGeneralizationItem = () => {
  editData.value.generalizationItems.push({ name: '', params: '' })
}

const removeGeneralizationItem = (index) => {
  if (editData.value.generalizationItems.length > 1) {
    editData.value.generalizationItems.splice(index, 1)
  }
}

const handleClose = () => {
  emit('close')
}

const handleCancel = () => {
  drawerVisible.value = false
}

const handleSave = async () => {
  try {
    await saveSceneDesignDetail(editData.value.id, {
      id: editData.value.id,
      version: editData.value.version,
      storageTime: editData.value.storageTime,
      name: editData.value.name,
      tags: editData.value.tags.join(','),
      sceneTags: editData.value.sceneTags,
      dataSource: editData.value.dataSource,
      designReason: editData.value.designReason,
      testScene: editData.value.testScene,
      generalization: editData.value.generalization,
      generalizationItems: JSON.stringify(editData.value.generalizationItems),
      testMethod: editData.value.testMethod,
      testObjective: editData.value.testObjective,
      environmentRoad: editData.value.environmentRoad,
      environmentSimulation: editData.value.environmentSimulation,
      environmentField: editData.value.environmentField,
      targetObject: editData.value.targetObject,
      equipmentSimulation: editData.value.equipmentSimulation,
      equipmentField: editData.value.equipmentField,
      recordContent: editData.value.recordContent,
      vehicleInteraction: editData.value.vehicleInteraction,
      vehicleLoad: editData.value.vehicleLoad,
      vehicleSettings: editData.value.vehicleSettings,
      testProcedure: editData.value.testProcedure,
      passCriteria: editData.value.passCriteria,
      endConditions: editData.value.endConditions
    })

    const fieldChanges = computeFieldChanges()
    if (fieldChanges.length > 0) {
      const now = new Date().toLocaleString('zh-CN', { hour12: false })
      await saveApprovalModification(editData.value.id, {
        flowType: 'scene',
        modifier: '当前用户',
        modifyTime: now,
        modifyContent: '保存修改，共修改' + fieldChanges.length + '个字段',
        fieldChanges: JSON.stringify(fieldChanges)
      })
      await refreshFlowData()
    }

    ElMessage.success('保存成功')
  } catch (e) {
    ElMessage.error('保存失败: ' + (e.message || '未知错误'))
  }
  emit('save', { ...editData.value })
  drawerVisible.value = false
}

const handleSubmit = async () => {
  if (sceneStatus.value === '设计中') {
    try {
      await submitSceneDesign(editData.value.id, '审核中')
      try {
        const now = new Date().toLocaleString('zh-CN', { hour12: false })
        await advanceApprovalStep(editData.value.id, {
          flowType: 'scene',
          newStatus: '审核中',
          operator: '当前用户',
          comment: '提交设计审核'
        })
        await refreshFlowData()
      } catch (flowErr) {
        console.warn('审批流更新失败，状态已提交:', flowErr.message)
      }
      ElMessage.success('已提交审核')
      emit('save', { ...editData.value })
      drawerVisible.value = false
    } catch (e) {
      ElMessage.error('提交失败: ' + (e.message || '未知错误'))
    }
  } else if (sceneStatus.value === '审核中') {
    ElMessageBox.prompt('请填写操作说明', '审批通过确认', {
      confirmButtonText: '确认通过',
      cancelButtonText: '取消',
      inputType: 'textarea',
      inputPlaceholder: '请输入操作说明...',
      inputValidator: (value) => {
        if (!value || !value.trim()) {
          return '操作说明不能为空'
        }
        return true
      }
    }).then(async ({ value }) => {
      try {
        await submitSceneDesign(editData.value.id, '待验证')
        try {
          const now = new Date().toLocaleString('zh-CN', { hour12: false })
          await advanceApprovalStep(editData.value.id, {
            flowType: 'scene',
            newStatus: '待验证',
            operator: '当前用户',
            comment: value.trim()
          })
          await refreshFlowData()
        } catch (flowErr) {
          console.warn('审批流更新失败，状态已提交:', flowErr.message)
        }
        emit('approve', { ...editData.value, approveRemark: value.trim() })
        drawerVisible.value = false
        ElMessage.success('审批通过，状态已更新为待验证')
      } catch (e) {
        ElMessage.error('操作失败: ' + (e.message || '未知错误'))
      }
    }).catch(() => {})
  } else if (sceneStatus.value === '待验证') {
    ElMessageBox.prompt('请填写操作说明', '审批通过确认', {
      confirmButtonText: '确认通过',
      cancelButtonText: '取消',
      inputType: 'textarea',
      inputPlaceholder: '请输入操作说明...',
      inputValidator: (value) => {
        if (!value || !value.trim()) {
          return '操作说明不能为空'
        }
        return true
      }
    }).then(async ({ value }) => {
      try {
        await submitSceneDesign(editData.value.id, '已完成')
        try {
          const now = new Date().toLocaleString('zh-CN', { hour12: false })
          await advanceApprovalStep(editData.value.id, {
            flowType: 'scene',
            newStatus: '已完成',
            operator: '当前用户',
            comment: value.trim()
          })
          await refreshFlowData()
        } catch (flowErr) {
          console.warn('审批流更新失败，状态已提交:', flowErr.message)
        }
        emit('approve', { ...editData.value, approveRemark: value.trim() })
        drawerVisible.value = false
        ElMessage.success('审批通过，场景设计已完成，已同步到用例开发和危险场景库')
      } catch (e) {
        ElMessage.error('操作失败: ' + (e.message || '未知错误'))
      }
    }).catch(() => {})
  } else {
    emit('save', { ...editData.value })
    drawerVisible.value = false
  }
}
</script>

<style lang="scss">
.scene-edit-drawer {
  right: 0 !important;
  z-index: 2001 !important;
}

.scene-edit-drawer .el-drawer__body {
  padding: 0;
  overflow: hidden;
}

.scene-edit-drawer.el-overlay {
  pointer-events: none !important;
}

.scene-edit-drawer .el-drawer {
  pointer-events: auto !important;
}
</style>

<style lang="scss" scoped>
.drawer-header-custom {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;

  .drawer-title {
    font-size: 16px;
    font-weight: 600;
    color: #1f2937;
  }

  .header-actions {
    display: flex;
    gap: 8px;
  }
}

.detail-content {
  padding: 20px 0;
  height: calc(100vh - 160px);
  overflow-y: auto;
}

.split-panel-container {
  display: flex;
  height: calc(100vh - 160px);
  overflow: hidden;
}

.split-panel-left {
  flex: 1;
  min-width: 0;
  overflow: hidden;

  &.has-right-panel {
    border-right: 1px solid #e5e7eb;
  }

  .detail-content {
    padding: 20px;
    height: 100%;
    overflow-y: auto;
  }
}

.split-panel-right {
  flex: 1;
  min-width: 0;
  overflow-y: auto;
  background: #FAFBFC;
}

.case-detail-content {
  padding: 16px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.case-loading {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 40px 0;
  color: #6B7280;
  font-size: 14px;
}

.case-empty {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 0;
  color: #9CA3AF;
  font-size: 14px;
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
      text-indent: 0;
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
  gap: 12px;
  padding: 16px 20px;
  border-top: 1px solid #e5e7eb;
}

.detail-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;

  .header-left {
    display: flex;
    gap: 12px;

    .scene-id-lg {
      font-size: 16px;
      font-weight: 700;
      color: #3B82F6;
    }

    .scene-version-lg {
      font-size: 14px;
      color: #9CA3AF;
      background: #F3F4F6;
      padding: 4px 12px;
      border-radius: 6px;
    }
  }

  .storage-badge {
    font-size: 14px;
    color: #6B7280;
  }
}

.scene-title-input {
  font-size: 20px;
  font-weight: 600;
  color: #1f2937;
  margin-bottom: 12px;
}

.detail-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 20px;
  align-items: center;

  .tag-input {
    width: 150px;
  }

  .tag-lg {
    background: #DBEAFE;
    color: #2563EB;
    padding: 6px 14px;
    border-radius: 20px;
    font-size: 14px;
    display: flex;
    align-items: center;
    gap: 6px;

    .tag-close {
      cursor: pointer;
      font-size: 12px;
    }
  }
}

.detail-section {
  margin-bottom: 20px;

  h3 {
    font-size: 16px;
    font-weight: 600;
    color: #374151;
    margin: 0 0 8px 0;
  }

  h4 {
    font-size: 14px;
    font-weight: 600;
    color: #374151;
    margin: 0 0 8px 0;
    text-indent: 2em;
  }

  .section-image {
    display: block;
    max-width: 400px;
    height: auto;
    margin: 12px auto 12px;
    border-radius: 8px;
  }

  .field-track-changes {
    margin-top: 8px;
    padding: 8px;
    background: #FFFBEB;
    border-radius: 8px;
    border: 1px solid #FEF3C7;
  }

  .table-container {
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    overflow: hidden;
    margin-top: 8px;

    .table-row {
      display: flex;
      border-bottom: 1px solid #e5e7eb;

      &:last-child {
        border-bottom: none;
      }

      &.table-header {
        background: #f9fafb;

        .table-cell {
          font-weight: 600;
          color: #374151;
        }
      }

      .table-cell,
      .table-input {
        flex: 1;
        padding: 10px 12px;
        border-right: 1px solid #e5e7eb;

        &:last-child {
          border-right: none;
        }
      }

      .table-input {
        border: none;
        background: transparent;
        padding: 10px 12px;
      }
    }
  }
}
</style>