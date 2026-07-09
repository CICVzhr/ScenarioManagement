<template>
  <el-aside width="250px" :class="['side-bar', { collapsed: isCollapsed }]">
    <div class="logo">
      <span class="logo-icon">📊</span>
      <span class="logo-text">场景数据管理平台</span>
    </div>
    <el-menu :default-active="activeMenu" class="menu" mode="vertical" @select="handleMenuSelect">
      <el-menu-item v-for="item in mainMenuItems" :key="item.path" :index="item.path">
        <span class="menu-icon">{{ item.icon }}</span>
        <span>{{ item.name }}</span>
      </el-menu-item>
      <el-menu-item index="/accident-data">
        <span class="menu-icon">📁</span>
        <span>场景数据</span>
      </el-menu-item>      
      <el-sub-menu index="scene-library">
        <template #title>
          <span class="menu-icon">🎨</span>
          <span>场景库管理</span>
          <el-icon class="arrow-icon"></el-icon>
        </template>
        <el-menu-item v-for="item in sceneLibraryItems" :key="item.path" :index="item.path">
          {{ item.name }}
        </el-menu-item>
      </el-sub-menu>

      <el-menu-item index="/tag-management">
        <span class="menu-icon">🏷️</span>
        <span>标签管理</span>
      </el-menu-item>
      <el-menu-item index="/system-settings">
        <span class="menu-icon">⚙️</span>
        <span>系统设置</span>
      </el-menu-item>
    </el-menu>
    <div class="user-footer">
      <div class="user-info">
        <div class="user-avatar">👤</div>
        <div class="user-detail">
          <span class="user-name">管理员</span>
          <span class="user-role">系统管理员</span>
        </div>
      </div>
    </div>
  </el-aside>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ArrowDown } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const isCollapsed = ref(false)

const mainMenuItems = [
  { id: 'dashboard', name: '总览', path: '/', icon: '📊' },
  // { id: 'test-management', name: '测试管理', path: '/test-management', icon: '🧪' },
  // { id: 'evaluation', name: '评价管理', path: '/evaluation', icon: '✅' },
  // { id: 'statistics', name: '统计分析', path: '/statistics', icon: '📈' }
]

// 场景数据改为顶级菜单项，不再展开子菜单

const sceneLibraryItems = [
  { id: 'scenario-design', name: '场景与用例设计', path: '/scenario-design' },
  { id: 'danger-library', name: '危险场景库', path: '/danger-library' }
  // { id: 'case-design', name: '用例设计', path: '/case-design' },
  // { id: 'test-case', name: '测试用例库', path: '/test-case' }
]


const activeMenu = computed(() => route.path)

const handleMenuSelect = (key) => {
  if (key.startsWith('/')) {
    router.push(key)
  }
}

defineExpose({
  toggle: () => {
    isCollapsed.value = !isCollapsed.value
  }
})
</script>

<style lang="scss" scoped>
.side-bar {
  background: #ffffff;
  border-right: 1px solid #e5e7eb;
  transition: width 0.3s;
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 100vh;

  &.collapsed {
    width: 64px;

    .logo-text {
      display: none;
    }

    .el-menu-item span:not(.menu-icon),
    .el-sub-menu__title span:not(.menu-icon) {
      display: none;
    }

    .arrow-icon {
      display: none;
    }

    .user-footer {
      .user-detail {
        display: none;
      }
    }
  }
}

.logo {
  display: flex;
  align-items: center;
  padding: 16px;
  border-bottom: 1px solid #f3f4f6;

  .logo-icon {
    font-size: 20px;
    margin-right: 8px;
  }

  .logo-text {
    font-size: 14px;
    font-weight: 600;
    color: #1f2937;
  }
}

.menu {
  border-right: none;
  background: transparent;
  padding: 8px 0;
  flex: 1;

  .el-menu-item,
  .el-sub-menu__title {
    color: #4b5563;
    height: 36px;
    line-height: 36px;
    margin: 0 6px;
    border-radius: 6px;
    margin-bottom: 3px;
    font-size: 14px;

    &:hover {
      background: #f3f4f6;
      color: #1f2937;
    }

    &.is-active {
      background: #eff6ff;
      color: #0052cc;

      .menu-icon {
        background: #0052cc;
        color: #fff;
      }
    }
  }

  .el-sub-menu .el-menu-item {
    padding-left: 44px !important;
    margin: 0 8px 3px 20px;
    border-radius: 0 6px 6px 0;
    font-size: 14px;
    color: #4b5563;
    height: 36px;
    line-height: 36px;

    &:hover {
      background: #f3f4f6;
      color: #1f2937;
    }

    &.is-active {
      background: #f3f4f6;
      color: #0052cc;
    }
  }

  .menu-icon {
    margin-right: 8px;
    font-size: 14px;
    width: 20px;
    height: 20px;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    border-radius: 5px;
    background: #f3f4f6;
    color: #6b7280;
  }

  .arrow-icon {
    margin-left: auto;
    font-size: 10px;
    color: #9ca3af;
  }
}

.user-footer {
  padding: 12px;
  border-top: 1px solid #f3f4f6;

  .user-info {
    display: flex;
    align-items: center;
    gap: 10px;
  }

  .user-avatar {
    width: 30px;
    height: 30px;
    background: #eff6ff;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
  }

  .user-detail {
    flex: 1;

    .user-name {
      display: block;
      font-size: 12px;
      font-weight: 600;
      color: #1f2937;
    }

    .user-role {
      display: block;
      font-size: 10px;
      color: #6b7280;
    }
  }
}
</style>