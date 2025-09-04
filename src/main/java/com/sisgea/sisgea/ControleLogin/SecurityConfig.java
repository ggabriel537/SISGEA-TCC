package com.sisgea.sisgea.ControleLogin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/login/logar", "/sisgea/login.html", "/sisgea/css/**", "/").permitAll()
                                .requestMatchers("/Testes-API-Site/**").permitAll() // Permitir acesso sem autenticação à pasta Testes-API-Site
                                .requestMatchers("**", "/**", "/api/**").permitAll() // Permitir acesso sem autenticação a todos os endpoints (ajuste conforme necessário)
                                //.requestMatchers("**", "/**", "/api/**").authenticated() // Exigir autenticação para todos os outros endpoints
                                .anyRequest().authenticated()
                )
                .httpBasic(basic -> basic.disable())
                .formLogin(login -> login.disable());

        return http.build();
    }
}
