package spring.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.jpa.model.Cours;
import spring.jpa.repository.CoursRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourService {

    @Autowired
    private CoursRepository coursRepository;

    // Get all
    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }


    public Optional<Cours> getCoursByID(Long id) {
        return coursRepository.findById(id);
    }

    // Create
    public Cours createCours(Cours cours) {
        return coursRepository.save(cours);
    }

    // Update
    public Optional<Cours> updateCours(Long id, Cours coursDetails) {
        return coursRepository.findById(id).map(cours -> {
            cours.setCode(coursDetails.getCode());           // ✅ Ajouté
            cours.setTitre(coursDetails.getTitre());
            cours.setDescription(coursDetails.getDescription());
            cours.setFormateurs(coursDetails.getFormateurs());
            cours.setEtudiants(coursDetails.getEtudiants());
            return coursRepository.save(cours);
        });
    }

    // Delete
    public boolean deleteCours(Long id) {
        if (coursRepository.existsById(id)) {
            coursRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
