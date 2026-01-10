package spring.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.jpa.model.Etudiant;
import spring.jpa.model.Groupe;
import spring.jpa.repository.GroupeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GroupeService {

	
	@Autowired 
	private EtudiantService etudiantService;
    @Autowired
    private GroupeRepository groupeRepository;

    // Récupérer tous les groupes
    public List<Groupe> getAllGroupes() {
        return groupeRepository.findAll();
    }

    // Récupérer un groupe par ID
    public Optional<Groupe> getGroupeById(Long id) {
        return groupeRepository.findById(id);
    }

    // Créer un groupe
    public Groupe createGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    // Mettre à jour un groupe
    public Optional<Groupe> updateGroupe(Long id, Groupe groupeDetails) {
        return groupeRepository.findById(id).map(groupe -> {
            groupe.setNom(groupeDetails.getNom());
            groupe.setNombreLimite(groupeDetails.getNombreLimite());
            groupe.setEtudiants(groupeDetails.getEtudiants());
            groupe.setCours(groupeDetails.getCours());
            return groupeRepository.save(groupe);
        });
    }

    // Supprimer un groupe
    public boolean deleteGroupe(Long id) {
        if (groupeRepository.existsById(id)) {
            groupeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Etudiant> getEtudiantsByGroupe(Long groupeId) {
        return groupeRepository.findById(groupeId)
                .map(Groupe::getEtudiants)
                .orElseThrow(() -> new IllegalArgumentException("Groupe introuvable avec l'id : " + groupeId));
    }
    @Transactional
    public void addEtudiantToGroupe(Long groupeId, String matricule) {
        Groupe groupe = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new IllegalArgumentException("Groupe introuvable avec l'id : " + groupeId));

        Etudiant etudiant = etudiantService.getEtudiantByMatricule(matricule)
                .orElseThrow(() -> new IllegalArgumentException("Étudiant introuvable avec le matricule : " + matricule));

        if (groupe.getEtudiants().size() >= groupe.getNombreLimite()) {
            throw new IllegalStateException("⚠️ Limite atteinte : " + groupe.getNombreLimite() + " étudiants maximum.");
        }

        if (!groupe.getEtudiants().contains(etudiant)) {
            groupe.getEtudiants().add(etudiant);
            groupeRepository.save(groupe);
        }
    }
    @Transactional
    public void removeEtudiantFromGroupe(Long groupeId, String matricule) {
        Groupe groupe = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new IllegalArgumentException("Groupe introuvable avec l'id : " + groupeId));

        Etudiant etudiant = etudiantService.getEtudiantByMatricule(matricule)
                .orElseThrow(() -> new IllegalArgumentException("Étudiant introuvable avec le matricule : " + matricule));

        if (groupe.getEtudiants().contains(etudiant)) {
            groupe.getEtudiants().remove(etudiant);
            groupeRepository.save(groupe);
        }
    }





}
