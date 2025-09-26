package com.sisgea.sisgea.ControleLogin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            // Arquivos e páginas públicas
            .requestMatchers(
                "/",
                "/sisgea/css/**",
                "/sisgea/login.html",
                "/sisgea/erro.html",
                "/favicon.ico",
                "/error",
                "/login/logar",
                "*",
                "**"
            ).permitAll()

            // APIs de admin
            .requestMatchers("/sisgea/administradores/**", "/sisgea/aeronaves/**")
                .hasAuthority("ADMIN")

            // APIs de instrutor
            .requestMatchers("/instrutor/**")
                .hasAuthority("INSTRUTOR")

            // Qualquer outra rota precisa estar logado
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .httpBasic(basic -> basic.disable())
        .formLogin(login -> login.disable());

    return http.build();
}
}

