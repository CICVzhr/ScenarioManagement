package com.example.scenario.service;

import com.example.scenario.entity.SysPermission;
import com.example.scenario.repository.SysPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysPermissionService {

    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    public List<SysPermission> findAll() {
        return sysPermissionRepository.findAll();
    }

    public List<SysPermission> findAllEnabled() {
        return sysPermissionRepository.findByStatus(1);
    }

    public List<SysPermission> findByType(String type) {
        return sysPermissionRepository.findByPermissionType(type);
    }

    public List<SysPermission> findByParentId(Long parentId) {
        return sysPermissionRepository.findByParentId(parentId);
    }

    public List<SysPermission> buildTree() {
        List<SysPermission> all = findAllEnabled();
        List<SysPermission> roots = new ArrayList<>();
        for (SysPermission perm : all) {
            if (perm.getParentId() == 0) {
                roots.add(perm);
                perm.setParentId(null);
            }
        }
        for (SysPermission root : roots) {
            buildChildren(root, all);
        }
        return roots;
    }

    private void buildChildren(SysPermission parent, List<SysPermission> all) {
        List<SysPermission> children = new ArrayList<>();
        for (SysPermission perm : all) {
            if (perm.getParentId() != null && perm.getParentId().equals(parent.getId())) {
                children.add(perm);
            }
        }
        for (SysPermission child : children) {
            buildChildren(child, all);
        }
        parent.setChildren(children);
    }

    public SysPermission findById(Long id) {
        return sysPermissionRepository.findById(id).orElse(null);
    }

    public SysPermission createPermission(SysPermission permission) {
        return sysPermissionRepository.save(permission);
    }

    public SysPermission updatePermission(SysPermission permission) {
        return sysPermissionRepository.save(permission);
    }

    public void deletePermission(Long id) {
        sysPermissionRepository.deleteById(id);
    }
}