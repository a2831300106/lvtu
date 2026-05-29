<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, User, Plus, Edit, Delete, Check, Calendar } from '@element-plus/icons-vue'

const searchForm = reactive({
  keyword: '',
  dateRange: []
})

const tableData = ref([
  { id: 1, username: 'zhangsan', nickname: '张三', phone: '138****8888', email: 'zhangsan@example.com', status: 1, createTime: '2026-05-20 10:30:00' },
  { id: 2, username: 'lisi', nickname: '李四', phone: '139****6666', email: 'lisi@example.com', status: 1, createTime: '2026-05-18 15:20:00' },
  { id: 3, username: 'wangwu', nickname: '王五', phone: '137****5555', email: 'wangwu@example.com', status: 0, createTime: '2026-05-15 09:45:00' },
  { id: 4, username: 'zhaoliu', nickname: '赵六', phone: '136****4444', email: 'zhaoliu@example.com', status: 1, createTime: '2026-05-12 14:30:00' },
  { id: 5, username: 'qianqi', nickname: '钱七', phone: '135****3333', email: 'qianqi@example.com', status: 1, createTime: '2026-05-10 11:15:00' }
])

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 100
})

const dialogVisible = ref(false)
const dialogTitle = ref('新增用户')
const formRef = ref(null)

const formData = reactive({
  id: null,
  username: '',
  nickname: '',
  phone: '',
  email: '',
  status: 1
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
  ]
}

const handleSearch = () => {
  ElMessage.success('搜索成功')
}

const handleReset = () => {
  searchForm.keyword = ''
  searchForm.dateRange = []
}

const handleAdd = () => {
  dialogTitle.value = '新增用户'
  Object.keys(formData).forEach(key => {
    if (key !== 'status') formData[key] = ''
    else formData.status = 1
  })
  formData.id = null
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑用户'
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该用户吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    const index = tableData.value.findIndex(item => item.id === row.id)
    if (index > -1) {
      tableData.value.splice(index, 1)
      ElMessage.success('删除成功')
    }
  }).catch(() => {})
}

const handleSubmit = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      if (formData.id) {
        const index = tableData.value.findIndex(item => item.id === formData.id)
        if (index > -1) {
          tableData.value[index] = { ...formData }
        }
        ElMessage.success('编辑成功')
      } else {
        formData.id = Date.now()
        tableData.value.unshift({ ...formData })
        ElMessage.success('新增成功')
      }
      dialogVisible.value = false
    }
  })
}

const handleSizeChange = (val) => {
  pagination.pageSize = val
}

const handleCurrentChange = (val) => {
  pagination.current = val
}
</script>

<template>
  <div class="user-list">
    <div class="search-card">
      <div class="search-header">
        <h3 class="search-title">用户搜索</h3>
      </div>
      <el-form :model="searchForm" inline class="search-form">
        <el-form-item label="关键词" class="form-item">
          <div class="input-wrapper">
            <el-icon class="input-icon"><Search /></el-icon>
            <el-input v-model="searchForm.keyword" placeholder="用户名/昵称/手机号" clearable />
          </div>
        </el-form-item>
        <el-form-item label="注册时间" class="form-item">
          <div class="input-wrapper">
            <el-icon class="input-icon"><Calendar /></el-icon>
            <el-date-picker
              v-model="searchForm.dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            />
          </div>
        </el-form-item>
        <el-form-item class="form-item actions">
          <el-button type="primary" class="search-btn" @click="handleSearch">
            <el-icon><Search /></el-icon>
            <span>搜索</span>
          </el-button>
          <el-button class="reset-btn" @click="handleReset">
            <el-icon><Refresh /></el-icon>
            <span>重置</span>
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-card">
      <div class="table-header">
        <div class="table-title-wrapper">
          <el-icon class="table-icon"><User /></el-icon>
          <span class="table-title">用户列表</span>
          <span class="table-count">共 {{ pagination.total }} 条</span>
        </div>
        <el-button type="primary" class="add-btn" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          <span>新增用户</span>
        </el-button>
      </div>

      <el-table :data="tableData" style="width: 100%" class="data-table">
        <el-table-column prop="id" label="ID" width="80">
          <template #default="{ row }">
            <span class="id-badge">{{ row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="120">
          <template #default="{ row }">
            <span class="username">{{ row.username }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="nickname" label="昵称" width="120">
          <template #default="{ row }">
            <span class="nickname">{{ row.nickname }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="130">
          <template #default="{ row }">
            <span class="phone">{{ row.phone }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" min-width="180">
          <template #default="{ row }">
            <span class="email">{{ row.email }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'" class="status-tag">
              {{ row.status === 1 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="注册时间" width="180" />
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" text class="action-btn edit-btn" @click="handleEdit(row)">
              <el-icon><Edit /></el-icon>
              <span>编辑</span>
            </el-button>
            <el-button type="danger" text class="action-btn delete-btn" @click="handleDelete(row)">
              <el-icon><Delete /></el-icon>
              <span>删除</span>
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
          v-model:current-page="pagination.current"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          class="pagination-component"
        />
      </div>
    </div>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="520px" class="form-dialog">
      <el-form ref="formRef" :model="formData" :rules="rules" label-width="100px" class="dialog-form">
        <el-form-item label="用户名" prop="username">
          <div class="form-input-wrapper">
            <el-input v-model="formData.username" :disabled="!!formData.id" placeholder="请输入用户名" />
          </div>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <div class="form-input-wrapper">
            <el-input v-model="formData.nickname" placeholder="请输入昵称" />
          </div>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <div class="form-input-wrapper">
            <el-input v-model="formData.phone" placeholder="请输入手机号" />
          </div>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <div class="form-input-wrapper">
            <el-input v-model="formData.email" placeholder="请输入邮箱" />
          </div>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="formData.status" class="status-radio">
            <el-radio :label="1" class="radio-item">
              <span>正常</span>
            </el-radio>
            <el-radio :label="0" class="radio-item">
              <span>禁用</span>
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button class="close-btn" @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" class="submit-btn" @click="handleSubmit">
          <el-icon><Check /></el-icon>
          <span>确定</span>
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.user-list {
  padding: 0;
}

.search-card {
  background: #ffffff;
  border-radius: 16px;
  padding: 24px;
  margin-bottom: 24px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.search-header {
  margin-bottom: 20px;
}

.search-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
}

.search-form {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 16px;
}

.form-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.form-item.actions {
  margin-left: auto;
  gap: 8px;
}

.form-item label {
  font-size: 14px;
  color: #64748b;
  font-weight: 500;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.input-icon {
  position: absolute;
  left: 12px;
  color: #94a3b8;
  z-index: 1;
}

:deep(.el-input__wrapper),
:deep(.el-date-picker__wrapper) {
  border-radius: 10px;
  border: 1px solid #e2e8f0;
  transition: all 0.25s ease;
}

:deep(.el-input__wrapper:hover),
:deep(.el-date-picker__wrapper:hover) {
  border-color: #cbd5e1;
}

:deep(.el-input__wrapper.is-focus),
:deep(.el-date-picker__wrapper.is-focus) {
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.search-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 10px 20px;
  border-radius: 10px;
  font-weight: 500;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
  transition: all 0.25s ease;
}

.search-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(59, 130, 246, 0.4);
}

.reset-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 10px 20px;
  border-radius: 10px;
  font-weight: 500;
  transition: all 0.25s ease;
}

.reset-btn:hover {
  background: #f1f5f9;
}

.table-card {
  background: #ffffff;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.table-title-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
}

.table-icon {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  border-radius: 10px;
  color: #fff;
}

.table-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
}

.table-count {
  font-size: 13px;
  color: #94a3b8;
  padding: 4px 10px;
  background: #f1f5f9;
  border-radius: 20px;
}

.add-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 10px 20px;
  border-radius: 10px;
  font-weight: 500;
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
  transition: all 0.25s ease;
}

.add-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(16, 185, 129, 0.4);
}

.data-table {
  --el-table-header-text-color: #64748b;
  --el-table-row-hover-bg-color: #f8fafc;
}

:deep(.el-table) {
  border-radius: 12px;
  overflow: hidden;
}

:deep(.el-table__header) {
  background: #f8fafc;
}

:deep(.el-table__header th) {
  padding: 14px 12px;
  font-weight: 600;
  font-size: 13px;
  color: #64748b;
  border-bottom: 1px solid #e2e8f0;
}

:deep(.el-table__row) {
  transition: all 0.2s ease;
}

:deep(.el-table__row:hover) {
  background: #f8fafc !important;
}

:deep(.el-table__cell) {
  padding: 14px 12px;
  font-size: 14px;
  color: #334155;
  border-bottom: 1px solid #f1f5f9;
}

.id-badge {
  font-size: 13px;
  color: #64748b;
  padding: 4px 10px;
  background: #f1f5f9;
  border-radius: 6px;
  font-family: 'Monaco', 'Menlo', monospace;
}

.username {
  font-weight: 500;
  color: #1e293b;
}

.nickname {
  color: #334155;
}

.phone,
.email {
  color: #64748b;
  font-size: 13px;
}

.status-tag {
  padding: 4px 10px;
  border-radius: 20px;
  font-size: 12px;
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 4px;
  font-weight: 500;
  transition: all 0.25s ease;
  padding: 6px 10px;
  border-radius: 6px;
}

.action-btn:hover {
  background: rgba(0, 0, 0, 0.04);
}

.edit-btn {
  color: #3b82f6;
}

.delete-btn {
  color: #ef4444;
}

.pagination {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
}

.pagination-component {
  --el-pagination-item-bg-color: transparent;
  --el-pagination-item-active-bg-color: #3b82f6;
  --el-pagination-item-active-text-color: #fff;
}

:deep(.el-pagination.is-background .el-pager li:not(.disabled):hover) {
  background: #eff6ff;
}

.form-dialog {
  --el-dialog-border-radius: 16px;
}

:deep(.form-dialog .el-dialog__header) {
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  border-radius: 16px 16px 0 0;
  padding: 20px 24px;
}

:deep(.form-dialog .el-dialog__title) {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
}

:deep(.form-dialog .el-dialog__body) {
  padding: 24px;
}

:deep(.form-dialog .el-dialog__footer) {
  padding: 16px 24px;
  border-top: 1px solid #f1f5f9;
}

.dialog-form {
  --el-form-item-label-color: #64748b;
}

.form-input-wrapper {
  margin-top: 4px;
}

:deep(.dialog-form .el-input__wrapper) {
  border-radius: 10px;
  border: 1px solid #e2e8f0;
  transition: all 0.25s ease;
}

:deep(.dialog-form .el-input__wrapper:focus-within) {
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

:deep(.dialog-form .el-input.is-disabled .el-input__wrapper) {
  background: #f8fafc;
  border-color: #e2e8f0;
}

.status-radio {
  display: flex;
  gap: 30px;
}

.radio-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
}

:deep(.status-radio .el-radio__input.is-checked .el-radio__inner) {
  border-color: #3b82f6;
  background: #3b82f6;
}

.close-btn {
  padding: 10px 20px;
  border-radius: 10px;
  font-weight: 500;
  transition: all 0.25s ease;
}

.close-btn:hover {
  background: #f1f5f9;
}

.submit-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 10px 24px;
  border-radius: 10px;
  font-weight: 500;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
  transition: all 0.25s ease;
}

.submit-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(59, 130, 246, 0.4);
}

@media screen and (max-width: 768px) {
  .search-form {
    flex-direction: column;
    align-items: stretch;
  }
  
  .form-item.actions {
    margin-left: 0;
    justify-content: flex-end;
  }
}
</style>