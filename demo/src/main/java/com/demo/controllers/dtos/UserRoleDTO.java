package com.demo.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor
@NoArgsConstructor @Builder
public class UserRoleDTO {
    private String userName;
    private String  roleName;
}
