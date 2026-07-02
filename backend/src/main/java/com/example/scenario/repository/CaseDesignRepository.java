package com.example.scenario.repository;

import com.example.scenario.entity.CaseDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseDesignRepository extends JpaRepository<CaseDesign, String> {
    List<CaseDesign> findByResponsible(String responsible);
}