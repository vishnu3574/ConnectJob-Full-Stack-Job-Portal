package com.connectjob.controller;

import com.connectjob.model.User;
import com.connectjob.repository.UserRepository;
import com.connectjob.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User u) {
        if (users.findByEmail(u.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        u.setPassword(encoder.encode(u.getPassword()));
        users.save(u);
        String token = jwt.generate(u.getEmail());
        return Map.of("token", token, "email", u.getEmail(), "role", u.getRole());
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String,String> body) {
        String email = body.get("email");
        String password = body.get("password");
        var user = users.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        String token = jwt.generate(user.getEmail());
        return Map.of("token", token, "email", user.getEmail(), "role", user.getRole());
    }
}
