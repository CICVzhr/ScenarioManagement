<template>
  <div class="app-container">
    <el-container>
      <Sidebar ref="sidebarRef" />
      <el-container class="main-container">
        <Header @toggle-sidebar="toggleSidebar" />
        <el-main class="main-content">
          <router-view v-slot="{ Component }">
            <transition name="fade" mode="out-in">
              <component :is="Component" />
            </transition>
          </router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import Sidebar from '@/components/common/Sidebar.vue'
import Header from '@/components/common/Header.vue'

const sidebarRef = ref(null)

const toggleSidebar = () => {
  sidebarRef.value?.toggle()
}
</script>

<style lang="scss">
@import './style.css';

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  height: 100%;
  width: 100%;
}

#app {
  height: 100%;
  width: 100%;
}

.app-container {
  height: 100%;
  width: 100%;
  background: #f3f4f6;
  display: flex;
}

.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.main-content {
  flex: 1;
  padding: 0;
  overflow-y: auto;
  background: #f3f4f6;
  display: flex;
  flex-direction: column;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
