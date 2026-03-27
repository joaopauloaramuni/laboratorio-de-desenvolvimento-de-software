package com.example.JWT_RestAPI.config;

import com.example.JWT_RestAPI.model.UserEntity;
import com.example.JWT_RestAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.CommandLineRunner;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    // Configura o filtro de segurança HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/username/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // apenas ADMIN acessa
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // Serviço de usuários baseado no banco de dados
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole().name()) // Spring adiciona ROLE_ automaticamente
                        .build()
                )
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + username));
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = securityProperties.getUsers()
                .stream()
                .map(u -> User.builder()
                        .username(u.getUsername())
                        .password(passwordEncoder().encode(u.getPassword()))
                        .roles(u.getRole().name())
                        .build()
                )
                .toList();
        return new InMemoryUserDetailsManager(users);
    }*/

    // Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager necessário para login
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Inicializa usuários no banco para teste
    @Bean
    public CommandLineRunner initUsers(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            securityProperties.getUsers().forEach(u -> {
                if (repo.findByUsername(u.getUsername()).isEmpty()) {
                    repo.save(new UserEntity(
                            u.getUsername(),
                            encoder.encode(u.getPassword()),
                            u.getRole()
                    ));
                }
            });
        };
    }
}
