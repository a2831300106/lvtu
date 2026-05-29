import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAppStore = defineStore('app', () => {
  const isSidebarCollapsed = ref(false)
  const breadcrumbs = ref([])

  const toggleSidebar = () => {
    isSidebarCollapsed.value = !isSidebarCollapsed.value
  }

  const setBreadcrumbs = (items) => {
    breadcrumbs.value = items
  }

  return {
    isSidebarCollapsed,
    breadcrumbs,
    toggleSidebar,
    setBreadcrumbs
  }
})