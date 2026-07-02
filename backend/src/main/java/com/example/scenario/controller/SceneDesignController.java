package com.example.scenario.controller;

import com.example.scenario.entity.SceneDesign;
import com.example.scenario.service.SceneDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scene-design")
public class SceneDesignController {

    @Autowired
    private SceneDesignService sceneDesignService;

    @GetMapping
    public List<SceneDesign> getAllSceneDesigns() {
        return sceneDesignService.getAllSceneDesigns();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SceneDesign> getSceneDesignById(@PathVariable String id) {
        return sceneDesignService.getSceneDesignById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SceneDesign createSceneDesign(@RequestBody SceneDesign sceneDesign) {
        return sceneDesignService.saveSceneDesign(sceneDesign);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SceneDesign> updateSceneDesign(@PathVariable String id, @RequestBody SceneDesign sceneDesign) {
        if (!sceneDesign.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(sceneDesignService.saveSceneDesign(sceneDesign));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSceneDesign(@PathVariable String id) {
        sceneDesignService.deleteSceneDesign(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}")
    public List<SceneDesign> getSceneDesignsByStatus(@PathVariable String status) {
        return sceneDesignService.getSceneDesignsByStatus(status);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SceneDesign> updateStatus(@PathVariable String id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        return ResponseEntity.ok(sceneDesignService.updateStatus(id, status));
    }

    @PatchMapping("/{id}/modify-count")
    public ResponseEntity<SceneDesign> updateModifyCount(@PathVariable String id, @RequestBody Map<String, Integer> request) {
        Integer modifyCount = request.get("modifyCount");
        return ResponseEntity.ok(sceneDesignService.updateModifyCount(id, modifyCount));
    }
}