package com.example.scenario.service;

import com.example.scenario.config.ResourceNotFoundException;
import com.example.scenario.entity.ApprovalFlow;
import com.example.scenario.repository.ApprovalFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApprovalFlowService {

    @Autowired
    private ApprovalFlowRepository approvalFlowRepository;

    public ApprovalFlow getFlowBySceneId(String sceneId, String flowType) {
        if (flowType != null && !flowType.isEmpty()) {
            return approvalFlowRepository.findBySceneIdAndFlowType(sceneId, flowType)
                    .orElseThrow(() -> new ResourceNotFoundException("Approval flow not found for scene: " + sceneId + ", type: " + flowType));
        }
        // If no flowType specified, try scene type first, then case type, then fail
        Optional<ApprovalFlow> sceneFlow = approvalFlowRepository.findBySceneIdAndFlowType(sceneId, "scene");
        if (sceneFlow.isPresent()) return sceneFlow.get();
        Optional<ApprovalFlow> caseFlow = approvalFlowRepository.findBySceneIdAndFlowType(sceneId, "case");
        if (caseFlow.isPresent()) return caseFlow.get();
        throw new ResourceNotFoundException("Approval flow not found for scene: " + sceneId);
    }

    public ApprovalFlow saveFlow(ApprovalFlow flow) {
        return approvalFlowRepository.save(flow);
    }
}
