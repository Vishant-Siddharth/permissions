package com.example.permissions.service.Repository;

import com.example.permissions.service.Dtos.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Integer> {
}
