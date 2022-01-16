package com.demo.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Data @AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotEmpty(message = "userName cannot empty.")
    private String userName;

    @NotEmpty(message = "password cannot empty.")
    private String password;
}
