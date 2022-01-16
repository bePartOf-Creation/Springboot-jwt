package com.demo.controllers;

import com.demo.controllers.dtos.RegisterDTO;
import com.demo.controllers.dtos.UpdateUserDTO;
import com.demo.models.ApplicationUser;
import com.demo.repositories.ApplicationUserRepository;
import com.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    ApiResponse apiResponse;

    @PostMapping("/signUp")
    public ResponseEntity<?> createUser(@RequestBody RegisterDTO registerDTO) {
        try {
            ApplicationUser user = userService.saveUser(registerDTO);
//            ApiResponse happyResponse = apiResponse.createUserResponse(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            ApiResponse badResponse = ApiResponse.builder().message(e.getMessage()).responseCode(HttpStatus.BAD_REQUEST.value()).build();
            return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateUserDetail(@RequestBody UpdateUserDTO updateUserDTO) {
        try {
            ApplicationUser user = userService.updateUserDetails(updateUserDTO);
            ApiResponse happyResponse = apiResponse.updateUserResponse(user);
            return new ResponseEntity<>(happyResponse, HttpStatus.OK);
        }catch (IllegalArgumentException exception) {
            ApiResponse response = ApiResponse.builder().message(exception.getMessage()).responseCode(HttpStatus.BAD_REQUEST.value()).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @Autowired
    ApplicationUserRepository userRepository;
    @GetMapping("")
    public ResponseEntity<?> getAllUser(){
        List<ApplicationUser> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
