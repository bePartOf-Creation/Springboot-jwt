package com.demo;

import com.demo.controllers.dtos.RegisterDTO;
import com.demo.controllers.dtos.RoleDTO;
import com.demo.controllers.dtos.UserRoleDTO;
import com.demo.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JwtSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtSecurityApplication.class, args);
	}

	@Bean
	PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner run(UserServiceImpl userService) {
		return args -> {
			userService.saveRole(new RoleDTO("ROLE_USER"));
			userService.saveRole(new RoleDTO("ROLE_ADMIN"));
			userService.saveRole(new RoleDTO("ROLE_MANGER"));
			userService.saveRole(new RoleDTO("ROLE_SUPERVISOR"));

			userService.saveUser(new RegisterDTO("Kehinde","k3nny"));
			userService.saveUser(new RegisterDTO("Felix","f3lix"));
			userService.saveUser(new RegisterDTO("Gafaru","g@f@r"));
			userService.saveUser(new RegisterDTO("Olasunkanmi","0l@"));

			userService.addRoleToUser(new UserRoleDTO("Felix","ROLE_ADMIN"));
			userService.addRoleToUser(new UserRoleDTO("Gafaru","ROLE_SUPERVISOR"));
			userService.addRoleToUser(new UserRoleDTO("Kehinde","ROLE_MANGER"));
			userService.addRoleToUser(new UserRoleDTO("Olasunkanmi","ROLE_USER"));
		};
	}
	public static ConfigurableApplicationContext run(String... args) {
		return SpringApplication.run(JwtSecurityApplication.class, args);
	}

}
