# 前后端分离API契约清单

## 文档信息

| 项目 | 内容 |
|------|------|
| 版本号 | V1.0.0 |
| 更新日期 | 2026-05-28 |
| 项目名称 | 旅图旅行平台 |
| 前端技术 | Vue3 + Vite + Axios |
| 后端框架 | 待定 |
| API规范 | RESTful |

---

## 一、基础信息

### 1.1 环境配置

| 环境 | BaseURL | 说明 |
|------|---------|------|
| 开发环境 | `/api/dev` | 本地开发 |
| 测试环境 | `/api/test` | 测试服务器 |
| 预发布 | `/api/pre` | 预发布验证 |
| 生产环境 | `/api` | 正式生产 |

### 1.2 统一响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

### 1.3 错误码规范

| 错误码 | 说明 | 处理建议 |
|--------|------|----------|
| 200 | 成功 | - |
| 400 | 请求参数错误 | 检查参数格式 |
| 401 | 未授权/Token过期 | 重新登录 |
| 403 | 无权限访问 | 联系管理员 |
| 404 | 资源不存在 | 检查请求路径 |
| 500 | 服务器内部错误 | 联系技术支持 |

### 1.4 认证方式

- **C端（H5）**：Bearer Token，存储在 `localStorage.token`
- **B端（Admin）**：Bearer Token，存储在 `localStorage.admin_token`
- Token 放在 HTTP Header：`Authorization: Bearer <token>`

---

## 二、C端API（H5端 - travel-web-h5）

### 2.1 用户模块

#### 2.1.1 用户登录

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-001 |
| 接口路径 | `/user/login` |
| Method | POST |
| 功能描述 | 用户登录（验证码登录） |
| 前端来源 | `LoginPage.vue` |
| 是否需要认证 | 否 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| phone | string | 是 | 手机号 | `13800000000` |
| code | string | 是 | 验证码（4-6位） | `123456` |

**请求示例：**
```json
{
  "phone": "13800000000",
  "code": "123456"
}
```

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| code | int | 状态码 |
| message | string | 提示信息 |
| data | object | 返回数据 |
| data.userId | string | 用户ID |
| data.token | string | 认证Token |
| data.userInfo | object | 用户信息对象 |

**返回示例：**
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "userId": "u_10001",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userInfo": {
      "id": "u_10001",
      "phone": "138****8888",
      "nickname": "旅行者",
      "avatar": "https://example.com/avatar.jpg",
      "level": "VIP",
      "memberSince": "2024-01-01"
    }
  }
}
```

---

#### 2.1.2 用户注册

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-002 |
| 接口路径 | `/user/register` |
| Method | POST |
| 功能描述 | 用户注册（验证码注册） |
| 前端来源 | `LoginPage.vue` |
| 是否需要认证 | 否 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| phone | string | 是 | 手机号 | `13800000000` |
| code | string | 是 | 验证码 | `123456` |
| agreeTerms | boolean | 是 | 同意用户协议 | `true` |

**请求示例：**
```json
{
  "phone": "13800000000",
  "code": "123456",
  "agreeTerms": true
}
```

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| code | int | 状态码 |
| message | string | 提示信息 |
| data | object | 返回数据 |
| data.userId | string | 新用户ID |
| data.token | string | 认证Token |

---

#### 2.1.3 发送验证码

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-003 |
| 接口路径 | `/user/sendCode` |
| Method | POST |
| 功能描述 | 发送手机验证码 |
| 前端来源 | `LoginPage.vue` |
| 是否需要认证 | 否 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| phone | string | 是 | 手机号 | `13800000000` |
| type | string | 是 | 验证码类型（login/register） | `login` |

**返回示例：**
```json
{
  "code": 200,
  "message": "验证码发送成功",
  "data": {
    "expireSeconds": 60
  }
}
```

---

#### 2.1.4 获取用户信息

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-004 |
| 接口路径 | `/user/info` |
| Method | GET |
| 功能描述 | 获取当前登录用户信息 |
| 前端来源 | `ProfilePage.vue`, `appStore.js` |
| 是否需要认证 | 是 |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.id | string | 用户ID |
| data.phone | string | 手机号（脱敏） |
| data.nickname | string | 昵称 |
| data.avatar | string | 头像URL |
| data.level | string | 会员等级 |
| data.memberSince | string | 注册日期 |

---

#### 2.1.5 更新用户信息

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-005 |
| 接口路径 | `/user/update` |
| Method | PUT |
| 功能描述 | 更新用户基本信息 |
| 前端来源 | `ProfileEditPage.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| nickname | string | 是 | 昵称 | `旅行者` |
| avatar | string | 否 | 头像URL | `https://...` |

---

#### 2.1.6 退出登录

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-006 |
| 接口路径 | `/user/logout` |
| Method | POST |
| 功能描述 | 用户退出登录 |
| 前端来源 | `ProfilePage.vue`, `SettingsPage.vue` |
| 是否需要认证 | 是 |

**返回示例：**
```json
{
  "code": 200,
  "message": "退出成功",
  "data": null
}
```

---

#### 2.1.7 第三方登录

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-007 |
| 接口路径 | `/user/thirdParty/login` |
| Method | POST |
| 功能描述 | 第三方授权登录 |
| 前端来源 | `LoginPage.vue` |
| 是否需要认证 | 否 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| platform | string | 是 | 平台类型（wechat/apple） | `wechat` |
| code | string | 是 | 授权code | `xxxxx` |

---

### 2.2 首页模块

#### 2.2.1 获取首页数据

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-008 |
| 接口路径 | `/home/index` |
| Method | GET |
| 功能描述 | 获取首页全部数据（Banner+分类+热门目的地+限时特惠+游记种草） |
| 前端来源 | `HomePage.vue` |
| 是否需要认证 | 否 |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.banners | array | 轮播图列表 |
| data.categories | array | 分类列表 |
| data.hotDestinations | array | 热门目的地 |
| data.flashDeals | array | 限时特惠 |
| data.travelNotes | array | 游记种草 |

**Banner对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | Banner ID |
| image | string | 图片URL |
| title | string | 标题 |
| subtitle | string | 副标题 |
| link | string | 跳转链接 |

**分类对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | 分类ID |
| icon | string | 图标 |
| name | string | 分类名称 |

**目的地对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | 目的地ID |
| name | string | 名称 |
| image | string | 图片URL |
| desc | string | 描述 |
| rating | float | 评分 |

**特惠对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | 产品ID |
| name | string | 产品名称 |
| original | float | 原价 |
| price | float | 现价 |
| sold | int | 已售数量 |
| image | string | 产品图片 |
| endTime | string | 结束时间 |

**游记对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | 游记ID |
| user | string | 作者名称 |
| avatar | string | 作者头像 |
| dest | string | 目的地 |
| image | string | 封面图 |
| likes | int | 点赞数 |
| content | string | 内容摘要 |

---

#### 2.2.2 获取城市列表

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-009 |
| 接口路径 | `/home/cities` |
| Method | GET |
| 功能描述 | 获取支持的城市列表 |
| 前端来源 | `HomePage.vue` |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data[].id | int | 城市ID |
| data[].name | string | 城市名称 |
| data[].pinyin | string | 拼音 |

---

### 2.3 目的地模块

#### 2.3.1 目的地列表

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-010 |
| 接口路径 | `/destination/list` |
| Method | GET |
| 功能描述 | 获取目的地列表（支持分类筛选和搜索） |
| 前端来源 | `DestinationPage.vue` |
| 是否需要认证 | 否 |

**请求参数（Query）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| keyword | string | 否 | 搜索关键词 | `丽江` |
| category | string | 否 | 分类筛选 | `hot` |
| page | int | 否 | 页码（默认1） | `1` |
| pageSize | int | 否 | 每页数量（默认10） | `10` |

**category可选值：** `hot`（热门）/ `scenic`（景点）/ `hotel`（酒店）/ `food`（美食）/ `activity`（活动）

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.list | array | 目的地列表 |
| data.pagination | object | 分页信息 |
| data.pagination.total | int | 总记录数 |
| data.pagination.page | int | 当前页 |
| data.pagination.pageSize | int | 每页数量 |

**目的地对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | 目的地ID |
| name | string | 目的地名称 |
| image | string | 图片URL |
| desc | string | 简介描述 |
| rating | float | 评分 |
| heat | int | 热度值 |

---

#### 2.3.2 目的地详情

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-011 |
| 接口路径 | `/destination/detail/:id` |
| Method | GET |
| 功能描述 | 获取目的地详细信息（含景点和酒店列表） |
| 前端来源 | `DestinationDetailPage.vue` |
| 是否需要认证 | 否 |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.id | int | 目的地ID |
| data.name | string | 名称 |
| data.image | string | 主图 |
| data.rating | float | 评分 |
| data.desc | string | 详细介绍 |
| data.location | string | 地址 |
| data.openTime | string | 开放时间 |
| data.tags | array | 标签列表 |
| data.attractions | array | 附近景点 |
| data.hotels | array | 附近酒店 |

**景点对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | 景点ID |
| name | string | 景点名称 |
| image | string | 图片 |
| desc | string | 简介 |
| price | float | 门票价格（0为免费） |

**酒店对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | 酒店ID |
| name | string | 酒店名称 |
| image | string | 图片 |
| rating | float | 评分 |
| price | float | 起价 |
| desc | string | 类型描述 |

---

### 2.4 商品模块

#### 2.4.1 产品详情

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-012 |
| 接口路径 | `/product/detail/:id` |
| Method | GET |
| 功能描述 | 获取产品详细信息（含价格日历和票种） |
| 前端来源 | `ProductDetailPage.vue` |
| 是否需要认证 | 否 |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.id | int | 产品ID |
| data.name | string | 产品名称 |
| data.images | array | 图片列表 |
| data.price | float | 当前价格 |
| data.originalPrice | float | 原价 |
| data.desc | string | 产品描述 |
| data.includes | array | 费用包含 |
| data.rules | array | 退改规则 |
| data.openTime | string | 开放时间 |
| data.dates | array | 可预约日期 |
| data.tickets | array | 票种列表 |

**日期对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| date | string | 日期（YYYY-MM-DD） |
| day | string | 星期几 |
| available | boolean | 是否可预约 |

**票种对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| type | string | 票种类型标识 |
| name | string | 票种名称 |
| price | float | 价格 |

---

#### 2.4.2 产品列表（按分类）

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-013 |
| 接口路径 | `/product/list` |
| Method | GET |
| 功能描述 | 获取产品列表（支持分类筛选） |
| 前端来源 | `HomePage.vue` |

**请求参数（Query）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| category | string | 否 | 产品分类 | `ticket` |
| keyword | string | 否 | 搜索关键词 | `古城` |
| sort | string | 否 | 排序（price_asc/price_desc/sales） | `sales` |
| page | int | 否 | 页码 | `1` |
| pageSize | int | 否 | 每页数量 | `10` |

**category可选值：** `hotel`（酒店民宿）/ `ticket`（景点门票）/ `group`（跟团游）/ `custom`（定制游）/ `around`（周边游）

---

### 2.5 订单模块

#### 2.5.1 优惠券列表

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-014 |
| 接口路径 | `/coupon/list` |
| Method | GET |
| 功能描述 | 获取用户可用优惠券列表 |
| 前端来源 | `OrderConfirmPage.vue` |
| 是否需要认证 | 是 |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data[].id | int | 优惠券ID |
| data[].name | string | 优惠券名称 |
| data[].amount | float | 优惠金额 |
| data[].condition | string | 使用条件（如"满50可用"） |
| data[].expireTime | string | 过期时间 |

---

#### 2.5.2 领取优惠券

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-015 |
| 接口路径 | `/coupon/receive` |
| Method | POST |
| 功能描述 | 领取优惠券 |
| 前端来源 | `OrderConfirmPage.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| couponId | int | 是 | 优惠券ID | `1` |

---

#### 2.5.3 提交订单

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-016 |
| 接口路径 | `/order/create` |
| Method | POST |
| 功能描述 | 创建新订单 |
| 前端来源 | `OrderConfirmPage.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| productId | int | 是 | 产品ID | `1` |
| productName | string | 是 | 产品名称 | `丽江古城木府门票` |
| date | string | 是 | 游玩日期 | `2026-06-01` |
| ticketType | string | 是 | 票种类型 | `adult` |
| quantity | int | 是 | 数量 | `2` |
| price | float | 是 | 单价 | `40` |
| totalAmount | float | 是 | 总金额 | `80` |
| couponId | int | 否 | 使用的优惠券ID | `1` |
| contactName | string | 是 | 联系人姓名 | `张三` |
| contactPhone | string | 是 | 联系人电话 | `13800000000` |
| remark | string | 否 | 订单备注 | `无特殊需求` |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.id | string | 订单号 |
| data.totalAmount | float | 订单金额 |
| data.status | string | 订单状态 |
| data.createTime | string | 创建时间 |

---

#### 2.5.4 订单列表

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-017 |
| 接口路径 | `/order/list` |
| Method | GET |
| 功能描述 | 获取用户订单列表（支持状态筛选） |
| 前端来源 | `OrderListPage.vue` |
| 是否需要认证 | 是 |

**请求参数（Query）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| status | string | 否 | 订单状态筛选 | `pending` |
| page | int | 否 | 页码 | `1` |
| pageSize | int | 否 | 每页数量 | `10` |

**status可选值：** `all`（全部）/ `pending`（待付款）/ `paid`（已支付）/ `completed`（已完成）/ `refund`（退款）

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.list | array | 订单列表 |
| data.pagination | object | 分页信息 |

**订单对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | string | 订单号 |
| productName | string | 产品名称 |
| date | string | 游玩日期 |
| ticket | string | 票种 |
| quantity | int | 数量 |
| total | float | 总金额 |
| status | string | 订单状态 |
| createTime | string | 下单时间 |
| contact | object | 联系人信息 |

---

#### 2.5.5 订单详情

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-018 |
| 接口路径 | `/order/detail/:id` |
| Method | GET |
| 功能描述 | 获取订单详细信息（含电子凭证） |
| 前端来源 | `OrderDetailPage.vue` |
| 是否需要认证 | 是 |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.id | string | 订单号 |
| data.status | string | 订单状态 |
| data.productName | string | 产品名称 |
| data.date | string | 游玩日期 |
| data.ticket | string | 票种 |
| data.quantity | int | 数量 |
| data.total | float | 订单金额 |
| data.contact | object | 联系人信息 |
| data.payMethod | string | 支付方式 |
| data.payTime | string | 支付时间 |
| data.createTime | string | 下单时间 |
| data.voucherCode | string | 电子凭证码 |

---

#### 2.5.6 订单支付

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-019 |
| 接口路径 | `/order/pay` |
| Method | POST |
| 功能描述 | 发起订单支付 |
| 前端来源 | `OrderDetailPage.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| orderId | string | 是 | 订单号 | `ORD20260528001` |
| payMethod | string | 是 | 支付方式 | `wechat` |

**payMethod可选值：** `wechat`（微信支付）/ `alipay`（支付宝）

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.orderId | string | 订单号 |
| data.payUrl | string | 支付跳转链接或支付参数 |
| data.qrCode | string | 支付二维码（扫码支付时） |

---

#### 2.5.7 取消订单

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-020 |
| 接口路径 | `/order/cancel/:id` |
| Method | PUT |
| 功能描述 | 取消未支付订单 |
| 前端来源 | `OrderListPage.vue` |
| 是否需要认证 | 是 |

---

#### 2.5.8 申请退款

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-021 |
| 接口路径 | `/order/refund/:id` |
| Method | POST |
| 功能描述 | 申请订单退款 |
| 前端来源 | `OrderDetailPage.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| reason | string | 是 | 退款原因 | `计划变更` |

---

### 2.6 攻略模块

#### 2.6.1 攻略列表

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-022 |
| 接口路径 | `/strategy/list` |
| Method | GET |
| 功能描述 | 获取攻略文章列表 |
| 前端来源 | `StrategyPage.vue` |
| 是否需要认证 | 否 |

**请求参数（Query）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| tab | string | 否 | 标签筛选（recommend/follow/dest） | `recommend` |
| keyword | string | 否 | 搜索关键词 | `丽江` |
| page | int | 否 | 页码 | `1` |
| pageSize | int | 否 | 每页数量 | `10` |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data[].id | int | 攻略ID |
| data[].title | string | 文章标题 |
| data[].author | string | 作者名称 |
| data[].avatar | string | 作者头像 |
| data[].dest | string | 目的地 |
| data[].cover | string | 封面图 |
| data[].likes | int | 点赞数 |
| data[].comments | int | 评论数 |
| data[].tags | array | 标签列表 |

---

#### 2.6.2 攻略详情

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-023 |
| 接口路径 | `/strategy/detail/:id` |
| Method | GET |
| 功能描述 | 获取攻略文章详情 |
| 前端来源 | `StrategyDetailPage.vue` |
| 是否需要认证 | 否 |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.id | int | 攻略ID |
| data.title | string | 标题 |
| data.author | string | 作者 |
| data.avatar | string | 作者头像 |
| data.dest | string | 目的地 |
| data.cover | string | 封面图 |
| data.likes | int | 点赞数 |
| data.comments | int | 评论数 |
| data.publishTime | string | 发布时间 |
| data.content | string | 文章内容（Markdown） |
| data.tags | array | 标签列表 |

---

#### 2.6.3 攻略评论列表

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-024 |
| 接口路径 | `/strategy/comments/:id` |
| Method | GET |
| 功能描述 | 获取攻略评论列表 |
| 前端来源 | `StrategyDetailPage.vue` |

**请求参数（Query）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| page | int | 否 | 页码 | `1` |
| pageSize | int | 否 | 每页数量 | `20` |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data[].id | int | 评论ID |
| data[].author | string | 评论者 |
| data[].avatar | string | 头像 |
| data[].text | string | 评论内容 |
| data[].time | string | 评论时间 |

---

#### 2.6.4 发布攻略

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-025 |
| 接口路径 | `/strategy/publish` |
| Method | POST |
| 功能描述 | 发布新攻略文章 |
| 前端来源 | `PublishPage.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| title | string | 是 | 文章标题 | `丽江三日游攻略` |
| content | string | 是 | 文章内容 | `...` |
| dest | string | 是 | 目的地 | `丽江` |
| tags | array | 否 | 标签列表 | `["美食","景点"]` |
| images | array | 否 | 图片URL列表 | `["http://..."]` |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.id | int | 新文章ID |
| data.createTime | string | 发布时间 |

---

#### 2.6.5 攻略点赞

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-026 |
| 接口路径 | `/strategy/like/:id` |
| Method | POST |
| 功能描述 | 点赞攻略文章 |
| 前端来源 | `StrategyDetailPage.vue` |
| 是否需要认证 | 是 |

---

#### 2.6.6 添加攻略评论

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-027 |
| 接口路径 | `/strategy/comment/:id` |
| Method | POST |
| 功能描述 | 对攻略文章发表评论 |
| 前端来源 | `StrategyDetailPage.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| text | string | 是 | 评论内容 | `写得真好！` |

---

### 2.7 收藏模块

#### 2.7.1 收藏列表

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-028 |
| 接口路径 | `/favorite/list` |
| Method | GET |
| 功能描述 | 获取用户收藏列表 |
| 前端来源 | `ProfilePage.vue` |
| 是否需要认证 | 是 |

**请求参数（Query）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| type | string | 否 | 收藏类型（product/strategy/destination） | `product` |
| page | int | 否 | 页码 | `1` |
| pageSize | int | 否 | 每页数量 | `10` |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data[].id | int | 收藏ID |
| data[].type | string | 收藏类型 |
| data[].targetId | int | 收藏对象ID |
| data[].targetName | string | 收藏对象名称 |
| data[].targetImage | string | 收藏对象图片 |
| data[].createTime | string | 收藏时间 |

---

#### 2.7.2 添加收藏

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-029 |
| 接口路径 | `/favorite/add` |
| Method | POST |
| 功能描述 | 添加收藏 |
| 前端来源 | `StrategyDetailPage.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| type | string | 是 | 收藏类型 | `strategy` |
| targetId | int | 是 | 收藏对象ID | `1` |

---

#### 2.7.3 取消收藏

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-030 |
| 接口路径 | `/favorite/remove` |
| Method | DELETE |
| 功能描述 | 取消收藏 |
| 前端来源 | `StrategyDetailPage.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| type | string | 是 | 收藏类型 | `strategy` |
| targetId | int | 是 | 收藏对象ID | `1` |

---

### 2.8 消息通知模块

#### 2.8.1 消息列表

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-031 |
| 接口路径 | `/message/list` |
| Method | GET |
| 功能描述 | 获取用户消息通知列表 |
| 前端来源 | `HomePage.vue` |
| 是否需要认证 | 是 |

**请求参数（Query）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| type | string | 否 | 消息类型（order/activity/system） | `order` |
| page | int | 否 | 页码 | `1` |
| pageSize | int | 否 | 每页数量 | `10` |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data[].id | int | 消息ID |
| data[].type | string | 消息类型 |
| data[].title | string | 消息标题 |
| data[].content | string | 消息内容 |
| data[].read | boolean | 是否已读 |
| data[].createTime | string | 发布时间 |

---

#### 2.8.2 标记消息已读

| 项目 | 内容 |
|------|------|
| 接口编号 | API-H5-032 |
| 接口路径 | `/message/read` |
| Method | PUT |
| 功能描述 | 标记消息为已读 |
| 前端来源 | `HomePage.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| messageId | int | 否 | 消息ID（不传则标记全部） | `1` |

---

---

## 三、B端API（Admin管理后台 - travel-web-admin）

### 3.1 认证模块

#### 3.1.1 管理员登录

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-001 |
| 接口路径 | `/admin/login` |
| Method | POST |
| 功能描述 | 后台管理员登录 |
| 前端来源 | `Login.vue` |
| 是否需要认证 | 否 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| username | string | 是 | 用户名 | `admin` |
| password | string | 是 | 密码 | `123456` |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.token | string | 认证Token |
| data.userInfo | object | 管理员信息 |
| data.userInfo.id | string | 管理员ID |
| data.userInfo.username | string | 用户名 |
| data.userInfo.nickname | string | 昵称 |
| data.userInfo.role | string | 角色（admin/editor） |

---

#### 3.1.2 管理员登出

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-002 |
| 接口路径 | `/admin/logout` |
| Method | POST |
| 功能描述 | 后台管理员退出 |
| 前端来源 | `Layout.vue` |
| 是否需要认证 | 是 |

---

#### 3.1.3 获取管理员信息

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-003 |
| 接口路径 | `/admin/info` |
| Method | GET |
| 功能描述 | 获取当前登录管理员信息 |
| 前端来源 | `Layout.vue` |
| 是否需要认证 | 是 |

---

### 3.2 数据统计模块

#### 3.2.1 统计概览

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-004 |
| 接口路径 | `/admin/stats/overview` |
| Method | GET |
| 功能描述 | 获取后台首页统计数据 |
| 前端来源 | `Dashboard.vue` |
| 是否需要认证 | 是 |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.totalUsers | int | 用户总数 |
| data.totalProducts | int | 产品总数 |
| data.totalOrders | int | 订单总数 |
| data.todayOrders | int | 今日订单数 |
| data.userIncrease | string | 用户增长百分比 |
| data.productIncrease | string | 产品增长百分比 |
| data.orderIncrease | string | 订单增长百分比 |

---

#### 3.2.2 订单趋势数据

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-005 |
| 接口路径 | `/admin/stats/orderTrend` |
| Method | GET |
| 功能描述 | 获取订单趋势图表数据 |
| 前端来源 | `Dashboard.vue` |
| 是否需要认证 | 是 |

**请求参数（Query）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| days | int | 否 | 查询天数（默认7） | `7` |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.labels | array | X轴标签（如["周一","周二"...]) |
| data.values | array | 订单量数据 |

---

#### 3.2.3 产品分类占比

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-006 |
| 接口路径 | `/admin/stats/productCategory` |
| Method | GET |
| 功能描述 | 获取产品分类占比数据 |
| 前端来源 | `Dashboard.vue` |
| 是否需要认证 | 是 |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data[].name | string | 分类名称 |
| data[].value | int | 数量 |

---

#### 3.2.4 最新订单列表

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-007 |
| 接口路径 | `/admin/stats/recentOrders` |
| Method | GET |
| 功能描述 | 获取最新订单列表（后台首页展示） |
| 前端来源 | `Dashboard.vue` |
| 是否需要认证 | 是 |

**请求参数（Query）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| limit | int | 否 | 返回数量（默认5） | `5` |

---

### 3.3 用户管理模块

#### 3.3.1 用户列表

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-008 |
| 接口路径 | `/admin/user/list` |
| Method | GET |
| 功能描述 | 获取用户列表（支持搜索和分页） |
| 前端来源 | `UserList.vue` |
| 是否需要认证 | 是 |

**请求参数（Query）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| keyword | string | 否 | 搜索关键词（用户名/昵称/手机号） | `张三` |
| startDate | string | 否 | 注册开始日期 | `2026-01-01` |
| endDate | string | 否 | 注册结束日期 | `2026-05-28` |
| page | int | 否 | 页码 | `1` |
| pageSize | int | 否 | 每页数量 | `10` |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.list | array | 用户列表 |
| data.pagination | object | 分页信息 |

**用户对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | 用户ID |
| username | string | 用户名 |
| nickname | string | 昵称 |
| phone | string | 手机号（脱敏） |
| email | string | 邮箱 |
| status | int | 状态（1:正常/0:禁用） |
| createTime | string | 注册时间 |

---

#### 3.3.2 新增用户

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-009 |
| 接口路径 | `/admin/user/create` |
| Method | POST |
| 功能描述 | 新增用户账号 |
| 前端来源 | `UserList.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| username | string | 是 | 用户名 | `testuser` |
| nickname | string | 是 | 昵称 | `测试用户` |
| phone | string | 是 | 手机号 | `13800000000` |
| email | string | 是 | 邮箱 | `test@example.com` |
| password | string | 否 | 初始密码（可不传自动生成） | `123456` |

---

#### 3.3.3 编辑用户

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-010 |
| 接口路径 | `/admin/user/update/:id` |
| Method | PUT |
| 功能描述 | 编辑用户信息 |
| 前端来源 | `UserList.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| nickname | string | 是 | 昵称 | `新昵称` |
| phone | string | 是 | 手机号 | `13800000000` |
| email | string | 是 | 邮箱 | `new@example.com` |
| status | int | 是 | 状态（1:正常/0:禁用） | `1` |

---

#### 3.3.4 删除用户

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-011 |
| 接口路径 | `/admin/user/delete/:id` |
| Method | DELETE |
| 功能描述 | 删除用户账号 |
| 前端来源 | `UserList.vue` |
| 是否需要认证 | 是（仅admin角色） |

---

#### 3.3.5 更新用户状态

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-012 |
| 接口路径 | `/admin/user/status/:id` |
| Method | PUT |
| 功能描述 | 启用/禁用用户账号 |
| 前端来源 | `UserList.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| status | int | 是 | 状态（1:启用/0:禁用） | `0` |

---

### 3.4 产品管理模块

#### 3.4.1 产品列表

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-013 |
| 接口路径 | `/admin/product/list` |
| Method | GET |
| 功能描述 | 获取产品列表（支持搜索、筛选和分页） |
| 前端来源 | `ProductList.vue` |
| 是否需要认证 | 是 |

**请求参数（Query）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| keyword | string | 否 | 搜索关键词 | `丽江` |
| category | string | 否 | 产品分类 | `hotel` |
| status | int | 否 | 上下架状态（1:上架/0:下架） | `1` |
| page | int | 否 | 页码 | `1` |
| pageSize | int | 否 | 每页数量 | `10` |

**category可选值：** `hotel`（酒店民宿）/ `ticket`（景点门票）/ `group`（跟团游）/ `custom`（定制游）/ `around`（周边游）

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.list | array | 产品列表 |
| data.pagination | object | 分页信息 |

**产品对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | 产品ID |
| name | string | 产品名称 |
| category | string | 产品分类 |
| price | float | 现价 |
| originalPrice | float | 原价 |
| sales | int | 销量 |
| status | int | 状态（1:上架/0:下架） |
| createTime | string | 创建时间 |

---

#### 3.4.2 新增产品

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-014 |
| 接口路径 | `/admin/product/create` |
| Method | POST |
| 功能描述 | 新增产品 |
| 前端来源 | `ProductList.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| name | string | 是 | 产品名称 | `丽江古城木府门票` |
| category | string | 是 | 产品分类 | `ticket` |
| price | float | 是 | 现价 | `40` |
| originalPrice | float | 否 | 原价 | `60` |
| desc | string | 否 | 产品描述 | `...` |
| images | array | 否 | 产品图片URL列表 | `["http://..."]` |
| includes | array | 否 | 费用包含 | `["门票","讲解"]` |
| rules | array | 否 | 退改规则 | `["未使用可退"]` |
| status | int | 否 | 上下架状态（默认1上架） | `1` |

---

#### 3.4.3 编辑产品

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-015 |
| 接口路径 | `/admin/product/update/:id` |
| Method | PUT |
| 功能描述 | 编辑产品信息 |
| 前端来源 | `ProductList.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| name | string | 是 | 产品名称 | `...` |
| category | string | 是 | 产品分类 | `...` |
| price | float | 是 | 现价 | `...` |
| originalPrice | float | 否 | 原价 | `...` |
| desc | string | 否 | 产品描述 | `...` |
| images | array | 否 | 产品图片 | `...` |
| includes | array | 否 | 费用包含 | `...` |
| rules | array | 否 | 退改规则 | `...` |
| status | int | 是 | 上下架状态 | `1` |

---

#### 3.4.4 产品上下架

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-016 |
| 接口路径 | `/admin/product/status/:id` |
| Method | PUT |
| 功能描述 | 上架/下架产品 |
| 前端来源 | `ProductList.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| status | int | 是 | 状态（1:上架/0:下架） | `0` |

---

#### 3.4.5 删除产品

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-017 |
| 接口路径 | `/admin/product/delete/:id` |
| Method | DELETE |
| 功能描述 | 删除产品 |
| 前端来源 | `ProductList.vue` |
| 是否需要认证 | 是（仅admin角色） |

---

### 3.5 订单管理模块

#### 3.5.1 订单列表

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-018 |
| 接口路径 | `/admin/order/list` |
| Method | GET |
| 功能描述 | 获取订单列表（支持搜索、状态筛选和分页） |
| 前端来源 | `OrderList.vue` |
| 是否需要认证 | 是 |

**请求参数（Query）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| keyword | string | 否 | 搜索关键词（订单号/用户/产品） | `ORD20260528` |
| status | string | 否 | 订单状态 | `pending` |
| startDate | string | 否 | 开始日期 | `2026-05-01` |
| endDate | string | 否 | 结束日期 | `2026-05-28` |
| page | int | 否 | 页码 | `1` |
| pageSize | int | 否 | 每页数量 | `10` |

**status可选值：** `pending`（待支付）/ `paid`（已支付）/ `processing`（进行中）/ `completed`（已完成）/ `cancelled`（已取消）/ `refunded`（已退款）

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.list | array | 订单列表 |
| data.pagination | object | 分页信息 |

**订单对象结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | string | 订单号 |
| user | string | 用户名称 |
| phone | string | 用户手机号 |
| product | string | 产品名称 |
| amount | float | 订单金额 |
| status | string | 订单状态 |
| payMethod | string | 支付方式 |
| createTime | string | 下单时间 |

---

#### 3.5.2 订单详情

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-019 |
| 接口路径 | `/admin/order/detail/:id` |
| Method | GET |
| 功能描述 | 获取订单详细信息 |
| 前端来源 | `OrderList.vue` |
| 是否需要认证 | 是 |

**返回字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| data.id | string | 订单号 |
| data.user | string | 用户名称 |
| data.phone | string | 手机号 |
| data.product | string | 产品名称 |
| data.date | string | 游玩日期 |
| data.ticketType | string | 票种 |
| data.quantity | int | 数量 |
| data.amount | float | 订单金额 |
| data.couponAmount | float | 优惠金额 |
| data.payAmount | float | 实付金额 |
| data.status | string | 订单状态 |
| data.payMethod | string | 支付方式 |
| data.payTime | string | 支付时间 |
| data.createTime | string | 下单时间 |
| data.contact | object | 联系人信息 |
| data.contact.name | string | 联系人姓名 |
| data.contact.phone | string | 联系人电话 |
| data.remark | string | 订单备注 |

---

#### 3.5.3 更新订单状态

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-020 |
| 接口路径 | `/admin/order/status/:id` |
| Method | PUT |
| 功能描述 | 管理员手动更新订单状态 |
| 前端来源 | `OrderList.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| status | string | 是 | 目标状态 | `completed` |
| remark | string | 否 | 状态变更备注 | `已核销使用` |

---

#### 3.5.4 处理退款申请

| 项目 | 内容 |
|------|------|
| 接口编号 | API-ADMIN-021 |
| 接口路径 | `/admin/order/refund/:id` |
| Method | POST |
| 功能描述 | 审核处理退款申请 |
| 前端来源 | `OrderList.vue` |
| 是否需要认证 | 是 |

**请求参数（Body）：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| action | string | 是 | 处理动作（approve/refuse） | `approve` |
| reason | string | 否 | 拒绝原因（拒绝时必填） | `不符合退款条件` |

---

---

## 四、附录

### 4.1 产品分类枚举

| 值 | 说明 |
|------|------|
| hotel | 酒店民宿 |
| ticket | 景点门票 |
| group | 跟团游 |
| custom | 定制游 |
| around | 周边游 |

### 4.2 订单状态枚举

| 值 | 中文 | 说明 |
|------|------|------|
| pending | 待支付 | 订单创建未付款 |
| paid | 已支付 | 已付款等待使用 |
| processing | 进行中 | 使用中 |
| completed | 已完成 | 已使用完成 |
| cancelled | 已取消 | 用户取消/超时取消 |
| refunded | 已退款 | 已退款 |

### 4.3 用户状态枚举

| 值 | 中文 | 说明 |
|------|------|------|
| 1 | 正常 | 用户状态正常 |
| 0 | 禁用 | 用户被禁用 |

### 4.4 收藏类型枚举

| 值 | 说明 |
|------|------|
| product | 产品收藏 |
| strategy | 攻略收藏 |
| destination | 目的地收藏 |

### 4.5 管理员角色枚举

| 值 | 中文 | 权限说明 |
|------|------|----------|
| admin | 超级管理员 | 全部权限 |
| editor | 运营人员 | 编辑权限，无删除用户/产品权限 |

---

## 文档维护

- **创建人**：
- **创建时间**：2026-05-28
- **最后更新**：
- **版本**：V1.0.0

---

*本契约清单根据前端代码分析生成，后续需根据实际后端实现进行调整和补充。*
