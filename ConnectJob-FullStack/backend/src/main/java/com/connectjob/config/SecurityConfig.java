package com.connectjob.config;

import com.connectjob.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwt) throws Exception{
        return http.csrf(c->c.disable())
            .cors(c->c.configurationSource(r->{CorsConfiguration x=new CorsConfiguration();x.setAllowedOrigins(List.of("http://localhost:5173"));x.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));x.setAllowedHeaders(List.of("*"));return x;}))
            .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/api/jobs","/api/jobs/**","/h2-console/**").permitAll().anyRequest().authenticated())
            .headers(h->h.frameOptions(f->f.disable()))
            .addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class).build();
    }
}
