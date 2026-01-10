package spring.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.jpa.model.Etudiant;
import spring.jpa.repository.EtudiantsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantsRepository etudiantRepository;

    // Récupérer tous les étudiants
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    // Récupérer un étudiant par matricule
    public Optional<Etudiant> getEtudiantByMatricule(String matricule) {
        return etudiantRepository.findById(matricule);
    }

    // Créer un étudiant
    public Etudiant createEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    // Mettre à jour un étudiant
    public Optional<Etudiant> updateEtudiant(String matricule, Etudiant etudiantDetails) {
        return etudiantRepository.findById(matricule).map(etudiant -> {
            etudiant.setNom(etudiantDetails.getNom());
            etudiant.setPrenom(etudiantDetails.getPrenom());
            etudiant.setEmail(etudiantDetails.getEmail());
            etudiant.setDateInscription(etudiantDetails.getDateInscription());
            etudiant.setGroupes(etudiantDetails.getGroupes());
            return etudiantRepository.save(etudiant);
        });
    }

    // Supprimer un étudiant
    public boolean deleteEtudiant(String matricule) {
        if (etudiantRepository.existsById(matricule)) {
            etudiantRepository.deleteById(matricule);
            return true;
        }
        return false;
    }
}
