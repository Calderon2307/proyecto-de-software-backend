package com.software.pokedexV2.security;

import com.software.pokedexV2.entities.Entrenador;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class EntrenadorDetails implements UserDetails, OAuth2User {

    private final Long id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public EntrenadorDetails(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Constructor para Login Local (JWT)
    public static EntrenadorDetails build(Entrenador entrenador) {
        return new EntrenadorDetails(
                entrenador.getId(),
                entrenador.getEmail(),
                entrenador.getContrasena(),
                Collections.emptyList()
        );
    }

    // Constructor para Login Social (OAuth2)
    public static EntrenadorDetails build(Entrenador entrenador, Map<String, Object> attributes) {
        EntrenadorDetails details = build(entrenador);
        details.attributes = attributes;
        return details;
    }

    // --- Implementación de UserDetails ---
    @Override
    public String getUsername() { return email; }
    @Override
    public String getPassword() { return password; }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }

    // --- Implementación de OAuth2User ---
    @Override
    public String getName() { return String.valueOf(id); }
    @Override
    public Map<String, Object> getAttributes() { return attributes; }
}