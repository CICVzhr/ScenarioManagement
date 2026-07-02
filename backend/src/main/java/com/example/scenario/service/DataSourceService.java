package com.example.scenario.service;

import com.example.scenario.entity.DataSource;
import com.example.scenario.repository.DataSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataSourceService {

    @Autowired
    private DataSourceRepository dataSourceRepository;

    public List<DataSource> getAllDataSources() {
        return dataSourceRepository.findAll();
    }

    public Optional<DataSource> getDataSourceById(Long id) {
        return dataSourceRepository.findById(id);
    }

    public DataSource saveDataSource(DataSource dataSource) {
        return dataSourceRepository.save(dataSource);
    }

    public void deleteDataSource(Long id) {
        dataSourceRepository.deleteById(id);
    }

    public DataSource updateStatus(Long id, String status) {
        DataSource dataSource = dataSourceRepository.findById(id).orElseThrow();
        dataSource.setStatus(status);
        return dataSourceRepository.save(dataSource);
    }
}