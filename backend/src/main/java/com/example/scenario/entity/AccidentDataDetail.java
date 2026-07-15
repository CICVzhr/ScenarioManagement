package com.example.scenario.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "accident_data_detail")
public class AccidentDataDetail {

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "creator", length = 100)
    private String creator;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;

    @Column(name = "occur_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate occurTime;

    @Column(name = "location", length = 200)
    private String location;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "road_description", length = 200)
    private String roadDescription;

    @Column(name = "lighting_condition", length = 100)
    private String lightingCondition;

    @Column(name = "weather_condition", length = 100)
    private String weatherCondition;

    @Column(name = "perception_scheme", length = 200)
    private String perceptionScheme;

    @Column(name = "vehicle_type", length = 100)
    private String vehicleType;

    @Column(name = "adaptive_function", length = 200)
    private String adaptiveFunction;

    @Column(name = "process_description", columnDefinition = "TEXT")
    private String processDescription;

    @Column(name = "cause_initial_judgment", columnDefinition = "TEXT")
    private String causeInitialJudgment;

    @Column(name = "accident_type", length = 50)
    private String accidentType;

    @Column(name = "case_type", length = 50)
    private String caseType;

    @Column(name = "cause_tag", length = 200)
    private String causeTag;

    @Column(name = "level", length = 50)
    private String level;

    @Column(name = "media_link", length = 500)
    private String mediaLink;

    @Column(name = "media_file", length = 500)
    private String mediaFile;

    public AccidentDataDetail() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCreator() { return creator; }
    public void setCreator(String creator) { this.creator = creator; }
    public LocalDate getCreateTime() { return createTime; }
    public void setCreateTime(LocalDate createTime) { this.createTime = createTime; }
    public LocalDate getOccurTime() { return occurTime; }
    public void setOccurTime(LocalDate occurTime) { this.occurTime = occurTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getRoadDescription() { return roadDescription; }
    public void setRoadDescription(String roadDescription) { this.roadDescription = roadDescription; }
    public String getLightingCondition() { return lightingCondition; }
    public void setLightingCondition(String lightingCondition) { this.lightingCondition = lightingCondition; }
    public String getWeatherCondition() { return weatherCondition; }
    public void setWeatherCondition(String weatherCondition) { this.weatherCondition = weatherCondition; }
    public String getPerceptionScheme() { return perceptionScheme; }
    public void setPerceptionScheme(String perceptionScheme) { this.perceptionScheme = perceptionScheme; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public String getAdaptiveFunction() { return adaptiveFunction; }
    public void setAdaptiveFunction(String adaptiveFunction) { this.adaptiveFunction = adaptiveFunction; }
    public String getProcessDescription() { return processDescription; }
    public void setProcessDescription(String processDescription) { this.processDescription = processDescription; }
    public String getCauseInitialJudgment() { return causeInitialJudgment; }
    public void setCauseInitialJudgment(String causeInitialJudgment) { this.causeInitialJudgment = causeInitialJudgment; }
    public String getAccidentType() { return accidentType; }
    public void setAccidentType(String accidentType) { this.accidentType = accidentType; }
    public String getCaseType() { return caseType; }
    public void setCaseType(String caseType) { this.caseType = caseType; }
    public String getCauseTag() { return causeTag; }
    public void setCauseTag(String causeTag) { this.causeTag = causeTag; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getMediaLink() { return mediaLink; }
    public void setMediaLink(String mediaLink) { this.mediaLink = mediaLink; }
    public String getMediaFile() { return mediaFile; }
    public void setMediaFile(String mediaFile) { this.mediaFile = mediaFile; }
}