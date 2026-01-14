package spring.jpa.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import spring.jpa.model.Utilisateur;
import spring.jpa.repository.UtilisateurRepository;

@Service
public class EtudiantUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepo;

    public EtudiantUserDetailsService(UtilisateurRepository utilisateurRepo) {
        this.utilisateurRepo = utilisateurRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = utilisateurRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        if (!"ETUDIANT".equals(user.getRole())) {
            throw new UsernameNotFoundException("Pas un étudiant");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_ETUDIANT"))
        );
    }
}
