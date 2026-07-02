<template>
  <div class="search-bar">
    <el-input 
      v-model="searchKeyword" 
      :placeholder="placeholder" 
      class="search-input"
      @input="handleSearch"
    >
      <template #append>
        <el-icon><component :is="icons.Search" /></el-icon>
      </template>
    </el-input>
    <slot name="filters"></slot>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import * as icons from '@element-plus/icons-vue'

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  placeholder: {
    type: String,
    default: '搜索...'
  }
})

const emit = defineEmits(['update:modelValue', 'search'])

const searchKeyword = ref(props.modelValue)

watch(() => props.modelValue, (val) => {
  searchKeyword.value = val
})

const handleSearch = () => {
  emit('update:modelValue', searchKeyword.value)
  emit('search', searchKeyword.value)
}
</script>

<style lang="scss" scoped>
.search-bar {
  display: flex;
  gap: 16px;
  align-items: center;
  flex-wrap: wrap;
}

.search-input {
  width: 300px;
}
</style>
