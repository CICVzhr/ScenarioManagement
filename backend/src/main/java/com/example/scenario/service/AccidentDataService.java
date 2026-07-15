package com.example.scenario.service;

import com.example.scenario.entity.AccidentData;
import com.example.scenario.entity.AccidentDataDetail;
import com.example.scenario.entity.SceneDesign;
import com.example.scenario.repository.AccidentDataDetailRepository;
import com.example.scenario.repository.AccidentDataRepository;
import com.example.scenario.repository.SceneDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccidentDataService {

    @Autowired
    private AccidentDataRepository accidentDataRepository;

    @Autowired
    private SceneDesignRepository sceneDesignRepository;

    @Autowired
    private AccidentDataDetailRepository accidentDataDetailRepository;

    public List<AccidentData> getAllAccidentData() {
        return accidentDataRepository.findAll();
    }

    public Optional<AccidentData> getAccidentDataById(String id) {
        return accidentDataRepository.findById(id);
    }

    public AccidentData saveAccidentData(AccidentData accidentData) {
        return accidentDataRepository.save(accidentData);
    }

    public void deleteAccidentData(String id) {
        accidentDataRepository.deleteById(id);
    }

    public List<AccidentData> getAccidentDataByStatus(String status) {
        return accidentDataRepository.findByStatus(status);
    }

    public AccidentData updateStatus(String id, String status) {
        AccidentData accidentData = accidentDataRepository.findById(id).orElseThrow();
        accidentData.setStatus(status);
        AccidentData saved = accidentDataRepository.save(accidentData);

        if ("已通过".equals(status)) {
            SceneDesign sceneDesign = new SceneDesign();
            sceneDesign.setId("SD" + System.currentTimeMillis());
            sceneDesign.setDcid(id);
            sceneDesign.setName(accidentData.getName());
            sceneDesign.setDesigner(accidentData.getReporter());
            sceneDesign.setStatus("设计中");
            sceneDesign.setCreateTime(LocalDate.now());
            sceneDesign.setModifyCount(0);
            sceneDesign.setType(accidentData.getCaseType());
            sceneDesign.setLevel(accidentData.getAccidentLevel());
            sceneDesign.setStage("scenariocase");
            sceneDesignRepository.save(sceneDesign);

            AccidentDataDetail detail = new AccidentDataDetail();
            detail.setId(id);
            detail.setCreator(accidentData.getReporter());
            detail.setCreateTime(accidentData.getReportTime());
            detail.setOccurTime(accidentData.getOccurTime());
            detail.setLocation(accidentData.getLocation());
            detail.setTitle(accidentData.getName());
            detail.setRoadDescription(accidentData.getRoadType());
            detail.setWeatherCondition(accidentData.getWeather());
            detail.setVehicleType(accidentData.getVehicleType());
            detail.setAccidentType(accidentData.getAccidentType());
            detail.setCaseType(accidentData.getCaseType());
            detail.setLevel(accidentData.getAccidentLevel());
            accidentDataDetailRepository.save(detail);
        }

        return saved;
    }
}