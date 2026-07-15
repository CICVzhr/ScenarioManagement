package com.example.scenario.service;

import com.example.scenario.entity.AccidentDataDetail;
import com.example.scenario.repository.AccidentDataDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccidentDataDetailService {

    @Autowired
    private AccidentDataDetailRepository repository;

    public Optional<AccidentDataDetail> getById(String id) {
        return repository.findById(id);
    }

    public AccidentDataDetail save(AccidentDataDetail detail) {
        return repository.save(detail);
    }
}