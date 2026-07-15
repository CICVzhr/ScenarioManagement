package com.example.scenario.controller;

import com.example.scenario.entity.ApprovalFlow;
import com.example.scenario.entity.ApprovalModification;
import com.example.scenario.service.ApprovalFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/approval-flows")
public class ApprovalFlowController {

    @Autowired
    private ApprovalFlowService approvalFlowService;

    @GetMapping("/{sceneId}")
    public ResponseEntity<ApprovalFlow> getFlowBySceneId(
            @PathVariable String sceneId,
            @RequestParam(name = "type", required = false) String flowType) {
        ApprovalFlow flow = approvalFlowService.getFlowBySceneId(sceneId, flowType);
        return ResponseEntity.ok(flow);
    }

    @PostMapping
    public ApprovalFlow createFlow(@RequestBody ApprovalFlow flow) {
        return approvalFlowService.saveFlow(flow);
    }

    @GetMapping("/{sceneId}/modifications")
    public ResponseEntity<List<ApprovalModification>> getModifications(
            @PathVariable String sceneId,
            @RequestParam(name = "type", required = false, defaultValue = "scene") String flowType) {
        try {
            List<ApprovalModification> modifications = approvalFlowService.getModifications(sceneId, flowType);
            return ResponseEntity.ok(modifications);
        } catch (Exception e) {
            return ResponseEntity.ok(java.util.Collections.emptyList());
        }
    }

    @PostMapping("/{sceneId}/modifications")
    public ResponseEntity<ApprovalModification> saveModification(
            @PathVariable String sceneId,
            @RequestBody ApprovalModification modification) {
        modification.setSceneId(sceneId);
        if (modification.getFlowType() == null || modification.getFlowType().isEmpty()) {
            modification.setFlowType("scene");
        }
        ApprovalModification saved = approvalFlowService.saveModification(modification);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{sceneId}/advance-step")
    public ResponseEntity<ApprovalFlow> advanceStep(
            @PathVariable String sceneId,
            @RequestBody Map<String, String> request) {
        String flowType = request.getOrDefault("flowType", "scene");
        String newStatus = request.get("newStatus");
        String operator = request.getOrDefault("operator", "系统");
        String comment = request.get("comment");
        ApprovalFlow flow = approvalFlowService.advanceStep(sceneId, flowType, newStatus, operator, comment);
        return ResponseEntity.ok(flow);
    }
}