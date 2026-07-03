package com.example.api.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Si en el futuro necesitas que alguna ruta pase sin token, usas .permitAll()
                .anyRequest().authenticated() // Obliga a que toda petición que pase por el Gateway traiga un token válido
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                // Esto hace la magia: lee el application.yml para saber dónde está el Auth Server (puerto 9000) y valida el JWT
                .jwt(Customizer.withDefaults())
            );

        return http.build();
    }
}