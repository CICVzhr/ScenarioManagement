<template>
  <div class="dashboard">
    <div class="stats-grid">
      <div class="stat-card">
        <div class="stat-icon blue">
          <span>📍</span>
        </div>
        <div class="stat-content">
          <div class="stat-label">总场景数</div>
          <div class="stat-value">{{ stats.totalScenes }}</div>
          <div class="stat-change positive">
            <span class="change-icon">↑</span>
            <span>{{ stats.totalScenesChange }}</span>
            <span>相比上月</span>
          </div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon green">
          <span>📦</span>
        </div>
        <div class="stat-content">
          <div class="stat-label">待审核数据</div>
          <div class="stat-value">{{ stats.pendingReview }}</div>
          <div class="stat-change positive">
            <span class="change-icon">↑</span>
            <span>{{ stats.pendingReviewChange }}</span>
            <span>相比上月</span>
          </div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon orange">
          <span>📋</span>
        </div>
        <div class="stat-content">
          <div class="stat-label">待审核场景</div>
          <div class="stat-value">{{ stats.pendingApproval }}</div>
          <div class="stat-change negative">
            <span class="change-icon">↓</span>
            <span>{{ stats.pendingApprovalChange }}</span>
            <span>相比上周</span>
          </div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon purple">
          <span>🗂️</span>
        </div>
        <div class="stat-content">
          <div class="stat-label">数据来源</div>
          <div class="stat-value">{{ stats.dataSources }}</div>
          <div class="stat-change positive">
            <span class="change-icon">+</span>
            <span>{{ stats.dataSourcesChange }}</span>
            <span>本月</span>
          </div>
        </div>
      </div>
    </div>

    <div class="charts-row">
      <div class="chart-card">
        <div class="card-header">
          <h3>流程处理状态分布</h3>
        </div>
        <div class="chart-content">
          <div ref="pieChart" class="chart"></div>
        </div>
        <div class="legend">
          <div v-for="item in processDistribution" :key="item.name" class="legend-item">
            <span class="legend-color" :style="{ background: item.color || '#0052cc' }"></span>
            <span class="legend-text">{{ item.name }}</span>
          </div>
        </div>
      </div>
      <div class="chart-card">
        <div class="card-header">
          <h3>危险场景分类统计</h3>
        </div>
        <div class="chart-content">
          <div ref="barChart" class="chart"></div>
        </div>
      </div>
    </div>

    <div class="table-card">
      <div class="card-header">
        <h3>最近待处理任务</h3>
        <a href="#" class="view-all">查看全部</a>
      </div>
      <div class="table-content">
        <table>
          <thead>
            <tr>
              <th>任务名称</th>
              <th>所属阶段</th>
              <th>提交人</th>
              <th>提交时间</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="task in recentTasks" :key="task.id">
              <td>{{ task.name }}</td>
              <td>{{ task.stage }}</td>
              <td>{{ task.submitter }}</td>
              <td>{{ formatTime(task.submitTime) }}</td>
              <td>
                <span :class="['status-badge', getStatusClass(task.status)]">{{ task.status }}</span>
              </td>
              <td>
                <button :class="['action-btn', 'btn-primary']">处理</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getDashboardStats, getProcessDistribution, getDangerCategory, getRecentTasks } from '@/api/dashboard'
import { getStatusClass } from '@/utils'

const pieChart = ref(null)
const barChart = ref(null)
let pieChartInstance = null
let barChartInstance = null

const stats = ref({
  totalScenes: 0,
  pendingReview: 0,
  pendingApproval: 0,
  dataSources: 0,
  totalScenesChange: '0%',
  pendingReviewChange: '0%',
  pendingApprovalChange: '0%',
  dataSourcesChange: '0%'
})

const processDistribution = ref([])
const dangerCategory = ref([])
const recentTasks = ref([])

const formatTime = (time) => {
  if (!time) return ''
  // Convert "2023-09-15T14:30:00" to "2023-09-15 14:30"
  return time.replace('T', ' ').substring(0, 16)
}

async function fetchDashboardData() {
  try {
    const [statsData, processData, categoryData, tasksData] = await Promise.all([
      getDashboardStats(),
      getProcessDistribution(),
      getDangerCategory(),
      getRecentTasks()
    ])
    stats.value = statsData
    processDistribution.value = processData
    dangerCategory.value = categoryData
    recentTasks.value = tasksData
    initCharts()
  } catch (e) {
    console.error('Failed to load dashboard data:', e)
  }
}

const initCharts = () => {
  if (pieChart.value && processDistribution.value.length > 0) {
    pieChartInstance = echarts.init(pieChart.value)
    pieChartInstance.setOption({
      series: [{
        type: 'pie',
        radius: ['45%', '70%'],
        center: ['50%', '50%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 8,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: { show: false },
        emphasis: {
          label: {
            show: true,
            fontSize: 16,
            fontWeight: 'bold'
          }
        },
        labelLine: { show: false },
        data: processDistribution.value.map(item => ({
          value: item.value,
          name: item.name,
          itemStyle: { color: item.color || '#0052cc' }
        }))
      }]
    })
  }

  if (barChart.value && dangerCategory.value.length > 0) {
    barChartInstance = echarts.init(barChart.value)
    barChartInstance.setOption({
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        top: '10%',
        containLabel: true
      },
      xAxis: {
        type: 'value',
        axisLine: { show: false },
        axisTick: { show: false },
        axisLabel: { color: '#6b7280', fontSize: 12 }
      },
      yAxis: {
        type: 'category',
        data: dangerCategory.value.map(item => item.name).reverse(),
        axisLine: { show: false },
        axisTick: { show: false },
        axisLabel: { color: '#374151', fontSize: 13 }
      },
      series: [{
        type: 'bar',
        barWidth: '60%',
        data: dangerCategory.value.map(item => item.value).reverse(),
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: '#0052cc' },
            { offset: 1, color: '#3b82f6' }
          ]),
          borderRadius: [0, 6, 6, 0]
        }
      }]
    })
  }
}

const handleResize = () => {
  pieChartInstance?.resize()
  barChartInstance?.resize()
}

onMounted(() => {
  fetchDashboardData()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  pieChartInstance?.dispose()
  barChartInstance?.dispose()
})
</script>

<style lang="scss" scoped>
.dashboard {
  padding: 20px;
  background: #f3f4f6;
  min-height: calc(100vh - 60px);
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

.stat-card {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);

  .stat-icon {
    width: 56px;
    height: 56px;
    border-radius: 14px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 24px;

    &.blue { background: #eff6ff; }
    &.green { background: #ecfdf5; }
    &.orange { background: #fffbeb; }
    &.purple { background: #f5f3ff; }
  }

  .stat-content {
    flex: 1;

    .stat-label {
      font-size: 13px;
      color: #6b7280;
      margin-bottom: 4px;
    }

    .stat-value {
      font-size: 28px;
      font-weight: 700;
      color: #1f2937;
      margin-bottom: 6px;
    }

    .stat-change {
      font-size: 12px;
      display: flex;
      align-items: center;
      gap: 4px;

      &.positive { color: #10b981; }
      &.negative { color: #ef4444; }

      .change-icon { font-weight: 600; }
    }
  }
}

.charts-row {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

.chart-card {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);

  .card-header {
    margin-bottom: 20px;
    h3 { font-size: 16px; font-weight: 600; color: #1f2937; margin: 0; }
  }

  .chart-content {
    .chart { height: 280px; }
  }

  .legend {
    display: flex;
    flex-wrap: wrap;
    gap: 16px;
    margin-top: 16px;
    justify-content: center;

    .legend-item {
      display: flex;
      align-items: center;
      gap: 6px;

      .legend-color { width: 12px; height: 12px; border-radius: 3px; }
      .legend-text { font-size: 12px; color: #6b7280; }
    }
  }
}

.table-card {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;

    h3 { font-size: 16px; font-weight: 600; color: #1f2937; margin: 0; }

    .view-all {
      font-size: 13px; color: #0052cc; text-decoration: none;
      &:hover { text-decoration: underline; }
    }
  }

  .table-content {
    overflow-x: auto;

    table {
      width: 100%;
      border-collapse: collapse;

      thead tr th {
        text-align: left; padding: 12px 16px; font-size: 13px; font-weight: 600;
        color: #6b7280; border-bottom: 1px solid #f3f4f6;
      }

      tbody tr {
        &:hover { background: #f9fafb; }
        td {
          padding: 14px 16px; font-size: 14px; color: #374151;
          border-bottom: 1px solid #f3f4f6;
        }
      }
    }
  }
}

.status-badge {
  padding: 4px 12px; border-radius: 20px; font-size: 12px; font-weight: 500;

  &.status-pending { background: #fff3e5; color: #f59e0b; }
  &.status-processing { background: #eff6ff; color: #0052cc; }
  &.status-completed { background: #ecfdf5; color: #10b981; }
  &.status-success { background: #ecfdf5; color: #10b981; }
  &.status-error { background: #fef2f2; color: #ef4444; }
  &.status-review { background: #f5f3ff; color: #8b5cf6; }
  &.status-warning { background: #fffbeb; color: #f59e0b; }
  &.status-modifying { background: #fff7ed; color: #f97316; }
  &.status-developing { background: #eff6ff; color: #0052cc; }
}

.action-btn {
  padding: 5px 16px; border-radius: 6px; font-size: 13px; font-weight: 500;
  border: none; cursor: pointer;

  &.btn-primary {
    background: #0052cc; color: #fff;
    &:hover { background: #0044a8; }
  }

  &.btn-secondary {
    background: #f3f4f6; color: #6b7280;
    &:hover { background: #e5e7eb; }
  }
}
</style>
