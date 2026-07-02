package com.example.scenario.entity;

import jakarta.persistence.*;
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
    private LocalDate createTime;

    @Column(name = "description", length = 1000)
    private String description;

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
} 