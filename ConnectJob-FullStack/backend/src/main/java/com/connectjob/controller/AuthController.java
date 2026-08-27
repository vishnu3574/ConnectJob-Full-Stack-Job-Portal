package com.connectjob.controller;

import com.connectjob.model.User;
import com.connectjob.repository.UserRepository;
import com.connectjob.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Request classes
    public static class RegisterRequest {
        public String name;
        public String email;
        public String password;
        public String role;
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.existsByEmail(req.email)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
        }

        User user = new User();
        user.setName(req.name);
        user.setEmail(req.email);
        user.setPassword(passwordEncoder.encode(req.password));
        
        if ("EMPLOYER".equalsIgnoreCase(req.role)) {
            user.setRole(User.Role.EMPLOYER);
        } else {
            user.setRole(User.Role.JOB_SEEKER);
        }

        userRepository.save(user);
        String token = jwtService.generate(user.getEmail());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "name", user.getName(),
                "role", user.getRole().name(),
                "email", user.getEmail()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User user = userRepository.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(req.password, user.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
        }

        String token = jwtService.generate(user.getEmail());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "name", user.getName(),
                "role", user.getRole().name(),
                "email", user.getEmail()
        ));
    }
}
