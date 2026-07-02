<template>
  <div class="scenario-design">
    <div class="page-header">
      <h2>场景与用例设计</h2>
      <ActionButton text="创建场景" type="primary" @click="showCreateModal = true" />
    </div>

    <div class="tabs-nav">
      <div
        v-for="(step, index) in designSteps"
        :key="step.id"
        class="tab-item"
        :class="{ active: activeStep === step.id }"
        @click="activeStep = step.id"
      >
        <span class="tab-number">{{ index + 1 }}</span>
        <span class="tab-title">{{ step.title }}</span>
      </div>
    </div>

    <div class="tab-content">
      <SceneDesignPage v-if="activeStep === 'scenariocase'" />
      <CaseDesignPage v-else-if="activeStep === 'testcase'" />
    </div>

    <CreateScenarioDrawer
      :visible="showCreateModal"
      @update:visible="showCreateModal = $event"
      @close="handleDrawerClose"
      @create="handleCreate"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ActionButton from '@/components/common/ActionButton.vue'
import SceneDesignPage from '@/components/pages/SceneDesignPage.vue'
import CaseDesignPage from '@/components/pages/CaseDesignPage.vue'
import CreateScenarioDrawer from '@/components/drawers/CreateScenarioDrawer.vue'
import { scenarioList } from '@/data/mockData'

const showCreateModal = ref(false)
const activeStep = ref('scenariocase')

const designSteps = [
  { id: 'scenariocase', title: '场景设计' },
  { id: 'testcase', title: '用例开发' }
]

const scenarios = ref([...scenarioList])

const handleDrawerClose = () => {}

const handleCreate = (formData) => {
  const newId = `SC${String(scenarios.value.length + 1).padStart(3, '0')}`
  scenarios.value.unshift({
    ...formData,
    id: newId,
    createTime: new Date().toISOString().split('T')[0],
    status: '待审核',
    stage: activeStep.value
  })
}
</script>

<style lang="scss" scoped>
.scenario-design {
  padding: 16px;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;
  overflow: hidden;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-shrink: 0;

  h2 {
    font-size: 20px;
    font-weight: 600;
    color: #1f2937;
    margin: 0;
  }
}

.tabs-nav {
  display: flex;
  gap: 0;
  background: #fff;
  border-radius: 12px;
  padding: 0 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  overflow-x: auto;
  flex-shrink: 0;

  .tab-item {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 14px 24px;
    font-size: 14px;
    color: #6b7280;
    cursor: pointer;
    border-bottom: 2px solid transparent;
    transition: all 0.2s;
    white-space: nowrap;

    &:hover {
      color: #3b82f6;
      background: #f9fafb;
    }

    &.active {
      color: #3b82f6;
      border-bottom-color: #3b82f6;
      background: #eff6ff;
    }

    .tab-number {
      width: 20px;
      height: 20px;
      background: #e5e7eb;
      color: #6b7280;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 12px;
      font-weight: 600;
    }

    &.active .tab-number {
      background: #3b82f6;
      color: #fff;
    }
  }
}

.tab-content {
  flex: 1;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}
</style>