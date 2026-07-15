package com.example.scenario.repository;

import com.example.scenario.entity.SceneDesignDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SceneDesignDetailRepository extends JpaRepository<SceneDesignDetail, String> {
}