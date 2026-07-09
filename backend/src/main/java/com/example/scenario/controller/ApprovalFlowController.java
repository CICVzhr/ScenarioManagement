package com.example.scenario.controller;

import com.example.scenario.entity.ApprovalFlow;
import com.example.scenario.service.ApprovalFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/approval-flows")
public class ApprovalFlowController {

    @Autowired
    private ApprovalFlowService approvalFlowService;

    @GetMapping("/{sceneId}")
    public ResponseEntity<ApprovalFlow> getFlowBySceneId(
            @PathVariable String sceneId,
            @RequestParam(name = "type", required = false) String flowType) {
        try {
            ApprovalFlow flow = approvalFlowService.getFlowBySceneId(sceneId, flowType);
            return ResponseEntity.ok(flow);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ApprovalFlow createFlow(@RequestBody ApprovalFlow flow) {
        return approvalFlowService.saveFlow(flow);
    }
}
