<template>
  <el-drawer
    v-model="drawerVisible"
    direction="rtl"
    :size="drawerSize"
    :close-on-click-modal="false"
    :modal="false"
    :append-to-body="true"
    @close="handleClose"
    :class="['scene-edit-drawer', { 'is-shifted': dataDrawerVisible }]"
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
          <el-button type="default" size="small" @click="handleViewData">查看数据</el-button>
        </div>
      </div>
    </template>
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
import { Close } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import TrackChanges from '@/components/common/TrackChanges.vue'
import { getApprovalFlowBySceneId } from '@/api/approvalFlow'

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  sceneData: {
    type: Object,
    default: () => null
  },
  dataDrawerVisible: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['update:visible', 'close', 'save', 'view-data', 'approve'])

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
  if (sceneStatus.value === '审核中' || sceneStatus.value === '待验证') {
    return '审批通过'
  }
  return '确认提交'
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
  return '40%'
})

const handleViewData = () => {
  emit('view-data', editData.value)
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

watch(() => props.sceneData, (newData) => {
  if (newData) {
    editData.value = {
      ...defaultEditData,
      id: newData.id || '',
      version: newData.version || 'v1.0',
      storageTime: newData.storageTime || '',
      name: newData.name || '',
      tags: newData.tags ? [...newData.tags] : [],
      sceneTags: newData.sceneTags || '事故场景、ODD识别与响应能力测试。',
      dataSource: newData.dataSource || '行业事故场景-智驾。',
      designReason: newData.designReason || '事故涉及弱势交通参与者保护，危险性较高；类似事故在传统汽车事故中较为常见，对智驾系统有较大挑战性。',
      testScene: newData.testScene || '试验道路为至少包含由四条双向两车道的长直城区道路连接的丁字交叉口（可用十字路口代替），道路设计速度为60km/h，道路边界线为白实线，对向车流分隔线为黄实线单线或双线，同向车流分隔线为白虚线。路口设置停止线，停止线后方可设置人行横道。道路未设置中央隔离带，交叉口未设置信号灯或设置信号灯但处于全灭状态。\n十字路口右侧支路的道路右侧有一静止成人，成人所处位置距离最右侧车道中心线5m。当被测车辆距与成人的预测冲突点的预计行驶时间到达3s时，假人从静止位置以6km/h的速度横穿道路。日间。',
      generalization: newData.generalization || '场景泛化至少应考虑要素：天气变化、光线变化、行人类型变化（成人/儿童）及假人速度变化。',
      generalizationItems: newData.generalizationItems ? [...newData.generalizationItems] : [
        { name: '行人类型', params: '{成人,儿童}' },
        { name: '行人速度', params: '[5,7]km/h' },
        { name: '横穿距离', params: '[5,7]m' }
      ],
      testMethod: newData.testMethod || '本次深度测试拟采用测试方法：模拟仿真测试、封闭场地测试。',
      testObjective: newData.testObjective || '考察试验车辆在无信号灯路口下对横穿动态目标物的识别、理解与响应能力。',
      environmentRoad: newData.environmentRoad || 'a)试验路面要求压实并且无可能造成传感器异常工作的不规则物（例如大的倾角、裂缝、井盖或是具有反射能力的螺栓等）；\nb)试验路面干燥，表面无可见水分；\nc)试验道路车道宽度于3.5m～3.75m之间；\nd)试验道路的峰值附着系数不小于0.7；\ne)试验道路配备车道边线，且保证车道边线清晰可见，车道边线符合GB 5768.3的要求。',
      environmentSimulation: newData.environmentSimulation || 'a)温度：保持在20°C~25°C之间；\nb)相对湿度：保持在20%~50%之间；\nc)网络：具备千兆带宽满足大数据的传输，端到端传输时延≤10ms，全场景下丢包率≤0.1%；\nd)电源稳定性：供电电压精度稳定性在±0.5%~±1%，电流精度稳定性在±1%~±2%。',
      environmentField: newData.environmentField || 'a)天气干燥，除雨雾测试外没有结冰、积水、积雪等情况；\nb)环境温度位于-20℃～45℃之间；\nc)平均风速不大于5m/s，瞬时最大风速不大于10m/s；\nd)交通标志符合GB 5768.2和GB 5768.5的要求；\ne)雾天能见度等级分为1级（200m＜能见度≤500m）、2级（100m＜能见度≤200m）、3级（50m＜能见度≤100m）、4级（能见度≤50m）；\nf)雨天雨量等级分为1级（1h降雨量10mm/h～14.9 mm/h）、2级（1h降雨量15mm/h～29.9mm/h）、3级（1h降雨量30mm/h～49.9mm/h），4级（1h降雨量≥50mm/h）。',
      targetObject: newData.targetObject || '成人软目标物。',
      equipmentSimulation: newData.equipmentSimulation || '1)仿真测试系统需支持固定步长仿真，最大延迟时间应不超过1ms；\n2)仿真测试系统采集数据频率不小于20Hz；\n3)仿真测试系统采集数据格式为float，精度不大于0.001。',
      equipmentField: newData.equipmentField || '1)运动状态采集和存储的频率不小于50Hz；\n2)视频采集设备分辨率不小于(720×480)像素，视频采样帧率不小于30fps；\n3)除在隧道内，速度采集精度不大于0.1km/h；\n4)除在隧道内，距离采集精度不大于0.1m；\n5)除在隧道内，加速度采集精度不大于0.1m/s²。',
      recordContent: newData.recordContent || 'a)试验车辆组合驾驶辅助系统软、硬件版本信息；\nb)试验车辆的控制模式，例如人工驾驶模式、辅助驾驶模式等；\nc)试验车辆运动状态参数：\n1)试验时间轴；\n2)试验车辆位置信息；\n3)试验车辆纵向速度；\n4)试验车辆横向速度；\n5)试验车辆纵向加速度；\n6)试验车辆横向加速度。\nd)试验车辆灯光状态；\ne)试验操作人员及人机交互状态（试验操作人员面部、仪表盘、转向盘、中控屏、踏板等）的视频及语音监控信息；\nf)试验车辆前方的交通状态视频信息；\ng)若有目标物，记录目标物的状态参数：\n1)试验时间轴；\n2)目标物位置信息；\n3)目标物纵向速度；\n4)目标物横向速度；\n5)目标物纵向加速度；\n6)目标物横向加速度。',
      vehicleInteraction: newData.vehicleInteraction || 'a)具备便于人工激活和关闭组合驾驶辅助系统的操作方式；\nb)系统状态及人机转换过程提示信息清晰可见。',
      vehicleLoad: newData.vehicleLoad || 'a)试验车辆质量处于整车整备质量加上驾驶员、测试员和试验设备的总质量与最大允许总质量之间，试验开始后不改变试验车辆载荷状态。',
      vehicleSettings: newData.vehicleSettings || 'a)若系统的跟车时距可调，试验车辆调整为跟车距离最近的设置；\nb)试验车辆的换道灵敏度/积极性设置使用上电/点火后的系统初始档位；\nc)对于夜间试验，试验车辆灯光档位要求：\n1)若系统仅在前照灯AUTO设置下可激活，试验车辆前照灯调整为AUTO设置；\n2)若系统仅在近光灯下可激活，试验车辆设置为近光灯；\n3)若系统仅在远光灯下可激活，试验车辆设置为远光灯；\n4)若系统在近光灯、远光灯和/或前照灯AUTO设置下均可激活，试验车辆设置为近光灯。',
      testProcedure: newData.testProcedure || '1)预试验：试验车辆在十字交叉口前300m处开启组合驾驶辅助系统向前行驶，达到稳定速度至少30km/h，在没有任何干扰的情况下右转通过十字交叉口，记录车辆通过预测冲突点的时间和在到达预测冲突点3s前的位置。\n2)调整测试设备相关参数；\n3)正式试验：试验车辆在十字交叉口前300m处开启组合驾驶辅助系统向前行驶，达到稳定速度至少30km/h，放置成人目标物正式试验。',
      passCriteria: newData.passCriteria || '1)若系统不具备该场景下的行驶能力（超出ODC），应在进入危险区域前发出提示信息。量化判定依据：附录C.2中C.2.2.4节；\n2)若系统具备该场景下的行驶能力（在ODC内），则应满足附录C.2中C.2.2.2.3节的所有指标阈值要求；',
      endConditions: newData.endConditions || '1)试验车辆与任一目标发生碰撞；\n2)试验车辆发出立即控制警告或执行风险减缓功能；\n3)试验车辆停车10s未启动；\n4)试验车辆驶过测试路段至终点。'
    }
  } else {
    editData.value = { ...defaultEditData }
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

const handleSave = () => {
  emit('save', { ...editData.value })
  drawerVisible.value = false
}

const handleSubmit = () => {
  if (sceneStatus.value === '审核中' || sceneStatus.value === '待验证') {
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
    }).then(({ value }) => {
      emit('approve', { ...editData.value, approveRemark: value.trim() })
      drawerVisible.value = false
      ElMessage.success('审批通过成功')
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
  width: 40% !important;
  z-index: 2001 !important;
  transition: right 0.3s ease !important;

  &.is-shifted {
    right: 40% !important;
  }
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