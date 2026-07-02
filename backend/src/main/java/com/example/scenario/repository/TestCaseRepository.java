package com.example.scenario.repository;

import com.example.scenario.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, String> {
    List<TestCase> findBySceneId(String sceneId);
    List<TestCase> findByStatus(String status);
    List<TestCase> findByType(String type);
}