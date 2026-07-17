-- ==================== 业务数据表 ====================

-- 事故数据主表
CREATE TABLE IF NOT EXISTS accident_data (
  id VARCHAR(20) NOT NULL,
  name VARCHAR(200) NOT NULL,
  reporter VARCHAR(50) DEFAULT NULL,
  report_time DATE DEFAULT NULL,
  occur_time DATE DEFAULT NULL,
  location VARCHAR(200) DEFAULT NULL,
  road_type VARCHAR(50) DEFAULT NULL,
  weather VARCHAR(50) DEFAULT NULL,
  vehicle_type VARCHAR(50) DEFAULT NULL,
  system_version VARCHAR(50) DEFAULT NULL,
  accident_type VARCHAR(50) DEFAULT NULL,
  accident_level VARCHAR(50) DEFAULT NULL,
  status VARCHAR(50) DEFAULT NULL,
  case_type VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 事故数据详情表
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

-- 场景设计主表（统一数据表，支持三阶段流转）
CREATE TABLE IF NOT EXISTS scene_design (
  id VARCHAR(20) NOT NULL,
  name VARCHAR(200) NOT NULL,
  designer VARCHAR(50) DEFAULT NULL,
  tags VARCHAR(500) DEFAULT NULL,
  verifiers VARCHAR(200) DEFAULT NULL,
  status VARCHAR(50) DEFAULT '设计中',
  modify_count INT DEFAULT 0,
  create_time DATE DEFAULT NULL,
  description VARCHAR(1000) DEFAULT NULL,
  dcid VARCHAR(20) DEFAULT NULL,
  type VARCHAR(50) DEFAULT NULL,
  level VARCHAR(20) DEFAULT NULL,
  stage VARCHAR(50) DEFAULT 'scenariocase',
  phase VARCHAR(50) DEFAULT 'scene_design',
  responsible VARCHAR(100) DEFAULT NULL,
  designers VARCHAR(500) DEFAULT NULL,
  progress INT DEFAULT 0,
  design_complete_count INT DEFAULT 0,
  total_designers INT DEFAULT 0,
  PRIMARY KEY (id),
  INDEX idx_phase (phase),
  INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 场景设计详情表
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

-- 注意：以下表已废弃，数据已合并到scene_design表中
-- case_design表 - 已废弃（使用scene_design.phase='case_design'代替）
-- test_case表 - 已废弃（使用scene_design.phase='case_dev'代替）

-- 危险场景表
CREATE TABLE IF NOT EXISTS danger_scene (
  id VARCHAR(20) NOT NULL,
  name VARCHAR(200) NOT NULL,
  type VARCHAR(50) DEFAULT NULL,
  level VARCHAR(20) DEFAULT NULL,
  status VARCHAR(50) DEFAULT NULL,
  create_time DATE DEFAULT NULL,
  scene_id VARCHAR(20) DEFAULT NULL,
  description TEXT DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 标签表
CREATE TABLE IF NOT EXISTS tag (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  type VARCHAR(50) DEFAULT NULL,
  color VARCHAR(20) DEFAULT NULL,
  sort_order INT DEFAULT 0,
  status TINYINT DEFAULT 1,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 数据源表
CREATE TABLE IF NOT EXISTS data_source (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  type VARCHAR(50) DEFAULT NULL,
  url VARCHAR(500) DEFAULT NULL,
  description TEXT DEFAULT NULL,
  status TINYINT DEFAULT 1,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 流程任务表
CREATE TABLE IF NOT EXISTS process_task (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  task_name VARCHAR(200) NOT NULL,
  task_type VARCHAR(50) DEFAULT NULL,
  status VARCHAR(50) DEFAULT 'pending',
  business_id VARCHAR(20) DEFAULT NULL,
  business_type VARCHAR(50) DEFAULT NULL,
  assignee VARCHAR(50) DEFAULT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 审批流程表
CREATE TABLE IF NOT EXISTS approval_flow (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  business_id VARCHAR(20) NOT NULL,
  flow_type VARCHAR(50) DEFAULT 'scene',
  current_step VARCHAR(50) DEFAULT NULL,
  flow_data TEXT DEFAULT NULL,
  status VARCHAR(50) DEFAULT 'active',
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_business_type (business_id, flow_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 审批修改记录表
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

-- 系统设置表
CREATE TABLE IF NOT EXISTS system_setting (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  setting_key VARCHAR(100) NOT NULL UNIQUE,
  setting_value TEXT DEFAULT NULL,
  setting_type VARCHAR(50) DEFAULT 'string',
  description VARCHAR(200) DEFAULT NULL,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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