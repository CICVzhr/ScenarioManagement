package com.example.scenario.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "scene_design_detail")
public class SceneDesignDetail {

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "version", length = 20)
    private String version;

    @Column(name = "storage_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate storageTime;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "tags", length = 500)
    private String tags;

    @Column(name = "scene_tags", length = 500)
    private String sceneTags;

    @Column(name = "data_source", length = 500)
    private String dataSource;

    @Column(name = "design_reason", columnDefinition = "TEXT")
    private String designReason;

    @Column(name = "test_scene", columnDefinition = "TEXT")
    private String testScene;

    @Column(name = "generalization", columnDefinition = "TEXT")
    private String generalization;

    @Column(name = "generalization_items", columnDefinition = "TEXT")
    private String generalizationItems;

    @Column(name = "test_method", columnDefinition = "TEXT")
    private String testMethod;

    @Column(name = "test_objective", columnDefinition = "TEXT")
    private String testObjective;

    @Column(name = "environment_road", columnDefinition = "TEXT")
    private String environmentRoad;

    @Column(name = "environment_simulation", columnDefinition = "TEXT")
    private String environmentSimulation;

    @Column(name = "environment_field", columnDefinition = "TEXT")
    private String environmentField;

    @Column(name = "target_object", columnDefinition = "TEXT")
    private String targetObject;

    @Column(name = "equipment_simulation", columnDefinition = "TEXT")
    private String equipmentSimulation;

    @Column(name = "equipment_field", columnDefinition = "TEXT")
    private String equipmentField;

    @Column(name = "record_content", columnDefinition = "TEXT")
    private String recordContent;

    @Column(name = "vehicle_interaction", columnDefinition = "TEXT")
    private String vehicleInteraction;

    @Column(name = "vehicle_load", columnDefinition = "TEXT")
    private String vehicleLoad;

    @Column(name = "vehicle_settings", columnDefinition = "TEXT")
    private String vehicleSettings;

    @Column(name = "test_procedure", columnDefinition = "TEXT")
    private String testProcedure;

    @Column(name = "pass_criteria", columnDefinition = "TEXT")
    private String passCriteria;

    @Column(name = "end_conditions", columnDefinition = "TEXT")
    private String endConditions;

    public SceneDesignDetail() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public LocalDate getStorageTime() { return storageTime; }
    public void setStorageTime(LocalDate storageTime) { this.storageTime = storageTime; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
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