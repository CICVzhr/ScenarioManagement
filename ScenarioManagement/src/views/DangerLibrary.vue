<template>
  <div class="danger-library">
    <div class="page-header">
      <h2>危险场景库</h2>
      <button class="batch-export-btn" @click="handleBatchExport">
        <el-icon><component :is="icons.Download" /></el-icon>
        批量导出
      </button>
    </div>

    <div class="search-bar">
      <el-input v-model="searchKeyword" placeholder="搜索场景..." class="search-input">
        <template #append>
          <el-icon><component :is="icons.Search" /></el-icon>
        </template>
      </el-input>
      <el-select v-model="categoryFilter" placeholder="全部分类" class="category-select">
        <el-option label="全部分类" value="" />
        <el-option label="多车碰撞" value="多车碰撞" />
        <el-option label="追尾" value="追尾" />
        <el-option label="鬼探头" value="鬼探头" />
        <el-option label="障碍物避让" value="障碍物避让" />
        <el-option label="超车" value="超车" />
      </el-select>
    </div>

    <el-table :data="filteredScenes" class="scene-table" border>
      <el-table-column prop="version" label="版本" width="80" />
      <el-table-column prop="id" label="场景ID" width="100" />
      <el-table-column prop="name" label="场景名称" min-width="200" />
      <el-table-column label="标签" min-width="200">
        <template #default="scope">
          <span v-for="(tag, index) in scope.row.tags" :key="index" class="tag">{{ tag }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="storageTime" label="入库时间" width="120" />
      <el-table-column label="操作" width="400">
        <template #default="scope">
          <el-button type="primary" link @click="viewScene(scope.row)">查看</el-button>
          <el-button type="primary" link @click="exportWord(scope.row)">导出Word</el-button>
          <el-button type="primary" link @click="exportPdf(scope.row)">导出PDF</el-button>
          <el-button type="primary" link @click="exportPdf(scope.row)">退回设计</el-button>
        </template>
      </el-table-column>
    </el-table>

    <SceneDetailDrawer
      :visible="showDetailModal"
      :scene-data="selectedScene"
      @update:visible="showDetailModal = $event"
      @close="handleDrawerClose"
      @export="handleExport"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import * as icons from '@element-plus/icons-vue'
import { dangerSceneList } from '@/data/mockData'
import SceneDetailDrawer from '@/components/drawers/SceneDetailDrawer.vue'

const sceneList = ref([...dangerSceneList])
const searchKeyword = ref('')
const categoryFilter = ref('')
const showDetailModal = ref(false)
const selectedScene = ref(null)

const filteredScenes = computed(() => {
  return sceneList.value.filter(item => {
    const matchKeyword = !searchKeyword.value ||
      item.name.toLowerCase().includes(searchKeyword.value.toLowerCase()) ||
      item.id.toLowerCase().includes(searchKeyword.value.toLowerCase())
    const matchCategory = !categoryFilter.value || item.tags.includes(categoryFilter.value)
    return matchKeyword && matchCategory
  })
})

const viewScene = (scene) => {
  selectedScene.value = scene
  showDetailModal.value = true
}


const exportWord = (scene) => {
  console.log('导出Word:', scene)
}

const exportPdf = (scene) => {
  console.log('导出PDF:', scene)
}

const handleBatchExport = () => {
  console.log('批量导出')
}

const handleDrawerClose = () => {
  selectedScene.value = null
}

const handleExport = (scene) => {
  console.log('导出场景文档:', scene)
}
</script>

<style lang="scss" scoped>
.danger-library {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;

  h2 {
    font-size: 20px;
    font-weight: 600;
    color: #1f2937;
    margin: 0;
  }

  .batch-export-btn {
    display: flex;
    align-items: center;
    gap: 6px;
    background: #3B82F6;
    color: #fff;
    border: none;
    padding: 10px 20px;
    border-radius: 8px;
    font-size: 14px;
    cursor: pointer;

    &:hover {
      background: #2563EB;
    }
  }
}

.search-bar {
  display: flex;
  gap: 16px;
  margin-bottom: 20px;

  .search-input {
    width: 300px;
  }

  .category-select {
    width: 160px;
  }
}

.scene-table {
  width: 100%;

  :deep(.el-table__header-wrapper) {
    th {
      background: #f9fafb;
      font-weight: 600;
      color: #374151;
    }
  }

  :deep(.el-table__body-wrapper) {
    .tag {
      background: #DBEAFE;
      color: #2563EB;
      padding: 4px 12px;
      border-radius: 16px;
      font-size: 12px;
      margin-right: 6px;
    }
  }
}
</style>