<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { Search, Refresh, Ticket, Check } from '@element-plus/icons-vue'

const searchForm = reactive({
  keyword: '',
  status: '',
  dateRange: []
})

const statusOptions = [
  { label: '待支付', value: 'pending' },
  { label: '已支付', value: 'paid' },
  { label: '进行中', value: 'processing' },
  { label: '已完成', value: 'completed' },
  { label: '已取消', value: 'cancelled' },
  { label: '已退款', value: 'refunded' }
]

const tableData = ref([
  { id: 'ORD20260528001', user: '张三', phone: '138****8888', product: '丽江古城木府门票', amount: 40, status: 'completed', payMethod: '微信支付', createTime: '2026-05-28 10:23:45' },
  { id: 'ORD20260528002', user: '李四', phone: '139****6666', product: '三亚亚特兰蒂斯酒店', amount: 888, status: 'processing', payMethod: '支付宝', createTime: '2026-05-28 09:15:32' },
  { id: 'ORD20260528003', user: '王五', phone: '137****5555', product: '张家界国家森林公园', amount: 158, status: 'paid', payMethod: '微信支付', createTime: '2026-05-28 08:45:12' },
  { id: 'ORD20260528004', user: '赵六', phone: '136****4444', product: '成都火锅一日游', amount: 268, status: 'pending', payMethod: '-', createTime: '2026-05-28 07:32:18' },
  { id: 'ORD20260528005', user: '钱七', phone: '135****3333', product: '杭州西湖游船', amount: 80, status: 'completed', payMethod: '支付宝', createTime: '2026-05-28 06:12:55' },
  { id: 'ORD20260527001', user: '孙八', phone: '134****2222', product: '厦门鼓浪屿船票', amount: 35, status: 'cancelled', payMethod: '-', createTime: '2026-05-27 18:20:10' },
  { id: 'ORD20260527002', user: '周九', phone: '133****1111', product: '北京故宫门票', amount: 60, status: 'refunded', payMethod: '微信支付', createTime: '2026-05-27 14:05:33' }
])

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 100
})

const detailVisible = ref(false)
const currentOrder = ref(null)

const statusMap = {
  pending: { label: '待支付', type: 'warning' },
  paid: { label: '已支付', type: 'success' },
  processing: { label: '进行中', type: 'primary' },
  completed: { label: '已完成', type: 'success' },
  cancelled: { label: '已取消', type: 'info' },
  refunded: { label: '已退款', type: 'danger' }
}

const getStatusInfo = (status) => {
  return statusMap[status] || { label: '-', type: 'info' }
}

const handleSearch = () => {
  ElMessage.success('搜索成功')
}

const handleReset = () => {
  searchForm.keyword = ''
  searchForm.status = ''
  searchForm.dateRange = []
}

const handleViewDetail = (row) => {
  currentOrder.value = { ...row }
  detailVisible.value = true
}

const handleSizeChange = (val) => {
  pagination.pageSize = val
}

const handleCurrentChange = (val) => {
  pagination.current = val
}
</script>

<template>
  <div class="order-list">
    <div class="search-card">
      <div class="search-header">
        <h3 class="search-title">订单搜索</h3>
      </div>
      <el-form :model="searchForm" inline class="search-form">
        <el-form-item label="关键词" class="form-item">
          <div class="input-wrapper">
            <el-icon class="input-icon"><Search /></el-icon>
            <el-input v-model="searchForm.keyword" placeholder="订单号/用户/产品" clearable />
          </div>
        </el-form-item>
        <el-form-item label="订单状态" class="form-item">
          <div class="input-wrapper">
            <el-select v-model="searchForm.status" placeholder="请选择" clearable>
              <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </div>
        </el-form-item>
        <el-form-item label="下单时间" class="form-item">
          <div class="input-wrapper">
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
          <el-icon class="table-icon"><Ticket /></el-icon>
          <span class="table-title">订单列表</span>
          <span class="table-count">共 {{ pagination.total }} 条</span>
        </div>
      </div>

      <el-table :data="tableData" style="width: 100%" class="data-table">
        <el-table-column prop="id" label="订单号" width="180">
          <template #default="{ row }">
            <span class="order-id">{{ row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="user" label="用户" width="100">
          <template #default="{ row }">
            <div class="user-cell">
              <el-avatar :size="24" class="mini-avatar">{{ row.user.charAt(0) }}</el-avatar>
              <span>{{ row.user }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="product" label="产品" min-width="200" />
        <el-table-column prop="amount" label="金额" width="100">
          <template #default="{ row }">
            <span class="price">¥{{ row.amount }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusInfo(row.status).type" class="status-tag">
              {{ getStatusInfo(row.status).label }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="payMethod" label="支付方式" width="100" />
        <el-table-column prop="createTime" label="下单时间" width="180" />
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" text class="action-btn" @click="handleViewDetail(row)">
              <el-icon><Check /></el-icon>
              <span>详情</span>
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

    <el-dialog v-model="detailVisible" title="订单详情" width="620px" class="detail-dialog">
      <div v-if="currentOrder" class="order-detail">
        <div class="detail-header">
          <div class="order-id-badge">{{ currentOrder.id }}</div>
          <el-tag :type="getStatusInfo(currentOrder.status).type" class="status-badge">
            {{ getStatusInfo(currentOrder.status).label }}
          </el-tag>
        </div>
        <el-descriptions :column="2" class="detail-descriptions">
          <el-descriptions-item label="用户" class="desc-item">
            <div class="user-info">
              <el-avatar :size="40" class="detail-avatar">{{ currentOrder.user.charAt(0) }}</el-avatar>
              <div class="user-text">
                <div class="user-name">{{ currentOrder.user }}</div>
                <div class="user-phone">{{ currentOrder.phone }}</div>
              </div>
            </div>
          </el-descriptions-item>
          <el-descriptions-item label="金额" class="desc-item">
            <span class="detail-price">¥{{ currentOrder.amount }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="产品" :span="2" class="desc-item">
            <span class="product-name">{{ currentOrder.product }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="支付方式" class="desc-item">
            <span class="pay-method">{{ currentOrder.payMethod }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="下单时间" :span="2" class="desc-item">
            <span class="order-time">{{ currentOrder.createTime }}</span>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <template #footer>
        <el-button class="close-btn" @click="detailVisible = false">关闭</el-button>
        <el-button type="primary" class="confirm-btn">
          <el-icon><Check /></el-icon>
          <span>确认订单</span>
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.order-list {
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

:deep(.el-date-editor) {
  width: 240px;
}

:deep(.el-date-editor .el-input__wrapper) {
  border-radius: 10px;
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

.order-id {
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 13px;
  color: #64748b;
}

.user-cell {
  display: flex;
  align-items: center;
  gap: 6px;
}

.mini-avatar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  font-size: 11px;
  font-weight: 600;
  color: #fff;
}

.price {
  color: #ef4444;
  font-weight: 600;
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
  color: #3b82f6;
  font-weight: 500;
  transition: all 0.25s ease;
}

.action-btn:hover {
  background: rgba(59, 130, 246, 0.08);
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

.detail-dialog {
  --el-dialog-border-radius: 16px;
}

:deep(.detail-dialog .el-dialog__header) {
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  border-radius: 16px 16px 0 0;
  padding: 20px 24px;
}

:deep(.detail-dialog .el-dialog__title) {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
}

:deep(.detail-dialog .el-dialog__body) {
  padding: 24px;
}

:deep(.detail-dialog .el-dialog__footer) {
  padding: 16px 24px;
  border-top: 1px solid #f1f5f9;
}

.order-detail {
  padding: 8px 0;
}

.detail-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 16px;
  border-bottom: 1px solid #f1f5f9;
}

.order-id-badge {
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 14px;
  color: #64748b;
  padding: 8px 16px;
  background: #f1f5f9;
  border-radius: 8px;
}

.status-badge {
  padding: 6px 14px;
  border-radius: 20px;
  font-size: 13px;
  font-weight: 500;
}

.detail-descriptions {
  --el-descriptions-item-label-color: #64748b;
  --el-descriptions-item-content-color: #1e293b;
}

:deep(.detail-descriptions .el-descriptions__label) {
  font-weight: 500;
  font-size: 14px;
  padding: 12px 16px;
  background: #f8fafc;
}

:deep(.detail-descriptions .el-descriptions__content) {
  padding: 12px 16px;
  font-size: 14px;
}

.desc-item {
  margin-bottom: 4px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.detail-avatar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  font-size: 16px;
  font-weight: 600;
  color: #fff;
}

.user-text {
  display: flex;
  flex-direction: column;
}

.user-name {
  font-weight: 600;
  color: #1e293b;
}

.user-phone {
  font-size: 13px;
  color: #64748b;
}

.detail-price {
  font-size: 24px;
  font-weight: 700;
  color: #ef4444;
}

.product-name {
  color: #334155;
}

.pay-method {
  color: #334155;
}

.order-time {
  color: #64748b;
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

.confirm-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 10px 24px;
  border-radius: 10px;
  font-weight: 500;
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
  transition: all 0.25s ease;
}

.confirm-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(16, 185, 129, 0.4);
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