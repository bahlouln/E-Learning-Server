package spring.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.jpa.model.Formateur;
import spring.jpa.repository.FormateursRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class FormateurService {

    @Autowired
    private FormateursRepository formateursRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;  
    // Récupérer tous les formateurs
    public List<Formateur> getAllFormateurs() {
        return formateursRepository.findAll();
    }

    // Récupérer un formateur par ID
    public Optional<Formateur> getFormateurById(Long id) {
        return formateursRepository.findById(id);
    }

    // Créer un formateur
    public Formateur createFormateur(Formateur formateur) {

        // username = matricule
        formateur.setUsername(formateur.getMatricule());

        // rôle obligatoire
        formateur.setRole("FORMATEUR");

        // password crypté
        formateur.setPassword(passwordEncoder.encode(formateur.getPassword()));

        // ⚡ Générer un matricule unique si vide
        if (formateur.getMatricule() == null || formateur.getMatricule().isEmpty()) {
            formateur.setMatricule("FRM" + System.currentTimeMillis());
        }

        return formateursRepository.save(formateur);
    }


    // Mettre à jour un formateur
    public Optional<Formateur> updateFormateur(Long id, Formateur formateurDetails) {
        return formateursRepository.findById(id).map(formateur -> {
            formateur.setNom(formateurDetails.getNom());
            formateur.setSpecialite(formateurDetails.getSpecialite());
            formateur.setEmail(formateurDetails.getEmail());
            return formateursRepository.save(formateur);
        });
    }

    // Supprimer un formateur
    public boolean deleteFormateur(Long id) {
        if (formateursRepository.existsById(id)) {
            formateursRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
