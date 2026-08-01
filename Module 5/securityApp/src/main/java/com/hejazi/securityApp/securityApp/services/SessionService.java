package com.hejazi.securityApp.securityApp.services;

import com.hejazi.securityApp.securityApp.entities.Session;
import com.hejazi.securityApp.securityApp.entities.User;
import com.hejazi.securityApp.securityApp.exceptions.ResourceNotFoundException;
import com.hejazi.securityApp.securityApp.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT=2;

    public void generateNewSession(User user, String refreshToken){
        List<Session> allSessions= sessionRepository.findByUser(user);
        if(allSessions.size()== SESSION_LIMIT){
            allSessions.sort(Comparator.comparing(session -> session.getLastUsedAt()));
            Session lastRecentlyUsedSession= allSessions.get(0);

            sessionRepository.delete(lastRecentlyUsedSession);
        }

        Session newSession= Session.builder()
                .refreshToken(refreshToken)
                .user(user)
                .build();

        sessionRepository.save(newSession);
    }

    public void validateSession(String refreshToken){
        Session session= sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new ResourceNotFoundException("Session not found for refresh Token "+ refreshToken));

        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

    public void logout(String refreshToken) {
        Session session= sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new SessionAuthenticationException("Session not found for refresh Token "+ refreshToken));

        sessionRepository.delete(session);
    }
}
