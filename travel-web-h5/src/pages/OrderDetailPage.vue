<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const order = ref(null)

const statusNames = {
  pending: '待付款',
  paid: '已支付',
  completed: '已完成',
  refund: '退款中'
}

onMounted(() => {
  const stored = localStorage.getItem('orders')
  if (stored) {
    const orders = JSON.parse(stored)
    order.value = orders.find(o => o.id === route.params.id)
  }
})

const goBack = () => {
  router.back()
}

const formatDate = (dateStr) => {
  return new Date(dateStr).toLocaleString('zh-CN')
}
</script>

<template>
  <div class="order-detail-page" v-if="order">
    <div class="navbar">
      <div class="navbar-back" @click="goBack">← 返回</div>
      <div class="navbar-title">订单详情</div>
      <div style="width: 50px;"></div>
    </div>

    <div class="detail-content">
      <div class="status-section card">
        <div class="status-icon">{{ order.status === 'paid' ? '✅' : order.status === 'pending' ? '⏳' : '📋' }}</div>
        <div class="status-info">
          <div class="status-name">{{ statusNames[order.status] }}</div>
          <div class="status-desc" v-if="order.status === 'paid'">请妥善保管电子凭证</div>
          <div class="status-desc" v-else-if="order.status === 'pending'">请尽快完成支付</div>
        </div>
      </div>

      <div class="info-section card">
        <div class="section-title">订单信息</div>
        <div class="info-row">
          <span class="label">订单编号</span>
          <span class="value">{{ order.id }}</span>
        </div>
        <div class="info-row">
          <span class="label">下单时间</span>
          <span class="value">{{ formatDate(order.createTime) }}</span>
        </div>
        <div class="info-row">
          <span class="label">游玩日期</span>
          <span class="value">{{ order.date }}</span>
        </div>
      </div>

      <div class="info-section card">
        <div class="section-title">产品信息</div>
        <div class="product-info">
          <div class="product-icon">🏯</div>
          <div class="product-detail">
            <div class="product-name">{{ order.productName }}</div>
            <div class="product-meta">{{ order.ticket }} · {{ order.quantity }}张</div>
          </div>
        </div>
      </div>

      <div class="info-section card">
        <div class="section-title">联系人信息</div>
        <div class="info-row">
          <span class="label">姓名</span>
          <span class="value">{{ order.contact?.name || '未填写' }}</span>
        </div>
        <div class="info-row">
          <span class="label">手机号</span>
          <span class="value">{{ order.contact?.phone || '未填写' }}</span>
        </div>
      </div>

      <div class="info-section card">
        <div class="section-title">支付信息</div>
        <div class="info-row">
          <span class="label">支付方式</span>
          <span class="value">{{ order.status !== 'pending' ? '微信支付' : '-' }}</span>
        </div>
        <div class="info-row">
          <span class="label">支付时间</span>
          <span class="value">{{ order.status !== 'pending' ? formatDate(order.createTime) : '-' }}</span>
        </div>
        <div class="info-row total">
          <span class="label">订单金额</span>
          <span class="value price">¥{{ order.total }}</span>
        </div>
      </div>

      <div class="voucher-section card" v-if="order.status === 'paid'">
        <div class="section-title">电子凭证</div>
        <div class="voucher-qr">
          <div class="qr-placeholder">二维码</div>
          <div class="qr-code">📋</div>
        </div>
        <div class="voucher-tip">请在入口处出示此凭证</div>
      </div>
    </div>

    <div class="bottom-bar">
      <button class="service-btn">联系客服</button>
      <button
        class="action-btn"
        v-if="order.status === 'pending'"
      >立即支付</button>
      <button
        class="action-btn"
        v-else-if="order.status === 'paid'"
      >申请退款</button>
    </div>
  </div>

  <div class="order-detail-page empty" v-else>
    <div class="empty-state">
      <div class="empty-icon">📋</div>
      <div class="empty-text">订单不存在</div>
      <button class="btn-primary btn" @click="router.push('/home')">返回首页</button>
    </div>
  </div>
</template>

<style scoped>
.order-detail-page {
  min-height: 100%;
  background: linear-gradient(180deg, #f8fafc 0%, #f1f5f9 100%);
  padding-bottom: 80px;
}

.order-detail-page.empty {
  display: flex;
  align-items: center;
  justify-content: center;
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

.detail-content {
  padding-top: calc(var(--header-height) + 16px);
  padding-left: 16px;
  padding-right: 16px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.card {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 16px;
  padding: 18px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
}

.status-section {
  display: flex;
  align-items: center;
  gap: 18px;
}

.status-icon {
  font-size: 56px;
  animation: pulse 2s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.05); }
}

.status-name {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 6px;
}

.status-desc {
  font-size: 13px;
  color: var(--text-hint);
}

.info-section {
  display: flex;
  flex-direction: column;
}

.section-title {
  font-size: 15px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 14px;
}

.info-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid rgba(0, 0, 0, 0.06);
}

.info-row:last-child {
  border-bottom: none;
}

.info-row.total {
  padding-top: 14px;
  margin-top: 6px;
  border-top: 1px solid rgba(0, 0, 0, 0.06);
  border-bottom: none;
}

.label {
  font-size: 14px;
  color: var(--text-secondary);
}

.value {
  font-size: 14px;
  color: var(--text-primary);
}

.value.price {
  color: #ef4444;
  font-size: 22px;
  font-weight: 700;
}

.product-info {
  display: flex;
  gap: 14px;
}

.product-icon {
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

.product-detail {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.product-name {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 6px;
}

.product-meta {
  font-size: 13px;
  color: var(--text-hint);
}

.voucher-section {
  text-align: center;
}

.voucher-qr {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 24px;
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  border-radius: 16px;
  margin-bottom: 14px;
  border: 2px dashed rgba(0, 0, 0, 0.08);
}

.qr-placeholder {
  font-size: 14px;
  color: var(--text-hint);
}

.qr-code {
  font-size: 90px;
  animation: bounce 2s ease-in-out infinite;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-8px); }
}

.voucher-tip {
  font-size: 13px;
  color: var(--text-hint);
}

.bottom-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 70px;
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 0 16px;
  gap: 14px;
  max-width: 430px;
  margin: 0 auto;
  box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.08);
  backdrop-filter: blur(10px);
}

.service-btn {
  padding: 10px 24px;
  border: 1.5px solid rgba(0, 0, 0, 0.08);
  border-radius: 24px;
  font-size: 14px;
  font-weight: 500;
  color: var(--text-secondary);
  transition: all 0.3s ease;
}

.service-btn:active {
  transform: scale(0.96);
  background: rgba(0, 0, 0, 0.03);
}

.action-btn {
  padding: 10px 28px;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  color: white;
  border-radius: 24px;
  font-size: 14px;
  font-weight: 500;
  box-shadow: 0 4px 14px rgba(59, 130, 246, 0.4);
  transition: all 0.3s ease;
}

.action-btn:active {
  transform: scale(0.96);
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.3);
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.empty-icon {
  font-size: 80px;
  opacity: 0.7;
}

.empty-text {
  font-size: 15px;
  color: var(--text-hint);
}

.btn-primary {
  padding: 12px 40px;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  color: white;
  border-radius: 24px;
  font-size: 15px;
  font-weight: 500;
  box-shadow: 0 4px 14px rgba(59, 130, 246, 0.4);
  transition: all 0.3s ease;
}

.btn-primary:active {
  transform: scale(0.98);
}
</style>