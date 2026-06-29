package com.springboot.hejazi.prod_ready_features.prod_ready_features.auth;

import org.springframework.data.domain.AuditorAware;

import javax.swing.*;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Mohammad Hejazi");
    }
}
