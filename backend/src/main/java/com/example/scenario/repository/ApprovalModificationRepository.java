package com.example.scenario.repository;

import com.example.scenario.entity.ApprovalModification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalModificationRepository extends JpaRepository<ApprovalModification, Long> {
    List<ApprovalModification> findBySceneIdAndFlowType(String sceneId, String flowType);
    List<ApprovalModification> findBySceneIdAndFlowTypeAndStepKey(String sceneId, String flowType, String stepKey);
}