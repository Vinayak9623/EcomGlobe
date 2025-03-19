package com.ecom.JWTAuth;

import io.jsonwebtoken.JwtHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/order/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/product/**").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET,"/category/**").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST,"/category/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/category/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/category/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/product/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/product/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/product/**").hasAuthority("ADMIN")
                                .anyRequest()
                                .authenticated()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

}
