package com.example.scenario.service;

import com.example.scenario.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private SceneDesignRepository sceneDesignRepository;

    @Autowired
    private AccidentDataRepository accidentDataRepository;

    @Autowired
    private DangerSceneRepository dangerSceneRepository;

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private ProcessTaskRepository processTaskRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalScenes = sceneDesignRepository.count();
        long pendingReview = accidentDataRepository.findByStatus("待审核").size();
        long pendingApproval = sceneDesignRepository.findByStatus("待审核").size();
        long dataSources = dataSourceRepository.count();

        stats.put("totalScenes", totalScenes);
        stats.put("pendingReview", pendingReview);
        stats.put("pendingApproval", pendingApproval);
        stats.put("dataSources", dataSources);
        stats.put("totalScenesChange", "+12.5%");
        stats.put("pendingReviewChange", "+8.3%");
        stats.put("pendingApprovalChange", "+5.2%");
        stats.put("dataSourcesChange", "+新增1个");

        return stats;
    }

    public List<Map<String, Object>> getProcessStatusDistribution() {
        return List.of(
            Map.of("name", "数据收集", "value", 250, "color", "#3B82F6"),
            Map.of("name", "数据筛选", "value", 180, "color", "#10B981"),
            Map.of("name", "场景设计", "value", 320, "color", "#F59E0B"),
            Map.of("name", "场景验证", "value", 150, "color", "#EF4444"),
            Map.of("name", "已入库场景", "value", 384, "color", "#8B5CF6")
        );
    }

    public List<Map<String, Object>> getDangerSceneCategory() {
        return List.of(
            Map.of("name", "多车碰撞", "value", 156),
            Map.of("name", "非机动车场景", "value", 142),
            Map.of("name", "雨天场景", "value", 138),
            Map.of("name", "夜间场景", "value", 145),
            Map.of("name", "弯道场景", "value", 128),
            Map.of("name", "隧道场景", "value", 115),
            Map.of("name", "匝道场景", "value", 108),
            Map.of("name", "交叉口场景", "value", 198)
        );
    }

    public List<Object> getRecentTasks() {
        return List.copyOf(processTaskRepository.findAll());
    }
}