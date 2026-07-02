package com.example.scenario.controller;

import com.example.scenario.entity.ProcessTask;
import com.example.scenario.service.ProcessTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/process-tasks")
public class ProcessTaskController {

    @Autowired
    private ProcessTaskService processTaskService;

    @GetMapping
    public List<ProcessTask> getAllProcessTasks() {
        return processTaskService.getAllProcessTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessTask> getProcessTaskById(@PathVariable Long id) {
        return processTaskService.getProcessTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProcessTask createProcessTask(@RequestBody ProcessTask processTask) {
        return processTaskService.saveProcessTask(processTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessTask> updateProcessTask(@PathVariable Long id, @RequestBody ProcessTask processTask) {
        if (!processTask.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(processTaskService.saveProcessTask(processTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcessTask(@PathVariable Long id) {
        processTaskService.deleteProcessTask(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}")
    public List<ProcessTask> getProcessTasksByStatus(@PathVariable String status) {
        return processTaskService.getProcessTasksByStatus(status);
    }

    @GetMapping("/stage/{stage}")
    public List<ProcessTask> getProcessTasksByStage(@PathVariable String stage) {
        return processTaskService.getProcessTasksByStage(stage);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProcessTask> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        return ResponseEntity.ok(processTaskService.updateStatus(id, status));
    }
}