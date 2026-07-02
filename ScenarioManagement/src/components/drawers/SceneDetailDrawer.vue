<template>
  <el-drawer
    v-model="drawerVisible"
    title="场景详情"
    direction="rtl"
    size="80%"
    :close-on-click-modal="false"
    @close="handleClose"
  >
    <div class="detail-content">
      <div class="detail-header">
        <div class="header-left">
          <span class="scene-id-lg">{{ sceneData?.id }}</span>
          <span class="scene-version-lg">{{ sceneData?.version }}</span>
        </div>
        <span class="storage-badge">入库时间: {{ sceneData?.storageTime }}</span>
      </div>

      <div class="version-selector">
        <span class="version-label">版本历史</span>
        <el-select v-model="selectedVersion" placeholder="请选择版本" size="default" class="version-select">
          <el-option
            v-for="(ver, index) in versionHistory"
            :key="index"
            :label="ver.version + ' - ' + ver.summary"
            :value="ver.version"
          />
        </el-select>
        <div v-if="currentVersionDetail" class="version-detail">
          <span class="version-info">{{ currentVersionDetail.date }} | {{ currentVersionDetail.writer }}</span>
          <span class="version-desc-text">{{ currentVersionDetail.description }}</span>
        </div>
      </div>

      <h2 class="scene-title">{{ sceneData?.name }}</h2>

      <div class="detail-tags">
        <span v-for="(tag, index) in sceneData?.tags" :key="index" class="tag-lg">{{ tag }}</span>
      </div>

      <div class="detail-section">
        <h3>（1）场景描述</h3>
      </div>

      <div class="detail-section">
        <h4>场景标签</h4>
        <p class="section-content">事故场景、ODD识别与响应能力测试。</p>
      </div>

      <div class="detail-section">
        <h4>数据来源</h4>
        <p class="section-content">行业事故场景-智驾。</p>
      </div>

      <div class="detail-section">
        <h4>设计原因</h4>
        <p class="section-content">
          事故涉及弱势交通参与者保护，危险性较高；类似事故在传统汽车事故中较为常见，对智驾系统有较大挑战性。
        </p>
      </div>

      <div class="detail-section">
        <h4>测试场景</h4>
        <p class="section-content">
          试验道路为至少包含由四条双向两车道的长直城区道路连接的丁字交叉口（可用十字路口代替），道路设计速度为60km/h，道路边界线为白实线，对向车流分隔线为黄实线单线或双线，同向车流分隔线为白虚线。路口设置停止线，停止线后方可设置人行横道。道路未设置中央隔离带，交叉口未设置信号灯或设置信号灯但处于全灭状态。
        </p>
        <p class="section-content">
          十字路口右侧支路的道路右侧有一静止成人，成人所处位置距离最右侧车道中心线5m。当被测车辆距与成人的预测冲突点的预计行驶时间到达3s时，假人从静止位置以6km/h的速度横穿道路。日间。
        </p>
        <img :src="imageSrc" alt="测试场景示意图" class="section-image" />
        <p class="section-content">
          场景泛化至少应考虑要素：天气变化、光线变化、行人类型变化（成人/儿童）及假人速度变化。
        </p>
      </div>

      <div class="detail-section">
        <table class="section-table">
          <thead>
            <tr>
              <th>泛化要素</th>
              <th>要素参数</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>行人类型</td>
              <td>{成人,儿童}</td>
            </tr>
            <tr>
              <td>行人速度</td>
              <td>[5,7]km/h</td>
            </tr>
            <tr>
              <td>横穿距离</td>
              <td>[5,7]m</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="detail-section">
        <h4>测试方法</h4>
        <p class="section-content">
          本次深度测试拟采用测试方法：模拟仿真测试、封闭场地测试。
        </p>
      </div>

      <div class="detail-section">
        <h3>（2）场景测试目标</h3>
        <p class="section-content">
          考察试验车辆在无信号灯路口下对横穿动态目标物的识别、理解与响应能力。
        </p>
      </div>

      <div class="detail-section">
        <h3>（3）试验场地及试验环境</h3>
        <h4>1)封闭场地测试及模拟仿真测试试验道路环境应满足以下要求：</h4>
        <p class="section-content">a)试验路面要求压实并且无可能造成传感器异常工作的不规则物（例如大的倾角、裂缝、井盖或是具有反射能力的螺栓等）；</p>
        <p class="section-content">b)试验路面干燥，表面无可见水分；</p>
        <p class="section-content">c)试验道路车道宽度于3.5m～3.75m之间；</p>
        <p class="section-content">d)试验道路的峰值附着系数不小于0.7；</p>
        <p class="section-content">e)试验道路配备车道边线，且保证车道边线清晰可见，车道边线符合GB 5768.3的要求。</p>
        <h4>2)模拟仿真试验环境应满足以下要求：</h4>
        <p class="section-content">a)温度：保持在20°C~25°C之间；</p>
        <p class="section-content">b)相对湿度：保持在20%~50%之间；</p>
        <p class="section-content">c)网络：具备千兆带宽满足大数据的传输，端到端传输时延≤10ms，全场景下丢包率≤0.1%；</p>
        <p class="section-content">d)电源稳定性：供电电压精度稳定性在±0.5%~±1%，电流精度稳定性在±1%~±2%。</p>
        <h4>3)封闭场地试验环境应满足以下要求：</h4>
        <p class="section-content">a)天气干燥，除雨雾测试外没有结冰、积水、积雪等情况；</p>
        <p class="section-content">b)环境温度位于-20℃～45℃之间；</p>
        <p class="section-content">c)平均风速不大于5m/s，瞬时最大风速不大于10m/s；</p>
        <p class="section-content">d)交通标志符合GB 5768.2和GB 5768.5的要求；</p>
        <p class="section-content">e)雾天能见度等级分为1级（200m＜能见度≤500m）、2级（100m＜能见度≤200m）、3级（50m＜能见度≤100m）、4级（能见度≤50m）；</p>
        <p class="section-content">f)雨天雨量等级分为1级（1h降雨量10mm/h～14.9 mm/h）、2级（1h降雨量15mm/h～29.9mm/h）、3级（1h降雨量30mm/h～49.9mm/h），4级（1h降雨量≥50mm/h）。</p>
        <h4>4)具备试验车辆组合驾驶辅助系统正常激活的必要数据和设施条件。</h4>
      </div>

      <div class="detail-section">
        <h3>（4）试验设备及数据采集</h3>
        <h4>1)目标物</h4>
        <p class="section-content">成人软目标物。</p>
        <h4>2)试验设备要求</h4>
        <h4>a)模拟仿真测试试验设备满足以下要求：</h4>
        <p class="section-content">1)仿真测试系统需支持固定步长仿真，最大延迟时间应不超过1ms；</p>
        <p class="section-content">2)仿真测试系统采集数据频率不小于20Hz；</p>
        <p class="section-content">3)仿真测试系统采集数据格式为float，精度不大于0.001。</p>
        <h4>b)封闭场地测试试验设备满足以下要求：</h4>
        <p class="section-content">1)运动状态采集和存储的频率不小于50Hz；</p>
        <p class="section-content">2)视频采集设备分辨率不小于(720×480)像素，视频采样帧率不小于30fps；</p>
        <p class="section-content">3)除在隧道内，速度采集精度不大于0.1km/h；</p>
        <p class="section-content">4)除在隧道内，距离采集精度不大于0.1m；</p>
        <p class="section-content">5)除在隧道内，加速度采集精度不大于0.1m/s²。</p>
        <h4>3)试验记录内容</h4>
        <p class="section-content">试验过程记录包含以下内容：</p>
        <p class="section-content">a)试验车辆组合驾驶辅助系统软、硬件版本信息；</p>
        <p class="section-content">b)试验车辆的控制模式，例如人工驾驶模式、辅助驾驶模式等；</p>
        <p class="section-content">c)试验车辆运动状态参数：</p>
        <p class="section-content">1)试验时间轴；</p>
        <p class="section-content">2)试验车辆位置信息；</p>
        <p class="section-content">3)试验车辆纵向速度；</p>
        <p class="section-content">4)试验车辆横向速度；</p>
        <p class="section-content">5)试验车辆纵向加速度；</p>
        <p class="section-content">6)试验车辆横向加速度。</p>
        <p class="section-content">d)试验车辆灯光状态；</p>
        <p class="section-content">e)试验操作人员及人机交互状态（试验操作人员面部、仪表盘、转向盘、中控屏、踏板等）的视频及语音监控信息；</p>
        <p class="section-content">f)试验车辆前方的交通状态视频信息；</p>
        <p class="section-content">g)若有目标物，记录目标物的状态参数：</p>
        <p class="section-content">1)试验时间轴；</p>
        <p class="section-content">2)目标物位置信息；</p>
        <p class="section-content">3)目标物纵向速度；</p>
        <p class="section-content">4)目标物横向速度；</p>
        <p class="section-content">5)目标物纵向加速度；</p>
        <p class="section-content">6)目标物横向加速度。</p>
      </div>

      <div class="detail-section">
        <h3>（5）试验车辆要求</h3>
        <h4>1)试验车辆满足以下人机交互要求：</h4>
        <p class="section-content">a)具备便于人工激活和关闭组合驾驶辅助系统的操作方式；</p>
        <p class="section-content">b)系统状态及人机转换过程提示信息清晰可见。</p>
        <h4>2)试验车辆满足以下载荷要求：</h4>
        <p class="section-content">a)试验车辆质量处于整车整备质量加上驾驶员、测试员和试验设备的总质量与最大允许总质量之间，试验开始后不改变试验车辆载荷状态。</p>
        <h4>3)试验车辆设置满足以下要求：</h4>
        <p class="section-content">a)若系统的跟车时距可调，试验车辆调整为跟车距离最近的设置；</p>
        <p class="section-content">b)试验车辆的换道灵敏度/积极性设置使用上电/点火后的系统初始档位；</p>
        <p class="section-content">c)对于夜间试验，试验车辆灯光档位要求：</p>
        <p class="section-content">1)若系统仅在前照灯AUTO设置下可激活，试验车辆前照灯调整为AUTO设置；</p>
        <p class="section-content">2)若系统仅在近光灯下可激活，试验车辆设置为近光灯；</p>
        <p class="section-content">3)若系统仅在远光灯下可激活，试验车辆设置为远光灯；</p>
        <p class="section-content">4)若系统在近光灯、远光灯和/或前照灯AUTO设置下均可激活，试验车辆设置为近光灯。</p>
      </div>

      <div class="detail-section">
        <h3>（6）试验方法</h3>
        <p class="section-content">1)预试验：试验车辆在十字交叉口前300m处开启组合驾驶辅助系统向前行驶，达到稳定速度至少30km/h，在没有任何干扰的情况下右转通过十字交叉口，记录车辆通过预测冲突点的时间和在到达预测冲突点3s前的位置。</p>
        <p class="section-content">2)调整测试设备相关参数；</p>
        <p class="section-content">3)正式试验：试验车辆在十字交叉口前300m处开启组合驾驶辅助系统向前行驶，达到稳定速度至少30km/h，放置成人目标物正式试验。</p>
      </div>

      <div class="detail-section">
        <h3>（7）通过要求</h3>
        <p class="section-content">1)若系统不具备该场景下的行驶能力（超出ODC），应在进入危险区域前发出提示信息。量化判定依据：附录C.2中C.2.2.4节；</p>
        <p class="section-content">2)若系统具备该场景下的行驶能力（在ODC内），则应满足附录C.2中C.2.2.2.3节的所有指标阈值要求；</p>
      </div>

      <div class="detail-section">
        <h3>（8）结束条件</h3>
        <p class="section-content">1)试验车辆与任一目标发生碰撞；</p>
        <p class="section-content">2)试验车辆发出立即控制警告或执行风险减缓功能；</p>
        <p class="section-content">3)试验车辆停车10s未启动；</p>
        <p class="section-content">4)试验车辆驶过测试路段至终点。</p>
      </div>
    </div>

    <template #footer>
      <div class="drawer-footer">
        <el-button @click="handleClose">关闭</el-button>
        <el-button type="primary" @click="handleExport">导出文档</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<script setup>
import { ref, computed, watch } from 'vue'

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

const emit = defineEmits(['update:visible', 'close', 'export'])

const drawerVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val)
})

const imageSrc = computed(() => {
  return new URL('@/image/图片1.png', import.meta.url).href
})

const defaultVersionHistory = ref([
  { version: 'v1.0', date: '2023-09-10', summary: '初始版本', writer: '张一', description: '完成基本功能，包括场景描述、标签、测试目标等' },
  { version: 'v1.1', date: '2023-09-12', summary: '优化场景描述', writer: '李四', description: '优化场景描述，包括场景描述的详细信息' },
  { version: 'v1.2', date: '2023-09-15', summary: '添加测试目标', writer: '王五', description: '添加测试目标，修改测试目标的描述' }
])

const versionHistory = ref([...defaultVersionHistory.value])

const selectedVersion = ref('')

const currentVersionDetail = computed(() => {
  if (!selectedVersion.value) return null
  return versionHistory.value.find(v => v.version === selectedVersion.value) || null
})

watch(() => props.sceneData, (newData) => {
  if (newData && newData.versionHistory) {
    versionHistory.value = [...newData.versionHistory]
  } else {
    versionHistory.value = [...defaultVersionHistory.value]
  }
  if (versionHistory.value.length > 0) {
    selectedVersion.value = versionHistory.value[0].version
  }
}, { immediate: true, deep: true })

const handleClose = () => {
  emit('close')
}

const handleExport = () => {
  emit('export', props.sceneData)
}
</script>

<style lang="scss" scoped>
.detail-content {
  padding: 20px 0;
  height: calc(100vh - 120px);
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

.version-selector {
  margin-bottom: 16px;
  padding: 16px;
  background: #f9fafb;
  border-radius: 8px;
  border: 1px solid #e5e7eb;

  .version-label {
    font-size: 14px;
    font-weight: 600;
    color: #374151;
    margin-right: 12px;
  }

  .version-select {
    width: 280px;
  }

  .version-detail {
    margin-top: 12px;
    display: flex;
    flex-direction: column;
    gap: 4px;

    .version-info {
      font-size: 13px;
      color: #6B7280;
    }

    .version-desc-text {
      font-size: 13px;
      color: #4B5563;
      line-height: 1.5;
    }
  }
}

.scene-title {
  font-size: 20px;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 12px 0;
}

.detail-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 20px;

  .tag-lg {
    background: #DBEAFE;
    color: #2563EB;
    padding: 6px 14px;
    border-radius: 20px;
    font-size: 14px;
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

  .section-content {
    font-size: 14px;
    color: #4B5563;
    line-height: 1.6;
    margin: 0;
    text-indent: 2em;
  }

  .section-image {
    display: block;
    max-width: 400px;
    height: auto;
    margin: 12px auto 0;
    border-radius: 8px;
  }

  .section-table {
    width: 100%;
    border-collapse: collapse;
    margin: 12px 0;

    thead {
      tr {
        th {
          background: #f9fafb;
          padding: 10px 12px;
          text-align: center;
          font-weight: 600;
          color: #374151;
          border: 1px solid #e5e7eb;
        }
      }
    }

    tbody {
      tr {
        td {
          padding: 10px 12px;
          text-align: center;
          color: #4B5563;
          border: 1px solid #e5e7eb;
        }
      }
    }
  }
}
</style>