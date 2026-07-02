package com.example.scenario.service;

import com.example.scenario.entity.AccidentData;
import com.example.scenario.repository.AccidentDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccidentDataService {

    @Autowired
    private AccidentDataRepository accidentDataRepository;

    public List<AccidentData> getAllAccidentData() {
        return accidentDataRepository.findAll();
    }

    public Optional<AccidentData> getAccidentDataById(String id) {
        return accidentDataRepository.findById(id);
    }

    public AccidentData saveAccidentData(AccidentData accidentData) {
        return accidentDataRepository.save(accidentData);
    }

    public void deleteAccidentData(String id) {
        accidentDataRepository.deleteById(id);
    }

    public List<AccidentData> getAccidentDataByStatus(String status) {
        return accidentDataRepository.findByStatus(status);
    }

    public AccidentData updateStatus(String id, String status) {
        AccidentData accidentData = accidentDataRepository.findById(id).orElseThrow();
        accidentData.setStatus(status);
        return accidentDataRepository.save(accidentData);
    }
}