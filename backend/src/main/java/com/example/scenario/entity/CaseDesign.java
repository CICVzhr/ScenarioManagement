package com.example.scenario.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "case_design")
public class CaseDesign {

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "responsible", length = 100)
    private String responsible;

    @Column(name = "progress")
    private Integer progress;

    @Column(name = "create_time")
    private LocalDate createTime;

    public CaseDesign() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getResponsible() { return responsible; }
    public void setResponsible(String responsible) { this.responsible = responsible; }
    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }
    public LocalDate getCreateTime() { return createTime; }
    public void setCreateTime(LocalDate createTime) { this.createTime = createTime; }
}