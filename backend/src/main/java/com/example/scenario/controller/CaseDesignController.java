package com.example.scenario.controller;

import com.example.scenario.entity.CaseDesign;
import com.example.scenario.service.CaseDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/case-design")
public class CaseDesignController {

    @Autowired
    private CaseDesignService caseDesignService;

    @GetMapping
    public List<CaseDesign> getAllCaseDesigns() {
        return caseDesignService.getAllCaseDesigns();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaseDesign> getCaseDesignById(@PathVariable String id) {
        return caseDesignService.getCaseDesignById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CaseDesign createCaseDesign(@RequestBody CaseDesign caseDesign) {
        return caseDesignService.saveCaseDesign(caseDesign);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaseDesign> updateCaseDesign(@PathVariable String id, @RequestBody CaseDesign caseDesign) {
        if (!caseDesign.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(caseDesignService.saveCaseDesign(caseDesign));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCaseDesign(@PathVariable String id) {
        caseDesignService.deleteCaseDesign(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/progress")
    public ResponseEntity<CaseDesign> updateProgress(@PathVariable String id, @RequestBody Map<String, Integer> request) {
        Integer progress = request.get("progress");
        return ResponseEntity.ok(caseDesignService.updateProgress(id, progress));
    }
}