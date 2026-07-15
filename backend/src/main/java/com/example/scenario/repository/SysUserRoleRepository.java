package com.example.scenario.repository;

import com.example.scenario.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole, Long> {
    List<SysUserRole> findByUserId(Long userId);
    List<SysUserRole> findByRoleId(Long roleId);
    @Modifying
    @Transactional
    void deleteByUserId(Long userId);
    @Modifying
    @Transactional
    void deleteByRoleId(Long roleId);
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);
}