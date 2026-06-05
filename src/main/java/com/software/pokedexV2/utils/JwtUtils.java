package com.software.pokedexV2.utils;

import com.software.pokedexV2.security.EntrenadorDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret.key}")
    private String jwtSecret;

    @Value("${jwt.time.expiration}")
    private long jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String subject;

        if (principal instanceof EntrenadorDetails) {
            subject = ((EntrenadorDetails) principal).getUsername();
        } else if (principal instanceof OAuth2User) {
            subject = ((OAuth2User) principal).getAttribute("email");
        } else {
            throw new IllegalArgumentException("Tipo de principal no soportado: " + principal.getClass().getName());
        }

        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        logger.info("Token generado para: {}", subject);
        return token;
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
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken);
            logger.info("Token JWT validado correctamente");
            return true;
        } catch (SignatureException e) {
            logger.error("JWT firma inválida: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("JWT malformado: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT no soportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Token vacío o nulo: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error al validar JWT: {}", e.getMessage());
        }
        return false;
    }
}