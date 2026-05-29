<script setup>
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAppStore } from '../stores/appStore'

const router = useRouter()
const route = useRoute()
const { state } = useAppStore()

const orderInfo = ref({
  id: route.query.id,
  name: route.query.name || '丽江古城木府门票',
  date: route.query.date || '2026-06-01',
  ticket: route.query.ticket || 'adult',
  quantity: parseInt(route.query.quantity) || 1,
  price: parseInt(route.query.price) || 40
})

const ticketNames = {
  adult: '成人票',
  child: '儿童票',
  student: '学生票'
}

const contactName = ref('')
const contactPhone = ref('')
const remark = ref('')
const coupon = ref(null)
const showCouponPicker = ref(false)

const coupons = ref([
  { id: 1, name: '新人专享券', amount: 10, condition: '满50可用' },
  { id: 2, name: '满减券', amount: 5, condition: '满30可用' }
])

const totalPrice = ref(orderInfo.value.price * orderInfo.value.quantity)
const discount = ref(coupon.value ? coupon.value.amount : 0)
const finalPrice = ref(totalPrice.value - discount.value)

const goBack = () => {
  router.back()
}

const selectCoupon = (c) => {
  coupon.value = c
  discount.value = c.amount
  finalPrice.value = totalPrice.value - discount.value
  showCouponPicker.value = false
}

const submitOrder = () => {
  if (!contactName.value || !contactPhone.value) {
    alert('请填写联系人信息')
    return
  }

  const mockOrder = {
    id: 'ORD' + Date.now(),
    productName: orderInfo.value.name,
    date: orderInfo.value.date,
    ticket: ticketNames[orderInfo.value.ticket],
    quantity: orderInfo.value.quantity,
    price: orderInfo.value.price,
    total: finalPrice.value,
    contact: { name: contactName.value, phone: contactPhone.value },
    status: 'pending',
    createTime: new Date().toISOString()
  }

  localStorage.setItem('currentOrder', JSON.stringify(mockOrder))
  localStorage.setItem('orders', JSON.stringify([mockOrder, ...(JSON.parse(localStorage.getItem('orders') || '[]'))]))

  router.push({
    path: `/order/${mockOrder.id}`,
    query: { from: 'confirm' }
  })
}
</script>

<template>
  <div class="confirm-page">
    <div class="navbar">
      <div class="navbar-back" @click="goBack">← 返回</div>
      <div class="navbar-title">订单确认</div>
      <div style="width: 50px;"></div>
    </div>

    <div class="confirm-content">
      <div class="product-info card">
        <div class="product-icon">🏯</div>
        <div class="product-detail">
          <div class="product-name">{{ orderInfo.name }}</div>
          <div class="product-meta">
            {{ orderInfo.date }} · {{ ticketNames[orderInfo.ticket] }} · {{ orderInfo.quantity }}张
          </div>
        </div>
      </div>

      <div class="section card">
        <div class="section-title">联系人信息</div>
        <div class="form-item">
          <label>姓名</label>
          <input type="text" v-model="contactName" placeholder="请输入联系人姓名" />
        </div>
        <div class="form-item">
          <label>手机号</label>
          <input type="tel" v-model="contactPhone" placeholder="请输入联系人手机号" maxlength="11" />
        </div>
      </div>

      <div class="section card">
        <div class="section-title">订单备注</div>
        <textarea v-model="remark" placeholder="选填，可备注特殊需求" rows="3"></textarea>
      </div>

      <div class="section card">
        <div class="section-title">优惠券</div>
        <div class="coupon-selector" @click="showCouponPicker = true">
          <span class="coupon-name">{{ coupon ? coupon.name : '选择优惠券' }}</span>
          <span class="arrow">></span>
        </div>
      </div>

      <div class="section card">
        <div class="section-title">订单金额明细</div>
        <div class="price-detail">
          <div class="price-row">
            <span>商品金额</span>
            <span>¥{{ totalPrice }}</span>
          </div>
          <div class="price-row discount" v-if="discount > 0">
            <span>优惠金额</span>
            <span>-¥{{ discount }}</span>
          </div>
          <div class="price-row total">
            <span>合计</span>
            <span class="total-price">¥{{ finalPrice }}</span>
          </div>
        </div>
      </div>
    </div>

    <div class="bottom-bar">
      <div class="total-info">
        合计: <span class="price">¥{{ finalPrice }}</span>
      </div>
      <button class="submit-btn" @click="submitOrder">提交订单</button>
    </div>

    <div class="coupon-picker" v-if="showCouponPicker">
      <div class="picker-overlay" @click="showCouponPicker = false"></div>
      <div class="picker-content">
        <div class="picker-header">
          <span>选择优惠券</span>
          <span class="close-btn" @click="showCouponPicker = false">×</span>
        </div>
        <div class="coupon-list">
          <div
            class="coupon-item"
            v-for="c in coupons"
            :key="c.id"
            @click="selectCoupon(c)"
          >
            <div class="coupon-left">
              <div class="coupon-amount">¥{{ c.amount }}</div>
              <div class="coupon-condition">{{ c.condition }}</div>
            </div>
            <div class="coupon-right">
              <div class="coupon-name">{{ c.name }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.confirm-page {
  min-height: 100%;
  background: linear-gradient(180deg, #f8fafc 0%, #f1f5f9 100%);
  padding-bottom: 80px;
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

.confirm-content {
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
  letter-spacing: 0.3px;
}

.product-meta {
  font-size: 13px;
  color: var(--text-hint);
}

.section-title {
  font-size: 15px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 14px;
}

.form-item {
  display: flex;
  align-items: center;
  border-bottom: 1px solid rgba(0, 0, 0, 0.06);
  padding: 14px 0;
  transition: all 0.3s ease;
}

.form-item:last-child {
  border-bottom: none;
}

.form-item label {
  width: 64px;
  font-size: 14px;
  color: var(--text-secondary);
}

.form-item input {
  flex: 1;
  font-size: 14px;
  outline: none;
  border: none;
  background: transparent;
}

textarea {
  width: 100%;
  border: 1.5px solid rgba(0, 0, 0, 0.08);
  border-radius: 12px;
  padding: 14px;
  font-size: 14px;
  resize: none;
  font-family: inherit;
  outline: none;
  transition: all 0.3s ease;
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
}

textarea:focus {
  border-color: rgba(59, 130, 246, 0.3);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.08);
}

.coupon-selector {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 0;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.coupon-selector:active {
  background: rgba(59, 130, 246, 0.05);
}

.coupon-name {
  color: #ef4444;
  font-size: 14px;
  font-weight: 500;
}

.arrow {
  color: var(--text-hint);
  font-size: 16px;
}

.price-detail {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.price-row {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  color: var(--text-secondary);
}

.price-row.discount {
  color: #10b981;
  font-weight: 500;
}

.price-row.total {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  padding-top: 12px;
  border-top: 1px solid rgba(0, 0, 0, 0.06);
}

.total-price {
  color: #ef4444;
  font-size: 22px;
  font-weight: 700;
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
  padding: 0 16px;
  gap: 16px;
  max-width: 430px;
  margin: 0 auto;
  box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.08);
  backdrop-filter: blur(10px);
}

.total-info {
  font-size: 15px;
  color: var(--text-secondary);
}

.total-info .price {
  color: #ef4444;
  font-size: 24px;
  font-weight: 700;
}

.submit-btn {
  flex: 1;
  height: 48px;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  color: white;
  border-radius: 14px;
  font-size: 16px;
  font-weight: 600;
  border: none;
  cursor: pointer;
  box-shadow: 0 4px 16px rgba(59, 130, 246, 0.4);
  transition: all 0.3s ease;
}

.submit-btn:active {
  transform: scale(0.98);
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.3);
}

.coupon-picker {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 200;
}

.picker-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.picker-content {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 24px 24px 0 0;
  max-height: 60%;
  overflow-y: auto;
  animation: slideUp 0.3s ease;
}

@keyframes slideUp {
  from { transform: translateY(100%); }
  to { transform: translateY(0); }
}

.picker-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 18px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.06);
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
}

.close-btn {
  font-size: 28px;
  color: var(--text-hint);
  transition: all 0.3s ease;
}

.close-btn:active {
  transform: scale(1.1);
}

.coupon-list {
  padding: 18px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.coupon-item {
  display: flex;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.08) 0%, rgba(59, 130, 246, 0.04) 100%);
  border-radius: 16px;
  overflow: hidden;
  transition: all 0.35s ease;
  border: 2px solid transparent;
}

.coupon-item:active {
  transform: scale(0.98);
  border-color: rgba(59, 130, 246, 0.3);
}

.coupon-left {
  width: 90px;
  padding: 18px;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  color: white;
  text-align: center;
}

.coupon-amount {
  font-size: 28px;
  font-weight: 700;
}

.coupon-condition {
  font-size: 12px;
  opacity: 0.85;
}

.coupon-right {
  flex: 1;
  padding: 18px;
  display: flex;
  align-items: center;
}

.coupon-name {
  font-size: 15px;
  color: #3b82f6;
  font-weight: 500;
}
</style>