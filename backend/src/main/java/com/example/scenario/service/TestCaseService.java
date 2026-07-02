package com.example.scenario.service;

import com.example.scenario.entity.TestCase;
import com.example.scenario.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    public List<TestCase> getAllTestCases() {
        return testCaseRepository.findAll();
    }

    public Optional<TestCase> getTestCaseById(String id) {
        return testCaseRepository.findById(id);
    }

    public TestCase saveTestCase(TestCase testCase) {
        return testCaseRepository.save(testCase);
    }

    public void deleteTestCase(String id) {
        testCaseRepository.deleteById(id);
    }

    public List<TestCase> getTestCasesBySceneId(String sceneId) {
        return testCaseRepository.findBySceneId(sceneId);
    }

    public List<TestCase> getTestCasesByStatus(String status) {
        return testCaseRepository.findByStatus(status);
    }

    public TestCase updateStatus(String id, String status) {
        TestCase testCase = testCaseRepository.findById(id).orElseThrow();
        testCase.setStatus(status);
        return testCaseRepository.save(testCase);
    }
}