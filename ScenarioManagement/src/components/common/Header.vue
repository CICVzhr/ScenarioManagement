<template>
  <el-header class="header">
    <div class="header-left">
      <el-button class="menu-toggle" @click="$emit('toggleSidebar')">
        <el-icon><component :is="icons.Menu" /></el-icon>
      </el-button>
      <el-breadcrumb class="breadcrumb">
        <el-breadcrumb-item v-for="(item, index) in breadcrumbs" :key="index">
          <span v-if="index === breadcrumbs.length - 1">{{ item.name }}</span>
          <a v-else @click="navigateTo(item.path)">{{ item.name }}</a>
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="header-right">
      <el-button class="header-btn"><el-icon><component :is="icons.Bell" /></el-icon></el-button>
      <el-button class="header-btn"><el-icon><component :is="icons.Setting" /></el-icon></el-button>
      <div class="divider"></div>
      <el-button class="header-btn"><el-icon><component :is="icons.User" /></el-icon></el-button>
    </div>
  </el-header>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useRouter } from 'vue-router'
import * as icons from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

const breadcrumbs = computed(() => {
  const matched = route.matched
  return matched.map(item => ({
    name: item.meta.title || item.name,
    path: item.path
  }))
})

const navigateTo = (path) => {
  router.push(path)
}

defineEmits(['toggleSidebar'])
</script>

<style lang="scss" scoped>
.header {
  background: #ffffff;
  border-bottom: 1px solid #e5e7eb;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
  height: 52px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.menu-toggle {
  width: 32px;
  height: 32px;
  border-radius: 6px;
  background: #f3f4f6;
  border: none;

  &:hover {
    background: #e5e7eb;
  }
}

.breadcrumb {
  font-size: 12px;

  .el-breadcrumb__item:last-child .el-breadcrumb__inner {
    color: #1f2937;
    font-weight: 600;
  }

  .el-breadcrumb__inner {
    color: #6b7280;
  }

  .el-breadcrumb__separator {
    color: #9ca3af;
    margin: 0 6px;
  }
}

.header-right {
  display: flex;
  align-items: center;
  gap: 6px;
}

.header-btn {
  width: 32px;
  height: 32px;
  border-radius: 6px;
  background: #f3f4f6;
  border: none;
  color: #4b5563;

  &:hover {
    background: #e5e7eb;
    color: #1f2937;
  }
}

.divider {
  width: 1px;
  height: 20px;
  background: #e5e7eb;
  margin: 0 6px;
}
</style>