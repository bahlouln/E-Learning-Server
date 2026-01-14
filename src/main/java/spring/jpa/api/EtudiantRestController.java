package spring.jpa.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import spring.jpa.model.Etudiant;
import spring.jpa.model.Groupe;
import spring.jpa.service.EtudiantService;
import spring.jpa.service.GroupeService;

import java.util.List;

@RestController
@RequestMapping("/api/etudiant")
public class EtudiantRestController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private GroupeService groupeService;

    // Profil de l'étudiant connecté
    @GetMapping("/me")
    @PreAuthorize("hasRole('ETUDIANT')")
    public ResponseEntity<?> getMyProfile(Authentication auth) {
        String username = auth.getName();
        // Vous devez lier username -> matricule via Utilisateur
        Etudiant etudiant = etudiantService.getEtudiantByMatricule(username)
                .orElse(null);
        if (etudiant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(etudiant);
    }

    // Mes groupes
    @GetMapping("/mes-groupes")
    @PreAuthorize("hasRole('ETUDIANT')")
    public ResponseEntity<List<Groupe>> getMesGroupes(Authentication auth) {
        String username = auth.getName();
        Etudiant etudiant = etudiantService.getEtudiantByMatricule(username)
                .orElse(null);
        if (etudiant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(etudiant.getGroupes());
    }

    // Liste tous les étudiants (pour formateur/admin)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FORMATEUR')")
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        return ResponseEntity.ok(etudiantService.getAllEtudiants());
    }
}
