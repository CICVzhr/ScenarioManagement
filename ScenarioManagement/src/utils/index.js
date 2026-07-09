export const getStatusClass = (status) => {
  const statusMap = {
    '待审核': 'status-pending',
    '已通过': 'status-success',
    '已驳回': 'status-error',
    '设计中': 'status-processing',
    '审核中': 'status-review',
    '待验证': 'status-warning',
    '修改中': 'status-modifying',
    '已完成': 'status-success',
    '开发中': 'status-developing',
    '待修改': 'status-error',
    '进行中': 'status-processing',
    '已修改 2 处': 'status-warning'
  }
  return statusMap[status] || 'status-default'
}

export const getLevelClass = (level) => {
  const levelMap = { '低': 'badge-low', '中': 'badge-medium', '高': 'badge-high', '严重': 'badge-critical' }
  return levelMap[level] || 'badge-low'
}

export const getPriorityClass = (priority) => {
  const priorityMap = { '低': 'badge-low', '中': 'badge-medium', '高': 'badge-high', '紧急': 'badge-urgent' }
  return priorityMap[priority] || 'badge-low'
}

export const getTypeClass = (type) => {
  const typeMap = {
    '功能测试': 'type-functional',
    '性能测试': 'type-performance',
    '安全测试': 'type-security',
    '兼容性测试': 'type-compatibility'
  }
  return typeMap[type] || 'type-functional'
}

export const getDevStatusClass = (status) => {
  const statusMap = {
    '开发中': 'status-developing',
    '已完成': 'status-completed',
    '测试中': 'status-testing',
    '已发布': 'status-released'
  }
  return statusMap[status] || 'status-developing'
}

/** Convert comma-separated tag string to array */
export const parseTags = (tagStr) => {
  if (!tagStr) return []
  return String(tagStr).split(',').map(t => t.trim()).filter(Boolean)
}

/** Convert tag array to comma-separated string */
export const formatTags = (tagArr) => {
  if (!Array.isArray(tagArr)) return tagArr || ''
  return tagArr.join(',')
}