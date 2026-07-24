package com.hejazi.securityApp.securityApp.controllers;

import com.hejazi.securityApp.securityApp.dto.LoginDTO;
import com.hejazi.securityApp.securityApp.dto.LoginResponseDTO;
import com.hejazi.securityApp.securityApp.dto.SignupDTO;
import com.hejazi.securityApp.securityApp.dto.UserDTO;
import com.hejazi.securityApp.securityApp.services.AuthService;
import com.hejazi.securityApp.securityApp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployenv;

    @PostMapping("/signup")
    ResponseEntity<UserDTO> signUp(@RequestBody SignupDTO signupDTO){
        UserDTO user= userService.signup(signupDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        LoginResponseDTO loginResponseDTO= authService.login(loginDTO);
        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployenv));
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/refresh")
    ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
        String refreshToken= Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()) )
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(()-> new AuthenticationServiceException("Refresh token not found inside the cookies."));

        LoginResponseDTO loginResponseDTO= authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDTO);
    }

}
