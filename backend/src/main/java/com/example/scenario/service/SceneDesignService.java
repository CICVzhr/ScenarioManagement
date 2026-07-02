package com.example.scenario.service;

import com.example.scenario.entity.SceneDesign;
import com.example.scenario.repository.SceneDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SceneDesignService {

    @Autowired
    private SceneDesignRepository sceneDesignRepository;

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

    public SceneDesign updateModifyCount(String id, Integer modifyCount) {
        SceneDesign sceneDesign = sceneDesignRepository.findById(id).orElseThrow();
        sceneDesign.setModifyCount(modifyCount);
        return sceneDesignRepository.save(sceneDesign);
    }
}