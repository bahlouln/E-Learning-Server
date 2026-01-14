package spring.jpa.service;

import org.springframework.stereotype.Service;
import spring.jpa.model.Utilisateur;
import spring.jpa.repository.UtilisateurRepository;

import java.util.Optional;

@Service
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;

    public AuthService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Optional<Utilisateur> authenticate(String username, String password) {
        return utilisateurRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password)); // ⚠️ en vrai, encoder avec BCrypt
    }
}
