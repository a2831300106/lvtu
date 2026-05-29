<script setup>
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const destination = ref({
  id: route.params.id,
  name: '丽江古城',
  image: '🏔️',
  rating: 4.8,
  desc: '丽江古城位于云南省丽江市古城区，是世界文化遗产，始建于宋末元初。古城内小桥流水、古道幽巷，融合了纳西族、白族等多个少数民族的传统文化。',
  location: '云南省丽江市古城区',
  openTime: '全天开放',
  tags: ['世界遗产', '古镇', '民族风情']
})

const attractions = ref([
  { id: 1, name: '木府', image: '🏯', desc: '纳西族土司府邸', price: 40 },
  { id: 2, name: '四方街', image: '🛤️', desc: '古城中心商业街', price: 0 },
  { id: 3, name: '黑龙潭', image: '🌊', desc: '古潭映雪山', price: 0 }
])

const hotels = ref([
  { id: 1, name: '丽江和府洲际度假酒店', image: '🏨', rating: 4.9, price: 888, desc: '豪华度假型' },
  { id: 2, name: '丽江古城亚朵酒店', image: '🏩', rating: 4.7, price: 368, desc: '精品人文酒店' }
])

const activeTab = ref(0)

const goBack = () => {
  router.back()
}

const goToProduct = (id) => {
  router.push(`/product/${id}`)
}

const goToBook = () => {
  router.push(`/product/${attractions.value[0].id}`)
}
</script>

<template>
  <div class="detail-page">
    <div class="detail-header">
      <div class="header-banner" @click="goBack">
        <div class="banner-icon">{{ destination.image }}</div>
        <div class="banner-overlay">
          <span class="back-btn">← 返回</span>
          <span class="share-btn">分享</span>
        </div>
      </div>
    </div>

    <div class="detail-content">
      <div class="info-section">
        <h1 class="dest-name">{{ destination.name }}</h1>
        <div class="dest-rating">⭐ {{ destination.rating }} <span class="text-hint">很好</span></div>
        <p class="dest-desc">{{ destination.desc }}</p>
        <div class="dest-meta">
          <div class="meta-item">📍 {{ destination.location }}</div>
          <div class="meta-item">🕐 {{ destination.openTime }}</div>
        </div>
        <div class="tags">
          <span class="tag" v-for="tag in destination.tags" :key="tag">{{ tag }}</span>
        </div>
      </div>

      <div class="action-bar">
        <div class="action-item" @click="goToBook">
          <span class="action-icon">🎫</span>
          <span>门票预订</span>
        </div>
        <div class="action-item" @click="router.push('/destination')">
          <span class="action-icon">🏨</span>
          <span>酒店预订</span>
        </div>
        <div class="action-item" @click="router.push('/strategy')">
          <span class="action-icon">📖</span>
          <span>游玩攻略</span>
        </div>
        <div class="action-item" @click="router.push('/strategy')">
          <span class="action-icon">🗺️</span>
          <span>路线推荐</span>
        </div>
      </div>

      <div class="tab-section">
        <div class="tabs">
          <div
            class="tab"
            :class="{ active: activeTab === 0 }"
            @click="activeTab = 0"
          >热门景点</div>
          <div
            class="tab"
            :class="{ active: activeTab === 1 }"
            @click="activeTab = 1"
          >特色酒店</div>
        </div>

        <div class="tab-content">
          <div class="attraction-list" v-show="activeTab === 0">
            <div
              class="attraction-card"
              v-for="item in attractions"
              :key="item.id"
              @click="goToProduct(item.id)"
            >
              <div class="attraction-image">{{ item.image }}</div>
              <div class="attraction-info">
                <div class="attraction-name">{{ item.name }}</div>
                <div class="attraction-desc">{{ item.desc }}</div>
                <div class="attraction-price" v-if="item.price > 0">
                  <span class="price-symbol">¥</span>
                  <span class="price-num">{{ item.price }}</span>
                  <span class="price-text">起</span>
                </div>
                <div class="attraction-price free" v-else>免费</div>
              </div>
            </div>
          </div>

          <div class="hotel-list" v-show="activeTab === 1">
            <div
              class="hotel-card"
              v-for="hotel in hotels"
              :key="hotel.id"
              @click="goToProduct(hotel.id)"
            >
              <div class="hotel-image">{{ hotel.image }}</div>
              <div class="hotel-info">
                <div class="hotel-name">{{ hotel.name }}</div>
                <div class="hotel-desc">{{ hotel.desc }} · ⭐{{ hotel.rating }}</div>
                <div class="hotel-price">
                  <span class="price-symbol">¥</span>
                  <span class="price-num">{{ hotel.price }}</span>
                  <span class="price-text">/晚</span>
                </div>
              </div>
            </div>
          </div>
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
.detail-page {
  min-height: 100%;
  background: linear-gradient(180deg, #f8fafc 0%, #f1f5f9 100%);
  padding-bottom: 80px;
}

.detail-header {
  position: relative;
  overflow: hidden;
}

.header-banner {
  height: 260px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 50%, #f093fb 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 90px;
  position: relative;
}

.header-banner::before {
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

.banner-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  padding: 16px;
  display: flex;
  justify-content: space-between;
}

.back-btn, .share-btn {
  color: white;
  font-size: 15px;
  background: rgba(0, 0, 0, 0.3);
  backdrop-filter: blur(10px);
  padding: 8px 14px;
  border-radius: 20px;
  transition: all 0.3s ease;
}

.back-btn:active, .share-btn:active {
  background: rgba(0, 0, 0, 0.5);
  transform: scale(0.95);
}

.detail-content {
  margin-top: -24px;
  position: relative;
  z-index: 10;
}

.info-section {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 24px 24px 0 0;
  padding: 24px 16px;
  box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.08);
}

.dest-name {
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 10px;
  letter-spacing: 0.5px;
}

.dest-rating {
  color: #f59e0b;
  font-size: 15px;
  font-weight: 500;
  margin-bottom: 14px;
  display: flex;
  align-items: center;
  gap: 6px;
}

.text-hint {
  color: var(--text-hint);
  font-weight: 400;
}

.dest-desc {
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.7;
  margin-bottom: 16px;
  text-align: justify;
}

.dest-meta {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 16px;
  padding-left: 4px;
}

.meta-item {
  font-size: 13px;
  color: var(--text-hint);
  display: flex;
  align-items: center;
  gap: 6px;
}

.tags {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.tag {
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.1) 0%, rgba(59, 130, 246, 0.05) 100%);
  color: #3b82f6;
  font-size: 12px;
  font-weight: 500;
  padding: 6px 12px;
  border-radius: 16px;
  border: 1px solid rgba(59, 130, 246, 0.2);
}

.action-bar {
  display: flex;
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  padding: 18px 16px;
  gap: 12px;
  margin-top: 1px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
}

.action-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
  font-size: 12px;
  color: var(--text-secondary);
  padding: 10px 0;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.action-item:active {
  background: rgba(59, 130, 246, 0.1);
  transform: scale(0.96);
}

.action-icon {
  font-size: 28px;
  transition: transform 0.3s ease;
}

.action-item:active .action-icon {
  transform: scale(1.1);
}

.tab-section {
  margin-top: 12px;
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
  overflow: hidden;
}

.tabs {
  display: flex;
  padding: 0 16px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.06);
}

.tab {
  flex: 1;
  text-align: center;
  padding: 16px 0;
  font-size: 15px;
  color: var(--text-secondary);
  font-weight: 500;
  position: relative;
  transition: all 0.3s ease;
}

.tab.active {
  color: #3b82f6;
}

.tab.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 32px;
  height: 3px;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  border-radius: 2px;
}

.tab-content {
  padding: 16px;
}

.attraction-card {
  display: flex;
  background: linear-gradient(135deg, #ffffff 0%, #f8fafc 100%);
  border-radius: 16px;
  overflow: hidden;
  margin-bottom: 14px;
  transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.04);
}

.attraction-card:active {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
}

.attraction-image {
  width: 90px;
  height: 90px;
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 40px;
  flex-shrink: 0;
  position: relative;
  overflow: hidden;
}

.attraction-image::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, rgba(255, 255, 255, 0.2) 0%, transparent 50%);
}

.attraction-info {
  flex: 1;
  padding: 14px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.attraction-name {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
}

.attraction-desc {
  font-size: 13px;
  color: var(--text-hint);
  margin-top: 6px;
}

.attraction-price {
  color: #ef4444;
  font-size: 15px;
  font-weight: 600;
  margin-top: 8px;
}

.attraction-price.free {
  color: #10b981;
}

.price-symbol {
  font-size: 13px;
}

.price-num {
  font-size: 18px;
  font-weight: 700;
}

.price-text {
  font-size: 12px;
  font-weight: 400;
  margin-left: 2px;
}

.hotel-card {
  display: flex;
  background: linear-gradient(135deg, #ffffff 0%, #f8fafc 100%);
  border-radius: 16px;
  overflow: hidden;
  margin-bottom: 14px;
  transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.04);
}

.hotel-card:active {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
}

.hotel-image {
  width: 110px;
  height: 110px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 52px;
  flex-shrink: 0;
  position: relative;
  overflow: hidden;
}

.hotel-image::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, rgba(255, 255, 255, 0.2) 0%, transparent 50%);
}

.hotel-info {
  flex: 1;
  padding: 14px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.hotel-name {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
}

.hotel-desc {
  font-size: 13px;
  color: var(--text-hint);
  margin-top: 6px;
}

.hotel-price {
  color: #ef4444;
  font-size: 15px;
  font-weight: 600;
  margin-top: 8px;
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