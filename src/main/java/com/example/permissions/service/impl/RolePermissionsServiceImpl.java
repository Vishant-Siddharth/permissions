package com.example.permissions.service.impl;

import com.example.permissions.service.Dtos.Permission;
import com.example.permissions.service.Dtos.Role;
import com.example.permissions.service.Dtos.RolePermissions;
import com.example.permissions.service.Dtos.UsersTable;
import com.example.permissions.service.Repository.PermissionRepo;
import com.example.permissions.service.Repository.RolePermissionRepo;
import com.example.permissions.service.Repository.RoleRepo;
import com.example.permissions.service.Repository.UserRepo;
import com.example.permissions.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RolePermissionsServiceImpl implements RolePermissionService {

    @Autowired
    private PermissionRepo permissionRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private RolePermissionRepo rolePermissionRepo;

    @Override
    public boolean addPermission(List<String> permissionList) {

        try {
            List<Permission> permissions = new ArrayList<>();
            for (String permission : permissionList) {
                Permission newPermission = Permission.builder().permissionName(permission).build();
                permissions.add(newPermission);
            }
            permissionRepo.saveAll(permissions);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Integer addRolePermissions(String roleName, List<Integer> permissionIds) {
        try {
            Role role = Role.builder().roleName(roleName).build();
            role = roleRepo.save(role);
            List<RolePermissions> rolePermissionsList = new ArrayList<>();

            for (Integer permissionId : permissionIds) {
                RolePermissions rolePermissions = RolePermissions.builder().roleId(role.getId()).permissionId(permissionId).build();
                rolePermissionsList.add(rolePermissions);
            }

            rolePermissionRepo.saveAll(rolePermissionsList);
            return role.getId();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateRolePermissions(Integer roleId, List<Integer> permissionIds) {

        try {

            if (roleRepo.findById(roleId).isEmpty()) {
                log.error("no role present for roleId: " + roleId);
                return false;
//                throw new CustomException("no role present");
            }
            rolePermissionRepo.deleteAll(rolePermissionRepo.findByRoleId(roleId));

            List<RolePermissions> rolePermissionsList = new ArrayList<>();

            for (Integer permissionId : permissionIds) {
                RolePermissions rolePermissions = RolePermissions.builder().roleId(roleId).permissionId(permissionId).build();
                rolePermissionsList.add(rolePermissions);
            }

            rolePermissionRepo.saveAll(rolePermissionsList);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean addPermissionsInExistingRoles(Integer roleId, List<Integer> permissionIds) {

        if (roleRepo.findById(roleId).isEmpty()) {
            log.error("no role present for roleId: " + roleId);
            return false;
//            throw new RuntimeException("no role present");
        }

        try {
            List<RolePermissions> rolePermissionsList = new ArrayList<>();

            List<Integer> existingPermissionIdsForRole = new ArrayList<>();
            try {
                existingPermissionIdsForRole = rolePermissionRepo.findByRoleId(roleId).stream().map(per -> per.getPermissionId()).collect(Collectors.toList());
            } catch (Exception e) {
                log.error("error while fetching permission " + e.getMessage());
            }

            for (Integer permissionId : permissionIds) {
                if(!existingPermissionIdsForRole.contains(permissionId)) {
                    RolePermissions rolePermissions = RolePermissions.builder().roleId(roleId).permissionId(permissionId).build();
                    rolePermissionsList.add(rolePermissions);
                }
            }

            rolePermissionRepo.saveAll(rolePermissionsList);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Integer addUser(String userName) {

        try {
            UsersTable user = UsersTable.builder().name(userName).build();
            user = userRepo.save(user);
            return user.getId();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean addRoleToUser(Integer userId, Integer roleId) {

        Optional<UsersTable> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            log.error("no user present for userId: " + userId);
            return false;
//            throw new RuntimeException("no user present");
        }

        if (roleRepo.findById(roleId).isEmpty()) {
            log.error("no role present for roleId: " + roleId);
            return false;
//            throw new RuntimeException("no role present");
        }

        UsersTable user1 = UsersTable.builder().id(userId).name(user.get().getName()).roleId(roleId).build();
        userRepo.save(user1);

        return true;
    }

    @Override
    public boolean updateUserRole(Integer userId, Integer roleId) {

        Optional<UsersTable> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            log.error("no user present for userId: " + userId);
            return false;
//            throw new RuntimeException("no user present");
        }

        if (roleRepo.findById(roleId).isEmpty()) {
            log.error("no role present for roleId: " + roleId);
            return false;
//            throw new RuntimeException("no role present");
        }

        UsersTable user1 = UsersTable.builder().id(userId).name(user.get().getName()).roleId(roleId).build();
        userRepo.save(user1);

        return true;
    }

    @Override
    public List<Role> getRoles() {
        return Streamable.of(roleRepo.findAll()).toList();
    }

    @Override
    public Role getUserRole(Integer userId) {

        if (userRepo.findById(userId).isEmpty()) {
            log.error("no user present for userId: " + userId);
            return null;
//            throw new RuntimeException("no user present");
        }

        return roleRepo.findById(userRepo.findById(userId).get().getRoleId()).get();
    }

    @Override
    public List<UsersTable> getUsersByRole(Integer roleId) {
        return Streamable.of(userRepo.findUserByRoleId(roleId)).toList();
    }

    @Override
    public List<Permission> getPermissionForRole(Integer roleId) {

        List<RolePermissions> rolePermissionsList = rolePermissionRepo.findByRoleId(roleId);

        List<Permission> permissions = new ArrayList<>();

        for(RolePermissions rolePermissions : rolePermissionsList) {
            Optional<Permission> optional = permissionRepo.findById(rolePermissions.getPermissionId());

            if(!optional.isEmpty()) {
                permissions.add(optional.get());
            }
        }
        return permissions;
    }

    @Override
    public List<Permission> getAllPermissions() {
        return Streamable.of(permissionRepo.findAll()).toList();
    }

    @Override
    public boolean deletePermission(Integer permissionId) {

        try {
            permissionRepo.deleteById(permissionId);

            rolePermissionRepo.deleteAll(rolePermissionRepo.findByPermissionId(permissionId));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
