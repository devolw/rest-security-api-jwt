package ru.popoff.spring.FirstSecurityApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.popoff.spring.FirstSecurityApp.services.PersonDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

// Главный класс с настройкой Spring Security
@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Дает возможность использовать @PreAuthorize
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;
    private final JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, JWTFilter jwtFilter) {
        this.personDetailsService = personDetailsService;
        this.jwtFilter = jwtFilter;
    }

    // Настраивает аутентификацию
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                // Для страниц login, registration, error получают доступ все роли
                                .requestMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                                .anyRequest()
                                // К остальным страницам получают доступ USER, ADMIN
                                .hasAnyRole("USER", "ADMIN")
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/process_login")
                                .defaultSuccessUrl("/hello", true)
                                .failureUrl("/auth/login?error")
                )

                .csrf(csrf ->
                        csrf.disable()
                )

                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/auth/login")
                )

                // Устанавливаем политику создания сессий на STATELESS, чтобы не создавать сессии
                .sessionManagement(sesMen ->
                        sesMen.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Добавляем наш фильтр перед аутентификационным фильтром
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(withDefaults())
                .userDetailsService(personDetailsService);

        return http.build();
    }

    // Используется для шифрования пароля
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}