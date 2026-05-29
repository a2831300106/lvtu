# 旅图旅行平台

一个完整的旅游电商平台，包含C端用户商城和B端管理后台，采用前后端分离架构。

## 项目简介

旅图旅行平台是一个集旅游产品预订系统，支持用户浏览、购买旅游产品，发布和查看旅游攻略，以及管理后台的产品、订单、用户管理等功能。

## 技术栈

### 前端
- **Vue 3.5+
- **Vite 8.0+
- **Vue Router 4.6+

### 后端
- **Spring Boot 3.2.0**
- **Java 17**
- **MyBatis-Plus 3.5.5**
- **MySQL 8.0+**
- **Redis 6.0+**
- **JWT (jjwt 0.12.3)**
- **Spring Security**

## 项目结构

```
lvtu/
├── travel-web-h5/          # C端用户商城
│   ├── src/
│   │   ├── pages/       # 页面组件
│   │   ├── router/       # 路由配置
│   │   └── stores/       # 状态管理
│   ├── package.json
│   └── vite.config.js
├── travel-web-admin/       # B端管理后台
│   ├── src/
│   │   ├── views/       # 页面组件
│   │   ├── router/       # 路由配置
│   │   ├── store/        # 状态管理
│   │   └── utils/       # 工具函数
│   ├── package.json
│   └── vite.config.js
├── travel-server/          # 后端服务
│   ├── src/main/
│   │   ├── java/com/lvtu/
│   │   │   ├── controller/  # RESTful API
│   │   │   ├── service/     # 业务逻辑
│   │   │   ├── mapper/      # MyBatis-Plus Mapper
│   │   │   ├── entity/     # 数据库实体
│   │   │   ├── dto/        # 数据传输对象
│   │   │   ├── vo/         # 视图对象
│   │   │   ├── common/    # 公共组件
│   │   │   ├── config/    # 配置类
│   │   │   └── security/  # 安全认证
│   │   └── resources/
│   │       ├── application.yml
│   │       └── mapper/
│   ├── sql/
│   │   └── init.sql    # 数据库初始化脚本
│   ├── DEPLOY.md     # 后端部署指南
│   └── pom.xml
├── 前后端分离API契约清单.md  # API接口文档
└── README.md
```

## 功能模块

### C端用户商城
- 用户注册/登录（手机号+验证码
- 首页（Banner、推荐产品、攻略
- 目的地列表/详情
- 产品列表/详情（SKU选择、日期选择
- 购物车管理（Redis缓存
- 订单流程（创建→支付→退款
- 优惠券领取/使用
- 旅游攻略（浏览、点赞、评论、发布
- 个人中心（我的收藏、我的订单、个人信息

### B端管理后台
- 管理员登录
- 数据统计概览
- 用户管理（增删改查
- 产品管理（增删改查、上下架
- 订单管理（查看、状态更新

## 快速开始

### 环境要求

| 依赖 | 版本 | 说明 |
|------|------|------|
| JDK | 17+ | 后端开发 |
| Maven | 3.8+ | 后端构建 |
| MySQL | 8.0+ | 数据库 |
| Redis | 6.0+ | 缓存服务 |
| Node.js | 20.19+ | 前端开发 |

### 后端启动

详细后端启动步骤请参考 [travel-server/DEPLOY.md](file:///d:/Trae/Projects/lvtu/travel-server/DEPLOY.md)

#### 1. 初始化数据库

```bash
# 创建数据库
CREATE DATABASE IF NOT EXISTS lvtu DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 导入数据
mysql -u root -p < travel-server/sql/init.sql
```

#### 2. 配置数据库连接

编辑 [travel-server/src/main/resources/application.yml](file:///d:/Trae/Projects/lvtu/travel-server/src/main/resources/application.yml)：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/lvtu
    username: root
    password: root
  data:
    redis:
      host: localhost
      port: 6379
```

#### 3. 启动后端服务

```bash
cd travel-server
mvn spring-boot:run
```

后端服务将在 `http://localhost:8080` 启动。

### 前端启动

#### C端用户商城

```bash
cd travel-web-h5
npm install
npm run dev
```

访问：`http://localhost:5173`

#### B端管理后台

```bash
cd travel-web-admin
npm install
npm run dev
```

访问：`http://localhost:5174`

## 默认账号

### C端测试用户
- 手机号：13800138000
- 验证码：123456

### B端管理员
- 用户名：admin
- 密码：admin123

## API文档

详细的API接口文档请查看 [前后端分离API契约清单.md](file:///d:/Trae/Projects/lvtu/前后端分离API契约清单.md)

### 接口总览

| 模块 | 路径前缀 | 说明 |
|------|----------|------|
| 首页 | /api/home | 首页数据 |
| 用户 | /api/user | 用户注册/登录/信息 |
| 产品 | /api/product | 产品列表/详情 |
| 购物车 | /api/cart | 购物车操作 |
| 订单 | /api/order | 订单管理 |
| 收藏 | /api/favorite | 收藏功能 |
| 攻略 | /api/strategy | 攻略浏览/发布 |
| 优惠券 | /api/coupon | 优惠券领取 |
| 后台 | /api/admin | 管理后台 |

## 部署指南

### 后端部署

参考 [travel-server/DEPLOY.md](file:///d:/Trae/Projects/lvtu/travel-server/DEPLOY.md)

### 前端部署

```bash
# C端打包
cd travel-web-h5
npm run build

# B端打包
cd travel-web-admin
npm run build
```

将 `dist` 目录部署到 Nginx 或其他 Web 服务器。

## 技术亮点

1. **前后端分离**：纯 RESTful API 设计，前后端完全解耦
2. **JWT 认证**：无状态 Token 登录，安全性高
3. **Redis 缓存**：购物车使用 Redis Hash 存储，性能优异
4. **MyBatis-Plus**：简化数据库操作
5. **统一响应格式**：统一 Result<T> 封装，便于前端统一处理
6. **全局异常处理**：统一异常捕获和返回
7. **CORS 跨域配置**：支持前后端分离开发
8. **三层架构**：Controller → Service → Mapper

## 开发规范

### 后端
- Controller 层：接收请求，参数校验，调用 Service
- Service 层：业务逻辑处理
- Mapper 层：数据库操作
- Entity：数据库实体
- DTO：数据传输对象（请求参数
- VO：视图对象（返回数据

### 前端
- pages/views：页面组件
- router：路由配置
- stores：状态管理
- utils：工具函数

## 常见问题

### 端口被占用
- 修改 [application.yml](file:///d:/Trae/Projects/lvtu/travel-server/src/main/resources/application.yml) 中的 `server.port`
- 或使用 `netstat -ano | findstr :8080` 查找并结束占用进程

### 数据库连接失败
- 确保 MySQL 服务已启动
- 检查用户名密码配置
- 确认 init.sql 是否已执行

### Redis 连接失败
- 确保 Redis 服务已启动
- 检查 Redis 配置

## 许可证

MIT License
