-- 测试数据脚本
-- 用于测试系统的基本功能
-- 注意：此脚本仅用于测试环境，生产环境请勿使用

-- 插入测试事故数据
INSERT INTO accident_data (id, name, reporter, report_time, occur_time, location, road_type, weather, vehicle_type, system_version, accident_type, accident_level, status, case_type) VALUES
('AD001', '测试事故数据1', '张三', '2024-01-15', '2024-01-14', '北京市朝阳区某路口', '城市道路', '晴', '轿车', 'v2.0', '碰撞', '高', '待设计', '一般事故'),
('AD002', '测试事故数据2', '李四', '2024-02-20', '2024-02-19', '上海市浦东新区某高速', '高速公路', '雨', 'SUV', 'v2.1', '追尾', '中', '设计中', '重大事故')
ON DUPLICATE KEY UPDATE name=name;

-- 插入测试场景设计数据（统一表，分阶段）
INSERT INTO scene_design (id, name, designer, tags, verifiers, status, modify_count, create_time, description, dcid, type, level, stage, phase, responsible, designers, progress, design_complete_count, total_designers) VALUES
('SC001', '城市道路碰撞场景设计', '设计师A', '城市,碰撞', '审核员A,审核员B', '设计中', 0, '2024-03-01', '基于AD001设计的城市场景', 'AD001', '功能场景', '高', 'scenariocase', 'scene_design', NULL, NULL, 0, 0, 0),
('SC002', '高速追尾场景设计', '设计师B', '高速,追尾', '审核员C', '审核中', 1, '2024-03-05', '基于AD002设计的高速场景', 'AD002', '性能场景', '中', 'scenariocase', 'scene_design', NULL, NULL, 0, 0, 0),
('SC003', '复杂交通流场景设计', '设计师A', '复杂,多车', '审核员A,审核员B', '修改中', 2, '2024-03-08', '需要修改的场景', NULL, '安全场景', '高', 'scenariocase', 'scene_design', NULL, NULL, 0, 0, 0),
('SC004', '已完成待流转场景', '设计师C', '测试,完成', '审核员D', '已完成', 5, '2024-04-01', '已完成的场景，将进入用例设计阶段', NULL, '功能场景', '中', 'scenariocase', 'scene_design', NULL, NULL, 100, 1, 1),
('CD001', '用例设计1-基础刹车', '设计师A', '用例,基础', '审核员A', '用例设计中', 0, '2024-05-01', '从SC004自动流转过来', 'SC004', '功能用例', '高', 'scenariocase', 'case_design', '负责人A', '设计师A,设计师B', 50, 0, 2),
('CD002', '用例设计2-AEB测试', '设计师B', '用例,AEB', '审核员B,C', '审批中', 2, '2024-06-01', '正在审批的用例设计', NULL, '安全用例', '高', 'scenariocase', 'case_design', '负责人B', '设计师B', 80, 1, 1),
('TC001', '开发中测试用例', '开发者X', '开发,进行中', NULL, '开发中', 0, '2024-07-01', '从CD002自动流转过来', 'CD002', '功能用例', '高', 'scenariocase', 'case_dev', NULL, NULL, 30, 0, 0),
('TC002', '测试中用例', '开发者Y', '测试,验证', NULL, '测试中', 3, '2024-07-10', '正在测试的用例', NULL, '性能用例', '中', 'scenariocase', 'case_dev', NULL, NULL, 70, 0, 0)
ON DUPLICATE KEY UPDATE name=name;

-- 插入测试场景设计详情
INSERT INTO scene_design_detail (id, version, storage_time, name, tags, scene_tags, data_source, design_reason, test_scene, generalization, generalization_items, test_method, test_objective, environment_road, environment_simulation, environment_field, target_object, equipment_simulation, equipment_field, record_content, vehicle_interaction, vehicle_load, vehicle_settings, test_procedure, pass_criteria, end_conditions) VALUES
('SDD001', 'v1.0', '2024-03-01', '城市道路碰撞场景详情', '城市,碰撞', '城市道路,碰撞测试', 'AD001', '验证AEB功能', '城市道路十字路口碰撞', '可泛化到类似城市道路场景', '不同车速、不同角度、不同天气', '实车测试+仿真测试', '验证自动紧急制动系统的有效性', '双向4车道城市道路,有信号灯', 'PreScan仿真软件', '封闭测试场地', '本车与目标车辆', '目标车辆可控,配备假人', '配备传感器和数据采集设备', '记录车辆速度、加速度、制动距离等', '车车交互,车路协同', '标准负载', '开启所有ADAS功能', '1.设置初始位置 2.开始测试 3.记录数据 4.分析结果', '制动距离<5m,无碰撞发生', '完成全部测试用例或达到预设里程'),
('SDD002', 'v1.1', '2024-03-05', '高速追尾场景详情', '高速,追尾', '高速公路,追尾测试', 'AD002', '验证ACC功能', '高速公路跟车行驶', '可泛化到不同高速路况', '不同车距、不同速度差', '仿真测试为主', '验证自适应巡航控制系统的稳定性', '3车道高速公路', 'CarSim+Simulink联合仿真', '高速测试跑道', '本车与前车', '前车可控制加减速', '高精度GPS和雷达', '记录车距、速度、加速度变化', '自适应巡航,车道保持', '满载状态', 'ACC开启状态', '1.设定跟随距离 2.前车减速 3.观察本车响应 4.记录数据', '保持安全距离>2s,无碰撞', '完成10次以上有效测试'),
('SDD003', 'v2.0', '2024-03-15', '复杂交通流场景详情', '复杂,多车', '城市复杂路况', '内部需求', '验证综合决策能力', '城市复杂路口多车交互', '高度泛化性', '多种组合场景', '实车+仿真混合测试', '验证在复杂交通环境下的决策正确性', '城市复杂路口,多车道', '高保真仿真环境', '专用测试场', '多车交互环境', '多台测试车辆,VRU', '全方位感知设备', '全景视频记录,多源数据融合', 'V2X通信,多车协作', '多种负载条件', '全功能开启', '1.构建复杂场景 2.多车同时操作 3.记录交互过程 4.评估决策', '决策正确率>95%,无安全事故', '完成所有预定测试场景')
ON DUPLICATE KEY UPDATE name=name;

-- 注意：case_design表不再使用，所有数据已合并到scene_design表中

-- 插入测试危险场景数据
INSERT INTO danger_scene (id, name, type, level, status, create_time, scene_id, description) VALUES
('DS001', '已归档的危险场景1', '碰撞', '高', '已归档', '2024-06-01', 'SC003', '从已完成场景设计归档')
ON DUPLICATE KEY UPDATE name=name;