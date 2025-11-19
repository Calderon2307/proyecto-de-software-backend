package com.software.pokedexV2.security;

import com.software.pokedexV2.entities.Entrenador;
import com.software.pokedexV2.repository.EntrenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String email = oauth2User.getAttribute("email");
        String nombre = oauth2User.getAttribute("name");

        Optional<Entrenador> entrenadorOpt = entrenadorRepository.findByEmail(email);
        Entrenador entrenador;

        if (entrenadorOpt.isEmpty()) {
            // REGISTRO AUTOMÁTICO (Social Registration)
            entrenador = Entrenador.builder()
                    .email(email)
                    .nombre(nombre)
                    .contrasena("OAUTH2_USER") // Placeholder de contraseña
                    .fechaRegistro(LocalDateTime.now())
                    // Aquí puedes añadir valores por defecto para regionPreferida o tipoPreferido
                    .build();
            entrenadorRepository.save(entrenador);
        } else {
            entrenador = entrenadorOpt.get();
        }

        // Retorna EntrenadorDetails que incluye los atributos de OAuth2 para el successHandler
        return EntrenadorDetails.build(entrenador, oauth2User.getAttributes());
    }
}