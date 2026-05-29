import { reactive } from 'vue'

const state = reactive({
  isLoggedIn: false,
  user: null,
  hasSeenGuide: false,
  currentCity: '郑州'
})

export function useAppStore() {
  const login = (userData) => {
    state.isLoggedIn = true
    state.user = userData
  }

  const logout = () => {
    state.isLoggedIn = false
    state.user = null
  }

  const setGuideSeen = () => {
    state.hasSeenGuide = true
    localStorage.setItem('hasSeenGuide', 'true')
  }

  const initFromStorage = () => {
    const seen = localStorage.getItem('hasSeenGuide')
    if (seen) {
      state.hasSeenGuide = true
    }
    const userData = localStorage.getItem('user')
    if (userData) {
      state.user = JSON.parse(userData)
      state.isLoggedIn = true
    }
  }

  const updateCity = (city) => {
    state.currentCity = city
  }

  return {
    state,
    login,
    logout,
    setGuideSeen,
    initFromStorage,
    updateCity
  }
}