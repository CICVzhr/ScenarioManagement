package com.example.scenario.repository;

import com.example.scenario.entity.ProcessTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessTaskRepository extends JpaRepository<ProcessTask, Long> {
    List<ProcessTask> findByStatus(String status);
    List<ProcessTask> findByStage(String stage);
    List<ProcessTask> findBySubmitter(String submitter);
}