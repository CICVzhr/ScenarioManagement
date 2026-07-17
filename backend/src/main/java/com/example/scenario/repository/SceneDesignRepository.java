package com.example.scenario.repository;

import com.example.scenario.entity.SceneDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SceneDesignRepository extends JpaRepository<SceneDesign, String> {
    List<SceneDesign> findByStatus(String status);
    List<SceneDesign> findByDesigner(String designer);
    List<SceneDesign> findByPhase(String phase);
    List<SceneDesign> findByPhaseIn(List<String> phases);
}