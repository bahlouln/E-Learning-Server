package spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.jpa.model.Cours;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

}
