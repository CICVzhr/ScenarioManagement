package com.example.scenario.entity;

import javax.persistence.*;

@Entity
@Table(name = "approval_flow")
public class ApprovalFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scene_id", length = 20, nullable = false)
    private String sceneId;

    @Column(name = "flow_type", length = 20)
    private String flowType;

    @Column(name = "current_step", length = 50)
    private String currentStep;

    @Column(name = "flow_data", columnDefinition = "TEXT")
    private String flowData;

    public ApprovalFlow() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSceneId() { return sceneId; }
    public void setSceneId(String sceneId) { this.sceneId = sceneId; }
    public String getFlowType() { return flowType; }
    public void setFlowType(String flowType) { this.flowType = flowType; }
    public String getCurrentStep() { return currentStep; }
    public void setCurrentStep(String currentStep) { this.currentStep = currentStep; }
    public String getFlowData() { return flowData; }
    public void setFlowData(String flowData) { this.flowData = flowData; }
}
