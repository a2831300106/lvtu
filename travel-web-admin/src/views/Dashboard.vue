<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import { ArrowRight } from '@element-plus/icons-vue'

const statsData = ref([
  { title: '用户总数', value: 12856, icon: 'User', color: '#3b82f6', gradient: 'linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%)', increase: '+12%' },
  { title: '产品总数', value: 2368, icon: 'Goods', color: '#10b981', gradient: 'linear-gradient(135deg, #10b981 0%, #059669 100%)', increase: '+8%' },
  { title: '订单总数', value: 8923, icon: 'Tickets', color: '#f59e0b', gradient: 'linear-gradient(135deg, #f59e0b 0%, #d97706 100%)', increase: '+15%' },
  { title: '今日订单', value: 156, icon: 'ShoppingCart', color: '#ef4444', gradient: 'linear-gradient(135deg, #ef4444 0%, #dc2626 100%)', increase: '+23%' }
])

const chartRef = ref(null)
const pieChartRef = ref(null)

const recentOrders = [
  { id: 'ORD20260528001', user: '张三', product: '丽江古城木府门票', amount: 40, status: '已完成', time: '2026-05-28 10:23:45' },
  { id: 'ORD20260528002', user: '李四', product: '三亚亚特兰蒂斯酒店', amount: 888, status: '进行中', time: '2026-05-28 09:15:32' },
  { id: 'ORD20260528003', user: '王五', product: '张家界国家森林公园', amount: 158, status: '已完成', time: '2026-05-28 08:45:12' },
  { id: 'ORD20260528004', user: '赵六', product: '成都火锅一日游', amount: 268, status: '进行中', time: '2026-05-28 07:32:18' },
  { id: 'ORD20260528005', user: '钱七', product: '杭州西湖游船', amount: 80, status: '已完成', time: '2026-05-28 06:12:55' }
]

onMounted(() => {
  initLineChart()
  initPieChart()
})

const initLineChart = () => {
  const chart = echarts.init(chartRef.value)
  const option = {
    tooltip: { 
      trigger: 'axis',
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e2e8f0',
      borderWidth: 1,
      textStyle: { color: '#334155' },
      boxShadow: '0 4px 12px rgba(0, 0, 0, 0.08)'
    },
    legend: { data: ['订单量'], bottom: 10, textStyle: { color: '#64748b' } },
    grid: { left: '3%', right: '4%', bottom: '15%', top: '10%', containLabel: true },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
      axisLine: { lineStyle: { color: '#e2e8f0' } },
      axisLabel: { color: '#64748b', fontSize: 12 }
    },
    yAxis: { 
      type: 'value',
      axisLine: { show: false },
      axisTick: { show: false },
      axisLabel: { color: '#64748b', fontSize: 12 },
      splitLine: { lineStyle: { color: '#f1f5f9', type: 'dashed' } }
    },
    series: [{
      name: '订单量',
      type: 'line',
      smooth: true,
      data: [820, 932, 901, 1234, 1290, 1330, 1520],
      areaStyle: { 
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: 'rgba(59, 130, 246, 0.3)' },
          { offset: 1, color: 'rgba(59, 130, 246, 0.02)' }
        ])
      },
      lineStyle: { color: '#3b82f6', width: 3 },
      itemStyle: { color: '#3b82f6' },
      symbol: 'circle',
      symbolSize: 8,
      emphasis: {
        itemStyle: {
          color: '#3b82f6',
          borderColor: '#fff',
          borderWidth: 2,
          shadowBlur: 10,
          shadowColor: 'rgba(59, 130, 246, 0.5)'
        }
      }
    }]
  }
  chart.setOption(option)
  
  window.addEventListener('resize', () => chart.resize())
}

const initPieChart = () => {
  const chart = echarts.init(pieChartRef.value)
  const option = {
    tooltip: { 
      trigger: 'item',
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      borderColor: '#e2e8f0',
      borderWidth: 1,
      textStyle: { color: '#334155' },
      boxShadow: '0 4px 12px rgba(0, 0, 0, 0.08)',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: { 
      orient: 'vertical', 
      right: '5%', 
      top: 'center',
      textStyle: { color: '#64748b', fontSize: 12 },
      itemGap: 12
    },
    series: [{
      type: 'pie',
      radius: ['45%', '70%'],
      center: ['35%', '50%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 8,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: { show: false },
      emphasis: {
        label: { show: true, fontSize: 14, fontWeight: 'bold', color: '#1e293b' },
        itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.15)' }
      },
      labelLine: { show: false },
      data: [
        { value: 335, name: '酒店民宿', itemStyle: { color: '#3b82f6' } },
        { value: 234, name: '景点门票', itemStyle: { color: '#10b981' } },
        { value: 154, name: '跟团游', itemStyle: { color: '#f59e0b' } },
        { value: 135, name: '定制游', itemStyle: { color: '#8b5cf6' } },
        { value: 98, name: '周边游', itemStyle: { color: '#ec4899' } }
      ]
    }]
  }
  chart.setOption(option)
  
  window.addEventListener('resize', () => chart.resize())
}
</script>

<template>
  <div class="dashboard">
    <el-row :gutter="24" class="stats-row">
      <el-col :span="6" v-for="(stat, index) in statsData" :key="stat.title">
        <div class="stat-card" :style="{ animationDelay: `${index * 0.1}s` }">
          <div class="stat-icon-wrapper">
            <div class="stat-icon" :style="{ background: stat.gradient }">
              <el-icon size="28"><component :is="stat.icon" /></el-icon>
            </div>
            <div class="stat-icon-glow" :style="{ background: stat.color }"></div>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stat.value.toLocaleString() }}</div>
            <div class="stat-title">{{ stat.title }}</div>
            <div class="stat-increase positive">
              <el-icon size="14"><ArrowRight /></el-icon>
              <span>{{ stat.increase }} 较上周</span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="24" class="chart-row">
      <el-col :span="16">
        <div class="chart-card">
          <div class="chart-header">
            <h3 class="chart-title">订单趋势</h3>
            <div class="chart-tabs">
              <span class="chart-tab active">本周</span>
              <span class="chart-tab">本月</span>
              <span class="chart-tab">本季度</span>
            </div>
          </div>
          <div ref="chartRef" class="chart-content"></div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="chart-card">
          <div class="chart-header">
            <h3 class="chart-title">产品分类占比</h3>
          </div>
          <div ref="pieChartRef" class="chart-content"></div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="24" class="table-row">
      <el-col :span="24">
        <div class="card">
          <div class="card-header">
            <div class="card-title-wrapper">
              <el-icon class="card-icon"><Ticket /></el-icon>
              <span class="card-title">最新订单</span>
            </div>
            <el-button type="primary" text class="view-more-btn" @click="$router.push('/order/list')">
              查看更多 <el-icon><ArrowRight /></el-icon>
            </el-button>
          </div>
          <el-table :data="recentOrders" style="width: 100%" class="order-table">
            <el-table-column prop="id" label="订单号" width="180">
              <template #default="{ row }">
                <span class="order-id">{{ row.id }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="user" label="用户" width="120">
              <template #default="{ row }">
                <div class="user-cell">
                  <el-avatar :size="28" class="mini-avatar">{{ row.user.charAt(0) }}</el-avatar>
                  <span>{{ row.user }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="product" label="产品" />
            <el-table-column prop="amount" label="金额" width="100">
              <template #default="{ row }">
                <span class="price">¥{{ row.amount }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template #default="{ row }">
                <el-tag :type="row.status === '已完成' ? 'success' : 'warning'" class="status-tag">
                  {{ row.status }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="time" label="下单时间" width="180" />
          </el-table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.dashboard {
  padding: 0;
}

.stats-row {
  margin-bottom: 24px;
}

.stat-card {
  background: #ffffff;
  border-radius: 16px;
  padding: 24px;
  display: flex;
  align-items: center;
  gap: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
  transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
  animation: slideUp 0.5s ease-out forwards;
  opacity: 0;
  position: relative;
  overflow: hidden;
}

.stat-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: linear-gradient(90deg, transparent, rgba(59, 130, 246, 0.3), transparent);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.1);
}

.stat-card:hover::before {
  opacity: 1;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.stat-icon-wrapper {
  position: relative;
}

.stat-icon {
  width: 64px;
  height: 64px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  position: relative;
  z-index: 1;
  box-shadow: 0 4px 14px rgba(0, 0, 0, 0.15);
}

.stat-icon-glow {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 80px;
  height: 80px;
  border-radius: 50%;
  opacity: 0.15;
  filter: blur(16px);
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 32px;
  font-weight: 700;
  color: #1e293b;
  line-height: 1.2;
}

.stat-title {
  font-size: 14px;
  color: #64748b;
  margin-top: 6px;
}

.stat-increase {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: #10b981;
  margin-top: 4px;
  font-weight: 500;
}

.chart-row {
  margin-bottom: 24px;
}

.chart-card {
  background: #ffffff;
  border-radius: 16px;
  padding: 24px;
  height: 360px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.chart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.chart-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
}

.chart-tabs {
  display: flex;
  gap: 4px;
}

.chart-tab {
  padding: 6px 12px;
  font-size: 12px;
  color: #64748b;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.25s ease;
}

.chart-tab.active {
  background: #eff6ff;
  color: #3b82f6;
  font-weight: 500;
}

.chart-tab:hover {
  background: #f1f5f9;
}

.chart-content {
  height: calc(100% - 60px);
}

.table-row {
  margin-bottom: 24px;
}

.card {
  background: #ffffff;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.card-title-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
}

.card-icon {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  border-radius: 10px;
  color: #fff;
}

.card-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
}

.view-more-btn {
  color: #3b82f6;
  font-weight: 500;
  transition: all 0.25s ease;
}

.view-more-btn:hover {
  background: rgba(59, 130, 246, 0.08);
}

.order-table {
  --el-table-header-text-color: #64748b;
  --el-table-row-hover-bg-color: #f8fafc;
}

:deep(.el-table) {
  border-radius: 12px;
  overflow: hidden;
}

:deep(.el-table__header) {
  background: #f8fafc;
}

:deep(.el-table__header th) {
  padding: 14px 12px;
  font-weight: 600;
  font-size: 13px;
  color: #64748b;
  border-bottom: 1px solid #e2e8f0;
}

:deep(.el-table__row) {
  transition: all 0.2s ease;
}

:deep(.el-table__row:hover) {
  background: #f8fafc !important;
}

:deep(.el-table__cell) {
  padding: 14px 12px;
  font-size: 14px;
  color: #334155;
  border-bottom: 1px solid #f1f5f9;
}

.order-id {
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 13px;
  color: #64748b;
}

.user-cell {
  display: flex;
  align-items: center;
  gap: 8px;
}

.mini-avatar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  font-size: 12px;
  font-weight: 600;
  color: #fff;
}

.price {
  color: #ef4444;
  font-weight: 600;
}

.status-tag {
  padding: 4px 10px;
  border-radius: 20px;
  font-size: 12px;
}

@media screen and (max-width: 1200px) {
  .stats-row .el-col {
    margin-bottom: 16px;
  }
  
  .chart-row .el-col {
    margin-bottom: 16px;
  }
}
</style>