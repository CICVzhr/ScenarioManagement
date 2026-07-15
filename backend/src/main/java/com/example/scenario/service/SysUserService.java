package com.example.scenario.service;

import com.example.scenario.entity.SysUser;
import com.example.scenario.entity.SysRole;
import com.example.scenario.entity.SysPermission;
import com.example.scenario.entity.SysUserRole;
import com.example.scenario.entity.SysRolePermission;
import com.example.scenario.repository.SysUserRepository;
import com.example.scenario.repository.SysRoleRepository;
import com.example.scenario.repository.SysPermissionRepository;
import com.example.scenario.repository.SysUserRoleRepository;
import com.example.scenario.repository.SysRolePermissionRepository;
import com.example.scenario.dto.LoginRequest;
import com.example.scenario.dto.LoginResponse;
import com.example.scenario.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;

    @Autowired
    private SysRolePermissionRepository sysRolePermissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Optional<SysUser> findByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }

    public Optional<SysUser> findById(Long id) {
        return sysUserRepository.findById(id);
    }

    public List<SysUser> findAll() {
        return sysUserRepository.findAll();
    }

    @Transactional
    public SysUser createUser(SysUser user, List<Long> roleIds) {
        if (sysUserRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        SysUser saved = sysUserRepository.save(user);

        for (Long roleId : roleIds) {
            if (!sysUserRoleRepository.existsByUserIdAndRoleId(saved.getId(), roleId)) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(saved.getId());
                ur.setRoleId(roleId);
                sysUserRoleRepository.save(ur);
            }
        }
        return saved;
    }

    @Transactional
    public SysUser updateUser(SysUser user, List<Long> roleIds) {
        SysUser existing = sysUserRepository.findById(user.getId())
            .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (!existing.getUsername().equals(user.getUsername()) &&
            sysUserRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        existing.setRealName(user.getRealName());
        existing.setEmail(user.getEmail());
        existing.setPhone(user.getPhone());
        existing.setStatus(user.getStatus());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        sysUserRepository.save(existing);

        sysUserRoleRepository.deleteByUserId(existing.getId());
        for (Long roleId : roleIds) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(existing.getId());
            ur.setRoleId(roleId);
            sysUserRoleRepository.save(ur);
        }

        return existing;
    }

    @Transactional
    public void deleteUser(Long id) {
        sysUserRoleRepository.deleteByUserId(id);
        sysUserRepository.deleteById(id);
    }

    public List<SysRole> getUserRoles(Long userId) {
        List<SysUserRole> userRoles = sysUserRoleRepository.findByUserId(userId);
        return userRoles.stream()
            .map(ur -> sysRoleRepository.findById(ur.getRoleId()).orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public List<SysPermission> getUserPermissions(Long userId) {
        List<SysRole> roles = getUserRoles(userId);
        if (roles.isEmpty()) {
            return new ArrayList<>();
        }

        Set<Long> permissionIds = new HashSet<>();
        for (SysRole role : roles) {
            List<SysRolePermission> rps = sysRolePermissionRepository.findByRoleId(role.getId());
            for (SysRolePermission rp : rps) {
                permissionIds.add(rp.getPermissionId());
            }
        }

        return permissionIds.stream()
            .map(pid -> sysPermissionRepository.findById(pid).orElse(null))
            .filter(Objects::nonNull)
            .filter(p -> p.getStatus() == 1)
            .collect(Collectors.toList());
    }

    public List<String> getUserPermissionCodes(Long userId) {
        return getUserPermissions(userId).stream()
            .map(SysPermission::getPermissionCode)
            .collect(Collectors.toList());
    }

    public boolean hasPermission(Long userId, String permissionCode) {
        List<String> codes = getUserPermissionCodes(userId);
        return codes.contains(permissionCode);
    }

    public LoginResponse login(LoginRequest request) {
        Optional<SysUser> userOpt = findByUsername(request.getUsername());
        if (!userOpt.isPresent()) {
            throw new RuntimeException("用户名或密码错误");
        }

        SysUser user = userOpt.get();
        if (user.getStatus() != 1) {
            throw new RuntimeException("用户已被禁用");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        List<SysRole> roles = getUserRoles(user.getId());
        List<String> roleCodes = roles.stream()
            .map(SysRole::getRoleCode)
            .collect(Collectors.toList());

        List<String> permissions = getUserPermissionCodes(user.getId());

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setRealName(user.getRealName());
        response.setRoles(roleCodes);
        response.setPermissions(permissions);
        return response;
    }
}