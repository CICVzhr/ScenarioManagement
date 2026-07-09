package com.example.scenario.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "accident_data")
public class AccidentData {

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "reporter", length = 50)
    private String reporter;

    @Column(name = "report_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportTime;

    @Column(name = "occur_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate occurTime;

    @Column(name = "location", length = 200)
    private String location;

    @Column(name = "road_type", length = 50)
    private String roadType;

    @Column(name = "weather", length = 50)
    private String weather;

    @Column(name = "vehicle_type", length = 50)
    private String vehicleType;

    @Column(name = "system_version", length = 50)
    private String systemVersion;

    @Column(name = "accident_type", length = 50)
    private String accidentType;

    @Column(name = "accident_level", length = 50)
    private String accidentLevel;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "case_type", length = 50)
    private String caseType;

    public AccidentData() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getReporter() { return reporter; }
    public void setReporter(String reporter) { this.reporter = reporter; }
    public LocalDate getReportTime() { return reportTime; }
    public void setReportTime(LocalDate reportTime) { this.reportTime = reportTime; }
    public LocalDate getOccurTime() { return occurTime; }
    public void setOccurTime(LocalDate occurTime) { this.occurTime = occurTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getRoadType() { return roadType; }
    public void setRoadType(String roadType) { this.roadType = roadType; }
    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public String getSystemVersion() { return systemVersion; }
    public void setSystemVersion(String systemVersion) { this.systemVersion = systemVersion; }
    public String getAccidentType() { return accidentType; }
    public void setAccidentType(String accidentType) { this.accidentType = accidentType; }
    public String getAccidentLevel() { return accidentLevel; }
    public void setAccidentLevel(String accidentLevel) { this.accidentLevel = accidentLevel; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCaseType() { return caseType; }
    public void setCaseType(String caseType) { this.caseType = caseType; }
}