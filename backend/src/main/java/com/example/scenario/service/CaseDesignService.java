package com.example.scenario.service;

import com.example.scenario.entity.CaseDesign;
import com.example.scenario.repository.CaseDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaseDesignService {

    @Autowired
    private CaseDesignRepository caseDesignRepository;

    public List<CaseDesign> getAllCaseDesigns() {
        return caseDesignRepository.findAll();
    }

    public Optional<CaseDesign> getCaseDesignById(String id) {
        return caseDesignRepository.findById(id);
    }

    public CaseDesign saveCaseDesign(CaseDesign caseDesign) {
        return caseDesignRepository.save(caseDesign);
    }

    public void deleteCaseDesign(String id) {
        caseDesignRepository.deleteById(id);
    }

    public CaseDesign updateProgress(String id, Integer progress) {
        CaseDesign caseDesign = caseDesignRepository.findById(id).orElseThrow();
        caseDesign.setProgress(progress);
        return caseDesignRepository.save(caseDesign);
    }
}