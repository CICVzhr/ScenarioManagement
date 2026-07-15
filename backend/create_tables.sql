CREATE TABLE IF NOT EXISTS accident_data_detail (
  id VARCHAR(20) NOT NULL,
  creator VARCHAR(100) DEFAULT NULL,
  create_time DATE DEFAULT NULL,
  occur_time DATE DEFAULT NULL,
  location VARCHAR(200) DEFAULT NULL,
  title VARCHAR(200) DEFAULT NULL,
  road_description VARCHAR(200) DEFAULT NULL,
  lighting_condition VARCHAR(100) DEFAULT NULL,
  weather_condition VARCHAR(100) DEFAULT NULL,
  perception_scheme VARCHAR(200) DEFAULT NULL,
  vehicle_type VARCHAR(100) DEFAULT NULL,
  adaptive_function VARCHAR(200) DEFAULT NULL,
  process_description TEXT DEFAULT NULL,
  cause_initial_judgment TEXT DEFAULT NULL,
  accident_type VARCHAR(50) DEFAULT NULL,
  case_type VARCHAR(50) DEFAULT NULL,
  cause_tag VARCHAR(200) DEFAULT NULL,
  level VARCHAR(50) DEFAULT NULL,
  media_link VARCHAR(500) DEFAULT NULL,
  media_file VARCHAR(500) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS scene_design_detail (
  id VARCHAR(20) NOT NULL,
  version VARCHAR(20) DEFAULT NULL,
  storage_time DATE DEFAULT NULL,
  name VARCHAR(200) DEFAULT NULL,
  tags VARCHAR(500) DEFAULT NULL,
  scene_tags VARCHAR(500) DEFAULT NULL,
  data_source VARCHAR(500) DEFAULT NULL,
  design_reason TEXT DEFAULT NULL,
  test_scene TEXT DEFAULT NULL,
  generalization TEXT DEFAULT NULL,
  generalization_items TEXT DEFAULT NULL,
  test_method TEXT DEFAULT NULL,
  test_objective TEXT DEFAULT NULL,
  environment_road TEXT DEFAULT NULL,
  environment_simulation TEXT DEFAULT NULL,
  environment_field TEXT DEFAULT NULL,
  target_object TEXT DEFAULT NULL,
  equipment_simulation TEXT DEFAULT NULL,
  equipment_field TEXT DEFAULT NULL,
  record_content TEXT DEFAULT NULL,
  vehicle_interaction TEXT DEFAULT NULL,
  vehicle_load TEXT DEFAULT NULL,
  vehicle_settings TEXT DEFAULT NULL,
  test_procedure TEXT DEFAULT NULL,
  pass_criteria TEXT DEFAULT NULL,
  end_conditions TEXT DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS approval_modification (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  scene_id VARCHAR(20) NOT NULL,
  flow_type VARCHAR(20) DEFAULT 'scene',
  step_key VARCHAR(50),
  modifier VARCHAR(50),
  modify_time VARCHAR(50),
  modify_content VARCHAR(500),
  field_changes TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_scene_flow (scene_id, flow_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==================== 用户权限管理系统 ====================

CREATE TABLE IF NOT EXISTS sys_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(200) NOT NULL,
  real_name VARCHAR(50),
  email VARCHAR(100),
  phone VARCHAR(20),
  status TINYINT DEFAULT 1 COMMENT '0禁用 1启用',
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS sys_role (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  role_name VARCHAR(50) NOT NULL,
  role_code VARCHAR(50) NOT NULL UNIQUE,
  description VARCHAR(200),
  status TINYINT DEFAULT 1 COMMENT '0禁用 1启用',
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS sys_permission (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  permission_name VARCHAR(50) NOT NULL,
  permission_code VARCHAR(100) NOT NULL UNIQUE,
  permission_type VARCHAR(20) DEFAULT 'button' COMMENT 'menu菜单 button按钮',
  parent_id BIGINT DEFAULT 0,
  path VARCHAR(200),
  icon VARCHAR(50),
  sort_order INT DEFAULT 0,
  status TINYINT DEFAULT 1,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS sys_user_role (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  UNIQUE KEY uk_user_role (user_id, role_id),
  INDEX idx_user_id (user_id),
  INDEX idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS sys_role_permission (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  role_id BIGINT NOT NULL,
  permission_id BIGINT NOT NULL,
  UNIQUE KEY uk_role_permission (role_id, permission_id),
  INDEX idx_role_id (role_id),
  INDEX idx_permission_id (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 默认管理员用户 (密码: admin123)
INSERT INTO sys_user (username, password, real_name, status) VALUES
('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '系统管理员', 1)
ON DUPLICATE KEY UPDATE username=username;

-- 默认角色
INSERT INTO sys_role (role_name, role_code, description) VALUES
('超级管理员', 'admin', '系统最高权限')
ON DUPLICATE KEY UPDATE role_code=role_code;

INSERT INTO sys_role (role_name, role_code, description) VALUES
('场景设计师', 'designer', '场景设计与用例设计权限')
ON DUPLICATE KEY UPDATE role_code=role_code;

INSERT INTO sys_role (role_name, role_code, description) VALUES
('审核员', 'reviewer', '审核与查看权限')
ON DUPLICATE KEY UPDATE role_code=role_code;

-- 默认权限
INSERT INTO sys_permission (permission_name, permission_code, permission_type, parent_id, path, sort_order) VALUES
('场景数据', 'accident_data', 'menu', 0, '/accident-data', 1),
('场景数据查看', 'accident_data:view', 'button', 1, NULL, 1),
('场景数据编辑', 'accident_data:edit', 'button', 1, NULL, 2),
('场景数据删除', 'accident_data:delete', 'button', 1, NULL, 3),
('场景设计', 'scenario_design', 'menu', 0, '/scenario-design', 2),
('场景设计查看', 'scenario_design:view', 'button', 5, NULL, 1),
('场景设计编辑', 'scenario_design:edit', 'button', 5, NULL, 2),
('场景设计删除', 'scenario_design:delete', 'button', 5, NULL, 3),
('场景设计审批', 'scenario_design:approve', 'button', 5, NULL, 4),
('危险场景库', 'danger_library', 'menu', 0, '/danger-library', 3),
('危险场景查看', 'danger_library:view', 'button', 10, NULL, 1),
('危险场景编辑', 'danger_library:edit', 'button', 10, NULL, 2),
('用例设计', 'case_design', 'menu', 0, '/case-design', 4),
('用例设计查看', 'case_design:view', 'button', 13, NULL, 1),
('用例设计编辑', 'case_design:edit', 'button', 13, NULL, 2),
('标签管理', 'tag_management', 'menu', 0, '/tag-management', 5),
('标签管理编辑', 'tag_management:edit', 'button', 16, NULL, 1),
('系统设置', 'system_settings', 'menu', 0, '/system-settings', 6),
('系统设置编辑', 'system_settings:edit', 'button', 18, NULL, 1),
('用户管理', 'user_management', 'menu', 0, '/user-management', 7),
('用户管理查看', 'user_management:view', 'button', 20, NULL, 1),
('用户管理编辑', 'user_management:edit', 'button', 20, NULL, 2),
('用户管理删除', 'user_management:delete', 'button', 20, NULL, 3)
ON DUPLICATE KEY UPDATE permission_code=permission_code;

-- 管理员角色绑定超级管理员权限
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1)
ON DUPLICATE KEY UPDATE user_id=user_id;

-- 超级管理员角色拥有所有权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 1, id FROM sys_permission
ON DUPLICATE KEY UPDATE role_id=role_id;