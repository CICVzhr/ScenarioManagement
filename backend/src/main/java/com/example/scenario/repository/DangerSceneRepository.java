package com.example.scenario.repository;

import com.example.scenario.entity.DangerScene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DangerSceneRepository extends JpaRepository<DangerScene, String> {
    List<DangerScene> findByLevel(String level);
    List<DangerScene> findBySource(String source);
}