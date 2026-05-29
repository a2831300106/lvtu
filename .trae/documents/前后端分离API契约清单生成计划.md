# 前后端分离API契约清单生成计划

## 任务目标
分析现有前端项目（travel-web-h5 C端商城 + travel-web-admin B端管理后台），生成完整的API契约清单文档。

## 分析范围

### 1. C端商城 (travel-web-h5)
技术栈：Vue3 + Vite，所有请求以 `/api/` 开头

**已扫描页面（共16个）：**
- SplashPage.vue - 启动页
- GuidePage.vue - 引导页
- LoginPage.vue - 登录/注册
- HomePage.vue - 首页
- DestinationPage.vue - 目的地列表
- DestinationDetailPage.vue - 目的地详情
- ProductDetailPage.vue - 产品详情
- OrderConfirmPage.vue - 订单确认
- OrderListPage.vue - 订单列表
- OrderDetailPage.vue - 订单详情
- ProfilePage.vue - 个人中心
- ProfileEditPage.vue - 资料编辑
- SettingsPage.vue - 设置页
- StrategyPage.vue - 攻略列表
- StrategyDetailPage.vue - 攻略详情
- PublishPage.vue - 发布游记

**当前状态：** 所有数据均为本地Mock（localStorage），无实际API调用

### 2. B端管理后台 (travel-web-admin)
技术栈：Vue3 + Vite + Element Plus + Pinia，axios baseURL: `/api`

**已扫描页面/模块（共5个）：**
- Login.vue - 后台登录
- Dashboard.vue - 数据概览
- UserList.vue - 用户管理
- ProductList.vue - 产品管理
- OrderList.vue - 订单管理

**当前状态：** 所有数据均为本地Mock，但已配置axios拦截器（含Token认证）

## 需要生成的API契约清单结构

### 契约清单字段说明
| 字段 | 说明 | 示例 |
|------|------|------|
| 接口编号 | 唯一标识 | API-H5-001 |
| 接口路径 | URL路径（不含baseURL） | /user/login |
| Method | HTTP方法 | GET/POST/PUT/DELETE |
| 功能描述 | 接口业务说明 | 用户登录 |
| 所属模块 | 模块分类 | 用户/商品/订单/攻略/后台 |
| 前端来源 | 调用的Vue组件 | LoginPage.vue |
| 请求参数 | Query/Body参数 | phone, code |
| 请求示例 | 典型请求数据 | {"phone": "13800000000", "code": "123456"} |
| 返回字段 | 响应数据结构 | code, message, data{userId, token} |
| 返回示例 | 典型响应数据 | {"code": 200, "message": "success", "data": {...}} |

### 模块分类

**C端模块（H5）：**
1. **用户模块** - 登录、注册、个人信息
2. **首页模块** - Banner、分类、热门目的地
3. **目的地模块** - 目的地列表、详情、搜索
4. **商品模块** - 产品详情、日期选择、票种
5. **订单模块** - 订单确认、提交、列表、详情、支付
6. **攻略模块** - 攻略列表、详情、评论、发布
7. **优惠券模块** - 优惠券列表、领取、使用
8. **收藏模块** - 收藏列表、添加、取消

**B端模块（Admin）：**
1. **认证模块** - 后台登录、Token刷新、登出
2. **数据统计模块** - 统计概览、趋势图
3. **用户管理模块** - 用户列表、编辑、删除、状态
4. **产品管理模块** - 产品列表、新增、编辑、上下架、删除
5. **订单管理模块** - 订单列表、详情、状态变更

## 输出文档结构

```
# 前后端分离API契约清单

## 一、文档说明
- 版本信息
- 更新日志
- 基础信息（BaseURL、环境配置）

## 二、C端API（H5）
### 2.1 用户模块
### 2.2 首页模块
### 2.3 目的地模块
### 2.4 商品模块
### 2.5 订单模块
### 2.6 攻略模块
### 2.7 优惠券模块
### 2.8 收藏模块

## 三、B端API（Admin）
### 3.1 认证模块
### 3.2 数据统计模块
### 3.3 用户管理模块
### 3.4 产品管理模块
### 3.5 订单管理模块

## 四、公共规范
- 统一响应格式
- 错误码说明
- 认证方式
```

## 实施步骤

1. ✅ **项目扫描** - 完成
2. ⏳ **提取接口契约** - 根据页面功能和数据结构推断API需求
3. ⏳ **生成契约文档** - 输出完整的API契约清单Markdown
4. ⏳ **验证完整性** - 确保覆盖所有前端功能

## 输出文件
`d:\Trae\Projects\lvtu\前后端分离API契约清单.md`
