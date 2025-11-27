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

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtTokenAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver // <-- NOVO
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver; // <-- NOVO
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");


        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


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