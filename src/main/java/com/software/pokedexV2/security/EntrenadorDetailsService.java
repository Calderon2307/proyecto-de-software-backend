package com.software.pokedexV2.security;

import com.software.pokedexV2.entities.Entrenador;
import com.software.pokedexV2.repository.EntrenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntrenadorDetailsService implements UserDetailsService {

    @Autowired
    EntrenadorRepository entrenadorRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Utiliza tu método existente en el repositorio
        Entrenador entrenador = entrenadorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Entrenador no encontrado con email: " + email));

        // Usa la clase de detalles que acabamos de crear
        return EntrenadorDetails.build(entrenador);
    }
}