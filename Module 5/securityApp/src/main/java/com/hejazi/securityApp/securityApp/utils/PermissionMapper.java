package com.hejazi.securityApp.securityApp.utils;

import com.hejazi.securityApp.securityApp.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PermissionMapper {

    private static final Map<Role, Set<Permission>> map= Map.of(
            Role.USER, Set.of(Permission.USER_VIEW,Permission.POST_VIEW),
            Role.CREATOR, Set.of(Permission.POST_UPDATE,Permission.POST_CREATE,Permission.USER_UPDATE),
            Role.ADMIN, Set.of(Permission.USER_CREATE,Permission.POST_DELETE,Permission.USER_DELETE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role){
        return map.get(role)
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
