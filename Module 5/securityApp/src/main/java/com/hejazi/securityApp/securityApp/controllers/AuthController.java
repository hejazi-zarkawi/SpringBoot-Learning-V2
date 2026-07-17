package com.hejazi.securityApp.securityApp.controllers;

import com.hejazi.securityApp.securityApp.dto.LoginDTO;
import com.hejazi.securityApp.securityApp.dto.SignupDTO;
import com.hejazi.securityApp.securityApp.dto.UserDTO;
import com.hejazi.securityApp.securityApp.services.AuthService;
import com.hejazi.securityApp.securityApp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    ResponseEntity<UserDTO> signUp(@RequestBody SignupDTO signupDTO){
        UserDTO user= userService.signup(signupDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        String token= authService.login(loginDTO);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(token);
    }

}
