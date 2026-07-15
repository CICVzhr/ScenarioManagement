package com.example.scenario.repository;

import com.example.scenario.entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysPermissionRepository extends JpaRepository<SysPermission, Long> {
    Optional<SysPermission> findByPermissionCode(String permissionCode);
    List<SysPermission> findByPermissionType(String permissionType);
    List<SysPermission> findByParentId(Long parentId);
    List<SysPermission> findByParentIdAndPermissionType(Long parentId, String permissionType);
    List<SysPermission> findByStatus(Integer status);
}