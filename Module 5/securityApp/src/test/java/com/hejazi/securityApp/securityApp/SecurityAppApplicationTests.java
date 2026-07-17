package com.hejazi.securityApp.securityApp;

import com.hejazi.securityApp.securityApp.entities.User;
import com.hejazi.securityApp.securityApp.services.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;

@SpringBootTest
class SecurityAppApplicationTests {

	@Autowired
	private JWTService jwtService;
	@Test
	void contextLoads() {

		User user= new User(4L,"hejazi123","1234", "Hejazi");

		String token= jwtService.generateToken(user);

		System.out.println(token);

		Long userId= jwtService.getUserIdFromToken(token);

		System.out.println(userId);

	}

}
