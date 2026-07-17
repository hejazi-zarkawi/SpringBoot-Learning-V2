package com.hejazi.securityApp.securityApp.services;

import com.hejazi.securityApp.securityApp.dto.LoginDTO;
import com.hejazi.securityApp.securityApp.entities.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    public String login(LoginDTO loginDTO) {
        Authentication authentication= authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));

        User user= (User) authentication.getPrincipal();
        return jwtService.generateToken(user);
    }
}