package com.example.scenario.service;

import com.example.scenario.entity.SysRole;
import com.example.scenario.entity.SysPermission;
import com.example.scenario.entity.SysRolePermission;
import com.example.scenario.repository.SysRoleRepository;
import com.example.scenario.repository.SysPermissionRepository;
import com.example.scenario.repository.SysRolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysRoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    @Autowired
    private SysRolePermissionRepository sysRolePermissionRepository;

    public List<SysRole> findAll() {
        return sysRoleRepository.findAll();
    }

    public List<SysRole> findAllEnabled() {
        return sysRoleRepository.findAll().stream()
            .filter(r -> r.getStatus() == 1)
            .collect(Collectors.toList());
    }

    public Optional<SysRole> findById(Long id) {
        return sysRoleRepository.findById(id);
    }

    @Transactional
    public SysRole createRole(SysRole role, List<Long> permissionIds) {
        if (sysRoleRepository.existsByRoleCode(role.getRoleCode())) {
            throw new RuntimeException("角色编码已存在");
        }
        SysRole saved = sysRoleRepository.save(role);

        for (Long permissionId : permissionIds) {
            if (!sysRolePermissionRepository.existsByRoleIdAndPermissionId(saved.getId(), permissionId)) {
                SysRolePermission rp = new SysRolePermission();
                rp.setRoleId(saved.getId());
                rp.setPermissionId(permissionId);
                sysRolePermissionRepository.save(rp);
            }
        }
        return saved;
    }

    @Transactional
    public SysRole updateRole(SysRole role, List<Long> permissionIds) {
        SysRole existing = sysRoleRepository.findById(role.getId())
            .orElseThrow(() -> new RuntimeException("角色不存在"));

        if (!existing.getRoleCode().equals(role.getRoleCode()) &&
            sysRoleRepository.existsByRoleCode(role.getRoleCode())) {
            throw new RuntimeException("角色编码已存在");
        }

        existing.setRoleName(role.getRoleName());
        existing.setRoleCode(role.getRoleCode());
        existing.setDescription(role.getDescription());
        existing.setStatus(role.getStatus());
        sysRoleRepository.save(existing);

        sysRolePermissionRepository.deleteByRoleId(existing.getId());
        for (Long permissionId : permissionIds) {
            SysRolePermission rp = new SysRolePermission();
            rp.setRoleId(existing.getId());
            rp.setPermissionId(permissionId);
            sysRolePermissionRepository.save(rp);
        }

        return existing;
    }

    @Transactional
    public void deleteRole(Long id) {
        sysRolePermissionRepository.deleteByRoleId(id);
        sysRoleRepository.deleteById(id);
    }

    public List<SysPermission> getRolePermissions(Long roleId) {
        List<SysRolePermission> rps = sysRolePermissionRepository.findByRoleId(roleId);
        return rps.stream()
            .map(rp -> sysPermissionRepository.findById(rp.getPermissionId()).orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}