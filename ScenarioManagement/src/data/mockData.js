export const dashboardStats = {
  totalScenes: 1284,
  pendingReview: 847,
  pendingApproval: 24,
  dataSources: 5,
  totalScenesChange: '+12.5%',
  pendingReviewChange: '+8.3%',
  pendingApprovalChange: '+5.2%',
  dataSourcesChange: '+新增1个'
}

export const processStatusDistribution = [
  { name: '数据收集', value: 250, color: '#3B82F6' },
  { name: '数据筛选', value: 180, color: '#10B981' },
  { name: '场景设计', value: 320, color: '#F59E0B' },
  { name: '场景验证', value: 150, color: '#EF4444' },
  { name: '已入库场景', value: 384, color: '#8B5CF6' }
]

export const dangerSceneCategory = [
  { name: '多车碰撞', value: 156 },
  { name: '非机动车场景', value: 142 },
  { name: '雨天场景', value: 138 },
  { name: '夜间场景', value: 145 },
  { name: '弯道场景', value: 128 },
  { name: '隧道场景', value: 115 },
  { name: '匝道场景', value: 108 },
  { name: '交叉口场景', value: 198 }
]

export const recentTasks = [
  {
    id: 1,
    name: '雨天高速公路多车碰撞场景',
    stage: '数据筛选审核',
    submitter: '张晓明',
    submitTime: '2023-09-15 14:30',
    status: '待审核'
  },
  {
    id: 2,
    name: '雨天高速公路非机动车鬼探头场景',
    stage: '数据筛选审核',
    submitter: '张晓明',
    submitTime: '2023-09-14 10:15',
    status: '待审核'
  },
  {
    id: 3,
    name: '城市路口非机动车鬼探头场景',
    stage: '场景设计',
    submitter: '李思琪',
    submitTime: '2023-09-14 10:15',
    status: '设计中'
  },
  {
    id: 4,
    name: '山区道路弯道障碍物避让场景',
    stage: '场景验证',
    submitter: '王建国',
    submitTime: '2023-09-13 16:45',
    status: '待验证'
  },
  {
    id: 5,
    name: '高速追尾事故场景分析',
    stage: '用例设计',
    submitter: '赵海洋',
    submitTime: '2023-09-12 09:20',
    status: '已完成'
  }
]

export const accidentDataList = [
  {
    id: 'DC001',
    name: '雨天高速公路多车碰撞事故',
    reporter: '张晓明',
    reportTime: '2023-09-10',
    occurTime: '2023-09-09',
    location: 'G15沈海高速',
    roadType: '高速公路',
    weather: '大雨',
    vehicleType: 'Model 3',
    systemVersion: 'Autopilot',
    accidentType: '事故',
    accidentLevel: '重大',
    status: '待审核'
  },
  {
    id: 'DC002',
    name: '城市路口非机动车碰撞事故',
    reporter: '李思琪',
    reportTime: '2023-09-08',
    occurTime: '2023-09-07',
    location: '北京市朝阳区建国路',
    roadType: '城市道路',
    weather: '晴天',
    vehicleType: '蔚来ES6',
    systemVersion: 'NAD 2.0',
    accidentType: '事故',
    accidentLevel: '一般',
    status: '已通过'
  },
  {
    id: 'DC003',
    name: '夜间隧道追尾事故',
    reporter: '王建国',
    reportTime: '2023-09-06',
    occurTime: '2023-09-05',
    location: '秦岭终南山隧道',
    roadType: '隧道',
    weather: '阴天',
    vehicleType: '小鹏P7',
    systemVersion: 'XPILOT 4.0',
    accidentType: '事故',
    accidentLevel: '较大',
    status: '待审核'
  },
  {
    id: 'DC004',
    name: '山区道路弯道超车事故',
    reporter: '赵海洋',
    reportTime: '2023-09-04',
    occurTime: '2023-09-03',
    location: '云南省丽江市山路',
    roadType: '山区道路',
    weather: '小雨',
    vehicleType: '理想L9',
    systemVersion: 'NOA 3.0',
    accidentType: '事故',
    accidentLevel: '重大',
    status: '已驳回'
  },
  {
    id: 'DC005',
    name: '十字路口鬼探头场景',
    reporter: '孙东方',
    reportTime: '2023-09-10',
    occurTime: '2023-09-09',
    location: 'G15沈海高速',
    roadType: '高速公路',
    weather: '晴',
    vehicleType: 'Model 3',
    systemVersion: 'Autopilot',
    accidentType: '事故',
    accidentLevel: '重大',
    status: '设计完成'
  }
]

export const dangerSceneList = [
  {
    id: 'SC001',
    version: 'v1.0',
    name: '雨天高速公路多车碰撞场景',
    description: '雨天环境下高速公路多车连环碰撞的危险场景',
    tags: ['雨天', '高速公路', '多车碰撞'],
    level: '高',
    source: '事故数据',
    caseCount: 5,
    storageTime: '2023-09-15'
  },
  {
    id: 'SC002',
    version: 'v1.2',
    name: '城市路口非机动车鬼探头场景',
    description: '城市路口非机动车突然出现导致碰撞风险场景',
    tags: ['城市道路', '交叉口', '非机动车'],
    level: '严重',
    source: '事故数据',
    caseCount: 8,
    storageTime: '2023-09-14'
  },
  {
    id: 'SC003',
    version: 'v1.1',
    name: '夜间隧道追尾场景',
    description: '夜间隧道内视线受限导致追尾事故场景',
    tags: ['夜间', '隧道', '追尾'],
    level: '中',
    source: '仿真生成',
    caseCount: 3,
    storageTime: '2023-09-13'
  },
  {
    id: 'SC004',
    version: 'v1.0',
    name: '山区道路弯道障碍物避让场景',
    description: '山区弯道突发障碍物需要紧急避让场景',
    tags: ['山区道路', '弯道', '障碍物'],
    level: '高',
    source: '事故数据',
    caseCount: 6,
    storageTime: '2023-09-12'
  }
]

export const approvalFlowData = {
  SC001: {
    flowType: 'scene',
    currentStep: '审核中',
    steps: [
      {
        stepName: '设计中',
        stepKey: 'designing',
        status: 'completed',
        operator: '刘设计',
        operateTime: '2023-09-12 09:00',
        action: '开始设计',
        comment: '场景设计初稿完成，包含雨天高速多车碰撞场景描述及测试目标',
        modifications: []
      },
      {
        stepName: '审核中',
        stepKey: 'reviewing',
        status: 'active',
        operator: '王工',
        operateTime: '2023-09-13 14:30',
        action: '提交审核',
        comment: '',
        modifications: [
          {
            modifier: '陈设计',
            modifyTime: '2023-09-13 16:00',
            modifyContent: '修改了场景描述部分，增加了天气条件说明',
            textChanges: [
              {
                field: '测试场景',
                segments: [
                  { type: 'equal', text: '试验道路为至少包含由四条双向两车道的长直城区道路连接的丁字交叉口，道路设计速度为60km/h，' },
                  { type: 'delete', text: '天气晴朗，', author: '陈设计', time: '2023-09-13 16:00' },
                  { type: 'add', text: '雨天路面湿滑，能见度降低，', author: '陈设计', time: '2023-09-13 16:00' },
                  { type: 'equal', text: '道路边界线为白实线。路口设置停止线，停止线后方可设置人行横道。' }
                ]
              }
            ]
          },
          {
            modifier: '陈设计',
            modifyTime: '2023-09-14 10:20',
            modifyContent: '补充了泛化要素中行人速度参数范围',
            textChanges: [
              {
                field: '泛化要素-行人速度',
                segments: [
                  { type: 'delete', text: '[5,7]km/h', author: '陈设计', time: '2023-09-14 10:20' },
                  { type: 'add', text: '[4,8]km/h', author: '陈设计', time: '2023-09-14 10:20' }
                ]
              }
            ]
          }
        ]
      },
      {
        stepName: '待验证',
        stepKey: 'pending_validation',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      },
      {
        stepName: '已完成',
        stepKey: 'completed',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      }
    ]
  },
  SC002: {
    flowType: 'scene',
    currentStep: '已完成',
    steps: [
      {
        stepName: '设计中',
        stepKey: 'designing',
        status: 'completed',
        operator: '陈设计',
        operateTime: '2023-09-10 08:30',
        action: '开始设计',
        comment: '城市路口非机动车鬼探头场景设计完成',
        modifications: []
      },
      {
        stepName: '审核中',
        stepKey: 'reviewing',
        status: 'completed',
        operator: '王工',
        operateTime: '2023-09-11 10:00',
        action: '提交审核',
        comment: '审核通过，场景描述完整，测试目标明确',
        modifications: [
          {
            modifier: '李设计',
            modifyTime: '2023-09-11 14:15',
            modifyContent: '调整了交叉口场景的描述，补充了信号灯状态说明',
            textChanges: [
              {
                field: '测试场景',
                segments: [
                  { type: 'equal', text: '十字路口右侧支路的道路右侧有一静止成人，成人所处位置距离最右侧车道中心线5m。' },
                  { type: 'delete', text: '交叉口未设置信号灯。', author: '李设计', time: '2023-09-11 14:15' },
                  { type: 'add', text: '交叉口未设置信号灯或设置信号灯但处于全灭状态。', author: '李设计', time: '2023-09-11 14:15' }
                ]
              },
              {
                field: '场景描述',
                segments: [
                  { type: 'equal', text: '道路边界线为白实线，' },
                  { type: 'delete', text: '对向车流分隔线为黄实线。', author: '李设计', time: '2023-09-11 14:15' },
                  { type: 'add', text: '对向车流分隔线为黄实线单线或双线，同向车流分隔线为白虚线。', author: '李设计', time: '2023-09-11 14:15' }
                ]
              }
            ]
          }
        ]
      },
      {
        stepName: '待验证',
        stepKey: 'pending_validation',
        status: 'completed',
        operator: '赵工',
        operateTime: '2023-09-12 09:00',
        action: '提交验证',
        comment: '验证通过，场景符合实际测试需求',
        modifications: [
          {
            modifier: '陈设计',
            modifyTime: '2023-09-12 11:30',
            modifyContent: '根据验证反馈调整了行人速度参数',
            textChanges: [
              {
                field: '测试场景',
                segments: [
                  { type: 'equal', text: '当被测车辆距与成人的预测冲突点的预计行驶时间到达3s时，假人从静止位置以' },
                  { type: 'delete', text: '5km/h', author: '陈设计', time: '2023-09-12 11:30' },
                  { type: 'add', text: '6km/h', author: '陈设计', time: '2023-09-12 11:30' },
                  { type: 'equal', text: '的速度横穿道路。' }
                ]
              }
            ]
          }
        ]
      },
      {
        stepName: '已完成',
        stepKey: 'completed',
        status: 'completed',
        operator: '赵工',
        operateTime: '2023-09-12 15:00',
        action: '验证通过',
        comment: '场景设计已完成，进入用例设计阶段',
        modifications: []
      }
    ]
  },
  SC003: {
    flowType: 'scene',
    currentStep: '设计中',
    steps: [
      {
        stepName: '设计中',
        stepKey: 'designing',
        status: 'active',
        operator: '张设计',
        operateTime: '2023-09-08 09:00',
        action: '开始设计',
        comment: '夜间隧道追尾场景设计进行中',
        modifications: []
      },
      {
        stepName: '审核中',
        stepKey: 'reviewing',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      },
      {
        stepName: '待验证',
        stepKey: 'pending_validation',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      },
      {
        stepName: '已完成',
        stepKey: 'completed',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      }
    ]
  },
  SC004: {
    flowType: 'scene',
    currentStep: '设计中',
    steps: [
      {
        stepName: '设计中',
        stepKey: 'designing',
        status: 'active',
        operator: '李设计',
        operateTime: '2023-09-06 10:00',
        action: '开始设计',
        comment: '山区道路弯道障碍物避让场景设计进行中',
        modifications: []
      },
      {
        stepName: '审核中',
        stepKey: 'reviewing',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      },
      {
        stepName: '待验证',
        stepKey: 'pending_validation',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      },
      {
        stepName: '已完成',
        stepKey: 'completed',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      }
    ]
  },
  SC005: {
    flowType: 'scene',
    currentStep: '审核中',
    steps: [
      {
        stepName: '设计中',
        stepKey: 'designing',
        status: 'completed',
        operator: '刘设计',
        operateTime: '2023-09-11 08:00',
        action: '开始设计',
        comment: '雾天高速公路能见度测试场景设计完成',
        modifications: []
      },
      {
        stepName: '审核中',
        stepKey: 'reviewing',
        status: 'active',
        operator: '王工',
        operateTime: '2023-09-12 10:30',
        action: '提交审核',
        comment: '',
        modifications: [
          {
            modifier: '刘设计',
            modifyTime: '2023-09-12 15:00',
            modifyContent: '补充了雾天能见度等级划分参数',
            textChanges: [
              {
                field: '封闭场地环境',
                segments: [
                  { type: 'equal', text: '雾天能见度等级分为' },
                  { type: 'delete', text: '1级（200m＜能见度≤500m）、2级（100m＜能见度≤200m）', author: '刘设计', time: '2023-09-12 15:00' },
                  { type: 'add', text: '1级（200m＜能见度≤500m）、2级（100m＜能见度≤200m）、3级（50m＜能见度≤100m）、4级（能见度≤50m）', author: '刘设计', time: '2023-09-12 15:00' },
                  { type: 'equal', text: '；' }
                ]
              }
            ]
          }
        ]
      },
      {
        stepName: '待验证',
        stepKey: 'pending_validation',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      },
      {
        stepName: '已完成',
        stepKey: 'completed',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      }
    ]
  },
  SC006: {
    flowType: 'scene',
    currentStep: '待验证',
    steps: [
      {
        stepName: '设计中',
        stepKey: 'designing',
        status: 'completed',
        operator: '陈设计',
        operateTime: '2023-09-09 08:00',
        action: '开始设计',
        comment: '城市快速路紧急变道场景设计完成',
        modifications: []
      },
      {
        stepName: '审核中',
        stepKey: 'reviewing',
        status: 'completed',
        operator: '王工',
        operateTime: '2023-09-10 14:00',
        action: '提交审核',
        comment: '审核通过',
        modifications: [
          {
            modifier: '陈设计',
            modifyTime: '2023-09-10 16:30',
            modifyContent: '优化了变道场景的描述，增加了车速参数',
            textChanges: [
              {
                field: '测试场景',
                segments: [
                  { type: 'equal', text: '城市快速路上，被测车辆以' },
                  { type: 'add', text: '60km/h', author: '陈设计', time: '2023-09-10 16:30' },
                  { type: 'equal', text: '速度行驶，前方车辆突然减速，被测车辆需要紧急变道避让。' }
                ]
              }
            ]
          }
        ]
      },
      {
        stepName: '待验证',
        stepKey: 'pending_validation',
        status: 'active',
        operator: '赵工',
        operateTime: '2023-09-11 09:00',
        action: '提交验证',
        comment: '',
        modifications: [
          {
            modifier: '陈设计',
            modifyTime: '2023-09-11 14:00',
            modifyContent: '根据验证反馈调整了变道距离参数',
            textChanges: [
              {
                field: '泛化要素-横穿距离',
                segments: [
                  { type: 'delete', text: '[5,7]m', author: '陈设计', time: '2023-09-11 14:00' },
                  { type: 'add', text: '[3,8]m', author: '陈设计', time: '2023-09-11 14:00' }
                ]
              }
            ]
          }
        ]
      },
      // {
      //   stepName: '已完成',
      //   stepKey: 'completed',
      //   status: 'pending',
      //   operator: '',
      //   operateTime: '',
      //   action: '',
      //   comment: '',
      //   modifications: []
      // }
    ]
  },
  SC007: {
    flowType: 'scene',
    currentStep: '修改中',
    steps: [
      {
        stepName: '设计中',
        stepKey: 'designing',
        status: 'completed',
        operator: '张设计',
        operateTime: '2023-09-07 08:00',
        action: '开始设计',
        comment: '乡村道路行人横穿场景设计完成',
        modifications: []
      },
      {
        stepName: '审核中',
        stepKey: 'reviewing',
        status: 'completed',
        operator: '王工',
        operateTime: '2023-09-08 10:00',
        action: '提交审核',
        comment: '审核通过，建议补充行人类型变化',
        modifications: [
          {
            modifier: '张设计',
            modifyTime: '2023-09-08 14:00',
            modifyContent: '补充了行人类型参数（成人/儿童）',
            textChanges: [
              {
                field: '泛化要素-行人类型',
                segments: [
                  { type: 'equal', text: '场景泛化至少应考虑要素：天气变化、光线变化、' },
                  { type: 'add', text: '行人类型变化（成人/儿童）及', author: '张设计', time: '2023-09-08 14:00' },
                  { type: 'equal', text: '假人速度变化。' }
                ]
              }
            ]
          }
        ]
      },
      {
        stepName: '待验证',
        stepKey: 'pending_validation',
        status: 'completed',
        operator: '赵工',
        operateTime: '2023-09-09 09:00',
        action: '提交验证',
        comment: '验证不通过，场景描述中缺少夜间条件说明',
        modifications: []
      },
      {
        stepName: '修改中',
        stepKey: 'modifying',
        status: 'active',
        operator: '张设计',
        operateTime: '2023-09-10 08:30',
        action: '开始修改',
        comment: '',
        modifications: [
          {
            modifier: '张设计',
            modifyTime: '2023-09-10 10:00',
            modifyContent: '增加了夜间照明条件说明',
            textChanges: [
              {
                field: '测试场景',
                segments: [
                  { type: 'equal', text: '乡村道路上，被测车辆以40km/h速度行驶，前方有行人从右侧横穿道路。' },
                  { type: 'add', text: '夜间，道路无照明设施，仅依靠车辆前照灯照明。', author: '张设计', time: '2023-09-10 10:00' }
                ]
              }
            ]
          },
          {
            modifier: '张设计',
            modifyTime: '2023-09-10 14:30',
            modifyContent: '调整了行人横穿速度参数范围',
            textChanges: [
              {
                field: '泛化要素-行人速度',
                segments: [
                  { type: 'delete', text: '[5,7]km/h', author: '张设计', time: '2023-09-10 14:30' },
                  { type: 'add', text: '[3,8]km/h', author: '张设计', time: '2023-09-10 14:30' }
                ]
              }
            ]
          }
        ]
      },
      {
        stepName: '审核中',
        stepKey: 'reviewing_2',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      },
      {
        stepName: '待验证',
        stepKey: 'pending_validation_2',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      },
      {
        stepName: '已完成',
        stepKey: 'completed',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      }
    ]
  },
  SC008: {
    flowType: 'scene',
    currentStep: '设计中',
    steps: [
      {
        stepName: '设计中',
        stepKey: 'designing',
        status: 'active',
        operator: '李设计',
        operateTime: '2023-09-05 09:00',
        action: '开始设计',
        comment: '停车场倒车入库场景设计进行中',
        modifications: []
      },
      {
        stepName: '审核中',
        stepKey: 'reviewing',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      },
      {
        stepName: '待验证',
        stepKey: 'pending_validation',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      },
      {
        stepName: '已完成',
        stepKey: 'completed',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        modifications: []
      }
    ]
  }
}

export const caseApprovalFlowData = {
  SC001: {
    flowType: 'case',
    currentStep: '开发中',
    steps: [
      {
        stepName: '开发中',
        stepKey: 'developing',
        status: 'active',
        operator: '赵测试',
        operateTime: '2023-09-16 10:00',
        action: '开始开发',
        comment: '用例开发进行中',
        attachments: [
          {
            fileName: '测试用例_v1.0.docx',
            uploader: '赵测试',
            uploadTime: '2023-09-16 14:30',
            version: 'v1.0'
          },
          {
            fileName: '测试数据_v1.0.xlsx',
            uploader: '王评价',
            uploadTime: '2023-09-17 09:15',
            version: 'v1.0'
          }
        ]
      },
      {
        stepName: '已完成',
        stepKey: 'completed',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        attachments: []
      }
    ]
  },
  SC002: {
    flowType: 'case',
    currentStep: '开发中',
    steps: [
      {
        stepName: '开发中',
        stepKey: 'developing',
        status: 'active',
        operator: '李测试',
        operateTime: '2023-09-17 08:30',
        action: '开始开发',
        comment: '用例开发进行中',
        attachments: [
          {
            fileName: '鬼探头测试用例_v1.0.pdf',
            uploader: '李测试',
            uploadTime: '2023-09-17 16:00',
            version: 'v1.0'
          }
        ]
      },
      {
        stepName: '已完成',
        stepKey: 'completed',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        attachments: []
      }
    ]
  },
  SC003: {
    flowType: 'case',
    currentStep: '已完成',
    steps: [
      {
        stepName: '开发中',
        stepKey: 'developing',
        status: 'completed',
        operator: '张测试',
        operateTime: '2023-09-15 09:00',
        action: '开始开发',
        comment: '用例开发完成',
        attachments: [
          {
            fileName: '隧道追尾测试用例_v1.0.docx',
            uploader: '张测试',
            uploadTime: '2023-09-15 14:00',
            version: 'v1.0'
          },
          {
            fileName: '隧道追尾测试用例_v1.1.docx',
            uploader: '赵评价',
            uploadTime: '2023-09-16 10:30',
            version: 'v1.1'
          }
        ]
      },
      {
        stepName: '已完成',
        stepKey: 'completed',
        status: 'completed',
        operator: '张测试',
        operateTime: '2023-09-16 11:00',
        action: '开发完成',
        comment: '所有附件已上传，用例开发完成',
        attachments: []
      }
    ]
  },
  SC004: {
    flowType: 'case',
    currentStep: '开发中',
    steps: [
      {
        stepName: '开发中',
        stepKey: 'developing',
        status: 'active',
        operator: '王测试',
        operateTime: '2023-09-14 10:00',
        action: '开始开发',
        comment: '用例开发进行中',
        attachments: [
          {
            fileName: '弯道避让测试用例_v1.0.docx',
            uploader: '王测试',
            uploadTime: '2023-09-14 15:00',
            version: 'v1.0'
          }
        ]
      },
      {
        stepName: '已完成',
        stepKey: 'completed',
        status: 'pending',
        operator: '',
        operateTime: '',
        action: '',
        comment: '',
        attachments: []
      }
    ]
  }
}

export const scenarioList = [
  {
    id: 'SC001',
    dcid: 'DC001',
    name: '雨天高速公路多车碰撞场景',
    type: '高速场景',
    level: '高',
    createTime: '2023-09-12',
    status: '审核中',
    stage: 'scenariocase'
  },
  {
    id: 'SC002',
    dcid: 'DC002',
    name: '城市路口非机动车鬼探头场景',
    type: '城市道路',
    level: '严重',
    createTime: '2023-09-10',
    status: '已完成',
    stage: 'scenariocase'
  },
  {
    id: 'SC003',
    dcid: 'DC003',
    name: '夜间隧道追尾场景',
    type: '隧道',
    level: '中',
    createTime: '2023-09-08',
    status: '设计中',
    stage: 'scenariocase'
  },
  {
    id: 'SC004',
    dcid: 'DC004',
    name: '山区道路弯道障碍物避让场景',
    type: '山区道路',
    level: '高',
    createTime: '2023-09-06',
    status: '设计中',
    stage: 'scenariocase'
  },
  {
    id: 'SC005',
    dcid: 'DC005',
    name: '雾天高速公路能见度测试场景',
    type: '高速场景',
    level: '中',
    createTime: '2023-09-11',
    status: '审核中',
    stage: 'scenariocase'
  },
  {
    id: 'SC006',
    dcid: 'DC006',
    name: '城市快速路紧急变道场景',
    type: '城市道路',
    level: '高',
    createTime: '2023-09-09',
    status: '待验证',
    stage: 'scenariocase'
  },
  {
    id: 'SC007',
    dcid: 'DC007',
    name: '乡村道路行人横穿场景',
    type: '乡村道路',
    level: '严重',
    createTime: '2023-09-07',
    status: '修改中',
    stage: 'scenariocase'
  },
  {
    id: 'SC008',
    dcid: 'DC008',
    name: '停车场倒车入库场景',
    type: '停车场',
    level: '低',
    createTime: '2023-09-05',
    status: '设计中',
    stage: 'scenariocase'
  }
]

export const tagList = [
  { id: 1, name: '危险场景类别', tags: ['多车碰撞', '追尾', '鬼探头', '障碍物避让', '超车'] },
  { id: 2, name: '交通参与者', tags: ['非机动车', '行人', '机动车', '骑行者'] },
  { id: 3, name: '道路类型', tags: ['高速公路', '城市道路', '山区道路', '隧道', '交叉口'] },
  { id: 4, name: '天气条件', tags: ['晴天', '雨天', '雪天', '雾天', '阴天'] },
  { id: 5, name: '时段', tags: ['白天', '夜间', '黄昏', '凌晨'] }
]

export const systemSettings = {
  dataSources: [
    { id: 1, name: '主机厂内部事故事件', status: '启用' },
    { id: 2, name: '部委监管数据', status: '启用' },
    { id: 3, name: '深度调查机构数据', status: '启用' },
    { id: 4, name: '司法鉴定机构数据', status: '启用' },
    { id: 5, name: '智驾事故研判数据', status: '启用' }
  ],
  roles: [
    { id: 1, name: '管理员', permissions: ['全部'] },
    { id: 2, name: '设计人员', permissions: ['场景设计', '用例设计'] },
    { id: 3, name: '审核人员', permissions: ['审核', '审批'] },
    { id: 4, name: '测试人员', permissions: ['用例补充', '用例转化'] },
    { id: 5, name: '评价人员', permissions: ['评价指标'] }
  ],
  exportSettings: {
    wordExport: true,
    pdfExport: true,
    excelExport: true
  }
}