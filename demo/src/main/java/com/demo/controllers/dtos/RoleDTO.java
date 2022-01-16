package com.demo.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    @NotEmpty(message = "roleName Cannot Be empty")
    private String roleName;
}
