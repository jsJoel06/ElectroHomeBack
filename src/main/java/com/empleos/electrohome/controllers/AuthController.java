package com.empleos.electrohome.controllers;

import com.empleos.electrohome.models.EROLE;
import com.empleos.electrohome.models.UserEntity;
import com.empleos.electrohome.repository.UserEntityRepository;
import com.empleos.electrohome.request.LoginRequest;
import com.empleos.electrohome.request.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
        origins = "https://electrohomes.onrender.com",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true"
)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserEntityRepository userEntityRepository;


    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login realizado con éxito");
            response.put("sesion_id", session.getId());
            response.put("email", loginRequest.getEmail());
            return ResponseEntity.ok(authentication);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas: " + e.getMessage());
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest requestRequest) {
        try {
            if (userEntityRepository.existsByEmail(requestRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "El email ya está registrado"));
            }

            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(requestRequest.getEmail());
            userEntity.setPassword(passwordEncoder.encode(requestRequest.getPassword()));

            // --- ASIGNACIÓN DE ROL POR DEFECTO ---
            // Opción A: Si usas un Set de Strings o Enums
            userEntity.setRoles(Set.of(EROLE.CLIENT));

            userEntityRepository.save(userEntity);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Usuario registrado correctamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }

}
