<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '../stores/appStore'

const router = useRouter()
const { state, logout } = useAppStore()

const userInfo = ref({
  id: '1',
  phone: '138****8888',
  nickname: '旅行者',
  avatar: '',
  level: 'VIP',
  memberSince: '2024-01-01'
})

const menuItems = [
  [
    { icon: '📋', name: '我的订单', path: '/orders' },
    { icon: '🎟️', name: '我的优惠券', path: '/profile' },
    { icon: '⭐', name: '我的收藏', path: '/profile' },
    { icon: '📖', name: '我的游记', path: '/profile' }
  ],
  [
    { icon: '🛡️', name: '出行保险', path: '/profile' },
    { icon: '💬', name: '客服中心', path: '/profile' },
    { icon: '⚙️', name: '设置', path: '/settings' }
  ]
]

const goToEdit = () => {
  router.push('/profile/edit')
}

const handleLogout = () => {
  logout()
  localStorage.removeItem('user')
  router.replace('/login')
}
</script>

<template>
  <div class="profile-page">
    <div class="profile-header">
      <div class="header-bg"></div>
      <div class="user-info">
        <div class="avatar" @click="goToEdit">
          <span>{{ userInfo.avatar || '👤' }}</span>
          <span class="edit-icon">✏️</span>
        </div>
        <div class="user-detail">
          <div class="nickname">{{ userInfo.nickname }}</div>
          <div class="user-meta">
            <span class="level">{{ userInfo.level }}</span>
            <span class="phone">{{ userInfo.phone }}</span>
          </div>
        </div>
      </div>
    </div>

    <div class="profile-content">
      <div class="stats-section">
        <div class="stat-item">
          <div class="stat-value">12</div>
          <div class="stat-label">全部订单</div>
        </div>
        <div class="stat-item">
          <div class="stat-value">5</div>
          <div class="stat-label">我的收藏</div>
        </div>
        <div class="stat-item">
          <div class="stat-value">8</div>
          <div class="stat-label">我的游记</div>
        </div>
        <div class="stat-item">
          <div class="stat-value">3</div>
          <div class="stat-label">优惠券</div>
        </div>
      </div>

      <div
        class="menu-section"
        v-for="(section, index) in menuItems"
        :key="index"
      >
        <div
          class="menu-item"
          v-for="item in section"
          :key="item.name"
          @click="router.push(item.path)"
        >
          <span class="menu-icon">{{ item.icon }}</span>
          <span class="menu-name">{{ item.name }}</span>
          <span class="menu-arrow">></span>
        </div>
      </div>

      <div class="logout-section">
        <button class="logout-btn" @click="handleLogout">退出登录</button>
      </div>
    </div>

    <div class="tabbar">
      <div class="tabbar-item" @click="router.push('/home')">
        <span class="tabbar-icon">🏠</span>
        <span>首页</span>
      </div>
      <div class="tabbar-item" @click="router.push('/destination')">
        <span class="tabbar-icon">📍</span>
        <span>目的地</span>
      </div>
      <div class="tabbar-item" @click="router.push('/strategy')">
        <span class="tabbar-icon">📖</span>
        <span>攻略</span>
      </div>
      <div class="tabbar-item" @click="router.push('/orders')">
        <span class="tabbar-icon">📋</span>
        <span>订单</span>
      </div>
      <div class="tabbar-item active">
        <span class="tabbar-icon">👤</span>
        <span>我的</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.profile-page {
  min-height: 100%;
  background: linear-gradient(180deg, #f8fafc 0%, #f1f5f9 100%);
  padding-bottom: calc(var(--tabbar-height) + var(--safe-area-bottom));
}

.profile-header {
  position: relative;
  padding-top: var(--safe-area-top);
}

.header-bg {
  height: 180px;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 50%, #1d4ed8 100%);
  border-radius: 0 0 32px 32px;
  box-shadow: 0 8px 32px rgba(59, 130, 246, 0.3);
}

.user-info {
  position: absolute;
  top: calc(var(--safe-area-top) + 24px);
  left: 16px;
  right: 16px;
  display: flex;
  align-items: center;
  gap: 18px;
}

.avatar {
  width: 76px;
  height: 76px;
  background: linear-gradient(135deg, #ffffff 0%, #f1f5f9 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 40px;
  position: relative;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  transition: all 0.35s ease;
}

.avatar:active {
  transform: scale(0.96);
}

.edit-icon {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 28px;
  height: 28px;
  background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  box-shadow: 0 2px 8px rgba(239, 68, 68, 0.4);
  transition: all 0.3s ease;
}

.edit-icon:active {
  transform: scale(0.9);
}

.user-detail {
  flex: 1;
  color: white;
}

.nickname {
  font-size: 24px;
  font-weight: 700;
  margin-bottom: 8px;
  letter-spacing: 1px;
}

.user-meta {
  display: flex;
  gap: 14px;
  font-size: 13px;
  opacity: 0.9;
}

.level {
  background: linear-gradient(135deg, rgba(255, 255, 255, 0.25) 0%, rgba(255, 255, 255, 0.1) 100%);
  padding: 4px 10px;
  border-radius: 12px;
  font-weight: 500;
  backdrop-filter: blur(10px);
}

.profile-content {
  padding: 16px;
  margin-top: -24px;
}

.stats-section {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 16px;
  padding: 20px;
  display: flex;
  justify-content: space-around;
  margin-bottom: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.stat-item {
  text-align: center;
  transition: all 0.3s ease;
}

.stat-item:active {
  transform: scale(0.95);
}

.stat-value {
  font-size: 24px;
  font-weight: 700;
  color: #3b82f6;
  margin-bottom: 6px;
  position: relative;
}

.stat-value::after {
  content: '';
  position: absolute;
  bottom: -4px;
  left: 50%;
  transform: translateX(-50%);
  width: 20px;
  height: 3px;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  border-radius: 2px;
}

.stat-label {
  font-size: 12px;
  color: var(--text-hint);
  margin-top: 8px;
}

.menu-section {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 16px;
  margin-bottom: 16px;
  overflow: hidden;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 16px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.menu-item:last-child {
  border-bottom: none;
}

.menu-item:active {
  background: rgba(59, 130, 246, 0.05);
}

.menu-icon {
  font-size: 22px;
  margin-right: 14px;
}

.menu-name {
  flex: 1;
  font-size: 15px;
  color: var(--text-primary);
  font-weight: 500;
}

.menu-arrow {
  color: var(--text-hint);
  font-size: 16px;
}

.logout-section {
  margin-top: 24px;
}

.logout-btn {
  width: 100%;
  height: 48px;
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 14px;
  font-size: 15px;
  color: #ef4444;
  font-weight: 500;
  border: 1.5px solid rgba(239, 68, 68, 0.2);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;
}

.logout-btn:active {
  transform: scale(0.98);
  background: rgba(239, 68, 68, 0.05);
}

.tabbar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: calc(var(--tabbar-height) + var(--safe-area-bottom));
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  display: flex;
  align-items: center;
  padding-bottom: var(--safe-area-bottom);
  z-index: 100;
  box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.08);
  max-width: 430px;
  margin: 0 auto;
  backdrop-filter: blur(10px);
}

.tabbar-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: var(--text-hint);
  font-size: 11px;
  gap: 3px;
  transition: all 0.3s ease;
  position: relative;
}

.tabbar-item.active {
  color: #3b82f6;
}

.tabbar-item.active::after {
  content: '';
  position: absolute;
  bottom: 4px;
  width: 24px;
  height: 3px;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.4);
}

.tabbar-icon {
  font-size: 22px;
}
</style>