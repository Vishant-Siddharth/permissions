package com.example.permissions;

import com.example.permissions.service.Dtos.Permission;
import com.example.permissions.service.Dtos.RequestObject;
import com.example.permissions.service.Dtos.Role;
import com.example.permissions.service.Dtos.UsersTable;
import com.example.permissions.service.impl.RolePermissionsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private RolePermissionsServiceImpl rolePermissionsService = new RolePermissionsServiceImpl();

    @PostMapping(value = "/addPermissions", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addPermission(@RequestBody RequestObject requestObject) {
        return rolePermissionsService.addPermission(requestObject.getPermissions());
    }

    @PostMapping(value = "/addRolePermissions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer addRolePermissions(@RequestBody RequestObject requestObject) {
        return rolePermissionsService.addRolePermissions(requestObject.getRoleName(), requestObject.getPermissionIds());
    }

    @PutMapping(value = "/updateRolePermissions", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateRolePermissions(@RequestBody RequestObject requestObject) {
        return rolePermissionsService.updateRolePermissions(requestObject.getRoleId(), requestObject.getPermissionIds());
    }

    @PostMapping(value = "/addPermissionsInExistingRoles", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addPermissionsInExistingRoles(@RequestBody RequestObject requestObject) {
        return rolePermissionsService.addPermissionsInExistingRoles(requestObject.getRoleId(), requestObject.getPermissionIds());
    }

    @PostMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer addUser(@RequestBody RequestObject requestObject) {
        return rolePermissionsService.addUser(requestObject.getUserName());
    }

    @PostMapping(value = "/addRoleToUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addRoleToUser(@RequestBody RequestObject requestObject) {
        return rolePermissionsService.addRoleToUser(requestObject.getUserId(), requestObject.getRoleId());
    }

    @PutMapping(value = "/updateUserRole", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateUserRole(@RequestBody RequestObject requestObject) {
        return rolePermissionsService.updateUserRole(requestObject.getUserId(), requestObject.getRoleId());
    }

    @GetMapping(value = "/getRoles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Role> getRoles() {
        return rolePermissionsService.getRoles();
    }

    @GetMapping(value = "/getAllPermissions", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Permission> getAllPermissions() {
        return rolePermissionsService.getAllPermissions();
    }

    @GetMapping(value = "/getUserRole", produces = MediaType.APPLICATION_JSON_VALUE)
    public Role getUserRole(@RequestParam Integer userId) {
        return rolePermissionsService.getUserRole(userId);
    }

    @GetMapping(value = "/getUsersForRole", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UsersTable> getUsersForRole(@RequestParam Integer roleId) {
        return rolePermissionsService.getUsersByRole(roleId);
    }

    @GetMapping(value = "/getPermissionForRole", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Permission> getPermissionForRole(@RequestParam Integer roleId) {
        return rolePermissionsService.getPermissionForRole(roleId);
    }

    @DeleteMapping(value = "/deletePermission", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deletePermission(@RequestParam Integer permissionId) {
        return rolePermissionsService.deletePermission(permissionId);
    }
}
