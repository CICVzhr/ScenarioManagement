package com.example.scenario.repository;

import com.example.scenario.entity.AccidentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccidentDataRepository extends JpaRepository<AccidentData, String> {
    List<AccidentData> findByStatus(String status);
    List<AccidentData> findByAccidentLevel(String accidentLevel);
    List<AccidentData> findByRoadType(String roadType);
}