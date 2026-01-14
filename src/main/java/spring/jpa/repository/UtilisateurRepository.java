package spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.jpa.model.Utilisateur;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByUsername(String username);
}
