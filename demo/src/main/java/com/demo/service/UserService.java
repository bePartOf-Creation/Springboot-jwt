package com.demo.service;

import com.demo.controllers.dtos.RegisterDTO;
import com.demo.controllers.dtos.RoleDTO;
import com.demo.controllers.dtos.UpdateUserDTO;
import com.demo.controllers.dtos.UserRoleDTO;
import com.demo.models.ApplicationUser;
import com.demo.models.Role;

public interface UserService {
    ApplicationUser saveUser(RegisterDTO registerDTO);
    Role saveRole(RoleDTO roleDTO);
    void addRoleToUser(UserRoleDTO userRoleDTO);
    ApplicationUser getUser(String username);
    ApplicationUser updateUserDetails(UpdateUserDTO updateUserDTO);
}
