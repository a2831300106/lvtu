<script setup>
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const strategy = ref({
  id: route.params.id,
  title: '三天两夜深度游丽江，这份攻略请收好！',
  author: '旅行达人小王',
  avatar: '👤',
  dest: '丽江',
  cover: '🏔️',
  likes: 856,
  comments: 42,
  publishTime: '2026-05-20',
  content: `🌟 行前准备
1. 最佳季节：3-10月
2. 必备物品：防晒霜、厚外套（早晚温差大）
3. 高原反应：提前服用红景天

🏯 必游景点
1. 丽江古城：世界文化遗产，夜景很美
2. 黑龙潭：拍玉龙雪山的最佳位置
3. 束河古镇：比古城更安静，适合发呆

🍜 美食推荐
1. 腊排骨火锅：阿婆腊排骨最正宗
2. 鸡豆凉粉：古城街边小摊最地道
3. 纳西烤鱼：新鲜美味

🏨 住宿建议
建议住在古城内或束河古镇，民宿很有特色

💡 小贴士
- 不要相信古城拉客的人
- 买东西记得讲价
- 玉龙雪山需要提前订票`,
  tags: ['丽江', '古城', '美食', '三日游']
})

const goBack = () => {
  router.back()
}

const like = () => {
  strategy.value.likes++
}
</script>

<template>
  <div class="strategy-detail-page">
    <div class="navbar">
      <div class="navbar-back" @click="goBack">← 返回</div>
      <div class="navbar-title">攻略详情</div>
      <div class="share-btn">分享</div>
    </div>

    <div class="detail-content">
      <div class="cover-section">
        <div class="cover-icon">{{ strategy.cover }}</div>
      </div>

      <div class="article-section">
        <h1 class="article-title">{{ strategy.title }}</h1>

        <div class="author-info">
          <span class="avatar">{{ strategy.avatar }}</span>
          <span class="name">{{ strategy.author }}</span>
          <span class="dest-tag">{{ strategy.dest }}</span>
        </div>

        <div class="article-meta">
          <span>{{ strategy.publishTime }}</span>
          <span>❤️ {{ strategy.likes }}</span>
          <span>💬 {{ strategy.comments }}</span>
        </div>

        <div class="tags">
          <span class="tag" v-for="tag in strategy.tags" :key="tag">{{ tag }}</span>
        </div>

        <div class="article-content">
          <p v-for="(line, index) in strategy.content.split('\n')" :key="index">
            {{ line }}
          </p>
        </div>
      </div>

      <div class="comment-section">
        <div class="section-title">评论 ({{ strategy.comments }})</div>
        <div class="comment-list">
          <div class="comment-item">
            <div class="comment-avatar">👤</div>
            <div class="comment-body">
              <div class="comment-author">游客小李</div>
              <div class="comment-text">写得真棒！正准备去丽江，攻略很有用！</div>
              <div class="comment-time">2小时前</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="bottom-bar">
      <div class="action-bar">
        <div class="action-item" @click="like">
          <span>❤️</span>
          <span>{{ strategy.likes }}</span>
        </div>
        <div class="action-item">
          <span>💬</span>
          <span>{{ strategy.comments }}</span>
        </div>
        <div class="action-item">
          <span>⭐</span>
          <span>收藏</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.strategy-detail-page {
  min-height: 100%;
  background: linear-gradient(180deg, #f8fafc 0%, #f1f5f9 100%);
  padding-bottom: 80px;
}

.navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: var(--header-height);
  background: linear-gradient(135deg, rgba(255, 255, 255, 0.98) 0%, rgba(248, 250, 252, 0.98) 100%);
  backdrop-filter: blur(12px);
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

.share-btn {
  font-size: 15px;
  color: #3b82f6;
  font-weight: 500;
  padding: 8px 14px;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.1) 0%, rgba(59, 130, 246, 0.05) 100%);
  border-radius: 20px;
  transition: all 0.3s ease;
}

.share-btn:active {
  background: rgba(59, 130, 246, 0.2);
  transform: scale(0.95);
}

.detail-content {
  padding-top: var(--header-height);
}

.cover-section {
  height: 240px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 50%, #f093fb 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.cover-section::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle at 30% 20%, rgba(255, 255, 255, 0.15) 0%, transparent 50%);
  animation: pulse 4s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% {
    opacity: 0.5;
    transform: scale(1);
  }
  50% {
    opacity: 1;
    transform: scale(1.1);
  }
}

.cover-icon {
  font-size: 110px;
  position: relative;
  z-index: 1;
}

.article-section {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  padding: 24px 16px;
  margin-top: -24px;
  border-radius: 24px 24px 0 0;
  position: relative;
  box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.08);
}

.article-title {
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
  line-height: 1.5;
  margin-bottom: 18px;
  letter-spacing: 0.3px;
}

.author-info {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 14px;
}

.avatar {
  font-size: 28px;
}

.name {
  font-size: 15px;
  color: var(--text-primary);
  font-weight: 500;
}

.dest-tag {
  font-size: 12px;
  color: #3b82f6;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.1) 0%, rgba(59, 130, 246, 0.05) 100%);
  padding: 4px 10px;
  border-radius: 8px;
  font-weight: 500;
}

.article-meta {
  display: flex;
  gap: 18px;
  font-size: 13px;
  color: var(--text-hint);
  margin-bottom: 18px;
}

.tags {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  margin-bottom: 24px;
}

.tag {
  font-size: 12px;
  color: #3b82f6;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.1) 0%, rgba(59, 130, 246, 0.05) 100%);
  padding: 5px 12px;
  border-radius: 16px;
  border: 1px solid rgba(59, 130, 246, 0.2);
  font-weight: 500;
}

.article-content {
  font-size: 15px;
  line-height: 1.9;
  color: var(--text-primary);
}

.article-content p {
  margin-bottom: 14px;
  padding: 12px 16px;
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  border-radius: 12px;
}

.comment-section {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  margin-top: 12px;
  padding: 18px 16px;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 16px;
}

.comment-list {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.comment-item {
  display: flex;
  gap: 14px;
  padding: 14px;
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  border-radius: 14px;
  transition: all 0.3s ease;
}

.comment-item:active {
  background: rgba(59, 130, 246, 0.1);
}

.comment-avatar {
  font-size: 36px;
  flex-shrink: 0;
}

.comment-body {
  flex: 1;
}

.comment-author {
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 6px;
}

.comment-text {
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.6;
  margin-bottom: 6px;
}

.comment-time {
  font-size: 12px;
  color: var(--text-hint);
}

.bottom-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  padding: 14px 16px;
  padding-bottom: calc(14px + var(--safe-area-bottom));
  max-width: 430px;
  margin: 0 auto;
  box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.08);
  backdrop-filter: blur(10px);
}

.action-bar {
  display: flex;
  justify-content: space-around;
}

.action-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 15px;
  color: var(--text-secondary);
  padding: 10px 24px;
  border-radius: 24px;
  transition: all 0.3s ease;
}

.action-item:active {
  background: rgba(59, 130, 246, 0.1);
  transform: scale(0.96);
}

.action-item span:first-child {
  font-size: 20px;
}
</style>