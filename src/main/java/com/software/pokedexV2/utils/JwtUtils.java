package com.software.pokedexV2.utils;

import com.software.pokedexV2.security.EntrenadorDetails;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User; // Importación necesaria
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String jwtSecret;

    @Value("${jwt.time.expiration}")
    private long jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        Object principal = authentication.getPrincipal();
        String subject; // Usaremos 'subject' para almacenar el email

        // 🛑 CORRECCIÓN: Manejo inteligente de tipos
        if (principal instanceof EntrenadorDetails) {
            // Caso 1: Login Local o procesado por CustomOAuth2UserService
            subject = ((EntrenadorDetails) principal).getUsername();
        } else if (principal instanceof OAuth2User) {
            // Caso 2: Login Social (DefaultOidcUser/OAuth2User).
            // Extraemos el email directamente del atributo 'email' proporcionado por Google.
            subject = ((OAuth2User) principal).getAttribute("email");
        } else {
            // Fallback para tipos no esperados, aunque en teoría no debería ocurrir
            // si los flujos están bien configurados.
            throw new IllegalArgumentException("Tipo de principal de autenticación no soportado: " + principal.getClass().getName());
        }

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            // Loguear errores de token aquí
        }
        return false;
    }
}