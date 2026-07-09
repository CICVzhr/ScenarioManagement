<template>
  <div class="tag-management">
    <div class="page-header">
      <h2>标签管理</h2>
      <button class="add-btn" @click="showAddModal = true">
        <el-icon><component :is="icons.Plus" /></el-icon>
        添加标签组
      </button>
    </div>

    <div class="tag-groups">
      <div v-for="group in tagGroups" :key="group.id" class="tag-group-card">
        <div class="group-header">
          <h3 class="group-name">{{ group.name }}</h3>
          <div class="group-actions">
            <button class="action-btn" @click="editGroup(group)">
              <el-icon><component :is="icons.Edit" /></el-icon>
            </button>
            <button class="action-btn" @click="deleteGroup(group)">
              <el-icon><component :is="icons.Delete" /></el-icon>
            </button>
          </div>
        </div>
        <div class="tags-container">
          <span 
            v-for="(tag, index) in group.tags" 
            :key="index" 
            class="tag-item"
          >
            {{ tag }}
            <button class="remove-tag" @click="removeTag(group.id, index)">
              <el-icon :size="14"><component :is="icons.Close" /></el-icon>
            </button>
          </span>
        </div>
        <button class="add-tag-btn" @click="addTagToGroup(group)">
          <el-icon><component :is="icons.Plus" /></el-icon>
          添加标签
        </button>
      </div>
    </div>

    <el-dialog title="添加标签组" :visible.sync="showAddModal" width="500px">
      <el-form :model="groupForm" label-width="100px">
        <el-form-item label="标签组名称">
          <el-input v-model="groupForm.name" placeholder="请输入标签组名称" />
        </el-form-item>
        <el-form-item label="标签列表">
          <el-select v-model="groupForm.tags" multiple placeholder="请添加标签">
            <el-option label="雨天" value="雨天" />
            <el-option label="晴天" value="晴天" />
            <el-option label="雪天" value="雪天" />
            <el-option label="高速公路" value="高速公路" />
            <el-option label="城市道路" value="城市道路" />
            <el-option label="山区道路" value="山区道路" />
            <el-option label="多车碰撞" value="多车碰撞" />
            <el-option label="追尾" value="追尾" />
            <el-option label="鬼探头" value="鬼探头" />
            <el-option label="障碍物避让" value="障碍物避让" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddModal = false">取消</el-button>
        <el-button type="primary" @click="handleAddGroup">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog title="添加标签" :visible.sync="showTagModal" width="400px">
      <el-form :model="tagForm" label-width="80px">
        <el-form-item label="标签名称">
          <el-input v-model="tagForm.name" placeholder="请输入标签名称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showTagModal = false">取消</el-button>
        <el-button type="primary" @click="handleAddTag">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as icons from '@element-plus/icons-vue'
import { getTagsGrouped, createTag, deleteTag, getTags } from '@/api/tag'

const tagGroups = ref([])
const flatTags = ref([])
const showAddModal = ref(false)
const showTagModal = ref(false)
const currentGroup = ref(null)

const fetchData = async () => {
  try {
    const [groupedData, flatData] = await Promise.all([
      getTagsGrouped(),
      getTags()
    ])
    tagGroups.value = Object.entries(groupedData).map(([name, tags], index) => ({
      id: index + 1,
      name,
      tags: [...tags]
    }))
    flatTags.value = flatData
  } catch (e) {
    console.error('Failed to load tags:', e)
  }
}

onMounted(() => {
  fetchData()
})

const groupForm = ref({
  name: '',
  tags: []
})

const tagForm = ref({
  name: ''
})

const addTagToGroup = (group) => {
  currentGroup.value = group
  tagForm.value = { name: '' }
  showTagModal.value = true
}

const handleAddTag = async () => {
  if (tagForm.value.name && currentGroup.value) {
    try {
      await createTag({ category: currentGroup.value.name, name: tagForm.value.name })
      currentGroup.value.tags.push(tagForm.value.name)
      showTagModal.value = false
      tagForm.value = { name: '' }
    } catch (e) {
      console.error('Failed to create tag:', e)
    }
  }
}

const removeTag = async (groupId, index) => {
  const group = tagGroups.value.find(g => g.id === groupId)
  if (group) {
    const tagName = group.tags[index]
    const flatTag = flatTags.value.find(t => t.category === group.name && t.name === tagName)
    try {
      if (flatTag) {
        await deleteTag(flatTag.id)
      }
      group.tags.splice(index, 1)
    } catch (e) {
      console.error('Failed to delete tag:', e)
    }
  }
}

const editGroup = (group) => {
  console.log('编辑标签组:', group)
}

const deleteGroup = (group) => {
  console.log('删除标签组:', group)
}

const handleAddGroup = async () => {
  if (groupForm.value.name) {
    try {
      const groupName = groupForm.value.name
      const tagNames = groupForm.value.tags
      await Promise.all(tagNames.map(name => createTag({ category: groupName, name })))
      tagGroups.value.push({
        id: tagGroups.value.length + 1,
        name: groupName,
        tags: [...tagNames]
      })
      showAddModal.value = false
      groupForm.value = { name: '', tags: [] }
    } catch (e) {
      console.error('Failed to add group:', e)
    }
  }
}
</script>

<style lang="scss" scoped>
.tag-management {
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

  .add-btn {
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

.tag-groups {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.tag-group-card {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.group-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;

  .group-name {
    font-size: 16px;
    font-weight: 600;
    color: #1f2937;
    margin: 0;
  }

  .group-actions {
    display: flex;
    gap: 8px;

    .action-btn {
      width: 32px;
      height: 32px;
      border: none;
      border-radius: 6px;
      background: #F3F4F6;
      color: #6B7280;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;

      &:hover {
        background: #E5E7EB;
        color: #374151;
      }
    }
  }
}

.tags-container {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 16px;
}

.tag-item {
  display: flex;
  align-items: center;
  gap: 4px;
  background: #DBEAFE;
  color: #2563EB;
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 13px;

  .remove-tag {
    background: none;
    border: none;
    color: #2563EB;
    cursor: pointer;
    padding: 0;

    &:hover {
      color: #DC2626;
    }
  }
}

.add-tag-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  width: 100%;
  padding: 10px;
  border: 1px dashed #D1D5DB;
  border-radius: 8px;
  background: #fff;
  color: #6B7280;
  font-size: 14px;
  cursor: pointer;

  &:hover {
    border-color: #3B82F6;
    color: #3B82F6;
  }
}
</style>
