import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('admin_token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('admin_user') || 'null'))

  const isLoggedIn = computed(() => !!token.value)

  const login = (loginData) => {
    const mockToken = 'mock_admin_token_' + Date.now()
    const mockUser = {
      id: '1',
      username: loginData.username,
      nickname: loginData.username === 'admin' ? '超级管理员' : '运营人员',
      role: loginData.username === 'admin' ? 'admin' : 'editor',
      avatar: ''
    }

    token.value = mockToken
    userInfo.value = mockUser

    localStorage.setItem('admin_token', mockToken)
    localStorage.setItem('admin_user', JSON.stringify(mockUser))
  }

  const logout = () => {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('admin_token')
    localStorage.removeItem('admin_user')
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    login,
    logout
  }
})