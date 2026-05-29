<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '../stores/appStore'

const router = useRouter()
const { login } = useAppStore()

const phone = ref('')
const code = ref('')
const isLogin = ref(true)
const countdown = ref(0)
const isAgree = ref(false)
const isFocused = ref('')

let countdownTimer = null

const canGetCode = computed(() => {
  return phone.value.length === 11 && countdown.value === 0
})

const canLogin = computed(() => {
  if (isLogin.value) {
    return phone.value.length === 11 && code.value.length >= 4
  }
  return phone.value.length === 11 && code.value.length >= 4 && isAgree.value
})

const getCode = () => {
  if (!canGetCode.value) return
  countdown.value = 60
  countdownTimer = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(countdownTimer)
    }
  }, 1000)
}

const toggleMode = () => {
  isLogin.value = !isLogin.value
}

const handleLogin = () => {
  if (!canLogin.value) return

  const mockUser = {
    id: '1',
    phone: phone.value,
    nickname: '旅行者',
    avatar: '',
    level: 'VIP'
  }

  login(mockUser)
  localStorage.setItem('user', JSON.stringify(mockUser))

  router.replace('/home')
}
</script>

<template>
  <div class="login-page">
    <div class="login-bg">
      <div class="bg-circle bg-circle-1"></div>
      <div class="bg-circle bg-circle-2"></div>
      <div class="bg-circle bg-circle-3"></div>
    </div>

    <div class="login-container">
      <div class="login-header">
        <div class="logo-wrapper">
          <div class="logo-icon">🌍</div>
          <span class="logo-text">旅图</span>
          <div class="logo-glow"></div>
        </div>
        <div class="login-subtitle">发现世界的美好</div>
      </div>

      <div class="login-form-wrapper">
        <div class="login-form">
          <div 
            class="form-item"
            :class="{ focused: isFocused === 'phone' }"
          >
            <div class="input-icon">📱</div>
            <input
              type="tel"
              v-model="phone"
              placeholder="请输入手机号"
              maxlength="11"
              @focus="isFocused = 'phone'"
              @blur="isFocused = ''"
            />
            <div class="input-border"></div>
          </div>

          <div 
            class="form-item code-item"
            :class="{ focused: isFocused === 'code' }"
          >
            <div class="input-icon">🔑</div>
            <input
              type="text"
              v-model="code"
              placeholder="请输入验证码"
              maxlength="6"
              @focus="isFocused = 'code'"
              @blur="isFocused = ''"
            />
            <div class="input-border"></div>
            <button
              class="get-code-btn"
              :class="{ disabled: !canGetCode, active: canGetCode }"
              @click="getCode"
              :disabled="!canGetCode"
            >
              {{ countdown > 0 ? `${countdown}s` : '获取验证码' }}
            </button>
          </div>

          <button
            class="btn btn-primary login-btn"
            :class="{ disabled: !canLogin, active: canLogin }"
            @click="handleLogin"
          >
            <span class="btn-text">{{ isLogin ? '登录' : '注册' }}</span>
            <span class="btn-arrow">→</span>
          </button>

          <div class="toggle-mode" @click="toggleMode">
            {{ isLogin ? '没有账号？立即注册' : '已有账号？立即登录' }}
            <span class="toggle-arrow">›</span>
          </div>
        </div>

        <div class="form-decoration"></div>
      </div>

      <div class="login-bottom">
        <div class="agreement" v-if="!isLogin">
          <label class="agree-label">
            <input type="checkbox" v-model="isAgree" />
            <span class="check-mark" v-if="isAgree">✓</span>
            <span class="agree-text">我已阅读并同意</span>
          </label>
          <a href="#" class="link">《用户协议》</a>
          <span class="divider">和</span>
          <a href="#" class="link">《隐私政策》</a>
        </div>

        <div class="third-party">
          <div class="third-divider">
            <span class="divider-line"></span>
            <span class="divider-text">其他登录方式</span>
            <span class="divider-line"></span>
          </div>
          <div class="third-icons">
            <button class="third-btn" title="微信登录">
              <span class="third-icon">💬</span>
              <span class="third-name">微信</span>
            </button>
            <button class="third-btn" title="苹果登录">
              <span class="third-icon">🍎</span>
              <span class="third-name">苹果</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-page {
  min-height: 100vh;
  background: linear-gradient(180deg, #0ec46a 0%, #08b35c 50%, #06994e 100%);
  display: flex;
  flex-direction: column;
  padding: 0 24px;
  position: relative;
  overflow: hidden;
}

.login-bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  pointer-events: none;
}

.bg-circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.1;
  animation: float 10s ease-in-out infinite;
}

.bg-circle-1 {
  width: 500px;
  height: 500px;
  background: radial-gradient(circle, rgba(255,255,255,0.4) 0%, transparent 70%);
  top: -150px;
  right: -150px;
}

.bg-circle-2 {
  width: 350px;
  height: 350px;
  background: radial-gradient(circle, rgba(255,255,255,0.3) 0%, transparent 70%);
  bottom: -100px;
  left: -100px;
  animation-delay: -4s;
}

.bg-circle-3 {
  width: 250px;
  height: 250px;
  background: radial-gradient(circle, rgba(255,255,255,0.25) 0%, transparent 70%);
  top: 40%;
  left: 10%;
  animation-delay: -7s;
}

.login-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 80px 0 40px;
  position: relative;
  z-index: 10;
}

.login-header {
  text-align: center;
  margin-bottom: 56px;
}

.logo-wrapper {
  position: relative;
  display: inline-flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;
}

.logo-icon {
  font-size: 52px;
  filter: drop-shadow(0 4px 16px rgba(0,0,0,0.15));
  animation: iconBreathe 3s ease-in-out infinite;
}

.logo-glow {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 70px;
  height: 70px;
  background: radial-gradient(circle, rgba(255,255,255,0.3) 0%, transparent 70%);
  border-radius: 50%;
  animation: glowPulse 2s ease-in-out infinite;
}

.logo-text {
  font-size: 36px;
  font-weight: 700;
  color: #ffffff;
  letter-spacing: 4px;
  text-shadow: 0 2px 12px rgba(0,0,0,0.15);
}

.login-subtitle {
  color: rgba(255, 255, 255, 0.85);
  font-size: 15px;
  font-weight: 300;
  letter-spacing: 1px;
}

.login-form-wrapper {
  position: relative;
  margin-bottom: auto;
}

.login-form {
  background: #ffffff;
  border-radius: 24px;
  padding: 32px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
  position: relative;
  z-index: 2;
}

.form-decoration {
  position: absolute;
  top: -8px;
  right: -8px;
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, rgba(14, 196, 106, 0.1) 0%, transparent 50%);
  border-radius: 0 24px 0 0;
  z-index: 1;
}

.form-item {
  position: relative;
  margin-bottom: 20px;
  padding: 8px 0;
  transition: all 0.3s ease;
}

.form-item.focused {
  transform: translateY(-2px);
}

.input-icon {
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  font-size: 18px;
  opacity: 0.6;
  transition: all 0.3s ease;
}

.form-item.focused .input-icon {
  opacity: 1;
  transform: translateY(-50%) scale(1.1);
}

.form-item input {
  width: 100%;
  height: 44px;
  font-size: 16px;
  padding-left: 36px;
  background: transparent;
  border: none;
  outline: none;
}

.input-border {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 2px;
  background: #f0f0f0;
  transition: all 0.3s ease;
}

.form-item.focused .input-border {
  background: linear-gradient(90deg, #0ec46a, #08b35c);
  box-shadow: 0 0 10px rgba(14, 196, 106, 0.4);
}

.form-item input::placeholder {
  color: #999999;
  font-size: 15px;
}

.code-item {
  display: flex;
  align-items: center;
  gap: 12px;
}

.code-item input {
  flex: 1;
}

.get-code-btn {
  padding: 10px 20px;
  font-size: 14px;
  font-weight: 500;
  border-radius: 20px;
  border: 1.5px solid #0ec46a;
  background: transparent;
  color: #0ec46a;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  min-width: 100px;
}

.get-code-btn.active {
  background: linear-gradient(135deg, #0ec46a, #08b35c);
  color: #ffffff;
  border-color: transparent;
  box-shadow: 0 4px 15px rgba(14, 196, 106, 0.4);
}

.get-code-btn.active:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(14, 196, 106, 0.5);
}

.get-code-btn.disabled {
  color: #cccccc;
  border-color: #e0e0e0;
  cursor: not-allowed;
}

.btn-primary {
  width: 100%;
  height: 52px;
  border-radius: 26px;
  font-size: 17px;
  font-weight: 600;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.login-btn {
  margin-top: 12px;
  background: linear-gradient(135deg, #0ec46a 0%, #08b35c 100%);
  color: #ffffff;
  box-shadow: 0 4px 20px rgba(14, 196, 106, 0.4);
}

.login-btn.active {
  box-shadow: 0 6px 25px rgba(14, 196, 106, 0.5);
}

.login-btn.active:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 30px rgba(14, 196, 106, 0.6);
}

.login-btn.disabled {
  background: #e8e8e8;
  color: #999999;
  box-shadow: none;
}

.btn-text {
  position: relative;
}

.btn-arrow {
  font-size: 18px;
  transition: transform 0.3s ease;
}

.login-btn.active:hover .btn-arrow {
  transform: translateX(4px);
}

.toggle-mode {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  margin-top: 20px;
  color: #0ec46a;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.3s ease;
}

.toggle-mode:hover {
  color: #08b35c;
}

.toggle-arrow {
  font-size: 16px;
  transition: transform 0.3s ease;
}

.toggle-mode:hover .toggle-arrow {
  transform: translateX(4px);
}

.login-bottom {
  padding-top: 32px;
}

.agreement {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  gap: 6px;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.85);
  margin-bottom: 32px;
}

.agree-label {
  display: flex;
  align-items: center;
  gap: 6px;
  cursor: pointer;
}

.agree-label input {
  display: none;
}

.check-mark {
  width: 14px;
  height: 14px;
  border-radius: 3px;
  background: linear-gradient(135deg, #0ec46a, #08b35c);
  color: #ffffff;
  font-size: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s ease;
}

.agree-label:not(:has(input:checked)) .check-mark {
  background: rgba(255, 255, 255, 0.2);
  color: transparent;
}

.agree-text {
  font-size: 12px;
}

.link {
  color: #ffffff;
  text-decoration: none;
  font-weight: 500;
  border-bottom: 1px solid rgba(255, 255, 255, 0.4);
  transition: all 0.3s ease;
}

.link:hover {
  border-bottom-color: #ffffff;
}

.divider {
  font-size: 11px;
  opacity: 0.6;
}

.third-party {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.third-divider {
  display: flex;
  align-items: center;
  gap: 16px;
  width: 100%;
}

.divider-line {
  flex: 1;
  height: 1px;
  background: rgba(255, 255, 255, 0.2);
}

.divider-text {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.7);
}

.third-icons {
  display: flex;
  gap: 48px;
}

.third-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  padding: 16px;
  background: rgba(255, 255, 255, 0.15);
  border-radius: 16px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  backdrop-filter: blur(10px);
}

.third-btn:hover {
  background: rgba(255, 255, 255, 0.25);
  transform: translateY(-4px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.third-icon {
  font-size: 28px;
}

.third-name {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.9);
  font-weight: 500;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0) scale(1);
  }
  50% {
    transform: translateY(-30px) scale(1.05);
  }
}

@keyframes iconBreathe {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
}

@keyframes glowPulse {
  0%, 100% {
    opacity: 0.2;
    transform: translate(-50%, -50%) scale(1);
  }
  50% {
    opacity: 0.4;
    transform: translate(-50%, -50%) scale(1.3);
  }
}

@media screen and (max-width: 375px) {
  .login-form {
    padding: 24px;
  }
  
  .logo-icon {
    font-size: 44px;
  }
  
  .logo-text {
    font-size: 32px;
  }
  
  .login-header {
    margin-bottom: 48px;
  }
}
</style>
