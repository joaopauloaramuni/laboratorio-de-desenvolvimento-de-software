package com.example.SecureLogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserConfig userConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/login/**").permitAll() // Permitir acesso a GET na URL de login
                        .requestMatchers(HttpMethod.POST, "/login/**").permitAll() // Permitir acesso a POST na URL de login
                        .requestMatchers(HttpMethod.GET, "/css/**").permitAll() // Permitir acesso a arquivos CSS
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Proteger URLs que começam com /admin para apenas ADMIN
                        .anyRequest().authenticated() // Proteger todas as outras URLs
                )
                .formLogin(form -> form
                        .loginPage("/login") // Especifica a URL da página de login
                        .permitAll()
                        //.defaultSuccessUrl("/home", true) // Redireciona após o login com sucesso
                        .successHandler((request, response, authentication) -> {
                            // Verifica se o usuário tem a role ADMIN
                            if (authentication.getAuthorities().stream()
                                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                                response.sendRedirect("/admin"); // Redireciona para /admin
                            } else {
                                response.sendRedirect("/home"); // Redireciona para /home
                            }
                        })
                        .failureHandler((request, response, authentication) -> {
                            response.sendRedirect("/error"); // Redireciona para /error em caso de falha
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Define a URL para logout
                        .logoutSuccessUrl("/login?logout=true") // Redireciona após logout com sucesso
                        .permitAll());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username(userConfig.getUserUsername())
                .password(passwordEncoder().encode(userConfig.getUserPassword())) // Codificar a senha
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username(userConfig.getAdminUsername())
                .password(passwordEncoder().encode(userConfig.getAdminPassword()))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user,admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
