package com.example.scenario.controller;

import com.example.scenario.entity.AccidentDataDetail;
import com.example.scenario.service.AccidentDataDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accident-data-detail")
public class AccidentDataDetailController {

    @Autowired
    private AccidentDataDetailService service;

    @GetMapping("/{id}")
    public ResponseEntity<AccidentDataDetail> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccidentDataDetail> save(@PathVariable String id, @RequestBody AccidentDataDetail detail) {
        detail.setId(id);
        return ResponseEntity.ok(service.save(detail));
    }
}