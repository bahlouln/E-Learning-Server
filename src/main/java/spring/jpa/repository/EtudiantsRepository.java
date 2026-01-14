package spring.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.jpa.model.Etudiant;


@Repository
public interface EtudiantsRepository extends JpaRepository<Etudiant, Long> {
    Optional<Etudiant> findByMatricule(String matricule);
    boolean existsByMatricule(String matricule);
    void deleteByMatricule(String matricule);
}

