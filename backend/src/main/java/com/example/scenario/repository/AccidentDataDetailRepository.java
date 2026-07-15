package com.example.scenario.repository;

import com.example.scenario.entity.AccidentDataDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentDataDetailRepository extends JpaRepository<AccidentDataDetail, String> {
}