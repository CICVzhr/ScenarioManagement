package com.example.scenario.service;

import com.example.scenario.entity.ProcessTask;
import com.example.scenario.repository.ProcessTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcessTaskService {

    @Autowired
    private ProcessTaskRepository processTaskRepository;

    public List<ProcessTask> getAllProcessTasks() {
        return processTaskRepository.findAll();
    }

    public Optional<ProcessTask> getProcessTaskById(Long id) {
        return processTaskRepository.findById(id);
    }

    public ProcessTask saveProcessTask(ProcessTask processTask) {
        return processTaskRepository.save(processTask);
    }

    public void deleteProcessTask(Long id) {
        processTaskRepository.deleteById(id);
    }

    public List<ProcessTask> getProcessTasksByStatus(String status) {
        return processTaskRepository.findByStatus(status);
    }

    public List<ProcessTask> getProcessTasksByStage(String stage) {
        return processTaskRepository.findByStage(stage);
    }

    public ProcessTask updateStatus(Long id, String status) {
        ProcessTask processTask = processTaskRepository.findById(id).orElseThrow();
        processTask.setStatus(status);
        return processTaskRepository.save(processTask);
    }
}