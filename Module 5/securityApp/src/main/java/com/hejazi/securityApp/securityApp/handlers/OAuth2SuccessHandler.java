package com.hejazi.securityApp.securityApp.handlers;

import com.hejazi.securityApp.securityApp.entities.User;
import com.hejazi.securityApp.securityApp.services.JWTService;
import com.hejazi.securityApp.securityApp.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public final UserService userService;
    public final JWTService jwtService;

//    @Value("${deploy.env}")
//    private String deployenv;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User= (DefaultOAuth2User) token.getPrincipal();

        String email= oAuth2User.getAttribute("email");

        User user= userService.findByEmail(email);

        if(user==null){

                User newUser= User.builder()
                        .name(oAuth2User.getName())
                        .email(email)
                        .build();

                user= userService.save(newUser);

        }

        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
//        cookie.setSecure("production".equals(deployenv));
        response.addCookie(cookie);

        String frontendUrl= "http://localhost:8080/home.html?token="+accessToken;

        response.sendRedirect(frontendUrl);
    }
}
