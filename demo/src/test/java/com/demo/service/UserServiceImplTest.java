package com.demo.service;

import com.demo.controllers.dtos.RegisterDTO;
import com.demo.controllers.dtos.RoleDTO;
import com.demo.controllers.dtos.UpdateUserDTO;
import com.demo.controllers.dtos.UserRoleDTO;
import com.demo.models.ApplicationUser;
import com.demo.models.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    RegisterDTO registerSetup(){
        return new RegisterDTO("John","1234");
    }
    RegisterDTO registerSetupp(){
        return new RegisterDTO("Doe","abcd");
    } RegisterDTO registerSetupX(){
        return new RegisterDTO("Dele","abcd12");
    }
    RoleDTO roleSetup(){
        return new RoleDTO("ROLE.MANAGER");
    }
    RoleDTO roleSetupp(){
        return new RoleDTO("ROLE.OPERATIONS");
    }
    RoleDTO roleSetupX(){
        return new RoleDTO("ROLE.SUPERVISOR");
    }
    UpdateUserDTO updateSetup() {
        return UpdateUserDTO.builder().userId(1L).userName("James").password("fde").build();
    }
    @Test
    @DisplayName("Test To Create A User.")
    void saveUser() {
        RegisterDTO userRegistration = registerSetup();
        ApplicationUser user = this.userService.saveUser(userRegistration);
        assertThat(user.getUserName()).isEqualTo("John");

    }
    @Test
    void saveRole() {
        RoleDTO roleCreation = roleSetup();
        Role newRole = this.userService.saveRole(roleCreation);
        assertThat(newRole.getName()).isEqualTo("ROLE.MANAGER");
    }
    @Test
    void addRoleToUser() {
        RoleDTO roleCreation1 = roleSetupX();
        Role newRolee = this.userService.saveRole(roleCreation1);
        assertThat(newRolee.getName()).isEqualTo("ROLE.SUPERVISOR");

        RegisterDTO userRegistration1 = registerSetupX();
        ApplicationUser anotherUser = this.userService.saveUser(userRegistration1);
        assertThat(anotherUser.getUserName()).isEqualTo("Dele");

        UserRoleDTO addUserToRole =  UserRoleDTO.builder().roleName(newRolee.getName()).userName(anotherUser.getUserName()).build();
        this.userService.addRoleToUser(addUserToRole);
        ApplicationUser savedUser = this.userService.getUser(addUserToRole.getUserName());
        assertThat(savedUser.getUserName()).isEqualTo("Dele");
        assertThat(savedUser.getRole().stream().findFirst().orElse(null).getName()).isEqualTo("ROLE.SUPERVISOR");
    }
    @Test
    void getUser() {
        RoleDTO roleCreation1 = roleSetupp();
        Role newRolee = this.userService.saveRole(roleCreation1);
        assertThat(newRolee.getName()).isEqualTo("ROLE.OPERATIONS");

        RegisterDTO userRegistration1 = registerSetupp();
        ApplicationUser anotherUser = this.userService.saveUser(userRegistration1);
        assertThat(anotherUser.getUserName()).isEqualTo("Doe");

        UserRoleDTO addUserToRole =  UserRoleDTO.builder().userName(anotherUser.getUserName()).roleName(newRolee.getName()).build();
        this.userService.addRoleToUser(addUserToRole);
        ApplicationUser foundUser = this.userService.getUser(addUserToRole.getUserName());
        assertThat(foundUser.getUserName()).isEqualTo("Doe");
        assertThat(foundUser.getRole().stream().findFirst().orElse(null).getName()).isEqualTo("ROLE.OPERATIONS");
    }

    @Test
    void updateUserDetails() {
        UpdateUserDTO userRoleDTO = updateSetup();
        ApplicationUser user = this.userService.updateUserDetails(userRoleDTO);
        assertThat(user.getUserName()).isEqualTo("James");
    }
}