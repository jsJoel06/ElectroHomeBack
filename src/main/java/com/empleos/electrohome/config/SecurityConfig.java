package com.empleos.electrohome.config;

import com.empleos.electrohome.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // CORS preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Públicos
                        .requestMatchers("/api/pedidos/**").permitAll()
                        .requestMatchers("/api/imagenes/**", "/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/productos/**",
                                "/api/categorias/**"
                        ).permitAll()

                        // Protegidos (ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/productos/**")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }

    // ✅ CORS CORRECTO PARA LOCAL + PRODUCCIÓN
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "https://electrohomes.onrender.com"
        ));

        config.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        config.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept"
        ));

        // 🔴 IMPORTANTE
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationProvider provider(PasswordEncoder encoder) {
        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }
}
