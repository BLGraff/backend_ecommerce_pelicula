package com.backend.pelicula.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita el uso de @PreAuthorize y @PostAuthorize
class SecurityConfiguration {

   /* @Bean
    public JwtDecoder jwtDecoder() {
        String jwkSetUri = "https://your-auth-server/.well-known/jwks.json"; // Reemplázalo con la URL correcta de tu proveedor de autenticación
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/pelicula/public/**").permitAll() // Permitir acceso anónimo
                        .anyRequest().authenticated() // Requiere autenticación
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var roles = extractRolesFromResourceAccess(jwt, "peliculas-api-rest");
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(/*"ROLE_" + */role/*.toUpperCase()*/)) // Convertir a GrantedAuthority
                    .collect(Collectors.toSet());
        });

        // converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }

    private Collection<String> extractRolesFromResourceAccess(Jwt jwt, String clientId) {
        var resourceAccess = jwt.getClaimAsMap("resource_access");
        if (resourceAccess == null || !resourceAccess.containsKey(clientId)) {
            return Set.of(); // Sin roles si no existe el cliente
        }

        var clientRoles = (Map<String, Object>) resourceAccess.get(clientId);
        return (Collection<String>) clientRoles.getOrDefault("roles", Set.of());
    }

}