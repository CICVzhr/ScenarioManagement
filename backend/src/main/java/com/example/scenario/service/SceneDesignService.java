package com.example.scenario.service;

import com.example.scenario.entity.AccidentData;
import com.example.scenario.entity.CaseDesign;
import com.example.scenario.entity.DangerScene;
import com.example.scenario.entity.SceneDesign;
import com.example.scenario.entity.SceneDesignDetail;
import com.example.scenario.repository.AccidentDataRepository;
import com.example.scenario.repository.CaseDesignRepository;
import com.example.scenario.repository.DangerSceneRepository;
import com.example.scenario.repository.SceneDesignDetailRepository;
import com.example.scenario.repository.SceneDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SceneDesignService {

    @Autowired
    private SceneDesignRepository sceneDesignRepository;

    @Autowired
    private AccidentDataRepository accidentDataRepository;

    @Autowired
    private CaseDesignRepository caseDesignRepository;

    @Autowired
    private DangerSceneRepository dangerSceneRepository;

    @Autowired
    private SceneDesignDetailRepository sceneDesignDetailRepository;

    public List<SceneDesign> getAllSceneDesigns() {
        return sceneDesignRepository.findAll();
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

        if ("设计中".equals(currentStatus) && "审核中".equals(newStatus)) {
            sceneDesign.setStatus(newStatus);
        } else if ("审核中".equals(currentStatus) && "待验证".equals(newStatus)) {
            sceneDesign.setStatus(newStatus);
        } else if ("待验证".equals(currentStatus) && "已完成".equals(newStatus)) {
            sceneDesign.setStatus(newStatus);
            sceneDesign = sceneDesignRepository.save(sceneDesign);

            String dcid = sceneDesign.getDcid();
            if (dcid != null && !dcid.isEmpty()) {
                Optional<AccidentData> accidentOpt = accidentDataRepository.findById(dcid);
                if (accidentOpt.isPresent()) {
                    AccidentData accidentData = accidentOpt.get();
                    accidentData.setStatus("设计完成");
                    accidentDataRepository.save(accidentData);
                }
            }

            CaseDesign caseDesign = new CaseDesign();
            caseDesign.setId(id);
            caseDesign.setName(sceneDesign.getName());
            caseDesign.setStatus("设计中");
            caseDesign.setProgress(0);
            caseDesign.setCreateTime(LocalDate.now());
            caseDesign.setSceneId(id);
            caseDesignRepository.save(caseDesign);

            DangerScene dangerScene = new DangerScene();
            dangerScene.setId(id);
            dangerScene.setName(sceneDesign.getName());
            dangerScene.setVersion("v1.0");
            dangerScene.setLevel(sceneDesign.getLevel());
            dangerScene.setSource("场景设计");
            dangerScene.setCaseCount(0);
            dangerScene.setStorageTime(LocalDate.now());
            dangerScene.setStatus("用例开发");
            dangerSceneRepository.save(dangerScene);

            return sceneDesign;
        } else {
            throw new IllegalStateException("无效的状态流转: " + currentStatus + " -> " + newStatus);
        }

        return sceneDesignRepository.save(sceneDesign);
    }

    public SceneDesign updateModifyCount(String id, Integer modifyCount) {
        SceneDesign sceneDesign = sceneDesignRepository.findById(id).orElseThrow();
        sceneDesign.setModifyCount(modifyCount);
        return sceneDesignRepository.save(sceneDesign);
    }
}