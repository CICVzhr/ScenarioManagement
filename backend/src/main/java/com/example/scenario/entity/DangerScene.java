package com.example.scenario.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "danger_scene")
public class DangerScene {

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "version", length = 20)
    private String version;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "tags", length = 500)
    private String tags;

    @Column(name = "level", length = 20)
    private String level;

    @Column(name = "source", length = 50)
    private String source;

    @Column(name = "case_count")
    private Integer caseCount;

    @Column(name = "storage_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate storageTime;

    @Column(name = "status", length = 50)
    private String status;

    public DangerScene() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public Integer getCaseCount() { return caseCount; }
    public void setCaseCount(Integer caseCount) { this.caseCount = caseCount; }
    public LocalDate getStorageTime() { return storageTime; }
    public void setStorageTime(LocalDate storageTime) { this.storageTime = storageTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}