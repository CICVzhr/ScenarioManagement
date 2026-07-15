package com.example.scenario.service;

import com.example.scenario.config.ResourceNotFoundException;
import com.example.scenario.entity.ApprovalFlow;
import com.example.scenario.entity.ApprovalModification;
import com.example.scenario.repository.ApprovalFlowRepository;
import com.example.scenario.repository.ApprovalModificationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ApprovalFlowService {

    @Autowired
    private ApprovalFlowRepository approvalFlowRepository;

    @Autowired
    private ApprovalModificationRepository modificationRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

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

    public List<ApprovalModification> getModifications(String sceneId, String flowType) {
        return modificationRepository.findBySceneIdAndFlowType(sceneId, flowType);
    }

    @Transactional
    public ApprovalModification saveModification(ApprovalModification modification) {
        String sceneId = modification.getSceneId();
        String flowType = modification.getFlowType() != null ? modification.getFlowType() : "scene";
        Optional<ApprovalFlow> flowOpt = approvalFlowRepository.findBySceneIdAndFlowType(sceneId, flowType);
        if (!flowOpt.isPresent()) {
            createDefaultFlow(sceneId, flowType);
        }
        ApprovalModification saved = modificationRepository.save(modification);
        updateFlowWithModification(saved);
        return saved;
    }

    @Transactional
    public ApprovalFlow advanceStep(String sceneId, String flowType, String newStatus, String operator, String comment) {
        ApprovalFlow flow;
        try {
            flow = getFlowBySceneId(sceneId, flowType);
        } catch (ResourceNotFoundException e) {
            flow = createDefaultFlow(sceneId, flowType);
        }

        try {
            String flowDataStr = flow.getFlowData();
            if (flowDataStr == null || flowDataStr.isEmpty()) {
                flowDataStr = "{\"steps\":[]}";
            }
            JsonNode root = objectMapper.readTree(flowDataStr);
            ArrayNode steps = (ArrayNode) root.get("steps");
            if (steps == null || steps.size() == 0) {
                steps = createDefaultSteps();
                ((ObjectNode) root).set("steps", steps);
            }

            String currentStepName = flow.getCurrentStep();
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            int currentIndex = -1;

            for (int i = 0; i < steps.size(); i++) {
                ObjectNode step = (ObjectNode) steps.get(i);
                String stepName = step.has("stepName") ? step.get("stepName").asText() : "";
                if (currentStepName != null && currentStepName.equals(stepName)) {
                    currentIndex = i;
                    step.put("status", "completed");
                    step.put("operator", operator);
                    step.put("operateTime", now);
                    step.put("action", "审批通过");
                    if (comment != null && !comment.isEmpty()) {
                        step.put("comment", comment);
                    }
                    break;
                }
            }

            if (currentIndex < 0) {
                currentIndex = 0;
            }

            int nextIndex = currentIndex + 1;
            if (nextIndex < steps.size()) {
                ObjectNode nextStep = (ObjectNode) steps.get(nextIndex);
                nextStep.put("status", "active");
                nextStep.put("operator", operator);
                nextStep.put("operateTime", now);
                nextStep.put("action", "开始处理");
                flow.setCurrentStep(nextStep.get("stepName").asText());
            } else {
                flow.setCurrentStep(newStatus);
            }

            String updatedFlowData = objectMapper.writeValueAsString(root);
            flow.setFlowData(updatedFlowData);
            approvalFlowRepository.save(flow);

            ApprovalModification mod = new ApprovalModification();
            mod.setSceneId(sceneId);
            mod.setFlowType(flowType);
            mod.setStepKey(flow.getCurrentStep());
            mod.setModifier(operator);
            mod.setModifyTime(now);
            mod.setModifyContent(comment != null ? comment : "审批通过，状态变更");
            mod.setFieldChanges("[]");
            modificationRepository.save(mod);

            return flow;
        } catch (Exception e) {
            throw new RuntimeException("Failed to advance approval step: " + e.getMessage(), e);
        }
    }

    private ApprovalFlow createDefaultFlow(String sceneId, String flowType) {
        ApprovalFlow flow = new ApprovalFlow();
        flow.setSceneId(sceneId);
        flow.setFlowType(flowType);
        flow.setCurrentStep("设计中");
        try {
            ArrayNode steps = createDefaultSteps();
            ObjectNode root = objectMapper.createObjectNode();
            root.set("steps", steps);
            flow.setFlowData(objectMapper.writeValueAsString(root));
        } catch (Exception e) {
            flow.setFlowData("{\"steps\":[]}");
        }
        return approvalFlowRepository.save(flow);
    }

    private ArrayNode createDefaultSteps() {
        ArrayNode steps = objectMapper.createArrayNode();
        steps.add(createStepNode("设计中", "designing", "active"));
        steps.add(createStepNode("审核中", "reviewing", "pending"));
        steps.add(createStepNode("待验证", "pending_validation", "pending"));
        steps.add(createStepNode("已完成", "completed", "pending"));
        return steps;
    }

    private ObjectNode createStepNode(String stepName, String stepKey, String status) {
        ObjectNode step = objectMapper.createObjectNode();
        step.put("stepName", stepName);
        step.put("stepKey", stepKey);
        step.put("status", status);
        step.put("operator", "");
        step.put("operateTime", "");
        step.put("action", "");
        step.put("comment", "");
        step.set("modifications", objectMapper.createArrayNode());
        return step;
    }

    private void updateFlowWithModification(ApprovalModification modification) {
        try {
            String sceneId = modification.getSceneId();
            String flowType = modification.getFlowType();
            Optional<ApprovalFlow> flowOpt = approvalFlowRepository.findBySceneIdAndFlowType(sceneId, flowType);
            if (!flowOpt.isPresent()) {
                return;
            }
            ApprovalFlow flow = flowOpt.get();
            JsonNode root = objectMapper.readTree(flow.getFlowData());
            ArrayNode steps = (ArrayNode) root.get("steps");
            if (steps == null) {
                return;
            }

            String currentStepName = flow.getCurrentStep();
            for (int i = 0; i < steps.size(); i++) {
                ObjectNode step = (ObjectNode) steps.get(i);
                String stepName = step.has("stepName") ? step.get("stepName").asText() : "";
                if (currentStepName != null && currentStepName.equals(stepName)) {
                    ArrayNode modifications = step.has("modifications") ?
                        (ArrayNode) step.get("modifications") : objectMapper.createArrayNode();

                    ObjectNode modNode = objectMapper.createObjectNode();
                    modNode.put("modifier", modification.getModifier());
                    modNode.put("modifyTime", modification.getModifyTime());
                    modNode.put("modifyContent", modification.getModifyContent());
                    if (modification.getFieldChanges() != null && !modification.getFieldChanges().isEmpty()) {
                        modNode.set("textChanges", objectMapper.readTree(modification.getFieldChanges()));
                    }

                    modifications.add(modNode);
                    step.set("modifications", modifications);
                    break;
                }
            }

            String updatedFlowData = objectMapper.writeValueAsString(root);
            flow.setFlowData(updatedFlowData);
            approvalFlowRepository.save(flow);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update flow with modification: " + e.getMessage(), e);
        }
    }
}