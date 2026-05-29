import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/',
    redirect: '/dashboard',
    meta: { requiresAuth: true }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../views/Dashboard.vue'),
    meta: { requiresAuth: true, title: '数据概览', icon: 'Odometer' }
  },
  {
    path: '/user',
    name: 'User',
    redirect: '/user/list',
    meta: { requiresAuth: true, title: '用户管理', icon: 'User' },
    children: [
      {
        path: 'list',
        name: 'UserList',
        component: () => import('../views/UserList.vue'),
        meta: { requiresAuth: true, title: '用户列表', parent: '用户管理' }
      }
    ]
  },
  {
    path: '/product',
    name: 'Product',
    redirect: '/product/list',
    meta: { requiresAuth: true, title: '产品管理', icon: 'Goods' },
    children: [
      {
        path: 'list',
        name: 'ProductList',
        component: () => import('../views/ProductList.vue'),
        meta: { requiresAuth: true, title: '产品列表', parent: '产品管理' }
      }
    ]
  },
  {
    path: '/order',
    name: 'Order',
    redirect: '/order/list',
    meta: { requiresAuth: true, title: '订单管理', icon: 'Tickets' },
    children: [
      {
        path: 'list',
        name: 'OrderList',
        component: () => import('../views/OrderList.vue'),
        meta: { requiresAuth: true, title: '订单列表', parent: '订单管理' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()

  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    next('/login')
  } else if (to.path === '/login' && userStore.isLoggedIn) {
    next('/dashboard')
  } else {
    next()
  }
})

export default router