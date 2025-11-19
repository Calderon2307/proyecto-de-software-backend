package com.software.pokedexV2.config;

import com.software.pokedexV2.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Autowired
    private OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;

    // 1. Define el filtro JWT como un Bean para que Spring pueda inyectarle sus dependencias (JwtUtils, EntrenadorDetailsService)
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    // 2. Define el encriptador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. Define el gestor de autenticación (necesario para el Login JWT)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // 4. Cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                // Maneja el error 401 si el token es inválido o está ausente
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                // API sin estado (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Reglas de acceso
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/v2/auth/**", "/oauth2/**", "/login/**").permitAll() // Rutas públicas (Registro, Login, OAuth2)
                                .anyRequest().authenticated() // Cualquier otra ruta requiere un JWT válido
                )

                // 5. Integra el filtro JWT antes de la autenticación de usuario/contraseña de Spring
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)

                // 6. Configuración de OAuth2
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(oauth2AuthenticationSuccessHandler) // Genera el JWT después del login con Google
                );

        return http.build();
    }
}