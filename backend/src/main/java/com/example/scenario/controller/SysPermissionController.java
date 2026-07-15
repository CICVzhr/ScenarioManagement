package com.example.scenario.controller;

import com.example.scenario.entity.SysPermission;
import com.example.scenario.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @GetMapping
    public ResponseEntity<List<SysPermission>> findAll() {
        return ResponseEntity.ok(sysPermissionService.findAll());
    }

    @GetMapping("/tree")
    public ResponseEntity<List<SysPermission>> getTree() {
        return ResponseEntity.ok(sysPermissionService.buildTree());
    }

    @GetMapping("/enabled")
    public ResponseEntity<List<SysPermission>> findAllEnabled() {
        return ResponseEntity.ok(sysPermissionService.findAllEnabled());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        SysPermission perm = sysPermissionService.findById(id);
        if (perm != null) {
            return ResponseEntity.ok(perm);
        }
        return ResponseEntity.notFound().build();
    }
}