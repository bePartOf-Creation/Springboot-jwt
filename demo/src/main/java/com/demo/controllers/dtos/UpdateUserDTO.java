package com.demo.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDTO {
    @NotEmpty(message = "userName cannot empty.")
    private Long userId;

    @NotEmpty(message = "userName cannot empty.")
    private String userName;
    private String password;
}
