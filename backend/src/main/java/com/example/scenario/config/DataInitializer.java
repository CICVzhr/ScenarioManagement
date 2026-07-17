package com.example.scenario.config;

import com.example.scenario.entity.*;
import com.example.scenario.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

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
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initSystemBaseData();
    }

    private void initSystemBaseData() {
        initPermissions();
        initRoles();
        initUsers();
        initRolePermissions();
        initUserRoles();
    }

    private void initPermissions() {
        if (sysPermissionRepository.count() > 0) return;

        Map<String, Long> menuIdMap = new HashMap<>();

        SysPermission p1 = createPerm("仪表盘", "dashboard", "menu", 0L, "/dashboard", 1);
        sysPermissionRepository.save(p1); menuIdMap.put("dashboard", p1.getId());

        SysPermission p2 = createPerm("事故数据管理", "accident", "menu", 0L, "/accident", 2);
        sysPermissionRepository.save(p2); menuIdMap.put("accident", p2.getId());
        saveChildPerms(p2.getId(), new String[][]{{"查看", "accident:view"}, {"编辑", "accident:edit"}, {"删除", "accident:delete"}});

        SysPermission p3 = createPerm("场景设计", "scene", "menu", 0L, "/scene", 3);
        sysPermissionRepository.save(p3); menuIdMap.put("scene", p3.getId());
        saveChildPerms(p3.getId(), new String[][]{{"查看", "scene:view"}, {"编辑", "scene:edit"}, {"审核", "scene:approve"}});

        SysPermission p4 = createPerm("危险场景库", "danger", "menu", 0L, "/danger", 4);
        sysPermissionRepository.save(p4); menuIdMap.put("danger", p4.getId());
        saveChildPerms(p4.getId(), new String[][]{{"查看", "danger:view"}, {"编辑", "danger:edit"}});

        SysPermission p5 = createPerm("用例设计", "case_design", "menu", 0L, "/case-design", 5);
        sysPermissionRepository.save(p5); menuIdMap.put("case_design", p5.getId());
        saveChildPerms(p5.getId(), new String[][]{{"查看", "case_design:view"}, {"编辑", "case_design:edit"}, {"审批", "case_design:approve"}});

        SysPermission p6 = createPerm("用例开发", "case_dev", "menu", 0L, "/case-dev", 6);
        sysPermissionRepository.save(p6); menuIdMap.put("case_dev", p6.getId());

        SysPermission p7 = createPerm("危险场景库管理", "danger_lib", "menu", 0L, "/danger-library", 7);
        sysPermissionRepository.save(p7); menuIdMap.put("danger_lib", p7.getId());

        SysPermission p8 = createPerm("标签管理", "tag", "menu", 0L, "/tag-management", 8);
        sysPermissionRepository.save(p8); menuIdMap.put("tag", p8.getId());
        saveChildPerms(p8.getId(), new String[][]{{"编辑", "tag:edit"}});

        SysPermission p9 = createPerm("系统设置", "settings", "menu", 0L, "/system-settings", 9);
        sysPermissionRepository.save(p9); menuIdMap.put("settings", p9.getId());
        saveChildPerms(p9.getId(), new String[][]{{"编辑", "settings:edit"}});

        SysPermission p10 = createPerm("用户管理", "user_mgmt", "menu", 0L, "/user-management", 10);
        sysPermissionRepository.save(p10); menuIdMap.put("user_mgmt", p10.getId());
        saveChildPerms(p10.getId(), new String[][]{{"查看", "user_mgmt:view"}, {"编辑", "user_mgmt:edit"}, {"删除", "user_mgmt:delete"}});
    }

    private void saveChildPerms(Long parentId, String[][] perms) {
        int sort = 1;
        for (String[] perm : perms) {
            SysPermission p = createPerm(perm[0], perm[1], "button", parentId, null, sort++);
            sysPermissionRepository.save(p);
        }
    }

    private SysPermission createPerm(String name, String code, String type, Long parentId, String path, int sort) {
        SysPermission p = new SysPermission();
        p.setPermissionName(name);
        p.setPermissionCode(code);
        p.setPermissionType(type);
        p.setParentId(parentId != null ? parentId : 0L);
        if (path != null) p.setPath(path);
        p.setSortOrder(sort);
        p.setStatus(1);
        return p;
    }

    private void initRoles() {
        if (sysRoleRepository.count() > 0) return;

        SysRole admin = new SysRole(); admin.setRoleName("超级管理员"); admin.setRoleCode("admin"); admin.setDescription("系统最高权限"); admin.setStatus(1);
        sysRoleRepository.save(admin);

        SysRole designer = new SysRole(); designer.setRoleName("设计人员"); designer.setRoleCode("designer"); designer.setDescription("场景/用例设计与编辑"); designer.setStatus(1);
        sysRoleRepository.save(designer);

        SysRole reviewer = new SysRole(); reviewer.setRoleName("审核人员"); reviewer.setRoleCode("reviewer"); reviewer.setDescription("审核与查看权限"); reviewer.setStatus(1);
        sysRoleRepository.save(reviewer);
    }

    private void initUsers() {
        SysUser admin = sysUserRepository.findByUsername("admin").orElse(null);
        if (admin == null) {
            admin = new SysUser();
            admin.setUsername("admin");
            admin.setRealName("系统管理员");
            admin.setStatus(1);
        }
        admin.setPassword(passwordEncoder.encode("admin123"));
        sysUserRepository.save(admin);

        if (sysUserRepository.count() <= 1) {
            SysUser designer = new SysUser();
            designer.setUsername("designer");
            designer.setPassword(passwordEncoder.encode("123456"));
            designer.setRealName("设计人员");
            designer.setStatus(1);
            sysUserRepository.save(designer);

            SysUser reviewer = new SysUser();
            reviewer.setUsername("reviewer");
            reviewer.setPassword(passwordEncoder.encode("123456"));
            reviewer.setRealName("审核人员");
            reviewer.setStatus(1);
            sysUserRepository.save(reviewer);
        }
    }

    private void initRolePermissions() {
        SysRole adminRole = sysRoleRepository.findByRoleCode("admin").orElse(null);
        if (adminRole != null) {
            List<SysPermission> allPerms = sysPermissionRepository.findAll();
            for (SysPermission perm : allPerms) {
                if (!sysRolePermissionRepository.existsByRoleIdAndPermissionId(adminRole.getId(), perm.getId())) {
                    SysRolePermission rp = new SysRolePermission();
                    rp.setRoleId(adminRole.getId());
                    rp.setPermissionId(perm.getId());
                    sysRolePermissionRepository.save(rp);
                }
            }
        }

        SysRole designerRole = sysRoleRepository.findByRoleCode("designer").orElse(null);
        if (designerRole != null) {
            String[] codes = {"accident", "accident:view", "scene", "scene:view", "scene:edit",
                "danger", "danger:view", "case_design", "case_design:view", "case_design:edit",
                "case_dev", "case_dev:view", "tag"};
            assignPermissions(designerRole.getId(), codes);
        }

        SysRole reviewerRole = sysRoleRepository.findByRoleCode("reviewer").orElse(null);
        if (reviewerRole != null) {
            String[] codes = {"accident", "accident:view", "scene", "scene:view", "scene:approve",
                "danger", "danger:view", "case_design", "case_design:view", "case_design:approve",
                "case_dev", "case_dev:view"};
            assignPermissions(reviewerRole.getId(), codes);
        }
    }

    private void assignPermissions(Long roleId, String[] codes) {
        for (String code : codes) {
            sysPermissionRepository.findByPermissionCode(code).ifPresent(perm -> {
                if (!sysRolePermissionRepository.existsByRoleIdAndPermissionId(roleId, perm.getId())) {
                    SysRolePermission rp = new SysRolePermission();
                    rp.setRoleId(roleId);
                    rp.setPermissionId(perm.getId());
                    sysRolePermissionRepository.save(rp);
                }
            });
        }
    }

    private void initUserRoles() {
        SysUser adminUser = sysUserRepository.findByUsername("admin").orElse(null);
        SysRole adminRole = sysRoleRepository.findByRoleCode("admin").orElse(null);
        if (adminUser != null && adminRole != null &&
            !sysUserRoleRepository.existsByUserIdAndRoleId(adminUser.getId(), adminRole.getId())) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(adminUser.getId());
            ur.setRoleId(adminRole.getId());
            sysUserRoleRepository.save(ur);
        }

        SysUser designerUser = sysUserRepository.findByUsername("designer").orElse(null);
        SysRole designerRole = sysRoleRepository.findByRoleCode("designer").orElse(null);
        if (designerUser != null && designerRole != null &&
            !sysUserRoleRepository.existsByUserIdAndRoleId(designerUser.getId(), designerRole.getId())) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(designerUser.getId());
            ur.setRoleId(designerRole.getId());
            sysUserRoleRepository.save(ur);
        }

        SysUser reviewerUser = sysUserRepository.findByUsername("reviewer").orElse(null);
        SysRole reviewerRole = sysRoleRepository.findByRoleCode("reviewer").orElse(null);
        if (reviewerUser != null && reviewerRole != null &&
            !sysUserRoleRepository.existsByUserIdAndRoleId(reviewerUser.getId(), reviewerRole.getId())) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(reviewerUser.getId());
            ur.setRoleId(reviewerRole.getId());
            sysUserRoleRepository.save(ur);
        }
    }
}