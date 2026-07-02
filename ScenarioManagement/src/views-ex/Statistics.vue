<template>
  <div class="statistics">
    <div class="page-header">
      <h2>统计分析</h2>
    </div>

    <div class="stats-overview">
      <div class="overview-card">
        <div class="overview-header">
          <h3>数据概览</h3>
          <el-select v-model="timeRange" placeholder="选择时间范围">
            <el-option label="本周" value="week" />
            <el-option label="本月" value="month" />
            <el-option label="本季度" value="quarter" />
            <el-option label="本年" value="year" />
          </el-select>
        </div>
        <div class="overview-stats">
          <div class="stat-item">
            <span class="stat-value">{{ overview.totalScenes }}</span>
            <span class="stat-label">场景总数</span>
          </div>
          <div class="stat-item">
            <span class="stat-value">{{ overview.totalCases }}</span>
            <span class="stat-label">用例总数</span>
          </div>
          <div class="stat-item">
            <span class="stat-value">{{ overview.totalTests }}</span>
            <span class="stat-label">测试总数</span>
          </div>
          <div class="stat-item">
            <span class="stat-value">{{ overview.passRate }}%</span>
            <span class="stat-label">通过率</span>
          </div>
        </div>
      </div>
    </div>

    <div class="charts-row">
      <div class="chart-card">
        <div class="card-header">
          <h3>场景类型分布</h3>
        </div>
        <div ref="sceneTypeChart" class="chart"></div>
      </div>

      <div class="chart-card">
        <div class="card-header">
          <h3>测试结果趋势</h3>
        </div>
        <div ref="trendChart" class="chart"></div>
      </div>
    </div>

    <div class="charts-row">
      <div class="chart-card">
        <div class="card-header">
          <h3>危险等级分布</h3>
        </div>
        <div ref="levelChart" class="chart"></div>
      </div>

      <div class="chart-card">
        <div class="card-header">
          <h3>评价结果统计</h3>
        </div>
        <div ref="evaluationChart" class="chart"></div>
      </div>
    </div>

    <div class="report-section">
      <div class="report-header">
        <h3>统计报告</h3>
        <ActionButton text="生成报告" type="primary" @click="generateReport" />
      </div>
      <DataTable :data="reports">
        <el-table-column prop="id" label="报告ID" width="100" />
        <el-table-column prop="name" label="报告名称" width="200" />
        <el-table-column prop="type" label="报告类型" width="120" />
        <el-table-column prop="createTime" label="生成时间" width="150" />
        <el-table-column prop="creator" label="生成人" width="100" />
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <ActionButton text="下载" type="primary" size="small" @click="downloadReport(scope.row)" />
            <ActionButton text="查看" type="default" size="small" @click="viewReport(scope.row)" />
          </template>
        </el-table-column>
      </DataTable>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts'
import ActionButton from '@/components/common/ActionButton.vue'
import DataTable from '@/components/common/DataTable.vue'

const timeRange = ref('month')
const sceneTypeChart = ref(null)
const trendChart = ref(null)
const levelChart = ref(null)
const evaluationChart = ref(null)

const overview = ref({
  totalScenes: 256,
  totalCases: 180,
  totalTests: 450,
  passRate: 85.6
})

const reports = ref([
  { id: 'R001', name: '月度测试报告', type: '月度报告', createTime: '2024-01-15', creator: '系统' },
  { id: 'R002', name: '场景分析报告', type: '专项报告', createTime: '2024-01-10', creator: '张三' },
  { id: 'R003', name: '危险场景统计', type: '专项报告', createTime: '2024-01-08', creator: '李四' }
])

const initCharts = () => {
  nextTick(() => {
    // 场景类型分布
    const sceneType = echarts.init(sceneTypeChart.value)
    sceneType.setOption({
      tooltip: { trigger: 'item' },
      series: [{
        type: 'pie',
        radius: ['40%', '70%'],
        data: [
          { name: '高速场景', value: 80, itemStyle: { color: '#3B82F6' } },
          { name: '城市道路', value: 60, itemStyle: { color: '#10B981' } },
          { name: '乡村道路', value: 40, itemStyle: { color: '#F59E0B' } },
          { name: '停车场', value: 30, itemStyle: { color: '#7C3AED' } },
          { name: '其他', value: 20, itemStyle: { color: '#6B7280' } }
        ]
      }]
    })

    // 测试结果趋势
    const trend = echarts.init(trendChart.value)
    trend.setOption({
      tooltip: { trigger: 'axis' },
      xAxis: {
        type: 'category',
        data: ['1月', '2月', '3月', '4月', '5月', '6月']
      },
      yAxis: { type: 'value' },
      series: [
        { name: '通过', type: 'line', data: [65, 72, 78, 82, 85, 88], itemStyle: { color: '#10B981' } },
        { name: '未通过', type: 'line', data: [35, 28, 22, 18, 15, 12], itemStyle: { color: '#EF4444' } }
      ]
    })

    // 危险等级分布
    const level = echarts.init(levelChart.value)
    level.setOption({
      tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
      xAxis: { type: 'category', data: ['低', '中', '高', '严重'] },
      yAxis: { type: 'value' },
      series: [{
        type: 'bar',
        data: [
          { value: 120, itemStyle: { color: '#10B981' } },
          { value: 80, itemStyle: { color: '#F59E0B' } },
          { value: 40, itemStyle: { color: '#EF4444' } },
          { value: 16, itemStyle: { color: '#B91C1C' } }
        ]
      }]
    })

    // 评价结果统计
    const evaluation = echarts.init(evaluationChart.value)
    evaluation.setOption({
      tooltip: { trigger: 'item' },
      series: [{
        type: 'pie',
        radius: '60%',
        data: [
          { name: '通过', value: 75, itemStyle: { color: '#10B981' } },
          { name: '需改进', value: 20, itemStyle: { color: '#F59E0B' } },
          { name: '未通过', value: 5, itemStyle: { color: '#EF4444' } }
        ]
      }]
    })

    window.addEventListener('resize', () => {
      sceneType.resize()
      trend.resize()
      level.resize()
      evaluation.resize()
    })
  })
}

const generateReport = () => console.log('生成报告')
const downloadReport = (row) => console.log('下载报告:', row)
const viewReport = (row) => console.log('查看报告:', row)

onMounted(() => {
  initCharts()
})
</script>

<style lang="scss" scoped>
.statistics {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;

  h2 {
    font-size: 20px;
    font-weight: 600;
    color: #1f2937;
    margin: 0;
  }
}

.stats-overview {
  margin-bottom: 20px;
}

.overview-card {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);

  .overview-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;

    h3 {
      font-size: 16px;
      font-weight: 600;
      color: #1f2937;
      margin: 0;
    }
  }

  .overview-stats {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;

    .stat-item {
      text-align: center;
      padding: 16px;
      background: #f9fafb;
      border-radius: 8px;

      .stat-value {
        font-size: 28px;
        font-weight: 700;
        color: #3B82F6;
        margin-bottom: 8px;
      }

      .stat-label {
        font-size: 14px;
        color: #6B7280;
      }
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
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);

  .card-header {
    margin-bottom: 16px;

    h3 {
      font-size: 16px;
      font-weight: 600;
      color: #1f2937;
      margin: 0;
    }
  }

  .chart {
    height: 280px;
  }
}

.report-section {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);

  .report-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;

    h3 {
      font-size: 16px;
      font-weight: 600;
      color: #1f2937;
      margin: 0;
    }
  }
}
</style>