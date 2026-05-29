<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const activeTab = ref(0)
const tabs = ['推荐', '关注', '目的地']

const searchValue = ref('')

const strategies = ref([
  {
    id: 1,
    title: '三天两夜深度游丽江，这份攻略请收好！',
    author: '旅行达人小王',
    avatar: '👤',
    dest: '丽江',
    cover: '🏔️',
    likes: 856,
    comments: 42,
    tags: ['丽江', '古城', '美食']
  },
  {
    id: 2,
    title: '环湖骑行，带你发现不一样的青海湖',
    author: '摄影爱好者',
    avatar: '📷',
    dest: '青海湖',
    cover: '🌊',
    likes: 1234,
    comments: 89,
    tags: ['青海', '自驾', '摄影']
  },
  {
    id: 3,
    title: '成都本地人带你吃最地道的火锅',
    author: '美食探索家',
    avatar: '🍜',
    dest: '成都',
    cover: '🐼',
    likes: 678,
    comments: 56,
    tags: ['成都', '美食', '火锅']
  },
  {
    id: 4,
    title: '张家界国家森林公园全攻略，轻松玩转仙境',
    author: '户外玩家',
    avatar: '🎒',
    dest: '张家界',
    cover: '🏔️',
    likes: 567,
    comments: 34,
    tags: ['张家界', '徒步', '自然']
  }
])

const goBack = () => {
  router.back()
}

const goToDetail = (id) => {
  router.push(`/strategy/${id}`)
}

const goToPublish = () => {
  router.push('/publish')
}
</script>

<template>
  <div class="strategy-page">
    <div class="navbar">
      <div class="navbar-back" @click="goBack">← 返回</div>
      <div class="navbar-title">旅行攻略</div>
      <div class="write-btn" @click="goToPublish">发布</div>
    </div>

    <div class="search-bar">
      <input
        type="text"
        v-model="searchValue"
        placeholder="搜索攻略"
      />
      <button class="search-btn">🔍</button>
    </div>

    <div class="tabs">
      <div
        class="tab"
        :class="{ active: activeTab === index }"
        v-for="(tab, index) in tabs"
        :key="tab"
        @click="activeTab = index"
      >
        {{ tab }}
      </div>
    </div>

    <div class="strategy-list">
      <div
        class="strategy-card"
        v-for="item in strategies"
        :key="item.id"
        @click="goToDetail(item.id)"
      >
        <div class="card-cover">{{ item.cover }}</div>
        <div class="card-content">
          <h3 class="card-title">{{ item.title }}</h3>
          <div class="card-meta">
            <div class="author">
              <span class="avatar">{{ item.avatar }}</span>
              <span class="name">{{ item.author }}</span>
            </div>
            <span class="dest-tag">{{ item.dest }}</span>
          </div>
          <div class="card-footer">
            <div class="tags">
              <span class="tag" v-for="tag in item.tags" :key="tag">{{ tag }}</span>
            </div>
            <div class="interactions">
              <span>❤️ {{ item.likes }}</span>
              <span>💬 {{ item.comments }}</span>
            </div>
          </div>
        </div>
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
      <div class="tabbar-item active">
        <span class="tabbar-icon">📖</span>
        <span>攻略</span>
      </div>
      <div class="tabbar-item" @click="router.push('/orders')">
        <span class="tabbar-icon">📋</span>
        <span>订单</span>
      </div>
      <div class="tabbar-item" @click="router.push('/profile')">
        <span class="tabbar-icon">👤</span>
        <span>我的</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.strategy-page {
  min-height: 100%;
  background: linear-gradient(180deg, #f8fafc 0%, #f1f5f9 100%);
  padding-bottom: calc(var(--tabbar-height) + var(--safe-area-bottom));
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

.write-btn {
  font-size: 15px;
  color: #3b82f6;
  font-weight: 500;
  padding: 8px 16px;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.1) 0%, rgba(59, 130, 246, 0.05) 100%);
  border-radius: 20px;
  transition: all 0.3s ease;
}

.write-btn:active {
  background: rgba(59, 130, 246, 0.2);
  transform: scale(0.95);
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

.tabs {
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

.tab {
  padding: 10px 16px;
  font-size: 14px;
  color: var(--text-secondary);
  border-radius: 20px;
  transition: all 0.3s ease;
  font-weight: 500;
}

.tab.active {
  color: #ffffff;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.tab:not(.active):active {
  background-color: rgba(59, 130, 246, 0.1);
}

.strategy-list {
  padding: calc(var(--header-height) + 128px) 16px 16px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.strategy-card {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 16px;
  overflow: hidden;
  display: flex;
  transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
}

.strategy-card:active {
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.card-cover {
  width: 110px;
  height: 130px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 52px;
  flex-shrink: 0;
  position: relative;
  overflow: hidden;
}

.card-cover::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: linear-gradient(45deg, transparent 30%, rgba(255, 255, 255, 0.15) 50%, transparent 70%);
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

.card-content {
  flex: 1;
  padding: 14px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.card-title {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  letter-spacing: 0.3px;
}

.card-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 10px 0;
}

.author {
  display: flex;
  align-items: center;
  gap: 8px;
}

.avatar {
  font-size: 18px;
}

.name {
  font-size: 12px;
  color: var(--text-secondary);
}

.dest-tag {
  font-size: 11px;
  color: #3b82f6;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.1) 0%, rgba(59, 130, 246, 0.05) 100%);
  padding: 3px 10px;
  border-radius: 8px;
  font-weight: 500;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.tags {
  display: flex;
  gap: 6px;
}

.tag {
  font-size: 10px;
  color: var(--text-hint);
  background: linear-gradient(135deg, #f1f5f9 0%, #e2e8f0 100%);
  padding: 3px 8px;
  border-radius: 6px;
}

.interactions {
  display: flex;
  gap: 14px;
  font-size: 12px;
  color: var(--text-hint);
}

.interactions span {
  display: flex;
  align-items: center;
  gap: 3px;
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
  box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.06);
  backdrop-filter: blur(10px);
  max-width: 430px;
  margin: 0 auto;
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
  padding: 8px 0;
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
  width: 4px;
  height: 4px;
  background: #3b82f6;
  border-radius: 50%;
}

.tabbar-item:active {
  transform: scale(0.95);
}

.tabbar-icon {
  font-size: 22px;
  transition: transform 0.3s ease;
}

.tabbar-item:active .tabbar-icon {
  transform: scale(1.1);
}
</style>