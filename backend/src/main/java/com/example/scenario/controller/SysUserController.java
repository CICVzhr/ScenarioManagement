package com.example.scenario.controller;

import com.example.scenario.entity.SysUser;
import com.example.scenario.entity.SysRole;
import com.example.scenario.entity.SysPermission;
import com.example.scenario.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping
    public ResponseEntity<List<SysUser>> findAll() {
        return ResponseEntity.ok(sysUserService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return sysUserService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        try {
            SysUser user = new SysUser();
            user.setUsername((String) body.get("username"));
            user.setPassword((String) body.get("password"));
            user.setRealName((String) body.get("realName"));
            user.setEmail((String) body.get("email"));
            user.setPhone((String) body.get("phone"));
            user.setStatus((Integer) body.getOrDefault("status", 1));

            @SuppressWarnings("unchecked")
            List<Integer> roleIdsRaw = (List<Integer>) body.get("roleIds");
            List<Long> roleIds = roleIdsRaw.stream().map(Integer::longValue).collect(java.util.stream.Collectors.toList());

            return ResponseEntity.ok(sysUserService.createUser(user, roleIds));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            SysUser user = new SysUser();
            user.setId(id);
            user.setUsername((String) body.get("username"));
            user.setPassword((String) body.get("password"));
            user.setRealName((String) body.get("realName"));
            user.setEmail((String) body.get("email"));
            user.setPhone((String) body.get("phone"));
            user.setStatus((Integer) body.getOrDefault("status", 1));

            @SuppressWarnings("unchecked")
            List<Integer> roleIdsRaw = (List<Integer>) body.get("roleIds");
            List<Long> roleIds = roleIdsRaw.stream().map(Integer::longValue).collect(java.util.stream.Collectors.toList());

            return ResponseEntity.ok(sysUserService.updateUser(user, roleIds));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            sysUserService.deleteUser(id);
            return ResponseEntity.ok(Map.of("message", "删除成功"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<List<SysRole>> getUserRoles(@PathVariable Long id) {
        return ResponseEntity.ok(sysUserService.getUserRoles(id));
    }

    @GetMapping("/{id}/permissions")
    public ResponseEntity<List<SysPermission>> getUserPermissions(@PathVariable Long id) {
        return ResponseEntity.ok(sysUserService.getUserPermissions(id));
    }

    @GetMapping("/{id}/check-permission")
    public ResponseEntity<Map<String, Boolean>> checkPermission(
            @PathVariable Long id, @RequestParam String code) {
        boolean has = sysUserService.hasPermission(id, code);
        return ResponseEntity.ok(Map.of("hasPermission", has));
    }
}