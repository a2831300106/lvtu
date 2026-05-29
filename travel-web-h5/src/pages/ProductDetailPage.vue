<script setup>
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const product = ref({
  id: route.params.id,
  name: '丽江古城木府门票',
  images: ['🏯', '🏔️', '🌸'],
  price: 40,
  originalPrice: 60,
  desc: '木府是丽江木氏土司衙门的俗称，位于古城狮子山下，是纳西族政治文化中心。门票包含专业讲解服务。',
  includes: ['门票', '专业讲解', '景区地图'],
  rules: ['未使用可退', '提前2小时可退', '过期不可退'],
  openTime: '08:30 - 18:00'
})

const selectedDate = ref('')
const selectedTicket = ref('adult')
const quantity = ref(1)

const dates = ref([
  { date: '2026-06-01', day: '周一', available: true },
  { date: '2026-06-02', day: '周二', available: true },
  { date: '2026-06-03', day: '周三', available: true },
  { date: '2026-06-04', day: '周四', available: true },
  { date: '2026-06-05', day: '周五', available: true },
  { date: '2026-06-06', day: '周六', available: true },
  { date: '2026-06-07', day: '周日', available: true }
])

const tickets = ref([
  { type: 'adult', name: '成人票', price: 40 },
  { type: 'child', name: '儿童票', price: 20 },
  { type: 'student', name: '学生票', price: 30 }
])

const currentImage = ref(0)

const goBack = () => {
  router.back()
}

const decreaseQty = () => {
  if (quantity.value > 1) {
    quantity.value--
  }
}

const increaseQty = () => {
  if (quantity.value < 10) {
    quantity.value++
  }
}

const goToBook = () => {
  if (!selectedDate.value) {
    alert('请选择游玩日期')
    return
  }
  router.push({
    path: '/order/confirm',
    query: {
      id: product.value.id,
      name: product.value.name,
      date: selectedDate.value,
      ticket: selectedTicket.value,
      quantity: quantity.value,
      price: getSelectedPrice()
    }
  })
}

const getSelectedPrice = () => {
  const ticket = tickets.value.find(t => t.type === selectedTicket.value)
  return ticket ? ticket.price : product.value.price
}
</script>

<template>
  <div class="product-page">
    <div class="navbar">
      <div class="navbar-back" @click="goBack">← 返回</div>
      <div class="navbar-title">商品详情</div>
      <div style="width: 50px;"></div>
    </div>

    <div class="product-content">
      <div class="image-banner">
        <div class="banner-icon">{{ product.images[currentImage] }}</div>
        <div class="banner-dots">
          <span
            v-for="(img, index) in product.images"
            :key="index"
            :class="{ active: currentImage === index }"
            @click="currentImage = index"
          ></span>
        </div>
      </div>

      <div class="price-section">
        <div class="current-price">
          <span class="price-symbol">¥</span>
          <span class="price-integer">{{ getSelectedPrice() }}</span>
        </div>
        <div class="original-price">
          <span>原价</span>
          <span class="line-through">¥{{ product.originalPrice }}</span>
        </div>
        <span class="discount-tag">限时优惠</span>
      </div>

      <div class="info-section">
        <h2 class="product-name">{{ product.name }}</h2>
        <p class="product-desc">{{ product.desc }}</p>
      </div>

      <div class="info-section">
        <div class="section-title">开放时间</div>
        <div class="open-time">🕐 {{ product.openTime }}</div>
      </div>

      <div class="info-section">
        <div class="section-title">费用包含</div>
        <div class="includes-list">
          <span class="include-item" v-for="item in product.includes" :key="item">✓ {{ item }}</span>
        </div>
      </div>

      <div class="info-section">
        <div class="section-title">退改规则</div>
        <div class="rules-list">
          <span class="rule-item" v-for="rule in product.rules" :key="rule">• {{ rule }}</span>
        </div>
      </div>

      <div class="info-section">
        <div class="section-title">选择日期</div>
        <div class="date-list">
          <div
            class="date-item"
            :class="{ active: selectedDate === date.date, disabled: !date.available }"
            v-for="date in dates"
            :key="date.date"
            @click="date.available && (selectedDate = date.date)"
          >
            <div class="date-day">{{ date.day }}</div>
            <div class="date-num">{{ date.date.split('-')[2] }}</div>
          </div>
        </div>
      </div>

      <div class="info-section">
        <div class="section-title">票种</div>
        <div class="ticket-list">
          <div
            class="ticket-item"
            :class="{ active: selectedTicket === ticket.type }"
            v-for="ticket in tickets"
            :key="ticket.type"
            @click="selectedTicket = ticket.type"
          >
            <span class="ticket-name">{{ ticket.name }}</span>
            <span class="ticket-price">¥{{ ticket.price }}</span>
          </div>
        </div>
      </div>

      <div class="quantity-section">
        <div class="section-title">数量</div>
        <div class="quantity-control">
          <button class="qty-btn" @click="decreaseQty">-</button>
          <span class="qty-num">{{ quantity }}</span>
          <button class="qty-btn" @click="increaseQty">+</button>
        </div>
      </div>
    </div>

    <div class="bottom-bar">
      <div class="service-btn">客服</div>
      <div class="book-btn" @click="goToBook">立即预订</div>
    </div>
  </div>
</template>

<style scoped>
.product-page {
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
  background: linear-gradient(135deg, rgba(255, 255, 255, 0.98) 0%, rgba(248, 250, 252, 0.98) 100%);
  backdrop-filter: blur(12px);
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

.product-content {
  padding-top: var(--header-height);
}

.image-banner {
  position: relative;
  height: 260px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 50%, #f093fb 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.image-banner::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle at 30% 20%, rgba(255, 255, 255, 0.15) 0%, transparent 50%);
  animation: pulse 4s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% {
    opacity: 0.5;
    transform: scale(1);
  }
  50% {
    opacity: 1;
    transform: scale(1.1);
  }
}

.banner-icon {
  font-size: 110px;
  position: relative;
  z-index: 1;
}

.banner-dots {
  position: absolute;
  bottom: 16px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 10px;
  z-index: 2;
}

.banner-dots span {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.4);
  transition: all 0.3s ease;
}

.banner-dots span.active {
  width: 24px;
  border-radius: 5px;
  background-color: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.price-section {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  padding: 20px 16px;
  display: flex;
  align-items: baseline;
  gap: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
}

.current-price {
  color: #ef4444;
  display: flex;
  align-items: baseline;
}

.price-symbol {
  font-size: 18px;
  font-weight: 600;
}

.price-integer {
  font-size: 36px;
  font-weight: 700;
}

.original-price {
  color: var(--text-hint);
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 4px;
}

.line-through {
  text-decoration: line-through;
}

.discount-tag {
  background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
  color: white;
  font-size: 12px;
  font-weight: 500;
  padding: 4px 10px;
  border-radius: 8px;
  margin-left: 10px;
  box-shadow: 0 2px 8px rgba(239, 68, 68, 0.3);
}

.info-section {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  padding: 18px;
  margin-top: 12px;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
}

.product-name {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 10px;
  letter-spacing: 0.3px;
}

.product-desc {
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.7;
}

.section-title {
  font-size: 15px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 14px;
}

.open-time {
  font-size: 14px;
  color: var(--text-secondary);
  padding: 12px;
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  border-radius: 10px;
}

.includes-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.include-item {
  font-size: 13px;
  color: #3b82f6;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.1) 0%, rgba(59, 130, 246, 0.05) 100%);
  padding: 6px 12px;
  border-radius: 10px;
  border: 1px solid rgba(59, 130, 246, 0.2);
}

.rules-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.rule-item {
  font-size: 14px;
  color: var(--text-secondary);
  padding: 10px 14px;
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  border-radius: 10px;
}

.date-list {
  display: flex;
  gap: 10px;
  overflow-x: auto;
  padding-bottom: 6px;
}

.date-item {
  min-width: 64px;
  padding: 12px 8px;
  border: 2px solid rgba(0, 0, 0, 0.06);
  border-radius: 12px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
}

.date-item.active {
  border-color: #3b82f6;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.1) 0%, rgba(59, 130, 246, 0.05) 100%);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.2);
}

.date-item.disabled {
  opacity: 0.4;
  pointer-events: none;
}

.date-item:active {
  transform: scale(0.96);
}

.date-day {
  font-size: 12px;
  color: var(--text-hint);
  margin-bottom: 6px;
}

.date-num {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
}

.ticket-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.ticket-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  border: 2px solid rgba(0, 0, 0, 0.06);
  border-radius: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
}

.ticket-item.active {
  border-color: #3b82f6;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.1) 0%, rgba(59, 130, 246, 0.05) 100%);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.2);
}

.ticket-item:active {
  transform: scale(0.99);
}

.ticket-name {
  font-size: 15px;
  font-weight: 500;
  color: var(--text-primary);
}

.ticket-price {
  color: #ef4444;
  font-weight: 600;
  font-size: 16px;
}

.quantity-section {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  padding: 18px;
  margin-top: 12px;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 20px;
}

.qty-btn {
  width: 36px;
  height: 36px;
  border: 2px solid rgba(59, 130, 246, 0.3);
  border-radius: 50%;
  font-size: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #3b82f6;
  background: linear-gradient(135deg, #ffffff 0%, #f8fafc 100%);
  transition: all 0.3s ease;
}

.qty-btn:active {
  background: rgba(59, 130, 246, 0.1);
  transform: scale(0.9);
}

.qty-num {
  font-size: 18px;
  font-weight: 600;
  min-width: 32px;
  text-align: center;
  color: var(--text-primary);
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

.service-btn {
  width: 52px;
  height: 44px;
  border: 1.5px solid rgba(59, 130, 246, 0.3);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  color: #3b82f6;
  font-weight: 500;
  transition: all 0.3s ease;
}

.service-btn:active {
  background: rgba(59, 130, 246, 0.1);
  transform: scale(0.96);
}

.book-btn {
  flex: 1;
  height: 48px;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  color: white;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  font-weight: 600;
  box-shadow: 0 4px 16px rgba(59, 130, 246, 0.4);
  transition: all 0.3s ease;
}

.book-btn:active {
  transform: scale(0.98);
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.3);
}
</style>