package com.example.scenario.controller;

import com.example.scenario.entity.TestCase;
import com.example.scenario.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test-cases")
public class TestCaseController {

    @Autowired
    private TestCaseService testCaseService;

    @GetMapping
    public List<TestCase> getAllTestCases() {
        return testCaseService.getAllTestCases();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCase> getTestCaseById(@PathVariable String id) {
        return testCaseService.getTestCaseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TestCase createTestCase(@RequestBody TestCase testCase) {
        return testCaseService.saveTestCase(testCase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestCase> updateTestCase(@PathVariable String id, @RequestBody TestCase testCase) {
        if (!testCase.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(testCaseService.saveTestCase(testCase));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestCase(@PathVariable String id) {
        testCaseService.deleteTestCase(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/scene/{sceneId}")
    public List<TestCase> getTestCasesBySceneId(@PathVariable String sceneId) {
        return testCaseService.getTestCasesBySceneId(sceneId);
    }

    @GetMapping("/status/{status}")
    public List<TestCase> getTestCasesByStatus(@PathVariable String status) {
        return testCaseService.getTestCasesByStatus(status);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TestCase> updateStatus(@PathVariable String id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        return ResponseEntity.ok(testCaseService.updateStatus(id, status));
    }
}