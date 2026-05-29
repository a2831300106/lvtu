<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const nickname = ref('旅行者')
const phone = ref('138****8888')

const goBack = () => {
  router.back()
}

const save = () => {
  localStorage.setItem('user', JSON.stringify({
    nickname: nickname.value,
    phone: phone.value
  }))
  router.back()
}
</script>

<template>
  <div class="profile-edit-page">
    <div class="navbar">
      <div class="navbar-back" @click="goBack">取消</div>
      <div class="navbar-title">编辑资料</div>
      <div class="save-btn" @click="save">保存</div>
    </div>

    <div class="edit-content">
      <div class="avatar-section">
        <div class="avatar-large">
          <span>👤</span>
        </div>
        <span class="change-avatar">修改头像</span>
      </div>

      <div class="form-section">
        <div class="form-item">
          <label>昵称</label>
          <input type="text" v-model="nickname" placeholder="请输入昵称" />
        </div>
        <div class="form-item">
          <label>手机号</label>
          <input type="tel" v-model="phone" disabled />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.profile-edit-page {
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

.save-btn {
  font-size: 16px;
  color: #3b82f6;
  font-weight: 600;
  padding: 8px 16px;
  background: rgba(59, 130, 246, 0.1);
  border-radius: 12px;
  transition: all 0.3s ease;
}

.save-btn:active {
  background: rgba(59, 130, 246, 0.2);
  transform: scale(0.96);
}

.edit-content {
  padding-top: calc(var(--header-height) + 28px);
  padding-left: 16px;
  padding-right: 16px;
}

.avatar-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 14px;
  margin-bottom: 28px;
}

.avatar-large {
  width: 88px;
  height: 88px;
  background: linear-gradient(135deg, #ffffff 0%, #f1f5f9 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 44px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.12);
  transition: all 0.35s ease;
}

.avatar-large:active {
  transform: scale(0.96);
}

.change-avatar {
  font-size: 14px;
  color: #3b82f6;
  font-weight: 500;
  padding: 8px 16px;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.change-avatar:active {
  background: rgba(59, 130, 246, 0.1);
}

.form-section {
  background: linear-gradient(135deg, #ffffff 0%, #fafafa 100%);
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
}

.form-item {
  display: flex;
  align-items: center;
  padding: 16px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.form-item:last-child {
  border-bottom: none;
}

.form-item label {
  width: 70px;
  font-size: 15px;
  color: var(--text-secondary);
}

.form-item input {
  flex: 1;
  font-size: 15px;
  text-align: right;
  outline: none;
  border: none;
  background: transparent;
  color: var(--text-primary);
}

.form-item input:disabled {
  color: var(--text-hint);
  background: none;
}
</style>