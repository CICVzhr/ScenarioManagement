package com.example.scenario.controller;

import com.example.scenario.entity.DangerScene;
import com.example.scenario.service.DangerSceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danger-scenes")
public class DangerSceneController {

    @Autowired
    private DangerSceneService dangerSceneService;

    @GetMapping
    public List<DangerScene> getAllDangerScenes() {
        return dangerSceneService.getAllDangerScenes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DangerScene> getDangerSceneById(@PathVariable String id) {
        return dangerSceneService.getDangerSceneById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DangerScene createDangerScene(@RequestBody DangerScene dangerScene) {
        return dangerSceneService.saveDangerScene(dangerScene);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DangerScene> updateDangerScene(@PathVariable String id, @RequestBody DangerScene dangerScene) {
        if (!dangerScene.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dangerSceneService.saveDangerScene(dangerScene));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDangerScene(@PathVariable String id) {
        dangerSceneService.deleteDangerScene(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/level/{level}")
    public List<DangerScene> getDangerScenesByLevel(@PathVariable String level) {
        return dangerSceneService.getDangerScenesByLevel(level);
    }

    @GetMapping("/source/{source}")
    public List<DangerScene> getDangerScenesBySource(@PathVariable String source) {
        return dangerSceneService.getDangerScenesBySource(source);
    }
}