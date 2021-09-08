package com.example.permissions.service.Repository;

import com.example.permissions.service.Dtos.Permission;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepo extends CrudRepository<Permission, Integer> {
}
