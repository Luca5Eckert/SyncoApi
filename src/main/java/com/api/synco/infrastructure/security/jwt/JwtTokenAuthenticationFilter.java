package com.api.synco.infrastructure.security.jwt;

import com.api.synco.infrastructure.exception.token.TokenException;
import com.api.synco.infrastructure.exception.token.TokenInvalidException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * JWT authentication filter that processes incoming HTTP requests.
 *
 * <p>This filter extends {@link OncePerRequestFilter} to ensure it executes once per request.
 * It intercepts incoming requests, extracts the JWT token from the Authorization header,
 * validates it, and sets up the Spring Security authentication context.</p>
 *
 * <p>The filter performs the following operations:</p>
 * <ol>
 *   <li>Extracts the JWT token from the "Authorization" header (Bearer scheme)</li>
 *   <li>Validates the token using {@link JwtTokenProvider}</li>
 *   <li>Loads user details from the database</li>
 *   <li>Creates and sets the authentication in the security context</li>
 * </ol>
 *
 * <p>If no token is present, the request proceeds without authentication.
 * If the token is invalid, an exception is thrown and handled by the
 * configured exception resolver.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see JwtTokenProvider
 * @see OncePerRequestFilter
 */
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    /**
     * Constructs a new JWT token authentication filter.
     *
     * @param jwtTokenProvider the provider for JWT token operations
     * @param userDetailsService the service for loading user details
     * @param handlerExceptionResolver the resolver for handling exceptions
     */
    public JwtTokenAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    /**
     * Extracts the JWT token from the Authorization header.
     *
     * <p>The token must be provided using the Bearer authentication scheme:
     * {@code Authorization: Bearer <token>}</p>
     *
     * @param request the HTTP request
     * @return the JWT token without the "Bearer " prefix, or {@code null} if not present
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");


        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    /**
     * Processes the incoming request and performs JWT authentication.
     *
     * <p>This method:</p>
     * <ul>
     *   <li>Extracts and validates the JWT token</li>
     *   <li>Loads user details and creates authentication</li>
     *   <li>Sets the authentication in the security context</li>
     *   <li>Delegates to the next filter in the chain</li>
     * </ul>
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            String jwt = getJwtFromRequest(request);

            if(jwt == null){
                filterChain.doFilter(request, response);
                return;
            }

            if (!jwtTokenProvider.validateToken(jwt)) {
                throw new TokenInvalidException();
            }


            String email = jwtTokenProvider.getUserEmail(jwt);


            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {


                UserDetails userDetails = userDetailsService.loadUserByUsername(email);


                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());


                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {

            logger.error("Authentication filter error: " + ex.getMessage());

            this.handlerExceptionResolver.resolveException(request, response, null, ex);

        }

    }

}