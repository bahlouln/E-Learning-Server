package spring.jpa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final AdminUserDetailsService adminDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(AdminUserDetailsService adminDetailsService, JwtTokenFilter jwtTokenFilter) {
        this.adminDetailsService = adminDetailsService;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    // ---------------------- SSR Admin ----------------------
    @Bean
    @Order(1)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http
            // On ne touche pas aux sessions ici, Spring gère la session
            .securityMatcher("/login", "/formateur/**", "/cours/**", "/groupe/**", "/etudiant/**", "/css/**", "/js/**")
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                corsConfig.setAllowedOrigins(java.util.List.of("http://localhost:3000")); // ton frontend React
                corsConfig.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                corsConfig.setAllowedHeaders(java.util.List.of("*"));
                corsConfig.setAllowCredentials(true);
                return corsConfig;
            }))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                .requestMatchers("/formateur/**", "/cours/**", "/groupe/**", "/etudiant/**").hasRole("ADMIN")
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/formateur/index", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
            );

        return http.build();
    }

    // ---------------------- JWT API ----------------------
    @Bean
    @Order(2)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**") // ⚠️ Très important pour que cette chaîne ne matche que /api/**
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                corsConfig.setAllowedOrigins(java.util.List.of("http://localhost:3000")); // ton frontend React
                corsConfig.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                corsConfig.setAllowedHeaders(java.util.List.of("*"));
                corsConfig.setAllowCredentials(true);
                return corsConfig;
            }))
            .authorizeHttpRequests(auth -> auth
            	    // endpoints publics (login, test, refresh...)
            	    .requestMatchers("/api/auth/**").permitAll()
            	    
            	    // endpoints sécurisés
            	    .requestMatchers("/api/etudiant/**").hasRole("ETUDIANT")
            	    .requestMatchers("/api/formateur/**").hasRole("FORMATEUR")
            	    
            	    // tout le reste doit être authentifié
            	    .anyRequest().authenticated()
            	)

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .httpBasic(httpBasic -> httpBasic.disable())
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ---------------------- Password encoder ----------------------
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ---------------------- AuthenticationManager ----------------------
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(adminDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .build();
    }
}
