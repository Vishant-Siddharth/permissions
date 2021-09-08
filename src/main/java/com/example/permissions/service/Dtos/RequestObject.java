package com.example.permissions.service.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class RequestObject {

    private String roleName;
    private List<String> permissions;
    private String userName;
    private List<Integer> permissionIds;
    private Integer roleId;
    private Integer userId;

}
