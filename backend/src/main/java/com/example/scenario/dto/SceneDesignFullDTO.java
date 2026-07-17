package com.example.scenario.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.Map;

public class SceneDesignFullDTO {
    private String id;
    private String name;
    private String responsible;
    private String designers;
    private Integer progress;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;
    private String status;
    private String sceneId;
    private String dcid;
    private String type;
    private String level;
    private Integer designCompleteCount;
    private Integer totalDesigners;

    private String version;
    private String storageTime;
    private String tags;
    private String sceneTags;
    private String dataSource;
    private String designReason;
    private String testScene;
    private String generalization;
    private String generalizationItems;
    private String testMethod;
    private String testObjective;
    private String environmentRoad;
    private String environmentSimulation;
    private String environmentField;
    private String targetObject;
    private String equipmentSimulation;
    private String equipmentField;
    private String recordContent;
    private String vehicleInteraction;
    private String vehicleLoad;
    private String vehicleSettings;
    private String testProcedure;
    private String passCriteria;
    private String endConditions;

    public SceneDesignFullDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getResponsible() { return responsible; }
    public void setResponsible(String responsible) { this.responsible = responsible; }
    public String getDesigners() { return designers; }
    public void setDesigners(String designers) { this.designers = designers; }
    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }
    public LocalDate getCreateTime() { return createTime; }
    public void setCreateTime(LocalDate createTime) { this.createTime = createTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getSceneId() { return sceneId; }
    public void setSceneId(String sceneId) { this.sceneId = sceneId; }
    public String getDcid() { return dcid; }
    public void setDcid(String dcid) { this.dcid = dcid; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public Integer getDesignCompleteCount() { return designCompleteCount; }
    public void setDesignCompleteCount(Integer designCompleteCount) { this.designCompleteCount = designCompleteCount; }
    public Integer getTotalDesigners() { return totalDesigners; }
    public void setTotalDesigners(Integer totalDesigners) { this.totalDesigners = totalDesigners; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public String getStorageTime() { return storageTime; }
    public void setStorageTime(String storageTime) { this.storageTime = storageTime; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public String getSceneTags() { return sceneTags; }
    public void setSceneTags(String sceneTags) { this.sceneTags = sceneTags; }
    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }
    public String getDesignReason() { return designReason; }
    public void setDesignReason(String designReason) { this.designReason = designReason; }
    public String getTestScene() { return testScene; }
    public void setTestScene(String testScene) { this.testScene = testScene; }
    public String getGeneralization() { return generalization; }
    public void setGeneralization(String generalization) { this.generalization = generalization; }
    public String getGeneralizationItems() { return generalizationItems; }
    public void setGeneralizationItems(String generalizationItems) { this.generalizationItems = generalizationItems; }
    public String getTestMethod() { return testMethod; }
    public void setTestMethod(String testMethod) { this.testMethod = testMethod; }
    public String getTestObjective() { return testObjective; }
    public void setTestObjective(String testObjective) { this.testObjective = testObjective; }
    public String getEnvironmentRoad() { return environmentRoad; }
    public void setEnvironmentRoad(String environmentRoad) { this.environmentRoad = environmentRoad; }
    public String getEnvironmentSimulation() { return environmentSimulation; }
    public void setEnvironmentSimulation(String environmentSimulation) { this.environmentSimulation = environmentSimulation; }
    public String getEnvironmentField() { return environmentField; }
    public void setEnvironmentField(String environmentField) { this.environmentField = environmentField; }
    public String getTargetObject() { return targetObject; }
    public void setTargetObject(String targetObject) { this.targetObject = targetObject; }
    public String getEquipmentSimulation() { return equipmentSimulation; }
    public void setEquipmentSimulation(String equipmentSimulation) { this.equipmentSimulation = equipmentSimulation; }
    public String getEquipmentField() { return equipmentField; }
    public void setEquipmentField(String equipmentField) { this.equipmentField = equipmentField; }
    public String getRecordContent() { return recordContent; }
    public void setRecordContent(String recordContent) { this.recordContent = recordContent; }
    public String getVehicleInteraction() { return vehicleInteraction; }
    public void setVehicleInteraction(String vehicleInteraction) { this.vehicleInteraction = vehicleInteraction; }
    public String getVehicleLoad() { return vehicleLoad; }
    public void setVehicleLoad(String vehicleLoad) { this.vehicleLoad = vehicleLoad; }
    public String getVehicleSettings() { return vehicleSettings; }
    public void setVehicleSettings(String vehicleSettings) { this.vehicleSettings = vehicleSettings; }
    public String getTestProcedure() { return testProcedure; }
    public void setTestProcedure(String testProcedure) { this.testProcedure = testProcedure; }
    public String getPassCriteria() { return passCriteria; }
    public void setPassCriteria(String passCriteria) { this.passCriteria = passCriteria; }
    public String getEndConditions() { return endConditions; }
    public void setEndConditions(String endConditions) { this.endConditions = endConditions; }
}