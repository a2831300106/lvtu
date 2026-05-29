<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '../stores/appStore'

const router = useRouter()
const { state } = useAppStore()

const banners = ref([
  { id: 1, image: '🏔️', title: '云南丽江古城', subtitle: '感受纳西族风情', gradient: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)' },
  { id: 2, image: '🏖️', title: '三亚亚特兰蒂斯', subtitle: '海洋主题度假区', gradient: 'linear-gradient(135deg, #00c6fb 0%, #005bea 100%)' },
  { id: 3, image: '🗼', title: '上海迪士尼', subtitle: '童话王国等你来', gradient: 'linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%)' }
])

const categories = ref([
  { id: 1, icon: '🏨', name: '酒店民宿', color: '#FF6B6B' },
  { id: 2, icon: '🎫', name: '景点门票', color: '#4ECDC4' },
  { id: 3, icon: '👥', name: '跟团游', color: '#45B7D1' },
  { id: 4, icon: '✨', name: '定制游', color: '#96CEB4' },
  { id: 5, icon: '🚗', name: '周边游', color: '#FFEAA7' },
  { id: 6, icon: '📖', name: '攻略游记', color: '#DDA0DD' }
])

const hotDestinations = ref([
  { id: 1, name: '丽江', image: '🏔️', desc: '世界文化遗产', gradient: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)' },
  { id: 2, name: '大理', image: '🌊', desc: '风花雪月', gradient: 'linear-gradient(135deg, #00c6fb 0%, #005bea 100%)' },
  { id: 3, name: '三亚', image: '🏖️', desc: '热带海滨', gradient: 'linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%)' },
  { id: 4, name: '杭州', image: '🌸', desc: '人间天堂', gradient: 'linear-gradient(135deg, #96CEB4 0%, #88D8B0 100%)' },
  { id: 5, name: '成都', image: '🐼', desc: '天府之国', gradient: 'linear-gradient(135deg, #FFEAA7 0%, #FDCB6E 100%)' }
])

const flashDeals = ref([
  { id: 1, name: '丽江千古情演出票', original: 280, price: 168, sold: 2356, image: '🎭', gradient: 'linear-gradient(135deg, #FF6B6B 0%, #FF8E53 100%)' },
  { id: 2, name: '三亚蜈支洲岛一日游', original: 398, price: 268, sold: 1892, image: '🏝️', gradient: 'linear-gradient(135deg, #00c6fb 0%, #005bea 100%)' },
  { id: 3, name: '张家界国家森林公园', original: 225, price: 158, sold: 3421, image: '🏔️', gradient: 'linear-gradient(135deg, #4ECDC4 0%, #44A08D 100%)' }
])

const travelNotes = ref([
  { id: 1, user: '旅行达人小王', avatar: '👤', dest: '丽江', image: '🏔️', likes: 856, content: '三天两夜深度游丽江，这份攻略请收好！', bgColor: '#FFF5F5' },
  { id: 2, user: '摄影爱好者', avatar: '📷', dest: '青海湖', image: '🌊', likes: 1234, content: '环湖骑行，带你发现不一样的青海湖', bgColor: '#F0F9FF' },
  { id: 3, user: '美食探索家', avatar: '🍜', dest: '成都', image: '🐼', likes: 678, content: '成都本地人带你吃最地道的火锅', bgColor: '#FFFBEB' }
])

const currentBanner = ref(0)
let bannerTimer = null

const startBannerLoop = () => {
  bannerTimer = setInterval(() => {
    currentBanner.value = (currentBanner.value + 1) % banners.value.length
  }, 4000)
}

onMounted(() => {
  startBannerLoop()
})

onUnmounted(() => {
  if (bannerTimer) {
    clearInterval(bannerTimer)
  }
})

const goToDestination = () => {
  router.push('/destination')
}

const goToProduct = (id) => {
  router.push(`/product/${id}`)
}

const goToSearch = () => {
  router.push('/destination')
}

const goToMessage = () => {
  router.push('/orders')
}

const goToCategory = (name) => {
  if (name === '攻略游记') {
    router.push('/strategy')
  } else {
    router.push('/destination')
  }
}

const goToCitySelect = () => {
  router.push('/destination')
}
</script>

<template>
  <div class="home-page">
    <div class="home-header">
      <div class="header-bg"></div>
      <div class="header-content">
        <div class="city-selector" @click="goToCitySelect">
          <span class="city-name">{{ state.currentCity }}</span>
          <span class="city-arrow">▼</span>
        </div>
        <div class="search-box" @click="goToSearch">
          <span class="search-icon">🔍</span>
          <span class="search-placeholder">搜索目的地/景点/酒店</span>
          <span class="search-border"></span>
        </div>
        <div class="message-btn" @click="goToMessage">
          <span class="message-icon">🔔</span>
          <span class="message-badge"></span>
        </div>
      </div>
    </div>

    <div class="home-content">
      <div class="banner-section">
        <div class="banner-container">
          <div class="banner-wrapper">
            <div
              class="banner-item"
              v-for="(banner, index) in banners"
              :key="banner.id"
              :class="{ active: currentBanner === index }"
              :style="{ background: banner.gradient }"
              @click="goToDestination"
            >
              <div class="banner-content">
                <div class="banner-icon-wrapper">
                  <span class="banner-icon">{{ banner.image }}</span>
                  <div class="icon-glow"></div>
                </div>
                <div class="banner-info">
                  <h2 class="banner-title">{{ banner.title }}</h2>
                  <p class="banner-subtitle">{{ banner.subtitle }}</p>
                </div>
              </div>
              <div class="banner-decoration"></div>
            </div>
          </div>
        </div>
        <div class="banner-dots">
          <span
            v-for="(banner, index) in banners"
            :key="banner.id"
            :class="{ active: currentBanner === index }"
            @click="currentBanner = index"
          >
            <span class="dot-inner"></span>
          </span>
        </div>
      </div>

      <div class="category-section">
        <div class="category-list">
          <div
            class="category-item"
            v-for="cat in categories"
            :key="cat.id"
            @click="goToCategory(cat.name)"
          >
            <div class="category-icon-wrapper" :style="{ background: cat.color + '15' }">
              <span class="category-icon">{{ cat.icon }}</span>
              <div class="category-glow" :style="{ background: cat.color }"></div>
            </div>
            <span class="category-name">{{ cat.name }}</span>
          </div>
        </div>
      </div>

      <div class="module-section">
        <div class="module-header">
          <div class="module-title-wrapper">
            <span class="module-icon">🔥</span>
            <span class="module-title">热门目的地</span>
          </div>
          <span class="module-more" @click="goToDestination">
            查看更多
            <span class="more-arrow">›</span>
          </span>
        </div>
        <div class="destination-list">
          <div
            class="destination-card"
            v-for="dest in hotDestinations"
            :key="dest.id"
            @click="goToDestination"
          >
            <div class="dest-image-wrapper" :style="{ background: dest.gradient }">
              <span class="dest-image">{{ dest.image }}</span>
              <div class="dest-overlay"></div>
            </div>
            <div class="dest-info">
              <h3 class="dest-name">{{ dest.name }}</h3>
              <p class="dest-desc">{{ dest.desc }}</p>
            </div>
          </div>
        </div>
      </div>

      <div class="module-section">
        <div class="module-header">
          <div class="flash-title-wrapper">
            <div class="module-title-wrapper">
              <span class="module-icon">⚡</span>
              <span class="module-title">限时特惠</span>
            </div>
            <div class="flash-countdown">
              <span class="countdown-label">距结束</span>
              <span class="countdown-time">02:34:15</span>
            </div>
          </div>
          <span class="module-more" @click="goToDestination">
            查看更多
            <span class="more-arrow">›</span>
          </span>
        </div>
        <div class="deals-list">
          <div
            class="deal-card"
            v-for="deal in flashDeals"
            :key="deal.id"
            @click="goToProduct(deal.id)"
          >
            <div class="deal-image-wrapper" :style="{ background: deal.gradient }">
              <span class="deal-image">{{ deal.image }}</span>
              <div class="deal-badge">限时</div>
            </div>
            <div class="deal-info">
              <h3 class="deal-name">{{ deal.name }}</h3>
              <div class="deal-price-wrapper">
                <span class="price-symbol">¥</span>
                <span class="price-integer">{{ deal.price }}</span>
                <span class="price-original">¥{{ deal.original }}</span>
              </div>
              <div class="deal-sold">已售 {{ deal.sold }} 件</div>
            </div>
          </div>
        </div>
      </div>

      <div class="module-section">
        <div class="module-header">
          <div class="module-title-wrapper">
            <span class="module-icon">✍️</span>
            <span class="module-title">游记种草</span>
          </div>
          <span class="module-more" @click="goToDestination">
            查看更多
            <span class="more-arrow">›</span>
          </span>
        </div>
        <div class="notes-list">
          <div
            class="note-card"
            v-for="note in travelNotes"
            :key="note.id"
            @click="goToDestination"
          >
            <div class="note-header">
              <div class="note-user">
                <span class="user-avatar">{{ note.avatar }}</span>
                <span class="user-name">{{ note.user }}</span>
              </div>
              <span class="note-dest">{{ note.dest }}</span>
            </div>
            <p class="note-content">{{ note.content }}</p>
            <div class="note-footer">
              <div class="note-image-wrapper">
                <span class="note-image">{{ note.image }}</span>
              </div>
              <div class="note-likes">
                <span class="like-icon">❤️</span>
                <span class="like-count">{{ note.likes }}</span>
              </div>
            </div>
            <div class="note-border"></div>
          </div>
        </div>
      </div>
    </div>

    <div class="tabbar">
      <div class="tabbar-bg"></div>
      <div class="tabbar-content">
        <div class="tabbar-item active">
          <div class="tabbar-icon-wrapper">
            <span class="tabbar-icon">🏠</span>
            <span class="icon-glow"></span>
          </div>
          <span class="tabbar-text">首页</span>
        </div>
        <div class="tabbar-item" @click="goToDestination">
          <div class="tabbar-icon-wrapper">
            <span class="tabbar-icon">📍</span>
          </div>
          <span class="tabbar-text">目的地</span>
        </div>
        <div class="tabbar-item" @click="router.push('/strategy')">
          <div class="tabbar-icon-wrapper">
            <span class="tabbar-icon">📖</span>
          </div>
          <span class="tabbar-text">攻略</span>
        </div>
        <div class="tabbar-item" @click="router.push('/orders')">
          <div class="tabbar-icon-wrapper">
            <span class="tabbar-icon">📋</span>
          </div>
          <span class="tabbar-text">订单</span>
        </div>
        <div class="tabbar-item" @click="router.push('/profile')">
          <div class="tabbar-icon-wrapper">
            <span class="tabbar-icon">👤</span>
          </div>
          <span class="tabbar-text">我的</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.home-page {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f7fa;
}

.home-header {
  position: relative;
  padding: 16px;
  padding-top: calc(16px + var(--status-bar-height));
  overflow: hidden;
}

.header-bg {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 180px;
  background: linear-gradient(180deg, #0ec46a 0%, #08b35c 100%);
  border-radius: 0 0 32px 32px;
}

.header-content {
  position: relative;
  z-index: 10;
  display: flex;
  align-items: center;
  gap: 12px;
}

.city-selector {
  display: flex;
  align-items: center;
  color: #ffffff;
  font-size: 15px;
  font-weight: 600;
  white-space: nowrap;
  padding: 6px 10px;
  background: rgba(255, 255, 255, 0.15);
  border-radius: 16px;
  backdrop-filter: blur(10px);
}

.city-arrow {
  font-size: 10px;
  margin-left: 4px;
  opacity: 0.8;
}

.search-box {
  flex: 1;
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 10px 16px;
  gap: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.search-box:active {
  transform: scale(0.98);
}

.search-icon {
  font-size: 16px;
  opacity: 0.6;
}

.search-placeholder {
  color: #999999;
  font-size: 14px;
}

.message-btn {
  position: relative;
  padding: 10px;
}

.message-icon {
  font-size: 22px;
}

.message-badge {
  position: absolute;
  top: 6px;
  right: 6px;
  width: 10px;
  height: 10px;
  background-color: #ff3b30;
  border-radius: 50%;
  animation: badgePulse 2s ease-in-out infinite;
}

.home-content {
  flex: 1;
  overflow-y: auto;
  padding-bottom: calc(var(--tabbar-height) + var(--safe-area-bottom) + 20px);
}

.banner-section {
  position: relative;
  padding: 0 16px;
  margin-top: -20px;
}

.banner-container {
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.15);
}

.banner-wrapper {
  position: relative;
  height: 200px;
}

.banner-item {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  visibility: hidden;
  transition: all 0.5s cubic-bezier(0.4, 0, 0.2, 1);
  display: flex;
  align-items: center;
  justify-content: center;
}

.banner-item.active {
  opacity: 1;
  visibility: visible;
}

.banner-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  color: #ffffff;
  padding: 20px;
}

.banner-icon-wrapper {
  position: relative;
  margin-bottom: 16px;
}

.banner-icon {
  font-size: 72px;
  filter: drop-shadow(0 8px 20px rgba(0, 0, 0, 0.2));
  animation: iconFloat 3s ease-in-out infinite;
}

.icon-glow {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 80px;
  height: 80px;
  background: radial-gradient(circle, rgba(255,255,255,0.3) 0%, transparent 70%);
  border-radius: 50%;
}

.banner-title {
  font-size: 24px;
  font-weight: 700;
  margin-bottom: 8px;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

.banner-subtitle {
  font-size: 15px;
  opacity: 0.9;
  text-shadow: 0 1px 6px rgba(0, 0, 0, 0.15);
}

.banner-decoration {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 150px;
  height: 150px;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%);
  border-radius: 50% 0 0 0;
}

.banner-dots {
  display: flex;
  justify-content: center;
  gap: 12px;
  margin-top: 16px;
}

.banner-dots span {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: rgba(14, 196, 106, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  cursor: pointer;
}

.banner-dots span.active {
  background: rgba(14, 196, 106, 0.8);
  transform: scale(1.2);
}

.dot-inner {
  width: 4px;
  height: 4px;
  border-radius: 50%;
  background: white;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.banner-dots span.active .dot-inner {
  opacity: 1;
}

.category-section {
  padding: 20px 16px;
  background: #ffffff;
  margin: 16px;
  border-radius: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.category-list {
  display: flex;
  justify-content: space-between;
}

.category-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 12px 8px;
  border-radius: 16px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.category-item:active {
  transform: scale(0.95);
}

.category-icon-wrapper {
  position: relative;
  width: 52px;
  height: 52px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.category-icon {
  font-size: 26px;
  position: relative;
  z-index: 2;
}

.category-glow {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
  height: 100%;
  border-radius: 16px;
  opacity: 0;
  filter: blur(10px);
  transition: opacity 0.3s ease;
}

.category-item:active .category-glow {
  opacity: 0.2;
}

.category-name {
  font-size: 12px;
  color: #333333;
  font-weight: 500;
}

.module-section {
  padding: 0 16px;
  margin-bottom: 20px;
}

.module-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.module-title-wrapper {
  display: flex;
  align-items: center;
  gap: 8px;
}

.module-icon {
  font-size: 18px;
}

.module-title {
  font-size: 17px;
  font-weight: 600;
  color: #1a1a1a;
}

.module-more {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  color: #999999;
  transition: all 0.3s ease;
}

.module-more:active {
  color: #0ec46a;
}

.more-arrow {
  font-size: 16px;
  transition: transform 0.3s ease;
}

.module-more:active .more-arrow {
  transform: translateX(4px);
}

.flash-title-wrapper {
  display: flex;
  align-items: center;
  gap: 16px;
}

.flash-countdown {
  display: flex;
  align-items: center;
  gap: 8px;
  background: linear-gradient(135deg, #fff5f0, #ffe4d4);
  padding: 6px 12px;
  border-radius: 12px;
}

.countdown-label {
  font-size: 11px;
  color: #ff6b35;
}

.countdown-time {
  font-size: 13px;
  color: #ff6b35;
  font-weight: 600;
  font-family: 'SF Mono', monospace;
}

.destination-list {
  display: flex;
  gap: 12px;
  overflow-x: auto;
  padding-bottom: 8px;
  -webkit-overflow-scrolling: touch;
}

.destination-list::-webkit-scrollbar {
  display: none;
}

.destination-card {
  min-width: 110px;
  background: #ffffff;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.destination-card:active {
  transform: scale(0.97);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.12);
}

.dest-image-wrapper {
  position: relative;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.dest-image {
  font-size: 44px;
  position: relative;
  z-index: 2;
}

.dest-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 30px;
  background: linear-gradient(transparent, rgba(0, 0, 0, 0.1));
}

.dest-info {
  padding: 12px;
}

.dest-name {
  font-size: 15px;
  font-weight: 600;
  color: #1a1a1a;
  margin-bottom: 4px;
}

.dest-desc {
  font-size: 12px;
  color: #999999;
}

.deals-list {
  display: flex;
  gap: 14px;
  overflow-x: auto;
  padding-bottom: 8px;
  -webkit-overflow-scrolling: touch;
}

.deals-list::-webkit-scrollbar {
  display: none;
}

.deal-card {
  min-width: 150px;
  background: #ffffff;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.deal-card:active {
  transform: scale(0.97);
}

.deal-image-wrapper {
  position: relative;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.deal-image {
  font-size: 44px;
}

.deal-badge {
  position: absolute;
  top: 8px;
  left: 8px;
  background: #ff6b35;
  color: #ffffff;
  font-size: 10px;
  padding: 4px 8px;
  border-radius: 8px;
  font-weight: 600;
}

.deal-info {
  padding: 14px;
}

.deal-name {
  font-size: 13px;
  color: #1a1a1a;
  margin-bottom: 10px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.4;
}

.deal-price-wrapper {
  display: flex;
  align-items: baseline;
  gap: 2px;
  margin-bottom: 6px;
}

.price-symbol {
  color: #ff6b35;
  font-size: 12px;
  font-weight: 600;
}

.price-integer {
  color: #ff6b35;
  font-size: 20px;
  font-weight: 700;
}

.price-original {
  color: #cccccc;
  text-decoration: line-through;
  font-size: 12px;
  margin-left: 6px;
}

.deal-sold {
  font-size: 11px;
  color: #999999;
}

.notes-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.note-card {
  background: #ffffff;
  border-radius: 16px;
  padding: 16px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.note-card:active {
  transform: scale(0.99);
}

.note-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.note-user {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-avatar {
  font-size: 28px;
}

.user-name {
  font-size: 14px;
  color: #1a1a1a;
  font-weight: 500;
}

.note-dest {
  font-size: 12px;
  color: #0ec46a;
  background: rgba(14, 196, 106, 0.1);
  padding: 4px 10px;
  border-radius: 12px;
  font-weight: 500;
}

.note-content {
  font-size: 15px;
  color: #333333;
  line-height: 1.6;
  margin-bottom: 14px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.note-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.note-image-wrapper {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  background: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
}

.note-image {
  font-size: 32px;
}

.note-likes {
  display: flex;
  align-items: center;
  gap: 6px;
}

.like-icon {
  font-size: 16px;
}

.like-count {
  font-size: 13px;
  color: #999999;
}

.note-border {
  position: absolute;
  top: 0;
  left: 0;
  width: 4px;
  height: 100%;
  background: linear-gradient(180deg, #0ec46a, #08b35c);
  border-radius: 16px 0 0 16px;
}

.tabbar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: calc(var(--tabbar-height) + var(--safe-area-bottom));
  max-width: 430px;
  margin: 0 auto;
  background: #ffffff;
  padding-bottom: var(--safe-area-bottom);
  z-index: 100;
  box-shadow: 0 -2px 20px rgba(0, 0, 0, 0.08);
}

.tabbar-bg {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(90deg, transparent, #e0e0e0, transparent);
}

.tabbar-content {
  display: flex;
  align-items: center;
  height: 100%;
}

.tabbar-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 4px;
  padding: 8px 0;
  transition: all 0.3s ease;
}

.tabbar-icon-wrapper {
  position: relative;
}

.tabbar-icon {
  font-size: 22px;
  transition: all 0.3s ease;
}

.tabbar-item.active .tabbar-icon {
  transform: scale(1.1);
}

.tabbar-item.active .icon-glow {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 40px;
  height: 40px;
  background: radial-gradient(circle, rgba(14, 196, 106, 0.2) 0%, transparent 70%);
  border-radius: 50%;
}

.tabbar-text {
  font-size: 10px;
  color: #999999;
  transition: all 0.3s ease;
}

.tabbar-item.active .tabbar-text {
  color: #0ec46a;
  font-weight: 600;
}

@keyframes badgePulse {
  0%, 100% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(1.2);
    opacity: 0.8;
  }
}

@keyframes iconFloat {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-8px);
  }
}

@media screen and (max-width: 350px) {
  .category-item {
    padding: 8px 4px;
  }
  
  .category-icon-wrapper {
    width: 44px;
    height: 44px;
  }
  
  .category-icon {
    font-size: 22px;
  }
  
  .category-name {
    font-size: 11px;
  }
}
</style>
