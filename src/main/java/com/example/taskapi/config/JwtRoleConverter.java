package com.example.taskapi.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess == null || realmAccess.isEmpty()) {
            return extractRolesFromClaims(jwt);
        }

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) realmAccess.get("roles");
        if (roles == null) {
            return extractRolesFromClaims(jwt);
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }

    private Collection<GrantedAuthority> extractRolesFromClaims(Jwt jwt) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        List<String> roles = jwt.getClaimAsStringList("roles");
        if (roles != null) {
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())));
        }

        Map<String, Object> claims = jwt.getClaims();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            if (entry.getValue() instanceof List<?> list) {
                list.forEach(item -> {
                    if (item instanceof String role) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
                    }
                });
            }
        }

        return authorities;
    }
}
