package com.example.permissions.service;

import com.example.permissions.service.Dtos.Permission;
import com.example.permissions.service.Dtos.Role;
import com.example.permissions.service.Dtos.UsersTable;

import java.util.List;

public interface RolePermissionService {
    boolean addPermission(List<String> permissionList);

    Integer addRolePermissions(String role, List<Integer> permissionIds);

    boolean updateRolePermissions(Integer roleId, List<Integer> permissionIds);

    boolean addPermissionsInExistingRoles(Integer roleId, List<Integer> permissionIds);

    Integer addUser(String userName);

    boolean addRoleToUser(Integer userId, Integer roleId);

    boolean updateUserRole(Integer userId, Integer roleId);

    List<Role> getRoles();

    Role getUserRole(Integer userId);

    List<UsersTable> getUsersByRole(Integer roleId);

    List<Permission> getPermissionForRole(Integer roleId);

    List<Permission> getAllPermissions();

    boolean deletePermission(Integer permissionId);

}
