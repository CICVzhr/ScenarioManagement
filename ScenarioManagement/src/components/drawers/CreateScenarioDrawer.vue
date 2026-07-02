<template>
  <el-drawer
    v-model="drawerVisible"
    title="创建场景"
    direction="rtl"
    size="600px"
    :close-on-click-modal="true"
    @close="handleClose"
  >
    <div class="create-form">
      <el-form :model="formData" label-width="120px" class="scenario-form">
        <el-form-item label="案例ID">
          <el-select v-model="formData.caseId" placeholder="选择案例ID" class="full-width">
            <el-option
              v-for="caseItem in caseOptions"
              :key="caseItem.value"
              :label="caseItem.label"
              :value="caseItem.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="场景名称">
          <el-input v-model="formData.name" placeholder="请输入场景名称" class="full-width" />
        </el-form-item>

        <el-form-item label="场景设计人员">
          <el-select v-model="formData.designer" placeholder="选择设计人员" class="full-width">
            <el-option
              v-for="user in userOptions"
              :key="user.value"
              :label="user.label"
              :value="user.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="用例设计人员">
          <el-select v-model="formData.caseDesigner" placeholder="选择用例设计人员" class="full-width">
            <el-option
              v-for="user in userOptions"
              :key="user.value"
              :label="user.label"
              :value="user.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="场景审核人员">
          <el-select v-model="formData.reviewers" placeholder="选择审核人员" multiple class="full-width">
            <el-option
              v-for="user in userOptions"
              :key="user.value"
              :label="user.label"
              :value="user.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="用例开发人员">
          <el-select v-model="formData.caseDeveloper" placeholder="选择用例开发人员" class="full-width">
            <el-option
              v-for="user in userOptions"
              :key="user.value"
              :label="user.label"
              :value="user.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="通知时间">
          <div class="notify-time-wrapper">
            <el-input-number
              v-model="formData.notifyDays"
              :min="1"
              :max="365"
              :step="1"
              placeholder="请输入天数"
              controls-position="right"
              class="notify-days-input"
            />
            <span class="notify-days-unit">天</span>
          </div>
        </el-form-item>
      </el-form>
    </div>

    <template #footer>
      <div class="drawer-footer">
        <el-button @click="handleCancel">取消</el-button>
        <el-button type="primary" @click="handleCreate">创建场景</el-button>
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
  defaultData: {
    type: Object,
    default: () => ({})
  }
})

const emit = defineEmits(['update:visible', 'close', 'create'])

const drawerVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val)
})

const defaultFormData = {
  caseId: '',
  name: '',
  designer: '',
  caseDesigner: '',
  reviewers: [],
  caseDeveloper: '',
  notifyDays: 2
}

const formData = ref({ ...defaultFormData })

const caseOptions = ref([
  { label: 'DC001 - 雨天高速公路多车碰撞事故', value: 'DC001' },
  { label: 'DC002 - 城市路口非机动车碰撞事故', value: 'DC002' },
  { label: 'DC003 - 夜间隧道追尾事故', value: 'DC003' },
  { label: 'DC004 - 山区道路弯道超车事故', value: 'DC004' },
  { label: 'DC005 - 十字路口鬼探头场景', value: 'DC005' }
])

const userOptions = ref([
  { label: '张晓明', value: '张晓明' },
  { label: '李思琪', value: '李思琪' },
  { label: '王建国', value: '王建国' },
  { label: '赵海洋', value: '赵海洋' },
  { label: '孙东方', value: '孙东方' },
  { label: '刘设计', value: '刘设计' },
  { label: '陈设计', value: '陈设计' },
  { label: '王工', value: '王工' },
  { label: '李工', value: '李工' },
  { label: '赵工', value: '赵工' },
  { label: '孙工', value: '孙工' }
])

watch(() => props.defaultData, (newData) => {
  if (Object.keys(newData).length > 0) {
    formData.value = { ...newData }
  } else {
    formData.value = { ...defaultFormData }
  }
}, { immediate: true, deep: true })

const handleClose = () => {
  formData.value = { ...defaultFormData }
  emit('close')
}

const handleCancel = () => {
  drawerVisible.value = false
}

const handleCreate = () => {
  emit('create', { ...formData.value })
  drawerVisible.value = false
  formData.value = { ...defaultFormData }
}
</script>

<style lang="scss" scoped>
.create-form {
  padding: 20px 0;
}

.scenario-form {
  .full-width {
    width: 100%;
  }

  .notify-time-wrapper {
    display: flex;
    align-items: center;
    gap: 8px;
    width: 100%;

    .notify-days-input {
      flex: 1;
    }

    .notify-days-unit {
      font-size: 14px;
      color: #606266;
      white-space: nowrap;
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
</style>