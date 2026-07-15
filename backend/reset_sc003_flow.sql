-- 将 SC003（DC003对应的场景）退回到「设计中」状态
-- 1. 重置审批流
UPDATE approval_flow
SET current_step = '设计中',
    flow_data = '{"steps":[{"stepName":"设计中","stepKey":"designing","status":"active","operator":"","operateTime":"","action":"","comment":"","modifications":[]},{"stepName":"审核中","stepKey":"reviewing","status":"pending","operator":"","operateTime":"","action":"","comment":"","modifications":[]},{"stepName":"待验证","stepKey":"pending_validation","status":"pending","operator":"","operateTime":"","action":"","comment":"","modifications":[]},{"stepName":"已完成","stepKey":"completed","status":"pending","operator":"","operateTime":"","action":"","comment":"","modifications":[]}]}'
WHERE scene_id = 'SC003' AND flow_type = 'scene';

-- 2. 清除修改记录
DELETE FROM approval_modification WHERE scene_id = 'SC003';

-- 3. 将场景状态也重置为「设计中」
UPDATE scene_design SET status = '设计中' WHERE id = 'SC003';