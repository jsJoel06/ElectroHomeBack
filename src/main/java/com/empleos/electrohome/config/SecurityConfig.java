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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Mantener deshabilitado si usas Postman/App móvil o manejas JWT manualmente
                .authorizeHttpRequests(auth -> auth
                        // 1. Rutas totalmente abiertas (Login y Registro)
                        .requestMatchers("/api/auth/**", "/login").permitAll()


                        // 2. Solo lectura: Permitir que TODOS vean productos, categorías y fotos
                        .requestMatchers(HttpMethod.GET, "/api/productos/**", "/api/categorias/**", "/uploads/**").permitAll()

                        // 3. PROTECCIÓN TOTAL: Solo Joel (ADMIN) puede crear, editar o borrar
                        // Esto protege las funciones de "Guardar" y "Editar" de tu AddForm
                        .requestMatchers(HttpMethod.POST, "/api/productos/**", "/api/categorias/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**", "/api/categorias/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**", "/api/categorias/**").hasAuthority("ADMIN")

                        // 4. Cualquier otra ruta requiere estar logueado
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/productos", true)
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationProvider provider(PasswordEncoder encoder){
        // Pasamos userDetailsService directamente al constructor para cumplir con el requerimiento
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }


}