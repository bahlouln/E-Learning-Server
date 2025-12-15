package spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.jpa.model.Inscription;

@Repository

public interface InscriptionsRepository extends JpaRepository<Inscription,Long>{

}
