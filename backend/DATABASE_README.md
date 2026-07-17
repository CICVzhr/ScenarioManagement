# 数据库初始化说明

## 当前状态

从现在起，**系统不再自动初始化业务数据**。DataInitializer仅负责初始化系统基础权限数据（用户、角色、权限），所有业务数据都需要通过以下方式获取：

1. **手动插入数据库** - 使用提供的SQL脚本
2. **通过前端界面创建** - 在系统中手动创建
3. **业务流程生成** - 通过状态流转自动生成（如场景设计完成后自动生成用例设计）

## 数据库表结构

完整的数据库表结构定义在 `create_tables_complete.sql` 文件中，包含：

### 业务数据表
- `accident_data` - 事故数据主表
- `accident_data_detail` - 事故数据详情表
- `scene_design` - 场景设计主表
- `scene_design_detail` - 场景设计详情表
- `case_design` - 用例设计表
- `test_case` - 测试用例表
- `danger_scene` - 危险场景表
- `tag` - 标签表
- `data_source` - 数据源表
- `process_task` - 流程任务表
- `approval_flow` - 审批流程表
- `approval_modification` - 审批修改记录表
- `system_setting` - 系统设置表

### 用户权限表
- `sys_user` - 用户表
- `sys_role` - 角色表
- `sys_permission` - 权限表
- `sys_user_role` - 用户角色关联表
- `sys_role_permission` - 角色权限关联表

## 初始化步骤

### 1. 首次部署

```bash
# 1. 执行建表脚本
mysql -u your_username -p your_database < create_tables_complete.sql

# 2. 启动后端服务（会自动初始化基础权限数据）
mvn spring-boot:run
```

### 2. 插入测试数据（可选）

如果需要测试数据来验证系统功能：

```bash
mysql -u your_username -p your_database < insert_test_data.sql
```

测试数据包括：
- 2条事故数据（AD001, AD002）
- 3条场景设计数据（SC001, SC002, SC003）
- 3条场景设计详情（SDD001, SDD002, SDD003）
- 2条用例设计数据（CD001, CD008）
- 1条危险场景数据（DS001）

### 3. 默认用户账号

系统启动后会自动创建以下用户：

| 用户名 | 密码 | 角色 | 说明 |
|--------|------|------|------|
| admin | admin123 | 超级管理员 | 拥有所有权限 |
| designer | 123456 | 设计人员 | 场景/用例设计与编辑 |
| reviewer | 123456 | 审核人员 | 审核与查看权限 |

## 数据流转说明

### 场景设计流程
```
事故数据(AD) → 场景设计中 → 场景审核中 → 待验证 → 已完成
                                                    ↓
                                              自动创建用例设计(CD)
                                                    ↓
                                              用例设计中 → 审批中 → 已完成
                                                                    ↓
                                                              自动创建测试用例(TC)
                                                                    ↓
                                                              开发中 → 测试中 → 已完成
                                                                    ↓
                                                              归档到危险场景库(DS)
```

### 关键状态转换

1. **场景设计完成**：当场景设计状态变为"已完成"时：
   - 对应的事故数据状态更新为"设计完成"
   - 自动创建用例设计记录
   - 自动创建危险场景记录

2. **用例设计完成**：当用例设计状态变为"已完成"时：
   - 自动创建测试用例记录
   - 更新危险场景状态为"用例开发"

3. **测试用例完成**：当测试用例状态变为"已完成"时：
   - 危险场景归档完成

## 注意事项

1. **不要删除基础权限数据**：sys_user、sys_role、sys_permission等表的数据是系统运行的基础
2. **业务数据可以清空**：如果需要重新开始，可以清空所有业务数据表，保留权限表
3. **ID格式规范**：
   - 事故数据：AD + 数字（如AD001）
   - 场景设计：SC + 数字（如SC001）
   - 场景详情：SDD + 数字（如SDD001）
   - 用例设计：CD + 数字（如CD001）
   - 测试用例：TC + 数字（如TC001）
   - 危险场景：DS + 数字（如DS001）

4. **数据一致性**：
   - case_design.scene_id 关联 scene_design.id
   - test_case.scene_id 关联 scene_design.id 或 case_design.id
   - danger_scene.scene_id 关联 scene_design.id

## 常见问题

### Q: 为什么启动后没有看到任何数据？
A: 这是正常的。系统现在不会自动创建业务数据，您需要手动插入或通过界面创建。

### Q: 如何快速验证系统功能？
A: 执行 insert_test_data.sql 脚本插入测试数据，然后使用admin/admin123登录系统。

### Q: 可以恢复之前的自动初始化功能吗？
A: 可以修改 DataInitializer.java，取消注释相关方法即可，但不建议这样做。

## 技术支持

如有问题，请检查：
1. 数据库连接配置是否正确（application.properties/yml）
2. 数据库表是否已创建
3. 基础权限数据是否存在
4. 后端日志是否有错误信息