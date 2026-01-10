package spring.jpa.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import spring.jpa.model.Admin;
import spring.jpa.repository.AdminRepository;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminRepository repo;

    public AdminUserDetailsService(AdminRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = repo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Admin introuvable"));
        return org.springframework.security.core.userdetails.User
                   .withUsername(admin.getEmail())
                   .password(admin.getPassword())
                   .roles("ADMIN")
                   .build();
    }
}
