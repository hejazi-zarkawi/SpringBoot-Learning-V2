package com.hejazi.securityApp.securityApp.dto;

import com.hejazi.securityApp.securityApp.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignupDTO {

    private String email;
    private String password;
    private String name;

    private Set<Role> roles;
}
