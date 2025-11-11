package com.example.reservation.controller;

import com.example.reservation.dto.SignupRequest;
import com.example.reservation.model.User;
import com.example.reservation.security.JwtUtil;
import com.example.reservation.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        User created = userService.register(req.getUsername(), req.getEmail(), req.getPassword(), req.getRole() == null ? "USER" : req.getRole());
        // نداشتن password در response امن تر است؛ اینجا نمونه ساده است
        return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "user", Map.of(
                        "id", created.getId(),
                        "username", created.getUsername(),
                        "email", created.getEmail(),
                        "roles", created.getRoles().stream().map(r -> r.getName()).toArray()
                )
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        var opt = userService.findByEmail(email);
        if (opt.isEmpty()) return ResponseEntity.status(401).body("Invalid credentials");
        User user = opt.get();
        if (!userService.passwordMatches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getEmail()); // یا username
        return ResponseEntity.ok(Map.of("token", token));
    }
}
