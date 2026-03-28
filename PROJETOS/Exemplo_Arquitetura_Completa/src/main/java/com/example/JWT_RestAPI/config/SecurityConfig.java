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

/**
 * Classe de configuração de segurança da aplicação.
 *
 * <p>Responsável por definir as regras de autenticação e autorização,
 * configuração de filtros de segurança, gerenciamento de usuários,
 * codificação de senhas e inicialização de dados no sistema.</p>
 *
 * <p>Utiliza o Spring Security para proteger os endpoints da API,
 * definindo quais rotas são públicas e quais exigem autenticação
 * ou papéis específicos (roles).</p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Propriedades de segurança carregadas da configuração da aplicação.
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * Configura o filtro de segurança HTTP da aplicação.
     *
     * <p>Define:</p>
     * <ul>
     *     <li>Desabilitação de CSRF</li>
     *     <li>Rotas públicas (login, consulta de usuário)</li>
     *     <li>Restrição de acesso a endpoints administrativos</li>
     *     <li>Autenticação obrigatória para demais requisições</li>
     *     <li>Uso de autenticação HTTP Basic</li>
     * </ul>
     *
     * @param http objeto de configuração de segurança HTTP
     * @return cadeia de filtros de segurança configurada
     * @throws Exception em caso de erro na configuração
     */
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

    /**
     * Define o serviço de carregamento de usuários baseado no banco de dados.
     *
     * <p>Busca um usuário pelo nome de usuário e o converte para um objeto
     * compatível com o Spring Security.</p>
     *
     * <p>Caso o usuário não seja encontrado, uma exceção é lançada.</p>
     *
     * @param userRepository repositório de usuários
     * @return implementação de {@link UserDetailsService}
     */
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

    /**
     * Define o codificador de senhas da aplicação.
     *
     * <p>Utiliza o algoritmo BCrypt para garantir segurança no armazenamento
     * de senhas.</p>
     *
     * @return instância de {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Fornece o {@link AuthenticationManager} necessário para o processo de autenticação.
     *
     * <p>Este componente é utilizado internamente pelo Spring Security
     * para validar credenciais de login.</p>
     *
     * @param config configuração de autenticação
     * @return instância de {@link AuthenticationManager}
     * @throws Exception em caso de erro ao obter o manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Inicializa usuários no banco de dados para fins de teste.
     *
     * <p>Para cada usuário definido nas propriedades de segurança,
     * verifica se ele já existe no banco. Caso não exista, realiza
     * a persistência com a senha devidamente codificada.</p>
     *
     * @param repo repositório de usuários
     * @param encoder codificador de senhas
     * @return {@link CommandLineRunner} que executa na inicialização da aplicação
     */
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