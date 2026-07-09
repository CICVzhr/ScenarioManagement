package com.example.scenario.service;

import com.example.scenario.config.ResourceNotFoundException;
import com.example.scenario.entity.SystemSetting;
import com.example.scenario.repository.SystemSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemSettingService {

    @Autowired
    private SystemSettingRepository systemSettingRepository;

    public String getSettingValue(String key) {
        return systemSettingRepository.findBySettingKey(key)
                .map(SystemSetting::getSettingValue)
                .orElseThrow(() -> new ResourceNotFoundException("Setting not found: " + key));
    }

    public SystemSetting saveSetting(String key, String value) {
        SystemSetting setting = systemSettingRepository.findBySettingKey(key)
                .orElse(new SystemSetting());
        setting.setSettingKey(key);
        setting.setSettingValue(value);
        return systemSettingRepository.save(setting);
    }
}
