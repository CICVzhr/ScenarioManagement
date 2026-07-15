package com.example.scenario.controller;

import com.example.scenario.entity.SysRole;
import com.example.scenario.entity.SysPermission;
import com.example.scenario.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping
    public ResponseEntity<List<SysRole>> findAll() {
        return ResponseEntity.ok(sysRoleService.findAll());
    }

    @GetMapping("/enabled")
    public ResponseEntity<List<SysRole>> findAllEnabled() {
        return ResponseEntity.ok(sysRoleService.findAllEnabled());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return sysRoleService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        try {
            SysRole role = new SysRole();
            role.setRoleName((String) body.get("roleName"));
            role.setRoleCode((String) body.get("roleCode"));
            role.setDescription((String) body.get("description"));
            role.setStatus((Integer) body.getOrDefault("status", 1));

            @SuppressWarnings("unchecked")
            List<Integer> permIdsRaw = (List<Integer>) body.get("permissionIds");
            List<Long> permIds = permIdsRaw != null
                ? permIdsRaw.stream().map(Integer::longValue).collect(Collectors.toList())
                : new java.util.ArrayList<>();

            return ResponseEntity.ok(sysRoleService.createRole(role, permIds));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            SysRole role = new SysRole();
            role.setId(id);
            role.setRoleName((String) body.get("roleName"));
            role.setRoleCode((String) body.get("roleCode"));
            role.setDescription((String) body.get("description"));
            role.setStatus((Integer) body.getOrDefault("status", 1));

            @SuppressWarnings("unchecked")
            List<Integer> permIdsRaw = (List<Integer>) body.get("permissionIds");
            List<Long> permIds = permIdsRaw != null
                ? permIdsRaw.stream().map(Integer::longValue).collect(Collectors.toList())
                : new java.util.ArrayList<>();

            return ResponseEntity.ok(sysRoleService.updateRole(role, permIds));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            sysRoleService.deleteRole(id);
            return ResponseEntity.ok(Map.of("message", "删除成功"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/permissions")
    public ResponseEntity<List<SysPermission>> getRolePermissions(@PathVariable Long id) {
        return ResponseEntity.ok(sysRoleService.getRolePermissions(id));
    }
}