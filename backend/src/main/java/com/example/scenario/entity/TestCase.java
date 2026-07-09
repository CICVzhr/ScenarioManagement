package com.example.scenario.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "test_case")
public class TestCase {

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "scene_id", length = 20)
    private String sceneId;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "version", length = 20)
    private String version;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;

    public TestCase() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSceneId() { return sceneId; }
    public void setSceneId(String sceneId) { this.sceneId = sceneId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public LocalDate getCreateTime() { return createTime; }
    public void setCreateTime(LocalDate createTime) { this.createTime = createTime; }
}