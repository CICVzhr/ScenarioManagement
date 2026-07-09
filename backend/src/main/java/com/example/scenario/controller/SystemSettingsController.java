package com.example.scenario.controller;

import com.example.scenario.service.SystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/system-settings")
public class SystemSettingsController {

    @Autowired
    private SystemSettingService systemSettingService;

    @GetMapping("/{key}")
    public ResponseEntity<String> getSetting(@PathVariable String key) {
        try {
            String value = systemSettingService.getSettingValue(key);
            return ResponseEntity.ok(value);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{key}")
    public ResponseEntity<String> updateSetting(@PathVariable String key, @RequestBody Map<String, String> body) {
        String value = body.get("value");
        if (value == null) {
            return ResponseEntity.badRequest().build();
        }
        systemSettingService.saveSetting(key, value);
        return ResponseEntity.ok(value);
    }
}
