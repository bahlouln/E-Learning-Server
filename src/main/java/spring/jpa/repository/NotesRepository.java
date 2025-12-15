package spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.jpa.model.Note;

@Repository

public interface NotesRepository extends JpaRepository <Note,Long>{

}
