package spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.jpa.model.Formateur;

@Repository

public interface FormateursRepository extends JpaRepository<Formateur, Long>{
	boolean existsByEmail(String email);
}
