package com.example.permissions.service.Repository;

import com.example.permissions.service.Dtos.UsersTable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<UsersTable, Integer> {

    List<UsersTable> findUserByRoleId(Integer roleId);
}
