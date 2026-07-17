package com.example.scenario.service;

import com.example.scenario.dto.SceneDesignFullDTO;
import com.example.scenario.entity.AccidentData;
import com.example.scenario.entity.DangerScene;
import com.example.scenario.entity.SceneDesign;
import com.example.scenario.entity.SceneDesignDetail;
import com.example.scenario.repository.AccidentDataRepository;
import com.example.scenario.repository.DangerSceneRepository;
import com.example.scenario.repository.SceneDesignDetailRepository;
import com.example.scenario.repository.SceneDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SceneDesignService {

    @Autowired
    private SceneDesignRepository sceneDesignRepository;

    @Autowired
    private AccidentDataRepository accidentDataRepository;

    @Autowired
    private DangerSceneRepository dangerSceneRepository;

    @Autowired
    private SceneDesignDetailRepository sceneDesignDetailRepository;

    public List<SceneDesign> getAllSceneDesigns() {
        return sceneDesignRepository.findAll();
    }

    public List<SceneDesign> getSceneDesignsByPhase(String phase) {
        return sceneDesignRepository.findByPhase(phase);
    }

    public Optional<SceneDesign> getSceneDesignById(String id) {
        return sceneDesignRepository.findById(id);
    }

    public SceneDesign saveSceneDesign(SceneDesign sceneDesign) {
        return sceneDesignRepository.save(sceneDesign);
    }

    public void deleteSceneDesign(String id) {
        sceneDesignRepository.deleteById(id);
    }

    public List<SceneDesign> getSceneDesignsByStatus(String status) {
        return sceneDesignRepository.findByStatus(status);
    }

    public SceneDesign updateStatus(String id, String status) {
        SceneDesign sceneDesign = sceneDesignRepository.findById(id).orElseThrow();
        sceneDesign.setStatus(status);
        return sceneDesignRepository.save(sceneDesign);
    }

    @Transactional
    public SceneDesign submitSceneDesign(String id, String newStatus) {
        SceneDesign sceneDesign = sceneDesignRepository.findById(id).orElseThrow();
        String currentStatus = sceneDesign.getStatus();
        String currentPhase = sceneDesign.getPhase();

        if (currentPhase == null || "scene_design".equals(currentPhase)) {
            handleSceneDesignPhase(sceneDesign, currentStatus, newStatus);
        } else if ("case_design".equals(currentPhase)) {
            handleCaseDesignPhase(sceneDesign, currentStatus, newStatus);
        } else if ("case_dev".equals(currentPhase)) {
            handleCaseDevPhase(sceneDesign, currentStatus, newStatus);
        } else {
            throw new IllegalStateException("无效的阶段: " + currentPhase);
        }

        return sceneDesignRepository.save(sceneDesign);
    }

    private void handleSceneDesignPhase(SceneDesign sceneDesign, String currentStatus, String newStatus) {
        if ("设计中".equals(currentStatus) && "审核中".equals(newStatus)) {
            sceneDesign.setStatus(newStatus);
        } else if ("审核中".equals(currentStatus) && ("待验证".equals(newStatus) || "修改中".equals(newStatus))) {
            sceneDesign.setStatus(newStatus);
        } else if (("待验证".equals(currentStatus) || "修改中".equals(currentStatus)) && "已完成".equals(newStatus)) {
            transitionToCaseDesignPhase(sceneDesign);
        } else {
            throw new IllegalStateException("无效的场景设计状态流转: " + currentStatus + " -> " + newStatus);
        }
    }

    private void handleCaseDesignPhase(SceneDesign sceneDesign, String currentStatus, String newStatus) {
        if ("用例设计中".equals(currentStatus) && "审批中".equals(newStatus)) {
            sceneDesign.setStatus(newStatus);
        } else if ("审批中".equals(currentStatus) && "已完成".equals(newStatus)) {
            transitionToCaseDevPhase(sceneDesign);
        } else {
            throw new IllegalStateException("无效的用例设计状态流转: " + currentStatus + " -> " + newStatus);
        }
    }

    private void handleCaseDevPhase(SceneDesign sceneDesign, String currentStatus, String newStatus) {
        if ("开发中".equals(currentStatus) && "测试中".equals(newStatus)) {
            sceneDesign.setStatus(newStatus);
        } else if ("测试中".equals(currentStatus) && "已完成".equals(newStatus)) {
            completeFinalPhase(sceneDesign);
        } else {
            throw new IllegalStateException("无效的用例开发状态流转: " + currentStatus + " -> " + newStatus);
        }
    }

    @Transactional
    private void transitionToCaseDesignPhase(SceneDesign sceneDesign) {
        sceneDesign.setStatus("用例设计中");
        sceneDesign.setPhase("case_design");

        String dcid = sceneDesign.getDcid();
        if (dcid != null && !dcid.isEmpty()) {
            Optional<AccidentData> accidentOpt = accidentDataRepository.findById(dcid);
            if (accidentOpt.isPresent()) {
                AccidentData accidentData = accidentOpt.get();
                accidentData.setStatus("设计完成");
                accidentDataRepository.save(accidentData);
            }
        }

        DangerScene dangerScene = new DangerScene();
        dangerScene.setId(sceneDesign.getId());
        dangerScene.setName(sceneDesign.getName());
        dangerScene.setVersion("v1.0");
        dangerScene.setLevel(sceneDesign.getLevel());
        dangerScene.setSource("场景设计");
        dangerScene.setCaseCount(0);
        dangerScene.setStorageTime(LocalDate.now());
        dangerScene.setStatus("用例设计");
        dangerSceneRepository.save(dangerScene);

        sceneDesignRepository.save(sceneDesign);
    }

    @Transactional
    private void transitionToCaseDevPhase(SceneDesign sceneDesign) {
        sceneDesign.setStatus("开发中");
        sceneDesign.setPhase("case_dev");

        Optional<DangerScene> dangerOpt = dangerSceneRepository.findById(sceneDesign.getId());
        if (dangerOpt.isPresent()) {
            DangerScene dangerScene = dangerOpt.get();
            dangerScene.setStatus("用例开发");
            dangerSceneRepository.save(dangerScene);
        }

        sceneDesignRepository.save(sceneDesign);
    }

    @Transactional
    private void completeFinalPhase(SceneDesign sceneDesign) {
        sceneDesign.setStatus("已完成");
        sceneDesign.setPhase("completed");

        Optional<DangerScene> dangerOpt = dangerSceneRepository.findById(sceneDesign.getId());
        if (dangerOpt.isPresent()) {
            DangerScene dangerScene = dangerOpt.get();
            dangerScene.setStatus("已归档");
            dangerSceneRepository.save(dangerScene);
        }

        sceneDesignRepository.save(sceneDesign);
    }

    public SceneDesign updateModifyCount(String id, Integer modifyCount) {
        SceneDesign sceneDesign = sceneDesignRepository.findById(id).orElseThrow();
        sceneDesign.setModifyCount(modifyCount);
        return sceneDesignRepository.save(sceneDesign);
    }

    @Transactional
    public SceneDesign advanceInCurrentPhase(String id, String action) {
        SceneDesign sceneDesign = sceneDesignRepository.findById(id).orElseThrow();
        String currentStatus = sceneDesign.getStatus();

        switch (action) {
            case "submit_review":
                if ("设计中".equals(currentStatus)) {
                    return submitSceneDesign(id, "审核中");
                } else if ("用例设计中".equals(currentStatus)) {
                    return submitSceneDesign(id, "审批中");
                }
                break;
            case "approve":
                if ("审核中".equals(currentStatus)) {
                    return submitSceneDesign(id, "待验证");
                } else if ("审批中".equals(currentStatus)) {
                    return submitSceneDesign(id, "已完成");
                } else if ("待验证".equals(currentStatus)) {
                    return submitSceneDesign(id, "已完成");
                }
                break;
            case "request_modification":
                if ("审核中".equals(currentStatus)) {
                    return submitSceneDesign(id, "修改中");
                }
                break;
            case "start_dev":
                if ("开发中".equals(currentStatus)) {
                    return submitSceneDesign(id, "测试中");
                }
                break;
            case "complete":
                if ("测试中".equals(currentStatus)) {
                    return submitSceneDesign(id, "已完成");
                }
                break;
            default:
                throw new IllegalStateException("无效的操作: " + action);
        }

        throw new IllegalStateException("无法执行操作 '" + action + "' 在当前状态 '" + currentStatus + "'");
    }

    public SceneDesignFullDTO getSceneDesignFullData(String id) {
        SceneDesign sceneDesign = sceneDesignRepository.findById(id).orElse(null);
        if (sceneDesign == null) return null;

        SceneDesignFullDTO dto = new SceneDesignFullDTO();
        dto.setId(sceneDesign.getId());
        dto.setName(sceneDesign.getName());
        dto.setResponsible(sceneDesign.getResponsible());
        dto.setDesigners(sceneDesign.getDesigners());
        dto.setProgress(sceneDesign.getProgress());
        dto.setCreateTime(sceneDesign.getCreateTime());
        dto.setStatus(sceneDesign.getStatus());
        dto.setSceneId(id);
        dto.setDcid(sceneDesign.getDcid());
        dto.setType(sceneDesign.getType());
        dto.setLevel(sceneDesign.getLevel());
        dto.setDesignCompleteCount(sceneDesign.getDesignCompleteCount());
        dto.setTotalDesigners(sceneDesign.getTotalDesigners());

        String detailId = "SDD" + id.substring(2);
        Optional<SceneDesignDetail> detailOpt = sceneDesignDetailRepository.findById(detailId);

        if (detailOpt.isEmpty()) {
            detailOpt = sceneDesignDetailRepository.findById(detailId);
        }

        if (detailOpt.isPresent()) {
            SceneDesignDetail detail = detailOpt.get();
            dto.setVersion(detail.getVersion());
            if (detail.getStorageTime() != null) {
                dto.setStorageTime(detail.getStorageTime().toString());
            }
            dto.setName(detail.getName() != null ? detail.getName() : sceneDesign.getName());
            dto.setTags(detail.getTags());
            dto.setSceneTags(detail.getSceneTags());
            dto.setDataSource(detail.getDataSource());
            dto.setDesignReason(detail.getDesignReason());
            dto.setTestScene(detail.getTestScene());
            dto.setGeneralization(detail.getGeneralization());
            dto.setGeneralizationItems(detail.getGeneralizationItems());
            dto.setTestMethod(detail.getTestMethod());
            dto.setTestObjective(detail.getTestObjective());
            dto.setEnvironmentRoad(detail.getEnvironmentRoad());
            dto.setEnvironmentSimulation(detail.getEnvironmentSimulation());
            dto.setEnvironmentField(detail.getEnvironmentField());
            dto.setTargetObject(detail.getTargetObject());
            dto.setEquipmentSimulation(detail.getEquipmentSimulation());
            dto.setEquipmentField(detail.getEquipmentField());
            dto.setRecordContent(detail.getRecordContent());
            dto.setVehicleInteraction(detail.getVehicleInteraction());
            dto.setVehicleLoad(detail.getVehicleLoad());
            dto.setVehicleSettings(detail.getVehicleSettings());
            dto.setTestProcedure(detail.getTestProcedure());
            dto.setPassCriteria(detail.getPassCriteria());
            dto.setEndConditions(detail.getEndConditions());
        }

        if (dto.getName() == null || dto.getName().isEmpty()) {
            dto.setName(sceneDesign.getName());
        }
        if (dto.getVersion() == null) {
            dto.setVersion("v1.0");
        }

        return dto;
    }

    public List<SceneDesignFullDTO> getAllSceneDesignsFullData() {
        List<SceneDesign> all = sceneDesignRepository.findAll();
        return all.stream()
                .map(sd -> getSceneDesignFullData(sd.getId()))
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }
}