# 旅图旅行平台 - 后端部署与联调指南

## 目录结构

```
travel-server/
├── pom.xml                                     # Maven项目配置
├── sql/
│   └── init.sql                                # MySQL建表+初始化数据
├── src/main/java/com/lvtu/
│   ├── TravelServerApplication.java            # 启动类
│   ├── common/
│   │   ├── BaseEntity.java                     # 实体基类(id,createTime,updateTime,deleted)
│   │   ├── GlobalExceptionHandler.java         # 全局异常处理
│   │   ├── JwtUtils.java                       # JWT令牌工具
│   │   ├── PageResult.java                     # 分页结果封装
│   │   ├── Result.java                         # 统一API响应
│   │   └── SecurityUtils.java                  # 安全上下文工具
│   ├── config/
│   │   ├── MyMetaObjectHandler.java            # MyBatis-Plus自动填充
│   │   ├── RedisConfig.java                    # Redis配置
│   │   ├── SecurityConfig.java                 # SpringSecurity配置
│   │   └── WebMvcConfig.java                   # CORS跨域配置
│   ├── security/
│   │   └── JwtAuthFilter.java                  # JWT认证过滤器
│   ├── entity/                                 # 数据库实体（16个）
│   ├── dto/                                    # 数据传输对象（15个）
│   ├── vo/                                     # 视图对象（10个）
│   ├── mapper/                                 # MyBatis-Plus Mapper接口（16个）
│   ├── service/
│   │   ├── *.java                              # Service接口（11个）
│   │   └── impl/*.java                         # Service实现（11个）
│   └── controller/                             # RESTful Controller（11个）
└── src/main/resources/
    ├── application.yml                         # 应用配置
    └── mapper/*.xml                            # MyBatis XML映射（16个）
```

---

## SQL 执行顺序

### 第一步：创建数据库
```sql
CREATE DATABASE IF NOT EXISTS lvtu DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lvtu;
```

### 第二步：执行完整建表脚本
直接执行 `sql/init.sql` 文件中的所有内容。

### 第三步：验证初始化数据
```sql
-- 验证管理员
SELECT * FROM admin_user;           -- 应有1条admin记录
-- 验证分类
SELECT * FROM category;             -- 应有5条记录
-- 验证Banner
SELECT * FROM banner;               -- 应有3条Banner
-- 验证产品
SELECT * FROM product;              -- 应有3条产品
-- 验证SKU
SELECT * FROM product_sku;          -- 应有7条SKU
-- 验证日期
SELECT * FROM product_date;         -- 应有12条记录
-- 验证优惠券
SELECT * FROM coupon;               -- 应有2条优惠券
-- 验证攻略
SELECT * FROM strategy;             -- 应有3条攻略
```

---

## 后端启动步骤

### 环境要求
| 依赖 | 版本 | 说明 |
|------|------|------|
| JDK | 17+ | 必须Java 17及以上 |
| Maven | 3.8+ | 项目构建工具 |
| MySQL | 8.0+ | 数据库 |
| Redis | 6.0+ | 购物车缓存 |

### 启动步骤

#### 1. 启动基础设施
```bash
# Windows - 确保 MySQL 和 Redis 服务已启动
net start mysql
redis-server
```

#### 2. 创建数据库并导入数据
```bash
# 方式一: 使用MySQL命令行
mysql -u root -p < sql/init.sql

# 方式二: 使用IDE(DataGrip/MySQL Workbench)打开并执行sql/init.sql
```

#### 3. 修改数据库配置（如有需要）
编辑 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    username: root          # 修改为你的MySQL用户名
    password: root          # 修改为你的MySQL密码
  data:
    redis:
      host: localhost       # 修改为你的Redis地址
```

#### 4. 启动后端服务
```bash
# 进入项目目录
cd travel-server

# 方式一: Maven直接运行
mvn spring-boot:run

# 方式二: 打包后运行
mvn clean package -DskipTests
java -jar target/travel-server.jar

# 方式三: IDE中运行
# 在TravelServerApplication.java中点击Run
```

#### 5. 验证启动成功
```bash
curl http://localhost:8080/api/home/index
# 返回: {"code":200,"message":"success","data":{...}}
```

---

## API 调用总览

### C端API（无需认证）

| Method | 路径 | 说明 |
|--------|------|------|
| GET | /api/home/index | 首页数据 |
| GET | /api/destination/list | 目的地列表 |
| GET | /api/product/list | 产品列表 |
| GET | /api/product/detail/{id} | 产品详情 |
| GET | /api/strategy/list | 攻略列表 |
| GET | /api/strategy/detail/{id} | 攻略详情 |
| POST | /api/user/login | 用户登录 |
| POST | /api/user/register | 用户注册 |
| POST | /api/user/sendCode | 发送验证码 |

### C端API（需Token认证）

| Method | 路径 | 说明 |
|--------|------|------|
| GET | /api/user/info | 用户信息 |
| PUT | /api/user/update | 更新信息 |
| GET | /api/favorite/list | 收藏列表 |
| POST | /api/favorite/add | 添加收藏 |
| DELETE | /api/favorite/remove | 取消收藏 |
| GET/POST/PUT/DELETE | /api/cart/* | 购物车操作 |
| POST | /api/order/create | 创建订单 |
| GET | /api/order/list | 订单列表 |
| GET | /api/order/detail/{no} | 订单详情 |
| POST | /api/order/pay | 支付 |
| POST | /api/order/cancel/{id} | 取消 |
| POST | /api/order/refund/{id} | 退款 |
| GET | /api/coupon/list | 优惠券列表 |
| POST | /api/coupon/receive/{id} | 领取优惠券 |
| POST | /api/strategy/publish | 发布攻略 |
| POST | /api/strategy/like/{id} | 点赞 |
| POST | /api/strategy/comment/{id} | 评论 |

### B端API（需管理员Token）

| Method | 路径 | 说明 |
|--------|------|------|
| POST | /api/admin/login | 管理员登录 |
| GET | /api/admin/stats | 统计概览 |
| GET | /api/admin/user/list | 用户列表 |
| POST | /api/admin/user/create | 新增用户 |
| PUT | /api/admin/user/update/{id} | 编辑用户 |
| DELETE | /api/admin/user/delete/{id} | 删除用户 |
| GET | /api/admin/product/list | 产品列表 |
| POST | /api/admin/product/create | 新增产品 |
| PUT | /api/admin/product/update/{id} | 编辑产品 |
| PUT | /api/admin/product/status/{id} | 上下架 |
| DELETE | /api/admin/product/delete/{id} | 删除产品 |
| GET | /api/admin/order/list | 订单列表 |
| GET | /api/admin/order/detail/{id} | 订单详情 |
| PUT | /api/admin/order/status/{id} | 更新状态 |

---

## 前后端联调测试用例

### 测试用例1：用户登录 → 查看首页 → 产品详情

```bash
# Step 1: 获取首页数据（无需Token）
curl http://localhost:8080/api/home/index

# Step 2: 用户登录（验证码固定为123456）
curl -X POST http://localhost:8080/api/user/login \
  -H "Content-Type: application/json" \
  -d '{"phone":"13800138000","code":"123456"}'
# 返回结果中获取 token 值

# Step 3: 查看产品详情
curl http://localhost:8080/api/product/detail/1

# Step 4: 获取用户信息（需Token）
curl http://localhost:8080/api/user/info \
  -H "Authorization: Bearer <你的token>"
```

### 测试用例2：购物车 → 下单 → 支付 → 查看订单

```bash
# Step 1: 先登录获取Token
TOKEN="<上一步获取的token>"

# Step 2: 加入购物车
curl -X POST http://localhost:8080/api/cart/add \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"productId":1, "skuType":"adult", "quantity":2}'

# Step 3: 查看购物车
curl http://localhost:8080/api/cart/list \
  -H "Authorization: Bearer $TOKEN"

# Step 4: 创建订单
curl -X POST http://localhost:8080/api/order/create \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "productId":1,
    "productName":"丽江古城木府门票",
    "date":"2026-06-01",
    "ticketType":"adult",
    "quantity":2,
    "unitPrice":40.00,
    "contactName":"张三",
    "contactPhone":"13800138000"
  }'
# 返回结果中获取 orderNo

# Step 5: 支付订单
curl -X POST http://localhost:8080/api/order/pay \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"orderId":"<orderNo>", "payMethod":"wechat"}'

# Step 6: 查看订单详情
curl http://localhost:8080/api/order/detail/<orderNo> \
  -H "Authorization: Bearer $TOKEN"

# Step 7: 查看订单列表
curl "http://localhost:8080/api/order/list?status=paid&page=1&pageSize=10" \
  -H "Authorization: Bearer $TOKEN"
```

### 测试用例3：收藏 → 攻略

```bash
TOKEN="<登录token>"

# Step 1: 查看攻略列表
curl http://localhost:8080/api/strategy/list

# Step 2: 查看攻略详情
curl http://localhost:8080/api/strategy/detail/1

# Step 3: 点赞攻略
curl -X POST http://localhost:8080/api/strategy/like/1 \
  -H "Authorization: Bearer $TOKEN"

# Step 4: 评论攻略
curl -X POST http://localhost:8080/api/strategy/comment/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"content":"攻略写得真好，收藏了！"}'

# Step 5: 收藏攻略
curl -X POST http://localhost:8080/api/favorite/add \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"targetType":"strategy","targetId":1}'

# Step 6: 查看收藏列表
curl "http://localhost:8080/api/favorite/list?type=strategy" \
  -H "Authorization: Bearer $TOKEN"
```

### 测试用例4：管理员后台

```bash
# Step 1: 管理员登录
curl -X POST http://localhost:8080/api/admin/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
# 返回结果中获取 admin_token

ADMIN_TOKEN="<admin_token>"

# Step 2: 查看数据统计
curl http://localhost:8080/api/admin/stats \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Step 3: 用户列表
curl "http://localhost:8080/api/admin/user/list?page=1&pageSize=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Step 4: 产品列表
curl "http://localhost:8080/api/admin/product/list?page=1&pageSize=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Step 5: 订单列表
curl "http://localhost:8080/api/admin/order/list?page=1&pageSize=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Step 6: 新增产品
curl -X POST http://localhost:8080/api/admin/product/create \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -d '{
    "name":"杭州西湖游船",
    "categoryId":2,
    "description":"泛舟西湖，感受人间天堂",
    "price":80.00,
    "originalPrice":120.00,
    "skus":[
      {"name":"成人票","type":"adult","price":80.00,"stock":500},
      {"name":"儿童票","type":"child","price":40.00,"stock":200}
    ]
  }'

# Step 7: 修改订单状态
curl -X PUT http://localhost:8080/api/admin/order/status/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -d '{"status":"completed"}'
```

---

## Redis 数据结构说明

购物车使用 Redis Hash 存储：

```
Key: cart:{userId}
Fields:
  {productId}:{skuType}  →  {"productId":1,"skuType":"adult","quantity":2}
```

示例：
```
Key: cart:1
Fields:
  "1:adult" → {"productId":1,"skuType":"adult","quantity":2}
  "2:garden" → {"productId":2,"skuType":"garden","quantity":1}
```

---

## 统一响应格式

所有接口返回格式：
```json
{
  "code": 200,
  "message": "success",
  "data": { ... }
}
```

| code | 说明 |
|------|------|
| 200 | 成功 |
| 400 | 参数错误 |
| 401 | 未认证/Token过期 |
| 403 | 无权限 |
| 404 | 资源不存在 |
| 500 | 服务器错误 |

---

## 常见问题排查

### Q1: 启动报错 "端口被占用"
```bash
# 查看端口占用
netstat -ano | findstr :8080
# 或修改 application.yml 中的 server.port
```

### Q2: 数据库连接失败
- 检查 MySQL 服务是否启动
- 检查 application.yml 中的用户名密码
- 检查 init.sql 是否已执行

### Q3: Redis连接失败
- 检查 Redis 服务是否启动
- 检查 application.yml 中的 Redis 配置

### Q4: 登录返回401
- 检查请求头是否包含 `Authorization: Bearer <token>`
- 检查 token 是否过期（默认24小时）
- 重新登录获取新 token
