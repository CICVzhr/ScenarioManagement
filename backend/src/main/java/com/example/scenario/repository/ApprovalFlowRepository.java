package com.example.scenario.repository;

import com.example.scenario.entity.ApprovalFlow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApprovalFlowRepository extends JpaRepository<ApprovalFlow, Long> {
    List<ApprovalFlow> findBySceneId(String sceneId);
    Optional<ApprovalFlow> findBySceneIdAndFlowType(String sceneId, String flowType);
}
