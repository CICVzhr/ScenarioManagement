package com.example.scenario.controller;

import com.example.scenario.entity.AccidentData;
import com.example.scenario.service.AccidentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accident-data")
public class AccidentDataController {

    @Autowired
    private AccidentDataService accidentDataService;

    @GetMapping
    public List<AccidentData> getAllAccidentData() {
        return accidentDataService.getAllAccidentData();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccidentData> getAccidentDataById(@PathVariable String id) {
        return accidentDataService.getAccidentDataById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AccidentData createAccidentData(@RequestBody AccidentData accidentData) {
        return accidentDataService.saveAccidentData(accidentData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccidentData> updateAccidentData(@PathVariable String id, @RequestBody AccidentData accidentData) {
        if (accidentData.getId() == null || !accidentData.getId().equals(id)) {
            accidentData.setId(id);
        }
        return ResponseEntity.ok(accidentDataService.saveAccidentData(accidentData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccidentData(@PathVariable String id) {
        accidentDataService.deleteAccidentData(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}")
    public List<AccidentData> getAccidentDataByStatus(@PathVariable String status) {
        return accidentDataService.getAccidentDataByStatus(status);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AccidentData> updateStatus(@PathVariable String id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        return ResponseEntity.ok(accidentDataService.updateStatus(id, status));
    }
}