<script setup>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '../stores/appStore'

const router = useRouter()
const { state, setGuideSeen } = useAppStore()

onMounted(() => {
  setTimeout(() => {
    if (!state.hasSeenGuide) {
      router.replace('/guide')
    } else if (!state.isLoggedIn) {
      router.replace('/login')
    } else {
      router.replace('/home')
    }
  }, 2500)
})
</script>

<template>
  <div class="splash">
    <div class="splash-bg">
      <div class="bg-circle bg-circle-1"></div>
      <div class="bg-circle bg-circle-2"></div>
      <div class="bg-circle bg-circle-3"></div>
    </div>
    
    <div class="splash-content">
      <div class="splash-logo-wrapper">
        <div class="logo-icon-wrapper">
          <div class="logo-icon">🌍</div>
          <div class="logo-glow"></div>
        </div>
        <div class="logo-text-wrapper">
          <span class="logo-text">旅图</span>
          <div class="logo-text-shadow"></div>
        </div>
      </div>
      <div class="splash-tagline">
        <span class="tagline-text">发现世界的美好</span>
      </div>
    </div>

    <div class="splash-footer">
      <div class="loading-container">
        <div class="loading-track">
          <div class="loading-progress"></div>
        </div>
        <div class="loading-dots">
          <span class="dot dot-1"></span>
          <span class="dot dot-2"></span>
          <span class="dot dot-3"></span>
        </div>
        <span class="loading-text">正在加载...</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.splash {
  width: 100%;
  height: 100vh;
  background: linear-gradient(135deg, #0ec46a 0%, #08b35c 50%, #06994e 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.splash-bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.bg-circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.15;
  animation: float 8s ease-in-out infinite;
}

.bg-circle-1 {
  width: 400px;
  height: 400px;
  background: radial-gradient(circle, rgba(255,255,255,0.3) 0%, transparent 70%);
  top: -100px;
  right: -100px;
  animation-delay: 0s;
}

.bg-circle-2 {
  width: 300px;
  height: 300px;
  background: radial-gradient(circle, rgba(255,255,255,0.2) 0%, transparent 70%);
  bottom: -50px;
  left: -50px;
  animation-delay: -3s;
}

.bg-circle-3 {
  width: 200px;
  height: 200px;
  background: radial-gradient(circle, rgba(255,255,255,0.25) 0%, transparent 70%);
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation-delay: -5s;
}

.splash-content {
  position: relative;
  z-index: 10;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.splash-logo-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  animation: logoAppear 1s cubic-bezier(0.34, 1.56, 0.64, 1) forwards;
}

.logo-icon-wrapper {
  position: relative;
  margin-bottom: 20px;
}

.logo-icon {
  font-size: 90px;
  filter: drop-shadow(0 8px 30px rgba(0,0,0,0.2));
  animation: iconFloat 3s ease-in-out infinite;
}

.logo-glow {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100px;
  height: 100px;
  background: radial-gradient(circle, rgba(255,255,255,0.4) 0%, transparent 70%);
  border-radius: 50%;
  animation: glowPulse 2s ease-in-out infinite;
}

.logo-text-wrapper {
  position: relative;
}

.logo-text {
  font-size: 42px;
  font-weight: 700;
  color: #ffffff;
  letter-spacing: 8px;
  text-shadow: 0 4px 20px rgba(0,0,0,0.15);
  position: relative;
  z-index: 2;
}

.logo-text-shadow {
  position: absolute;
  top: 2px;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(180deg, rgba(255,255,255,0.1) 0%, transparent 100%);
  filter: blur(10px);
}

.splash-tagline {
  margin-top: 16px;
  animation: taglineFadeIn 0.8s ease-out 0.4s both;
}

.tagline-text {
  font-size: 17px;
  color: rgba(255, 255, 255, 0.9);
  font-weight: 300;
  letter-spacing: 2px;
  text-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.splash-footer {
  position: absolute;
  bottom: 80px;
  z-index: 10;
}

.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.loading-track {
  width: 60px;
  height: 3px;
  background: rgba(255,255,255,0.2);
  border-radius: 2px;
  overflow: hidden;
}

.loading-progress {
  width: 0%;
  height: 100%;
  background: rgba(255,255,255,0.9);
  border-radius: 2px;
  animation: progressFill 2.3s ease-out forwards;
}

.loading-dots {
  display: flex;
  gap: 6px;
}

.dot {
  width: 6px;
  height: 6px;
  background: rgba(255,255,255,0.5);
  border-radius: 50%;
  transition: all 0.3s ease;
}

.dot-1 { animation: dotBounce 1.4s infinite ease-in-out; }
.dot-2 { animation: dotBounce 1.4s infinite ease-in-out 0.2s; }
.dot-3 { animation: dotBounce 1.4s infinite ease-in-out 0.4s; }

.loading-text {
  font-size: 12px;
  color: rgba(255,255,255,0.7);
  letter-spacing: 1px;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0) scale(1);
  }
  50% {
    transform: translateY(-20px) scale(1.05);
  }
}

@keyframes logoAppear {
  0% {
    opacity: 0;
    transform: translateY(30px) scale(0.9);
  }
  100% {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

@keyframes iconFloat {
  0%, 100% {
    transform: translateY(0) rotate(0deg);
  }
  50% {
    transform: translateY(-5px) rotate(2deg);
  }
}

@keyframes glowPulse {
  0%, 100% {
    opacity: 0.3;
    transform: translate(-50%, -50%) scale(1);
  }
  50% {
    opacity: 0.6;
    transform: translate(-50%, -50%) scale(1.2);
  }
}

@keyframes taglineFadeIn {
  0% {
    opacity: 0;
    transform: translateY(15px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes progressFill {
  0% {
    width: 0%;
  }
  100% {
    width: 100%;
  }
}

@keyframes dotBounce {
  0%, 80%, 100% {
    transform: scale(0.6);
    opacity: 0.5;
  }
  40% {
    transform: scale(1);
    opacity: 1;
  }
}

@media screen and (max-width: 375px) {
  .logo-icon {
    font-size: 75px;
  }
  
  .logo-text {
    font-size: 36px;
    letter-spacing: 6px;
  }
  
  .tagline-text {
    font-size: 15px;
  }
  
  .splash-footer {
    bottom: 60px;
  }
}
</style>
