# 快速启动指南

## 🚀 立即开始使用

### 方式一：全新部署（推荐）

#### 1. 准备数据库
```bash
# 登录MySQL
mysql -u root -p

# 创建数据库
CREATE DATABASE scenario_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 退出MySQL
exit;
```

#### 2. 初始化表结构
```bash
cd c:\zhr\ScenarioManagement\backend
mysql -u root -p scenario_management < create_tables_complete.sql
```

#### 3. 启动后端服务
```bash
cd c:\zhr\ScenarioManagement\backend
mvn spring-boot:run
```
✅ 后端会自动初始化基础权限数据（用户、角色、权限）

#### 4. 插入测试数据（可选）
打开新的终端窗口：
```bash
cd c:\zhr\ScenarioManagement\backend
mysql -u root -p scenario_management < insert_test_data.sql
```

#### 5. 启动前端服务
```bash
cd c:\zhr\ScenarioManagement\ScenarioManagement
npm install  # 如果还没有安装依赖
npm run dev
```

#### 6. 访问系统
```
浏览器打开: http://localhost:5173

默认账号:
- 用户名: admin    密码: admin123  (超级管理员)
- 用户名: designer 密码: 123456   (设计人员)
- 用户名: reviewer 密码: 123456   (审核人员)
```

---

### 方式二：已有数据库，仅更新代码

如果您已经有运行中的系统：

1. **停止当前运行的服务**
2. **更新DataInitializer代码**（已完成 ✅）
3. **重新编译后端**
   ```bash
   cd c:\zhr\ScenarioManagement\backend
   mvn clean compile
   ```
4. **重启后端服务**
   ```bash
   mvn spring-boot:run
   ```

⚠️ **注意**: 重启后不会影响您现有的业务数据，只会初始化/更新权限数据。

---

## 📱 系统功能导航

### 登录后可见的菜单

#### 📊 仪表盘
- 系统数据统计概览
- 最近活动记录

#### 🚗 事故数据管理
- 查看事故数据列表
- 新增事故数据
- 编辑事故详情

#### 🎨 场景与用例设计（核心功能）
包含3个子页面：

1. **场景设计**
   - 创建新场景（基于事故数据）
   - 编辑场景信息
   - 提交审批流程
   - 状态：设计中 → 审核中 → 待验证 → 已完成

2. **用例设计**
   - 自动从"已完成"的场景生成
   - 多人协作设计
   - 设计完成后提交审批
   - 状态：设计中 → 审批中 → 已完成

3. **用例开发**
   - 自动从"已完成"的用例生成
   - 开发测试用例
   - 执行测试
   - 状态：开发中 → 测试中 → 已完成

#### ⚠️ 危险场景库
- 查看已归档的危险场景
- 从完成的测试用例自动归档

#### 👥 用户管理（管理员功能）
- 用户账号管理
- 角色分配
- 权限控制

#### 🔐 角色管理
- 创建角色
- 分配菜单权限
- 分配按钮权限

#### 📋 权限管理
- 菜单权限配置
- 按钮权限配置
- 权限树形展示

---

## 🎯 快速体验流程（使用测试数据）

### 步骤1：登录系统
1. 打开浏览器访问 http://localhost:5173
2. 输入用户名 `admin`，密码 `admin123`
3. 点击登录

### 步骤2：查看场景设计
1. 点击左侧菜单 "场景与用例设计"
2. 默认显示"场景设计"标签页
3. 您应该能看到3条测试数据：
   - SC001 - 城市道路碰撞场景设计（状态：设计中）
   - SC002 - 高速追尾场景设计（状态：审核中）
   - SC003 - 复杂交通流场景设计（状态：已完成）

### 步骤3：编辑场景
1. 在SC001行点击"查看"按钮
2. 弹出编辑抽屉窗口
3. 可以查看和编辑场景详细信息
4. 尝试修改一些内容并保存

### 步骤4：查看用例设计
1. 点击顶部的"用例设计"标签
2. 应该能看到CD001和CD008两条数据
3. CD008状态为"审批中"

### 步骤5：查看用例开发
1. 点击顶部的"用例开发"标签
2. 当前应该显示0条记录（因为测试数据中没有已完成的用例）

### 步骤6：查看危险场景库
1. 点击左侧菜单 "危险场景库"
2. 应该能看到DS001归档数据

### 步骤7：尝试用户管理
1. 点击左侧菜单 "用户管理"
2. 查看3个默认用户
3. 尝试创建新用户或修改密码

---

## ❓ 常见问题快速解决

### Q1: 启动后端报错 "Table doesn't exist"
**原因**: 数据库表未创建
**解决**: 执行 `create_tables_complete.sql`

### Q2: 登录失败 "用户名或密码错误"
**原因**: 基础数据未初始化
**解决**: 重启后端服务，DataInitializer会自动创建默认用户

### Q3: 页面显示"暂无数据"
**原因**: 正常现象！系统不再自动初始化业务数据
**解决**:
- 选项A: 手动通过界面创建数据
- 选项B: 执行 `insert_test_data.sql` 插入测试数据

### Q4: 前端无法连接后端
**检查项**:
1. 后端是否正常启动（控制台无报错）
2. 前端API配置是否正确（查看 `.env` 文件）
3. 浏览器F12 → Network是否有红色请求
4. CORS跨域配置是否正确

### Q5: 权限不生效
**解决**:
1. 重新登录（刷新Token）
2. 检查用户角色分配
3. 检查角色权限配置
4. 清除浏览器缓存

---

## 🔧 开发调试技巧

### 查看后端日志
```bash
# 控制台直接输出
mvn spring-boot:run

# 或输出到日志文件
mvn spring-boot:run > log.txt 2>&1
```

### 查看前端请求
1. 浏览器按F12打开开发者工具
2. 切换到Network标签
3. 刷新页面，查看所有API请求
4. 点击具体请求查看响应数据

### 数据库直接查询
```bash
mysql -u root -p scenario_management

-- 查看所有表
SHOW TABLES;

-- 查看场景设计数据
SELECT * FROM scene_design;

-- 查看用户数据
SELECT id, username, real_name FROM sys_user;
```

### 重置测试环境
```bash
# 清空业务数据（保留权限数据）
mysql -u root -p scenario_management -e "
TRUNCATE TABLE accident_data;
TRUNCATE TABLE accident_data_detail;
TRUNCATE TABLE scene_design;
TRUNCATE TABLE scene_design_detail;
TRUNCATE TABLE case_design;
TRUNCATE TABLE test_case;
TRUNCATE TABLE danger_scene;
TRUNCATE TABLE tag;
TRUNCATE TABLE data_source;
TRUNCATE TABLE process_task;
TRUNCATE TABLE approval_flow;
TRUNCATE TABLE approval_modification;
"

# 重新插入测试数据
mysql -u root -p scenario_management < insert_test_data.sql
```

---

## 📞 技术支持

### 日志位置
- 后端日志: 控制台输出或 `log.txt`
- 前端日志: 浏览器F12 → Console
- 数据库日志: MySQL日志文件

### 关键配置文件
```
backend/
├── src/main/resources/
│   └── application.properties  # 数据库连接配置

ScenarioManagement/
├── .env                        # 前端API地址配置
├── vite.config.js              # Vite构建配置
└── src/api/index.js            # Axios请求配置
```

### 性能监控
- 后端: Spring Boot Actuator (如已启用)
- 前端: Vue DevTools浏览器扩展
- 数据库: MySQL Workbench或Navicat

---

## ✅ 验证清单

部署完成后，请逐项确认：

### 基础功能
- [ ] 后端服务启动无报错
- [ ] 前端页面正常访问
- [ ] 能够使用admin/admin123登录
- [ ] 数据库表全部创建成功
- [ ] 基础权限数据存在（3个用户，3个角色）

### 业务功能（需要测试数据）
- [ ] 事故数据显示正常
- [ ] 场景设计CRUD正常
- [ ] 用例设计显示正常
- [ ] 用例开发显示正常
- [ ] 危险场景归档正常
- [ ] 审批流程可执行

### 权限功能
- [ ] 不同用户看到不同菜单
- [ ] 按钮权限控制生效
- [ ] 用户管理CRUD正常
- [ ] 角色权限分配正常

---

## 🎉 下一步

现在您可以：
1. ✅ 开始使用系统进行实际工作
2. 📖 阅读详细的 `DATABASE_README.md` 了解更多
3. 🔧 根据 `MODIFICATION_SUMMARY.md` 了解技术细节
4. 🧪 使用测试数据进行功能验证
5. 💡 根据实际需求定制系统配置

---

**祝您使用愉快！** 🚀

如有问题，请参考 `DATABASE_README.md` 的常见问题部分。