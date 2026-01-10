package spring.jpa.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.jpa.model.Admin;
import spring.jpa.repository.AdminRepository;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final AdminRepository repo;
    private final PasswordEncoder encoder;

    public AdminSeeder(AdminRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        // Vérifie si un admin existe déjà
        repo.findByEmail("admin@centre.tn").orElseGet(() -> {
            Admin a = new Admin();
            a.setEmail("admin@centre.tn");
            a.setPassword(encoder.encode("1234")); // mot de passe encodé en BCrypt
            return repo.save(a);
        });
    }
}
