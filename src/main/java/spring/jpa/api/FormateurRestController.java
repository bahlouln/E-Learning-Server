package spring.jpa.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import spring.jpa.model.Cours;
import spring.jpa.model.Formateur;
import spring.jpa.repository.FormateursRepository;
import spring.jpa.service.CourService;

import java.util.List;

@RestController
@RequestMapping("/api/formateur")
public class FormateurRestController {

    @Autowired
    private FormateursRepository formateurRepository;

    @Autowired
    private CourService courService;

    // Profil du formateur connecté
    @GetMapping("/me")
    @PreAuthorize("hasRole('FORMATEUR')")
    public ResponseEntity<?> getMyProfile(Authentication auth) {
        String username = auth.getName();
        // Vous devez lier username -> formateur via Utilisateur
        // Pour l'instant, cherche par email
        Formateur formateur = formateurRepository.findAll().stream()
                .filter(f -> f.getEmail().equals(username))
                .findFirst()
                .orElse(null);
        
        if (formateur == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(formateur);
    }

    // Mes cours
    @GetMapping("/mes-cours")
    @PreAuthorize("hasRole('FORMATEUR')")
    public ResponseEntity<List<Cours>> getMesCours(Authentication auth) {
        String username = auth.getName();
        Formateur formateur = formateurRepository.findAll().stream()
                .filter(f -> f.getEmail().equals(username))
                .findFirst()
                .orElse(null);
        
        if (formateur == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(formateur.getCours());
    }

    // Liste tous les cours
    @GetMapping("/cours")
    @PreAuthorize("hasAnyRole('FORMATEUR', 'ADMIN')")
    public ResponseEntity<List<Cours>> getAllCours() {
        return ResponseEntity.ok(courService.getAllCours());
    }
}