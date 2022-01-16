package com.demo.controllers;

import com.demo.models.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Class for ApiResponse Message.
 * @author Latitude V
 * @since v1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class ApiResponse {
    /**
     * This message display a user created name.
     */
    private String userName;
    /**
     * This message display a user created password.
     */
    private String password;
    /**
     * This message display a response status code.
     */
    private int responseCode;
    /**
     * The message displays a response message .
     */
    private String message;


    /**
     * This method format any user response endpoint.
     * @param createdUser .
     * @return a userResponse.
     */
    public ApiResponse createUserResponse(ApplicationUser createdUser) {
        return ApiResponse.builder()
                .userName(createdUser.getUserName())
                .password(createdUser.getPassword())
                .responseCode(HttpStatus.CREATED.value())
                .message(createdUser.getUserName() +" You've Signed Up Successfully. ")
                .build();
    }
    /**
     * This method format any user response endpoint.
     * @param updatedUser .
     * @return a userResponse.
     */
    public ApiResponse updateUserResponse(ApplicationUser updatedUser) {
        return ApiResponse.builder()
                .userName(updatedUser.getUserName())
                .password(updatedUser.getPassword())
                .responseCode(HttpStatus.OK.value())
                .message("Update Successfully. ")
                .build();
    }
}