package spring.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.jpa.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
		Optional<Admin> findByEmail(String email); 

}
