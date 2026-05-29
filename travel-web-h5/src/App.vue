<script setup>
import { onMounted } from 'vue'
import { useAppStore } from './stores/appStore'

const { initFromStorage } = useAppStore()

onMounted(() => {
  initFromStorage()
})
</script>

<template>
  <router-view />
</template>