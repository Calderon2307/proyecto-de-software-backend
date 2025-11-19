package com.software.pokedexV2.security;

import com.software.pokedexV2.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    // URL frontal donde enviar el JWT
    private final String redirectUrl = "http://localhost:3000/oauth/redirect";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        // 1. Generar el JWT
        String token = jwtUtils.generateJwtToken(authentication);

        // 2. Construir la URL de redirección con el token como parámetro
        String targetUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("token", token)
                .build().toUriString();

        // 3. Redirigir al cliente de vuelta a la aplicación frontal
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}