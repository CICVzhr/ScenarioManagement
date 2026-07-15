package com.example.scenario.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "approval_modification")
public class ApprovalModification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scene_id", length = 20, nullable = false)
    private String sceneId;

    @Column(name = "flow_type", length = 20)
    private String flowType;

    @Column(name = "step_key", length = 50)
    private String stepKey;

    @Column(name = "modifier", length = 50)
    private String modifier;

    @Column(name = "modify_time", length = 50)
    private String modifyTime;

    @Column(name = "modify_content", length = 500)
    private String modifyContent;

    @Column(name = "field_changes", columnDefinition = "TEXT")
    private String fieldChanges;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ApprovalModification() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSceneId() { return sceneId; }
    public void setSceneId(String sceneId) { this.sceneId = sceneId; }
    public String getFlowType() { return flowType; }
    public void setFlowType(String flowType) { this.flowType = flowType; }
    public String getStepKey() { return stepKey; }
    public void setStepKey(String stepKey) { this.stepKey = stepKey; }
    public String getModifier() { return modifier; }
    public void setModifier(String modifier) { this.modifier = modifier; }
    public String getModifyTime() { return modifyTime; }
    public void setModifyTime(String modifyTime) { this.modifyTime = modifyTime; }
    public String getModifyContent() { return modifyContent; }
    public void setModifyContent(String modifyContent) { this.modifyContent = modifyContent; }
    public String getFieldChanges() { return fieldChanges; }
    public void setFieldChanges(String fieldChanges) { this.fieldChanges = fieldChanges; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}