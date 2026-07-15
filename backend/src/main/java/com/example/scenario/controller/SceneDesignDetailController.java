package com.example.scenario.controller;

import com.example.scenario.entity.SceneDesignDetail;
import com.example.scenario.service.SceneDesignDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scene-design-detail")
public class SceneDesignDetailController {

    @Autowired
    private SceneDesignDetailService service;

    @GetMapping("/{id}")
    public ResponseEntity<SceneDesignDetail> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SceneDesignDetail> save(@PathVariable String id, @RequestBody SceneDesignDetail detail) {
        detail.setId(id);
        return ResponseEntity.ok(service.save(detail));
    }
}