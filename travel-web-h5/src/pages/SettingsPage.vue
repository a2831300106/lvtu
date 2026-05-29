<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '../stores/appStore'

const router = useRouter()
const { logout } = useAppStore()

const notifications = ref({
  order: true,
  activity: true,
  message: false
})

const goBack = () => {
  router.back()
}

const menuItems = [
  [
    { icon: '🔐', name: '账号安全', path: '/profile' },
    { icon: '📱', name: '手机号', path: '/profile' },
    { icon: '🔑', name: '修改密码', path: '/profile' }
  ],
  [
    { icon: '🔔', name: '消息通知', path: '/profile', toggle: true },
    { icon: '🔒', name: '隐私设置', path: '/profile' }
  ],
  [
    { icon: 'ℹ️', name: '关于我们', path: '/profile' },
    { icon: '📜', name: '用户协议', path: '/profile' },
    { icon: '🔐', name: '隐私政策', path: '/profile' }
  ]
]

const toggleNotification = (key) => {
  notifications.value[key] = !notifications.value[key]
}

const handleLogout = () => {
  logout()
  localStorage.removeItem('user')
  router.replace('/login')
}
</script>

<template>
  <div class="settings-page">
    <div class="navbar">
      <div class="navbar-back" @click="goBack">← 返回</div>
      <div class="navbar-title">设置</div>
      <div style="width: 50px;"></div>
    </div>

    <div class="settings-content">
      <div
        class="menu-section"
        v-for="(section, sIndex) in menuItems"
        :key="sIndex"
      >
        <div
          class="menu-item"
          v-for="item in section"
          :key="item.name"
          @click="!item.toggle && router.push(item.path)"
        >
          <span class="menu-icon">{{ item.icon }}</span>
          <span class="menu-name">{{ item.name }}</span>
          <div class="menu-action" v-if="item.toggle">
            <div
              class="toggle"
              :class="{ active: notifications.order }"
              @click.stop="toggleNotification('order')"
            ></div>
          </div>
          <span class="menu-arrow" v-else>></span>
        </div>
      </div>

      <div class="logout-section">
        <button class="logout-btn" @click="handleLogout">退出登录</button>
      </div>

      <div class="version-info">
        <span>旅图 v1.0.0</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.settings-page {
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

.settings-content {
  padding-top: calc(var(--header-height) + 16px);
  padding-left: 16px;
  padding-right: 16px;
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

.menu-action {
  display: flex;
  align-items: center;
}

.toggle {
  width: 48px;
  height: 28px;
  background: linear-gradient(135deg, rgba(0, 0, 0, 0.08) 0%, rgba(0, 0, 0, 0.04) 100%);
  border-radius: 14px;
  position: relative;
  transition: all 0.35s ease;
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.1);
}

.toggle::after {
  content: '';
  position: absolute;
  top: 3px;
  left: 3px;
  width: 22px;
  height: 22px;
  background: linear-gradient(135deg, #ffffff 0%, #f1f5f9 100%);
  border-radius: 50%;
  transition: all 0.35s ease;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.toggle.active {
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  box-shadow: 0 2px 12px rgba(59, 130, 246, 0.4);
}

.toggle.active::after {
  transform: translateX(20px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.menu-arrow {
  color: var(--text-hint);
  font-size: 16px;
}

.logout-section {
  margin-top: 28px;
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

.version-info {
  text-align: center;
  margin-top: 28px;
  font-size: 13px;
  color: var(--text-hint);
  padding-bottom: 32px;
}
</style>