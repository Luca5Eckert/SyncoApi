package com.api.synco.infrastructure.security;

import com.api.synco.infrastructure.security.jwt.JwtTokenAuthenticationFilter;
import com.api.synco.infrastructure.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Spring Security configuration class for the Synco API.
 *
 * <p>This configuration class sets up the security infrastructure including:</p>
 * <ul>
 *   <li>JWT-based stateless authentication</li>
 *   <li>CSRF protection (disabled for REST API)</li>
 *   <li>Endpoint authorization rules</li>
 *   <li>Password encoding using BCrypt</li>
 * </ul>
 *
 * <p>The security configuration follows a stateless approach where each request
 * must include a valid JWT token for protected endpoints.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see JwtTokenAuthenticationFilter
 * @see JwtTokenProvider
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    /**
     * Creates the JWT token authentication filter bean.
     *
     * <p>This filter intercepts incoming requests and validates JWT tokens
     * to authenticate users.</p>
     *
     * @param jwtTokenProvider the JWT token provider for token operations
     * @param userDetailsService the service for loading user details
     * @return a configured {@link JwtTokenAuthenticationFilter} instance
     */
    @Bean
    public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider,
            UserDetailsService userDetailsService
    ) {
        return new JwtTokenAuthenticationFilter(jwtTokenProvider, userDetailsService, resolver);
    }

    /**
     * Configures the security filter chain.
     *
     * <p>This method configures:</p>
     * <ul>
     *   <li>CSRF protection disabled (suitable for REST APIs)</li>
     *   <li>Stateless session management</li>
     *   <li>Authorization rules for different endpoints</li>
     *   <li>JWT authentication filter placement in the filter chain</li>
     * </ul>
     *
     * <p>Protected endpoints:</p>
     * <ul>
     *   <li>{@code /api/users/**} - Requires authentication</li>
     *   <li>{@code /api/courses/**} - Requires authentication</li>
     *   <li>{@code /api/auth/password} - Requires authentication</li>
     * </ul>
     *
     * @param http the {@link HttpSecurity} to configure
     * @param authenticationFilter the JWT authentication filter
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenAuthenticationFilter authenticationFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/actuator/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Creates the authentication manager bean.
     *
     * <p>The authentication manager is used to authenticate user credentials
     * during the login process.</p>
     *
     * @param authenticationConfiguration the Spring authentication configuration
     * @return the configured {@link AuthenticationManager}
     * @throws Exception if the authentication manager cannot be created
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Creates the password encoder bean using BCrypt algorithm.
     *
     * <p>BCrypt is a strong adaptive hashing function that automatically
     * handles salt generation and is resistant to brute-force attacks.</p>
     *
     * @return a {@link BCryptPasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}