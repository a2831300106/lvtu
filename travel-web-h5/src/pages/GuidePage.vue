<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '../stores/appStore'

const router = useRouter()
const { setGuideSeen } = useAppStore()

const currentPage = ref(0)
const pages = [
  { icon: '🏨', title: '预订酒店民宿', desc: '全球百万优质酒店任你选' },
  { icon: '🎫', title: '景点门票', desc: '热门景点一键预订无需排队' },
  { icon: '📖', title: '旅行攻略', desc: '达人分享实用旅行攻略' },
  { icon: '✨', title: '开始旅程', desc: '探索世界从这里出发' }
]

const nextPage = () => {
  if (currentPage.value < pages.length - 1) {
    currentPage.value++
  } else {
    goToLogin()
  }
}

const goToLogin = () => {
  setGuideSeen()
  router.replace('/login')
}
</script>

<template>
  <div class="guide">
    <div class="guide-content">
      <transition-group name="slide">
        <div class="guide-page" v-for="(page, index) in pages" :key="index" v-show="currentPage === index">
          <div class="guide-icon">{{ page.icon }}</div>
          <div class="guide-title">{{ page.title }}</div>
          <div class="guide-desc">{{ page.desc }}</div>
        </div>
      </transition-group>
    </div>

    <div class="guide-bottom">
      <div class="dots">
        <span v-for="(page, index) in pages" :key="index" :class="{ active: currentPage === index }"></span>
      </div>

      <button class="btn-primary btn" @click="nextPage">
        {{ currentPage === pages.length - 1 ? '立即体验' : '下一步' }}
      </button>

      <button class="skip-btn" @click="goToLogin" v-if="currentPage < pages.length - 1">
        跳过
      </button>
    </div>
  </div>
</template>

<style scoped>
.guide {
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 50%, #1d4ed8 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 24px;
  position: relative;
  overflow: hidden;
}

.guide::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255, 255, 255, 0.08) 0%, transparent 50%);
  animation: pulseBg 4s ease-in-out infinite;
}

@keyframes pulseBg {
  0%, 100% { transform: scale(1); opacity: 0.5; }
  50% { transform: scale(1.1); opacity: 0.8; }
}

.guide-content {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  position: relative;
  z-index: 1;
}

.guide-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.guide-icon {
  font-size: 120px;
  margin-bottom: 40px;
  animation: float 3s ease-in-out infinite;
  filter: drop-shadow(0 8px 32px rgba(0, 0, 0, 0.2));
}

@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-16px); }
}

.guide-title {
  font-size: 32px;
  font-weight: 700;
  color: white;
  margin-bottom: 16px;
  letter-spacing: 2px;
  text-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.guide-desc {
  font-size: 17px;
  color: rgba(255, 255, 255, 0.85);
  line-height: 1.6;
  max-width: 280px;
}

.guide-bottom {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;
  position: relative;
  z-index: 1;
}

.dots {
  display: flex;
  gap: 10px;
}

.dots span {
  width: 10px;
  height: 10px;
  border-radius: 5px;
  background-color: rgba(255, 255, 255, 0.35);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}

.dots span.active {
  width: 32px;
  background-color: white;
  box-shadow: 0 4px 12px rgba(255, 255, 255, 0.4);
}

.btn-primary {
  width: 100%;
  max-width: 320px;
  height: 52px;
  background: linear-gradient(135deg, #ffffff 0%, #f1f5f9 100%);
  color: #3b82f6;
  font-weight: 600;
  font-size: 17px;
  border-radius: 26px;
  box-shadow: 0 8px 24px rgba(255, 255, 255, 0.3);
  transition: all 0.35s ease;
}

.btn-primary:active {
  transform: scale(0.96);
  box-shadow: 0 4px 12px rgba(255, 255, 255, 0.2);
}

.skip-btn {
  color: rgba(255, 255, 255, 0.85);
  font-size: 15px;
  font-weight: 500;
  padding: 8px 20px;
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
}

.skip-btn:active {
  background: rgba(255, 255, 255, 0.2);
}

.slide-enter-active,
.slide-leave-active {
  transition: all 0.5s cubic-bezier(0.4, 0, 0.2, 1);
}

.slide-enter-from {
  opacity: 0;
  transform: translateX(40px) scale(0.95);
}

.slide-leave-to {
  opacity: 0;
  transform: translateX(-40px) scale(0.95);
}
</style>