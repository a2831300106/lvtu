<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Box, Plus, Edit, Delete, Check, CircleCheck, CircleClose } from '@element-plus/icons-vue'

const searchForm = reactive({
  keyword: '',
  category: '',
  status: ''
})

const categoryOptions = [
  { label: '酒店民宿', value: 'hotel' },
  { label: '景点门票', value: 'ticket' },
  { label: '跟团游', value: 'group' },
  { label: '定制游', value: 'custom' },
  { label: '周边游', value: 'around' }
]

const statusOptions = [
  { label: '上架', value: 1 },
  { label: '下架', value: 0 }
]

const tableData = ref([
  { id: 1, name: '丽江古城木府门票', category: 'ticket', price: 40, originalPrice: 60, sales: 2356, status: 1, createTime: '2026-05-15' },
  { id: 2, name: '三亚亚特兰蒂斯酒店', category: 'hotel', price: 888, originalPrice: 1288, sales: 856, status: 1, createTime: '2026-05-12' },
  { id: 3, name: '张家界国家森林公园', category: 'ticket', price: 158, originalPrice: 225, sales: 3421, status: 1, createTime: '2026-05-10' },
  { id: 4, name: '成都火锅一日游', category: 'group', price: 268, originalPrice: 398, sales: 1892, status: 0, createTime: '2026-05-08' },
  { id: 5, name: '杭州西湖游船', category: 'around', price: 80, originalPrice: 120, sales: 4567, status: 1, createTime: '2026-05-05' }
])

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 100
})

const dialogVisible = ref(false)
const dialogTitle = ref('新增产品')
const formRef = ref(null)

const formData = reactive({
  id: null,
  name: '',
  category: 'ticket',
  price: 0,
  originalPrice: 0,
  status: 1
})

const rules = {
  name: [{ required: true, message: '请输入产品名称', trigger: 'blur' }],
  category: [{ required: true, message: '请选择产品分类', trigger: 'change' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }]
}

const getCategoryName = (val) => {
  const item = categoryOptions.find(opt => opt.value === val)
  return item ? item.label : '-'
}

const getStatusName = (val) => {
  return val === 1 ? '上架' : '下架'
}

const handleSearch = () => {
  ElMessage.success('搜索成功')
}

const handleReset = () => {
  searchForm.keyword = ''
  searchForm.category = ''
  searchForm.status = ''
}

const handleAdd = () => {
  dialogTitle.value = '新增产品'
  Object.keys(formData).forEach(key => {
    if (key === 'status') formData[key] = 1
    else if (key === 'category') formData[key] = 'ticket'
    else formData[key] = ''
  })
  formData.price = 0
  formData.originalPrice = 0
  formData.id = null
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑产品'
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleToggleStatus = (row) => {
  const newStatus = row.status === 1 ? 0 : 1
  ElMessageBox.confirm(`确定要${newStatus === 1 ? '上架' : '下架'}该产品吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    row.status = newStatus
    ElMessage.success('操作成功')
  }).catch(() => {})
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该产品吗？', '提示', {
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
        formData.createTime = new Date().toISOString().split('T')[0]
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
  <div class="product-list">
    <div class="search-card">
      <div class="search-header">
        <h3 class="search-title">产品搜索</h3>
      </div>
      <el-form :model="searchForm" inline class="search-form">
        <el-form-item label="关键词" class="form-item">
          <div class="input-wrapper">
            <el-icon class="input-icon"><Search /></el-icon>
            <el-input v-model="searchForm.keyword" placeholder="产品名称" clearable />
          </div>
        </el-form-item>
        <el-form-item label="产品分类" class="form-item">
          <div class="input-wrapper">
            <el-select v-model="searchForm.category" placeholder="请选择" clearable>
              <el-option v-for="item in categoryOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </div>
        </el-form-item>
        <el-form-item label="状态" class="form-item">
          <div class="input-wrapper">
            <el-select v-model="searchForm.status" placeholder="请选择" clearable>
              <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
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
          <el-icon class="table-icon"><Box /></el-icon>
          <span class="table-title">产品列表</span>
          <span class="table-count">共 {{ pagination.total }} 条</span>
        </div>
        <el-button type="primary" class="add-btn" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          <span>新增产品</span>
        </el-button>
      </div>

      <el-table :data="tableData" style="width: 100%" class="data-table">
        <el-table-column prop="id" label="ID" width="80">
          <template #default="{ row }">
            <span class="id-badge">{{ row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="产品名称" min-width="200">
          <template #default="{ row }">
            <span class="product-name">{{ row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="category" label="分类" width="120">
          <template #default="{ row }">
            <el-tag type="info" class="category-tag">{{ getCategoryName(row.category) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="现价" width="110">
          <template #default="{ row }">
            <span class="price">¥{{ row.price }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="originalPrice" label="原价" width="110">
          <template #default="{ row }">
            <span class="original-price">¥{{ row.originalPrice }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="sales" label="销量" width="100">
          <template #default="{ row }">
            <span class="sales-count">{{ row.sales }} 笔</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'" class="status-tag">
              {{ getStatusName(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="120" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" text class="action-btn edit-btn" @click="handleEdit(row)">
              <el-icon><Edit /></el-icon>
              <span>编辑</span>
            </el-button>
            <el-button :type="row.status === 1 ? 'warning' : 'success'" text class="action-btn toggle-btn" @click="handleToggleStatus(row)">
              <el-icon><CircleClose /></el-icon>
              <span>{{ row.status === 1 ? '下架' : '上架' }}</span>
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
        <el-form-item label="产品名称" prop="name">
          <div class="form-input-wrapper">
            <el-input v-model="formData.name" placeholder="请输入产品名称" />
          </div>
        </el-form-item>
        <el-form-item label="产品分类" prop="category">
          <div class="form-input-wrapper">
            <el-select v-model="formData.category" placeholder="请选择" style="width: 100%">
              <el-option v-for="item in categoryOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </div>
        </el-form-item>
        <el-form-item label="现价" prop="price">
          <div class="form-input-wrapper">
            <div class="price-input">
              <span class="price-prefix">¥</span>
              <el-input-number v-model="formData.price" :min="0" :precision="2" style="flex: 1" />
            </div>
          </div>
        </el-form-item>
        <el-form-item label="原价" prop="originalPrice">
          <div class="form-input-wrapper">
            <div class="price-input">
              <span class="price-prefix">¥</span>
              <el-input-number v-model="formData.originalPrice" :min="0" :precision="2" style="flex: 1" />
            </div>
          </div>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="formData.status" class="status-radio">
            <el-radio :label="1" class="radio-item">
              <el-icon class="radio-icon active-icon"><CircleCheck /></el-icon>
              <span>上架</span>
            </el-radio>
            <el-radio :label="0" class="radio-item">
              <el-icon class="radio-icon inactive-icon"><CircleClose /></el-icon>
              <span>下架</span>
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
.product-list {
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
:deep(.el-select__wrapper) {
  border-radius: 10px;
  border: 1px solid #e2e8f0;
  transition: all 0.25s ease;
}

:deep(.el-input__wrapper:hover),
:deep(.el-select__wrapper:hover) {
  border-color: #cbd5e1;
}

:deep(.el-input__wrapper.is-focus),
:deep(.el-select__wrapper.is-focus) {
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
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
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

.product-name {
  font-weight: 500;
  color: #1e293b;
}

.category-tag {
  padding: 4px 10px;
  border-radius: 6px;
  font-size: 12px;
}

.price {
  color: #ef4444;
  font-weight: 600;
  font-size: 15px;
}

.original-price {
  color: #94a3b8;
  text-decoration: line-through;
  font-size: 13px;
}

.sales-count {
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

.toggle-btn {
  color: inherit;
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

:deep(.dialog-form .el-input__wrapper),
:deep(.dialog-form .el-select__wrapper) {
  border-radius: 10px;
  border: 1px solid #e2e8f0;
  transition: all 0.25s ease;
}

:deep(.dialog-form .el-input__wrapper:focus-within),
:deep(.dialog-form .el-select__wrapper:focus-within) {
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.price-input {
  display: flex;
  align-items: center;
  gap: 8px;
}

.price-prefix {
  font-size: 16px;
  color: #64748b;
  font-weight: 500;
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

.radio-icon {
  font-size: 18px;
}

.active-icon {
  color: #10b981;
}

.inactive-icon {
  color: #ef4444;
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