package com.unipay.config;

import com.unipay.security.SessionValidationFilter;
import com.unipay.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SessionValidationFilter sessionValidationFilter;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // disable CSRF for stateless authentication (API-based applications)
                .csrf(csrf -> csrf.disable())

                // configure session management as stateless (for APIs)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // authorization configuration
                .authorizeHttpRequests(auth -> auth
                        // public paths that do not require authentication
                        .requestMatchers(
                                "/v1/auth/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/webjars/**",
                                "/swagger-resources/**"
                        ).permitAll()

                        // specific endpoint protections based on roles
                        .requestMatchers("/v1/users/{userId}/mfa/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/v1/admin/**").hasAuthority("ROLE_ADMIN")

                        // any other request requires authentication
                        .anyRequest().authenticated()
                )

                // set authentication provider to use the custom user details service
                .authenticationProvider(authenticationProvider())

                // add custom session validation filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(sessionValidationFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }
    // configure CORS if needed (example for handling front-end and back-end communication)
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**"
        );
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
