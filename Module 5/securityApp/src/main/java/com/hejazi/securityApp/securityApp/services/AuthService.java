package com.hejazi.securityApp.securityApp.services;

import com.hejazi.securityApp.securityApp.dto.LoginDTO;
import com.hejazi.securityApp.securityApp.dto.LoginResponseDTO;
import com.hejazi.securityApp.securityApp.entities.Session;
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
    private final UserService userService;
    private final SessionService sessionService;
    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication= authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));

        User user= (User) authentication.getPrincipal();
        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);
        sessionService.generateNewSession(user,refreshToken);
        return new LoginResponseDTO(user.getId(),accessToken,refreshToken);
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        Long userId= jwtService.getUserIdFromToken(refreshToken);
        sessionService.validateSession(refreshToken);
        User user= userService.getUserById(userId);
        String accessToken= jwtService.generateAccessToken(user);
        return new LoginResponseDTO(user.getId(),accessToken,refreshToken);
    }

    public void logout(String refreshToken) {
        sessionService.validateSession(refreshToken);
        sessionService.logout(refreshToken);
    }
}