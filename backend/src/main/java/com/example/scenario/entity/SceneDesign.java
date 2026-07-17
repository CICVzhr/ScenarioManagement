package com.example.scenario.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "scene_design")
public class SceneDesign {

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "designer", length = 50)
    private String designer;

    @Column(name = "tags", length = 500)
    private String tags;

    @Column(name = "verifiers", length = 200)
    private String verifiers;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "modify_count")
    private Integer modifyCount;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "dcid", length = 20)
    private String dcid;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "level", length = 20)
    private String level;

    @Column(name = "stage", length = 50)
    private String stage;

    @Column(name = "phase", length = 50)
    private String phase;

    @Column(name = "responsible", length = 100)
    private String responsible;

    @Column(name = "designers", length = 500)
    private String designers;

    @Column(name = "progress")
    private Integer progress;

    @Column(name = "design_complete_count")
    private Integer designCompleteCount;

    @Column(name = "total_designers")
    private Integer totalDesigners;

    public SceneDesign() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDesigner() { return designer; }
    public void setDesigner(String designer) { this.designer = designer; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public String getVerifiers() { return verifiers; }
    public void setVerifiers(String verifiers) { this.verifiers = verifiers; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getModifyCount() { return modifyCount; }
    public void setModifyCount(Integer modifyCount) { this.modifyCount = modifyCount; }
    public LocalDate getCreateTime() { return createTime; }
    public void setCreateTime(LocalDate createTime) { this.createTime = createTime; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDcid() { return dcid; }
    public void setDcid(String dcid) { this.dcid = dcid; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }
    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }
    public String getResponsible() { return responsible; }
    public void setResponsible(String responsible) { this.responsible = responsible; }
    public String getDesigners() { return designers; }
    public void setDesigners(String designers) { this.designers = designers; }
    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }
    public Integer getDesignCompleteCount() { return designCompleteCount; }
    public void setDesignCompleteCount(Integer designCompleteCount) { this.designCompleteCount = designCompleteCount; }
    public Integer getTotalDesigners() { return totalDesigners; }
    public void setTotalDesigners(Integer totalDesigners) { this.totalDesigners = totalDesigners; }
}