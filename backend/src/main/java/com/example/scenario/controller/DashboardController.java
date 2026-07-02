package com.example.scenario.controller;

import com.example.scenario.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        return dashboardService.getDashboardStats();
    }

    @GetMapping("/process-distribution")
    public List<Map<String, Object>> getProcessDistribution() {
        return dashboardService.getProcessStatusDistribution();
    }

    @GetMapping("/danger-category")
    public List<Map<String, Object>> getDangerCategory() {
        return dashboardService.getDangerSceneCategory();
    }

    @GetMapping("/recent-tasks")
    public List<Object> getRecentTasks() {
        return dashboardService.getRecentTasks();
    }
}