<script setup>
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '../store/user'
import { useAppStore } from '../store/app'
import { ElMessageBox } from 'element-plus'
import { Fold, Expand, ArrowDown, User, ArrowRight } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const appStore = useAppStore()

const menuItems = [
  {
    path: '/dashboard',
    title: '数据概览',
    icon: 'Odometer'
  },
  {
    path: '/user',
    title: '用户管理',
    icon: 'User',
    children: [
      { path: '/user/list', title: '用户列表' }
    ]
  },
  {
    path: '/product',
    title: '产品管理',
    icon: 'Goods',
    children: [
      { path: '/product/list', title: '产品列表' }
    ]
  },
  {
    path: '/order',
    title: '订单管理',
    icon: 'Tickets',
    children: [
      { path: '/order/list', title: '订单列表' }
    ]
  }
]

const isCollapsed = computed(() => appStore.isSidebarCollapsed)
const username = computed(() => userStore.userInfo?.nickname || '管理员')

const activeMenu = computed(() => {
  const path = route.path
  if (route.meta.parent) {
    return '/' + path.split('/')[1]
  }
  return path
})

const breadcrumbs = computed(() => {
  const crumbs = [{ title: '首页', path: '/dashboard' }]
  if (route.meta.parent) {
    crumbs.push({ title: route.meta.parent, path: activeMenu.value })
  }
  if (route.meta.title && route.path !== '/dashboard') {
    crumbs.push({ title: route.meta.title, path: route.path })
  }
  return crumbs
})

const handleCommand = (command) => {
  if (command === 'logout') {
    ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      userStore.logout()
      router.push('/login')
    }).catch(() => {})
  } else if (command === 'profile') {
    ElMessageBox.info('个人中心功能开发中')
  }
}
</script>

<template>
  <div class="layout-container">
    <aside class="sidebar" :class="{ collapsed: isCollapsed }">
      <div class="logo">
        <div class="logo-inner">
          <span class="logo-icon">🌍</span>
          <span class="logo-text" v-if="!isCollapsed">旅图后台</span>
        </div>
        <div class="logo-glow"></div>
      </div>

      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapsed"
        router
        class="sidebar-menu"
      >
        <el-sub-menu
          v-for="item in menuItems.filter(item => item.children)"
          :key="item.path"
          :index="item.path"
        >
          <template #title>
            <el-icon><component :is="item.icon" /></el-icon>
            <span>{{ item.title }}</span>
          </template>
          <el-menu-item
            v-for="child in item.children"
            :key="child.path"
            :index="child.path"
          >
            {{ child.title }}
          </el-menu-item>
        </el-sub-menu>

        <el-menu-item
          v-for="item in menuItems.filter(item => !item.children)"
          :key="item.path"
          :index="item.path"
        >
          <el-icon><component :is="item.icon" /></el-icon>
          <span>{{ item.title }}</span>
        </el-menu-item>
      </el-menu>
    </aside>

    <div class="main-wrapper">
      <header class="header">
        <div class="header-left">
          <el-button
            text
            @click="appStore.toggleSidebar"
            class="collapse-btn"
          >
            <el-icon size="20">
              <Fold v-if="!isCollapsed" />
              <Expand v-else />
            </el-icon>
          </el-button>

          <el-breadcrumb separator="/">
            <el-breadcrumb-item
              v-for="crumb in breadcrumbs"
              :key="crumb.path"
              :to="crumb.path"
            >
              {{ crumb.title }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="36" class="user-avatar">
                {{ username.charAt(0) }}
              </el-avatar>
              <span class="username">{{ username }}</span>
              <el-icon class="dropdown-icon"><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu class="dropdown-menu">
                <el-dropdown-item command="profile" class="dropdown-item">
                  <el-icon><User /></el-icon>
                  <span>个人中心</span>
                </el-dropdown-item>
                <el-dropdown-item command="logout" divided class="dropdown-item">
                  <el-icon><ArrowRight /></el-icon>
                  <span>退出登录</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </header>

      <main class="main-content">
        <router-view />
      </main>
    </div>
  </div>
</template>

<style scoped>
.layout-container {
  display: flex;
  width: 100%;
  height: 100%;
}

.sidebar {
  width: var(--sidebar-width);
  height: 100%;
  background: linear-gradient(180deg, #1e293b 0%, #0f172a 100%);
  transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
  overflow: hidden;
  position: relative;
  box-shadow: 4px 0 20px rgba(0, 0, 0, 0.1);
}

.sidebar.collapsed {
  width: var(--sidebar-collapsed-width);
}

.logo {
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.2) 0%, rgba(139, 92, 246, 0.2) 100%);
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.logo-inner {
  display: flex;
  align-items: center;
  gap: 10px;
  position: relative;
  z-index: 1;
}

.logo-icon {
  font-size: 26px;
  animation: logoPulse 3s ease-in-out infinite;
}

@keyframes logoPulse {
  0%, 100% {
    transform: scale(1);
    filter: drop-shadow(0 0 0 rgba(59, 130, 246, 0));
  }
  50% {
    transform: scale(1.05);
    filter: drop-shadow(0 0 8px rgba(59, 130, 246, 0.5));
  }
}

.logo-text {
  font-size: 18px;
  font-weight: 700;
  white-space: nowrap;
  color: #fff;
  letter-spacing: 1px;
}

.logo-glow {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 60px;
  height: 60px;
  background: radial-gradient(circle, rgba(59, 130, 246, 0.15) 0%, transparent 70%);
  border-radius: 50%;
}

.sidebar-menu {
  border-right: none;
  background-color: transparent;
  padding-top: 16px;
}

.sidebar-menu:not(.el-menu--collapse) {
  width: var(--sidebar-width);
}

:deep(.el-menu) {
  background-color: transparent;
}

:deep(.el-menu-item),
:deep(.el-sub-menu__title) {
  color: #94a3b8;
  height: 48px;
  line-height: 48px;
  margin: 0 8px;
  border-radius: 8px;
  transition: all 0.25s ease;
}

:deep(.el-menu-item:hover),
:deep(.el-sub-menu__title:hover) {
  background: rgba(59, 130, 246, 0.15) !important;
  color: #e2e8f0;
}

:deep(.el-menu-item.is-active) {
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.3) 0%, rgba(139, 92, 246, 0.3) 100%) !important;
  color: #fff;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.2);
}

:deep(.el-sub-menu.is-active) {
  background: rgba(59, 130, 246, 0.1) !important;
}

:deep(.el-sub-menu__title) {
  position: relative;
}

:deep(.el-sub-menu__title::after) {
  content: '';
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #94a3b8;
  transition: all 0.25s ease;
}

:deep(.el-sub-menu.is-active .el-sub-menu__title::after),
:deep(.el-sub-menu__title:hover::after) {
  background: #3b82f6;
}

:deep(.el-menu-item-group) {
  background: rgba(59, 130, 246, 0.05);
  margin: 0 8px;
  border-radius: 8px;
}

:deep(.el-menu-item-group__title) {
  padding: 8px 16px;
  font-size: 11px;
  color: #64748b;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.main-wrapper {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  background: #f1f5f9;
}

.header {
  height: var(--header-height);
  background: #ffffff;
  border-bottom: 1px solid #e2e8f0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
  position: relative;
  z-index: 100;
}

.header::before {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(59, 130, 246, 0.3), transparent);
}

.header-left {
  display: flex;
  align-items: center;
  gap: 20px;
}

.collapse-btn {
  padding: 8px 12px;
  border-radius: 8px;
  color: #64748b;
  transition: all 0.25s ease;
}

.collapse-btn:hover {
  background: #f1f5f9;
  color: #3b82f6;
}

:deep(.el-breadcrumb) {
  font-size: 14px;
}

:deep(.el-breadcrumb__item) {
  color: #64748b;
}

:deep(.el-breadcrumb__item:last-child) {
  color: #1e293b;
  font-weight: 500;
}

:deep(.el-breadcrumb__separator) {
  color: #cbd5e1;
  margin: 0 8px;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 6px 12px;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.25s ease;
}

.user-info:hover {
  background: #f1f5f9;
}

.user-avatar {
  background: linear-gradient(135deg, #3b82f6 0%, #8b5cf6 100%);
  font-weight: 600;
  color: #fff;
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.3);
}

.username {
  font-size: 14px;
  color: #334155;
  font-weight: 500;
}

.dropdown-icon {
  font-size: 14px;
  color: #94a3b8;
  transition: transform 0.25s ease;
}

.user-info:hover .dropdown-icon {
  transform: rotate(180deg);
}

.dropdown-menu {
  border-radius: 12px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
  border: none;
  overflow: hidden;
}

.dropdown-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  transition: all 0.2s ease;
}

.dropdown-item:hover {
  background: #f8fafc;
}

.main-content {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
  background: #f1f5f9;
}

@media screen and (max-width: 768px) {
  .header-left {
    gap: 12px;
  }
  
  .header {
    padding: 0 16px;
  }
  
  .main-content {
    padding: 16px;
  }
}
</style>