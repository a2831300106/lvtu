<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const activeTab = ref(0)
const tabs = ['热门', '景点', '酒店', '美食', '活动']

const searchValue = ref('')

const destinations = ref([
  { id: 1, name: '丽江古城', image: '🏔️', desc: '世界文化遗产，纳西族古都', rating: 4.8, heat: 9856 },
  { id: 2, name: '大理古城', image: '🌊', desc: '风花雪月，浪漫之都', rating: 4.7, heat: 8623 },
  { id: 3, name: '三亚', image: '🏖️', desc: '热带海滨度假胜地', rating: 4.9, heat: 12453 },
  { id: 4, name: '杭州西湖', image: '🌸', desc: '人间天堂，苏堤春晓', rating: 4.8, heat: 7654 },
  { id: 5, name: '成都', image: '🐼', desc: '天府之国，美食之都', rating: 4.6, heat: 9432 },
  { id: 6, name: '张家界', image: '🏔️', desc: '国家森林公园，奇峰异石', rating: 4.7, heat: 6789 }
])

const goBack = () => {
  router.back()
}

const goToDetail = (id) => {
  router.push(`/destination/${id}`)
}

const onSearch = () => {
  console.log('search:', searchValue.value)
}
</script>

<template>
  <div class="destination-page">
    <div class="navbar">
      <div class="navbar-back" @click="goBack">← 返回</div>
      <div class="navbar-title">目的地</div>
      <div style="width: 50px;"></div>
    </div>

    <div class="search-bar">
      <input
        type="text"
        v-model="searchValue"
        placeholder="搜索目的地"
        @keyup.enter="onSearch"
      />
      <button class="search-btn">🔍</button>
    </div>

    <div class="filter-tabs">
      <div
        class="filter-tab"
        :class="{ active: activeTab === index }"
        v-for="(tab, index) in tabs"
        :key="tab"
        @click="activeTab = index"
      >
        {{ tab }}
      </div>
    </div>

    <div class="dest-list">
      <div
        class="dest-card"
        v-for="dest in destinations"
        :key="dest.id"
        @click="goToDetail(dest.id)"
      >
        <div class="dest-image">{{ dest.image }}</div>
        <div class="dest-info">
          <div class="dest-name">{{ dest.name }}</div>
          <div class="dest-desc">{{ dest.desc }}</div>
          <div class="dest-meta">
            <span class="dest-rating">⭐ {{ dest.rating }}</span>
            <span class="dest-heat">🔥 {{ dest.heat }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.destination-page {
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
  backdrop-filter: blur(10px);
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

.search-bar {
  position: fixed;
  top: var(--header-height);
  left: 0;
  right: 0;
  padding: 12px 16px;
  background: linear-gradient(135deg, #ffffff 0%, #f8fafc 100%);
  z-index: 100;
  display: flex;
  gap: 10px;
}

.search-bar input {
  flex: 1;
  height: 40px;
  background: linear-gradient(135deg, #f1f5f9 0%, #e2e8f0 100%);
  border-radius: 20px;
  padding: 0 20px;
  font-size: 14px;
  border: none;
  outline: none;
  transition: all 0.3s ease;
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.06);
}

.search-bar input:focus {
  background: #ffffff;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1), 0 4px 12px rgba(0, 0, 0, 0.08);
}

.search-btn {
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(59, 130, 246, 0.35);
}

.search-btn:active {
  transform: scale(0.95);
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.3);
}

.filter-tabs {
  position: fixed;
  top: calc(var(--header-height) + 64px);
  left: 0;
  right: 0;
  display: flex;
  background: linear-gradient(135deg, #ffffff 0%, #f8fafc 100%);
  padding: 8px 16px;
  gap: 16px;
  z-index: 100;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
}

.filter-tab {
  padding: 10px 16px;
  font-size: 14px;
  color: var(--text-secondary);
  border-radius: 20px;
  transition: all 0.3s ease;
  background-color: transparent;
}

.filter-tab.active {
  color: #ffffff;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.filter-tab:not(.active):active {
  background-color: rgba(59, 130, 246, 0.1);
}

.dest-list {
  padding: calc(var(--header-height) + 128px) 16px 16px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.dest-card {
  display: flex;
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 16px;
  overflow: hidden;
  transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
  transform: translateY(0);
}

.dest-card:active {
  transform: translateY(-2px) scale(0.99);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.dest-image {
  width: 110px;
  height: 110px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 52px;
  flex-shrink: 0;
  position: relative;
  overflow: hidden;
}

.dest-image::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: linear-gradient(45deg, transparent 30%, rgba(255, 255, 255, 0.1) 50%, transparent 70%);
  transform: rotate(45deg);
  animation: shimmer 3s infinite;
}

@keyframes shimmer {
  0% {
    transform: translateX(-100%) rotate(45deg);
  }
  100% {
    transform: translateX(100%) rotate(45deg);
  }
}

.dest-info {
  flex: 1;
  padding: 14px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.dest-name {
  font-size: 17px;
  font-weight: 600;
  color: var(--text-primary);
  letter-spacing: 0.3px;
}

.dest-desc {
  font-size: 13px;
  color: var(--text-secondary);
  margin-top: 6px;
  line-height: 1.5;
}

.dest-meta {
  display: flex;
  align-items: center;
  gap: 14px;
  margin-top: 10px;
}

.dest-rating {
  font-size: 12px;
  color: #f59e0b;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 2px;
}

.dest-heat {
  font-size: 12px;
  color: #ef4444;
  display: flex;
  align-items: center;
  gap: 2px;
}
</style>