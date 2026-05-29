<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const activeTab = ref(0)
const tabs = ['全部', '待付款', '待出行', '已完成', '退款']

const orders = ref([])

onMounted(() => {
  const stored = localStorage.getItem('orders')
  if (stored) {
    orders.value = JSON.parse(stored)
  }
})

const filteredOrders = () => {
  if (activeTab.value === 0) return orders.value
  const statusMap = ['all', 'pending', 'paid', 'completed', 'refund']
  const status = statusMap[activeTab.value]
  return orders.value.filter(o => o.status === status)
}

const statusNames = {
  pending: '待付款',
  paid: '已支付',
  completed: '已完成',
  refund: '退款中'
}

const goBack = () => {
  router.back()
}

const goToDetail = (id) => {
  router.push(`/order/${id}`)
}

const goToPay = (order) => {
  order.status = 'paid'
  localStorage.setItem('orders', JSON.stringify(orders.value))
}

const formatDate = (dateStr) => {
  return new Date(dateStr).toLocaleDateString('zh-CN')
}
</script>

<template>
  <div class="orders-page">
    <div class="navbar">
      <div class="navbar-back" @click="goBack">← 返回</div>
      <div class="navbar-title">我的订单</div>
      <div style="width: 50px;"></div>
    </div>

    <div class="tabs">
      <div
        class="tab"
        :class="{ active: activeTab === index }"
        v-for="(tab, index) in tabs"
        :key="tab"
        @click="activeTab = index"
      >
        {{ tab }}
      </div>
    </div>

    <div class="orders-list">
      <div
        class="order-card card"
        v-for="order in filteredOrders()"
        :key="order.id"
        @click="goToDetail(order.id)"
      >
        <div class="order-header">
          <span class="order-id">订单号: {{ order.id }}</span>
          <span class="order-status" :class="order.status">{{ statusNames[order.status] }}</span>
        </div>
        <div class="order-info">
          <div class="order-icon">{{ order.productName.includes('木府') ? '🏯' : '🏨' }}</div>
          <div class="order-detail">
            <div class="order-name">{{ order.productName }}</div>
            <div class="order-meta">{{ order.date }} · {{ order.ticket }} · {{ order.quantity }}张</div>
          </div>
        </div>
        <div class="order-footer">
          <div class="order-price">¥{{ order.total }}</div>
          <div class="order-actions">
            <button
              class="action-btn pay-btn"
              v-if="order.status === 'pending'"
              @click.stop="goToPay(order)"
            >立即支付</button>
            <button
              class="action-btn"
              v-if="order.status === 'paid'"
              @click.stop="goToDetail(order.id)"
            >查看凭证</button>
          </div>
        </div>
      </div>

      <div class="empty-state" v-if="filteredOrders().length === 0">
        <div class="empty-icon">📋</div>
        <div class="empty-text">暂无订单</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.orders-page {
  min-height: 100%;
  background: linear-gradient(180deg, #f8fafc 0%, #f1f5f9 100%);
}

.navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: var(--header-height);
  background: linear-gradient(135deg, #ffffff 0%, #f8fafc 100%);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
  z-index: 100;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
}

.navbar-back {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  color: var(--text-primary);
  border-radius: 50%;
  transition: all 0.3s ease;
}

.navbar-back:active {
  background-color: rgba(0, 0, 0, 0.05);
  transform: scale(0.95);
}

.navbar-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  letter-spacing: 0.5px;
}

.tabs {
  position: fixed;
  top: var(--header-height);
  left: 0;
  right: 0;
  display: flex;
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  padding: 0 16px;
  z-index: 100;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
}

.tab {
  flex: 1;
  text-align: center;
  padding: 14px 0;
  font-size: 14px;
  color: var(--text-secondary);
  position: relative;
  transition: all 0.3s ease;
}

.tab.active {
  color: #3b82f6;
  font-weight: 500;
}

.tab.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 24px;
  height: 3px;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.4);
}

.orders-list {
  padding: calc(var(--header-height) + 56px) 16px 16px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.card {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 16px;
  padding: 18px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
  transition: all 0.35s ease;
}

.order-card:active {
  transform: scale(0.99);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 14px;
}

.order-id {
  font-size: 12px;
  color: var(--text-hint);
  letter-spacing: 0.3px;
}

.order-status {
  font-size: 13px;
  padding: 4px 10px;
  border-radius: 16px;
  font-weight: 500;
}

.order-status.pending {
  color: #ef4444;
  background: linear-gradient(135deg, #fef2f2 0%, #fee2e2 100%);
}

.order-status.paid {
  color: #3b82f6;
  background: linear-gradient(135deg, #eff6ff 0%, #dbeafe 100%);
}

.order-status.completed {
  color: #6b7280;
  background: linear-gradient(135deg, #f3f4f6 0%, #e5e7eb 100%);
}

.order-status.refund {
  color: #f59e0b;
  background: linear-gradient(135deg, #fffbeb 0%, #fef3c7 100%);
}

.order-info {
  display: flex;
  gap: 14px;
  margin-bottom: 14px;
}

.order-icon {
  width: 64px;
  height: 64px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 32px;
  flex-shrink: 0;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.order-detail {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.order-name {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 6px;
}

.order-meta {
  font-size: 13px;
  color: var(--text-hint);
}

.order-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 14px;
  border-top: 1px solid rgba(0, 0, 0, 0.06);
}

.order-price {
  color: #ef4444;
  font-size: 20px;
  font-weight: 700;
}

.order-actions {
  display: flex;
  gap: 10px;
}

.action-btn {
  padding: 8px 20px;
  border-radius: 20px;
  font-size: 13px;
  font-weight: 500;
  border: 1.5px solid rgba(0, 0, 0, 0.08);
  color: var(--text-secondary);
  transition: all 0.3s ease;
}

.action-btn:active {
  transform: scale(0.96);
}

.action-btn.pay-btn {
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  color: white;
  border: none;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.4);
}

.action-btn.pay-btn:active {
  box-shadow: 0 2px 6px rgba(59, 130, 246, 0.3);
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 100px 0;
  color: var(--text-hint);
}

.empty-icon {
  font-size: 80px;
  margin-bottom: 20px;
  opacity: 0.7;
}

.empty-text {
  font-size: 15px;
  color: var(--text-hint);
}
</style>