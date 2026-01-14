package spring.jpa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.jpa.security.EtudiantUserDetailsService;
import spring.jpa.security.FormateurUserDetailsService;
import spring.jpa.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final EtudiantUserDetailsService etudiantService;
    private final FormateurUserDetailsService formateurService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(EtudiantUserDetailsService etudiantService,
                          FormateurUserDetailsService formateurService,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder) {
        this.etudiantService = etudiantService;
        this.formateurService = formateurService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        UserDetails userDetails;

        try {
            if ("ETUDIANT".equalsIgnoreCase(request.getRole())) {
                userDetails = etudiantService.loadUserByUsername(request.getUsername());
            } else if ("FORMATEUR".equalsIgnoreCase(request.getRole())) {
                userDetails = formateurService.loadUserByUsername(request.getUsername());
            } else {
                return ResponseEntity.badRequest().body("Role invalide");
            }

            if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
                return ResponseEntity.status(401).body("Mot de passe incorrect");
            }

            String token = jwtUtil.generateToken(userDetails.getUsername(), 
                           userDetails.getAuthorities().stream()
                           .map(a -> a.getAuthority())
                           .toList());

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(401).body("Utilisateur non trouvé");
        }
    }

    // 🔹 Classe interne LoginRequest
    public static class LoginRequest {
        private String username;
        private String password;
        private String role; // "ETUDIANT" ou "FORMATEUR"

        // ✅ Getters & Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }

    // 🔹 Classe interne LoginResponse
    public static class LoginResponse {
        private String token;
        public LoginResponse(String token) { this.token = token; }
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
    @GetMapping("/test")
    public String test() {
        return "Backend OK !";
    }

}
