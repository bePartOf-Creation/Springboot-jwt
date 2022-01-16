package com.demo.service;

import com.demo.controllers.dtos.RegisterDTO;
import com.demo.controllers.dtos.RoleDTO;
import com.demo.controllers.dtos.UpdateUserDTO;
import com.demo.controllers.dtos.UserRoleDTO;
import com.demo.models.ApplicationUser;
import com.demo.models.Role;
import com.demo.repositories.ApplicationUserRepository;
import com.demo.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@Service
@Slf4j
public class UserServiceImpl implements  UserService, UserDetailsService {
    @Autowired
    private ApplicationUserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This method creates and save a  User Credentials.
     * @param registerDTO
     * @return an registered user
     */
    @Override
    public ApplicationUser saveUser(RegisterDTO registerDTO) {
        log.info("Creating A New User ...... --> {}", registerDTO);
        boolean userExists = userRepository.existsByUserName(registerDTO.getUserName());
        if (userExists){
            throw new IllegalArgumentException("User Already Exists");
        }
        ApplicationUser user = ApplicationUser.builder()
                .userName(registerDTO.getUserName())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .build();
        return userRepository.save(user);
    }
    /**
     * This method enable the admin create and save a  User Role.
     * @param roleDTO
     * @return an approved Role.
     */
    @Override
    public Role saveRole(RoleDTO roleDTO) {
        log.info("Creating A New Role ...... --> {}", roleDTO);
        Role newRole = Role.builder()
                .name(roleDTO.getRoleName())
                .build();
        return roleRepository.save(newRole);
    }
    /**
     * This method enables admin assign role to a specific User.
     * @param userRoleDTO
     */
    @Override
    public void addRoleToUser(UserRoleDTO userRoleDTO) {
        log.info("Adding Role to user Credentials ...... --> {}", userRoleDTO);
        ApplicationUser user = userRepository.findByUserName(userRoleDTO.getUserName());

        Role role = roleRepository.findByName(userRoleDTO.getRoleName());
        log.info("Role and User for  Creation FOUND ...... --> {},{}", user,role);

        user.getRole().add(role);
        this.userRepository.save(user);
    }
    /**
     * This method retrieve a User.
     * @param username
     * @return an savedUser
     */
    @Override
    public ApplicationUser getUser(String username) {
        if (username.isEmpty()) {
            log.info("parameter passed is --> {}", username);
            throw new IllegalArgumentException("No Parameter FOUND");
        }
        log.info("Fetching A User.........");
        ApplicationUser foundUser = userRepository.findByUserName(username);
        log.info("User FOUND...... --> {}", foundUser);
        return foundUser;
    }
    /**
     * This method update and save an existing  User Credentials.
     * @param updateUserDTO
     * @return an registered user
     */
    @Override
    public ApplicationUser updateUserDetails(UpdateUserDTO updateUserDTO) {
        ApplicationUser foundUser = userRepository.findById(updateUserDTO.getUserId()).orElse(null);
        log.info("User FOUND...... --> {}", foundUser);
        if(foundUser.equals(null) ) {
            throw new IllegalArgumentException("NO USER FOUND");
        }
        foundUser = ApplicationUser.builder()
                .userName(updateUserDTO.getUserName())
                .password(passwordEncoder.encode(updateUserDTO.getPassword()))
                .build();
        return userRepository.save(foundUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser foundUser = userRepository.findByUserName(username);
        if(foundUser == null) {
            throw new UsernameNotFoundException("User with Name" + username + "Cannot Be Found In Our Database..");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        foundUser.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
       return new User(foundUser.getUserName(),foundUser.getPassword(),authorities);
    }
}
