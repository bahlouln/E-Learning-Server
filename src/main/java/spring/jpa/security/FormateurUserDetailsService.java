package spring.jpa.security;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import spring.jpa.model.Utilisateur;
import spring.jpa.repository.UtilisateurRepository;

@Service
public class FormateurUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepo;

    public FormateurUserDetailsService(UtilisateurRepository utilisateurRepo) {
        this.utilisateurRepo = utilisateurRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Cherche l'utilisateur par username
        Utilisateur user = utilisateurRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));

        // Vérifie que c'est un formateur
        if (!"FORMATEUR".equals(user.getRole())) {
            throw new UsernameNotFoundException("Utilisateur trouvé mais ce n'est pas un formateur");
        }

        // Retourne un UserDetails pour Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // mot de passe déjà encodé avec BCrypt
                List.of(new SimpleGrantedAuthority("ROLE_FORMATEUR"))
        );
    }
}
