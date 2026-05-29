<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const title = ref('')
const content = ref('')
const selectedDest = ref('')
const tags = ref([])
const images = ref([])

const destinations = ['丽江', '大理', '三亚', '成都', '杭州', '张家界']

const tagOptions = ['美食', '景点', '住宿', '穿搭', '拍照', '购物', '交通']

const goBack = () => {
  router.back()
}

const toggleTag = (tag) => {
  const index = tags.value.indexOf(tag)
  if (index > -1) {
    tags.value.splice(index, 1)
  } else {
    tags.value.push(tag)
  }
}

const selectDest = (dest) => {
  selectedDest.value = dest
}

const publish = () => {
  if (!title.value || !content.value) {
    alert('请填写标题和内容')
    return
  }

  const article = {
    id: 'ART' + Date.now(),
    title: title.value,
    content: content.value,
    dest: selectedDest.value,
    tags: tags.value,
    author: '旅行者',
    avatar: '👤',
    cover: '🏔️',
    likes: 0,
    comments: 0,
    createTime: new Date().toISOString()
  }

  localStorage.setItem('publishedArticle', JSON.stringify(article))

  router.back()
}
</script>

<template>
  <div class="publish-page">
    <div class="navbar">
      <div class="navbar-back" @click="goBack">取消</div>
      <div class="navbar-title">发布游记</div>
      <div class="publish-btn" @click="publish">发布</div>
    </div>

    <div class="publish-content">
      <div class="title-input">
        <input
          type="text"
          v-model="title"
          placeholder="添加标题会有更多赞哦~"
          maxlength="50"
        />
      </div>

      <div class="content-input">
        <textarea
          v-model="content"
          placeholder="分享你的旅行故事..."
          rows="10"
        ></textarea>
      </div>

      <div class="image-upload">
        <div class="upload-placeholder">
          <span class="upload-icon">📷</span>
          <span class="upload-text">添加图片</span>
        </div>
      </div>

      <div class="section">
        <div class="section-title">目的地</div>
        <div class="dest-list">
          <div
            class="dest-item"
            :class="{ active: selectedDest === dest }"
            v-for="dest in destinations"
            :key="dest"
            @click="selectDest(dest)"
          >
            {{ dest }}
          </div>
        </div>
      </div>

      <div class="section">
        <div class="section-title">标签</div>
        <div class="tag-list">
          <div
            class="tag-item"
            :class="{ active: tags.includes(tag) }"
            v-for="tag in tagOptions"
            :key="tag"
            @click="toggleTag(tag)"
          >
            {{ tag }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.publish-page {
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
}

.navbar-back {
  font-size: 16px;
  color: var(--text-secondary);
  padding: 8px 12px;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.navbar-back:active {
  background: rgba(0, 0, 0, 0.05);
}

.navbar-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  letter-spacing: 0.5px;
}

.publish-btn {
  font-size: 16px;
  color: #3b82f6;
  font-weight: 600;
  padding: 8px 16px;
  background: rgba(59, 130, 246, 0.1);
  border-radius: 12px;
  transition: all 0.3s ease;
}

.publish-btn:active {
  background: rgba(59, 130, 246, 0.2);
  transform: scale(0.96);
}

.publish-content {
  padding-top: calc(var(--header-height) + 16px);
  padding-left: 16px;
  padding-right: 16px;
  padding-bottom: 32px;
}

.title-input {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 16px;
  margin-bottom: 16px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
}

.title-input input {
  width: 100%;
  height: 52px;
  padding: 0 18px;
  font-size: 16px;
  font-weight: 600;
  outline: none;
  border: none;
  background: transparent;
  color: var(--text-primary);
}

.title-input input::placeholder {
  color: var(--text-hint);
}

.content-input {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 16px;
  margin-bottom: 16px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
}

.content-input textarea {
  width: 100%;
  padding: 18px;
  font-size: 15px;
  line-height: 1.7;
  border: none;
  resize: none;
  font-family: inherit;
  outline: none;
  background: transparent;
  color: var(--text-primary);
}

.content-input textarea::placeholder {
  color: var(--text-hint);
}

.image-upload {
  margin-bottom: 16px;
}

.upload-placeholder {
  height: 110px;
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 16px;
  border: 2px dashed rgba(0, 0, 0, 0.08);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 10px;
  transition: all 0.35s ease;
}

.upload-placeholder:active {
  background: rgba(59, 130, 246, 0.05);
  border-color: rgba(59, 130, 246, 0.3);
}

.upload-icon {
  font-size: 36px;
  animation: bounce 2s ease-in-out infinite;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-8px); }
}

.upload-text {
  font-size: 14px;
  color: var(--text-hint);
}

.section {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 16px;
  padding: 18px;
  margin-bottom: 16px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
}

.section-title {
  font-size: 15px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 14px;
}

.dest-list {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.dest-item {
  padding: 8px 16px;
  border: 1.5px solid rgba(0, 0, 0, 0.06);
  border-radius: 20px;
  font-size: 14px;
  color: var(--text-secondary);
  transition: all 0.35s ease;
}

.dest-item:active {
  transform: scale(0.96);
}

.dest-item.active {
  border-color: #3b82f6;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.15) 0%, rgba(59, 130, 246, 0.08) 100%);
  color: #3b82f6;
  font-weight: 500;
}

.tag-list {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.tag-item {
  padding: 8px 16px;
  border: 1.5px solid rgba(0, 0, 0, 0.06);
  border-radius: 20px;
  font-size: 14px;
  color: var(--text-secondary);
  transition: all 0.35s ease;
}

.tag-item:active {
  transform: scale(0.96);
}

.tag-item.active {
  border-color: #3b82f6;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.15) 0%, rgba(59, 130, 246, 0.08) 100%);
  color: #3b82f6;
  font-weight: 500;
}
</style>