package com.example.scenario.controller;

import com.example.scenario.entity.DataSource;
import com.example.scenario.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/data-sources")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;

    @GetMapping
    public List<DataSource> getAllDataSources() {
        return dataSourceService.getAllDataSources();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataSource> getDataSourceById(@PathVariable Long id) {
        return dataSourceService.getDataSourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DataSource createDataSource(@RequestBody DataSource dataSource) {
        return dataSourceService.saveDataSource(dataSource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataSource> updateDataSource(@PathVariable Long id, @RequestBody DataSource dataSource) {
        if (!dataSource.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dataSourceService.saveDataSource(dataSource));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDataSource(@PathVariable Long id) {
        dataSourceService.deleteDataSource(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DataSource> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        return ResponseEntity.ok(dataSourceService.updateStatus(id, status));
    }
}