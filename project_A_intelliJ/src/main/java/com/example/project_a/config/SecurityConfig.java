package com.example.project_a.config;

import com.example.project_a.model.User;
import com.example.project_a.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Configuration
public class SecurityConfig {

    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            Optional<User> existingAdmin = userRepository.findByUsername("admin@gmail.com");

            if (existingAdmin.isEmpty()) {
                User admin = new User();
                admin.setUsername("admin@gmail.com");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("123")); // Set hashed password
                admin.setRole("Admin"); // Ensure this matches Spring Security role
                admin.setStatus("Active");

                userRepository.save(admin);
                System.out.println("Admin user created");
            } else {
                System.out.println("Admin user already exists, skipping creation.");
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return email -> {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            return user;
        };
    }


    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF if using REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/assets/**", "/uploads/**", "/shop", "/home", "/about",
                                "/blog", "/contact","/", "/login", "/product-details", "/register",
                                "/users/save", "/filter-products",
                                "/shop/register", "/users/check/email", "/users/check/username", "/users/check/**",
                                "/media/**","/forgot-password","/auth/forgot-password","/reset-password", "/auth/reset-password",
                                "/search")
                        .permitAll()

                        .requestMatchers("/admin/**").hasRole("ADMIN") // Only "/admin/**" is for admins
                        .anyRequest().hasRole("CLIENT") // Clients can access anything else
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .successHandler((request, response, authentication) -> {
                            request.getSession().removeAttribute("SPRING_SECURITY_SAVED_REQUEST"); // Clear any saved request

                            boolean isAdmin = authentication.getAuthorities().stream()
                                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

                            String redirectUrl = isAdmin ? "/admin" : "/home";
                            request.getSession().setAttribute("message", "Login successful!");
                            response.sendRedirect(request.getContextPath() + redirectUrl);
                        })
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .expiredUrl("/login?expired")
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }
}



