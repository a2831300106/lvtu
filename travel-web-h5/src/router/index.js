import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/splash'
  },
  {
    path: '/splash',
    name: 'Splash',
    component: () => import('../pages/SplashPage.vue')
  },
  {
    path: '/guide',
    name: 'Guide',
    component: () => import('../pages/GuidePage.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../pages/LoginPage.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('../pages/HomePage.vue')
  },
  {
    path: '/destination',
    name: 'Destination',
    component: () => import('../pages/DestinationPage.vue')
  },
  {
    path: '/destination/:id',
    name: 'DestinationDetail',
    component: () => import('../pages/DestinationDetailPage.vue')
  },
  {
    path: '/product/:id',
    name: 'ProductDetail',
    component: () => import('../pages/ProductDetailPage.vue')
  },
  {
    path: '/order/confirm',
    name: 'OrderConfirm',
    component: () => import('../pages/OrderConfirmPage.vue')
  },
  {
    path: '/orders',
    name: 'OrderList',
    component: () => import('../pages/OrderListPage.vue')
  },
  {
    path: '/order/:id',
    name: 'OrderDetail',
    component: () => import('../pages/OrderDetailPage.vue')
  },
  {
    path: '/strategy',
    name: 'Strategy',
    component: () => import('../pages/StrategyPage.vue')
  },
  {
    path: '/strategy/:id',
    name: 'StrategyDetail',
    component: () => import('../pages/StrategyDetailPage.vue')
  },
  {
    path: '/publish',
    name: 'Publish',
    component: () => import('../pages/PublishPage.vue')
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../pages/ProfilePage.vue')
  },
  {
    path: '/profile/edit',
    name: 'ProfileEdit',
    component: () => import('../pages/ProfileEditPage.vue')
  },
  {
    path: '/settings',
    name: 'Settings',
    component: () => import('../pages/SettingsPage.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router