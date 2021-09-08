package com.example.permissions.service.Repository;

import com.example.permissions.service.Dtos.RolePermissions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RolePermissionRepo extends CrudRepository<RolePermissions, Integer> {
    List<RolePermissions> findByRoleId(Integer roleId);
    List<RolePermissions> findByPermissionId(Integer roleId);

}
