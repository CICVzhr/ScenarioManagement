package com.example.scenario.service;

import com.example.scenario.entity.SceneDesignDetail;
import com.example.scenario.repository.SceneDesignDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SceneDesignDetailService {

    @Autowired
    private SceneDesignDetailRepository repository;

    public Optional<SceneDesignDetail> getById(String id) {
        return repository.findById(id);
    }

    public SceneDesignDetail save(SceneDesignDetail detail) {
        return repository.save(detail);
    }
}