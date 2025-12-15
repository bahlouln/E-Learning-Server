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

    // Get by code
    public Optional<Cours> getCoursByCode(String code) {
        return coursRepository.findById(code);
    }

    // Create
    public Cours createCours(Cours cours) {
        return coursRepository.save(cours);
    }

    // Update
    public Optional<Cours> updateCours(String code, Cours coursDetails) {
        return coursRepository.findById(code).map(cours -> {

            cours.setTitre(coursDetails.getTitre());
            cours.setDescription(coursDetails.getDescription());
            cours.setFormateurs(coursDetails.getFormateurs());
            cours.setEtudiants(coursDetails.getEtudiants());

            return coursRepository.save(cours);
        });
    }

    // Delete
    public boolean deleteCours(String code) {
        if (coursRepository.existsById(code)) {
            coursRepository.deleteById(code);
            return true;
        }
        return false;
    }
}
