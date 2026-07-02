package com.example.scenario.service;

import com.example.scenario.entity.DangerScene;
import com.example.scenario.repository.DangerSceneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DangerSceneService {

    @Autowired
    private DangerSceneRepository dangerSceneRepository;

    public List<DangerScene> getAllDangerScenes() {
        return dangerSceneRepository.findAll();
    }

    public Optional<DangerScene> getDangerSceneById(String id) {
        return dangerSceneRepository.findById(id);
    }

    public DangerScene saveDangerScene(DangerScene dangerScene) {
        return dangerSceneRepository.save(dangerScene);
    }

    public void deleteDangerScene(String id) {
        dangerSceneRepository.deleteById(id);
    }

    public List<DangerScene> getDangerScenesByLevel(String level) {
        return dangerSceneRepository.findByLevel(level);
    }

    public List<DangerScene> getDangerScenesBySource(String source) {
        return dangerSceneRepository.findBySource(source);
    }
}