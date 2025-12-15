package spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.jpa.model.Etudiant;


@Repository

public interface EtudiantsRepository extends JpaRepository<Etudiant, String> {
}

