package com.api.synco.shared.security.jwt;

import com.api.synco.shared.exception.token.TokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.Key;

/**
 * Provider class for JWT token operations.
 *
 * <p>This component handles all JWT-related operations including:</p>
 * <ul>
 *   <li>Token generation with configurable expiration</li>
 *   <li>Token validation and parsing</li>
 *   <li>Claims extraction from tokens</li>
 *   <li>User email extraction from tokens</li>
 * </ul>
 *
 * <p>The JWT tokens are signed using the HMAC-SHA256 algorithm with a secret key
 * configured via application properties.</p>
 *
 * <p>Configuration properties:</p>
 * <ul>
 *   <li>{@code jwt.secret} - The secret key for signing tokens</li>
 *   <li>{@code jwt.token.validity} - Token validity in milliseconds</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see JwtTokenAuthenticationFilter
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final Key key;
    private final long validityInMilliseconds;

    /**
     * Constructs a new JWT token provider with the specified configuration.
     *
     * @param secret the secret key for signing tokens (must not be null or empty)
     * @param validityInMilliseconds the token validity period in milliseconds
     * @throws IllegalArgumentException if the secret is null or empty
     */
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token.validity}") long validityInMilliseconds
    ) {
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT secret must not be null or empty");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    /**
     * Generates a JWT token for the specified user email.
     *
     * <p>The generated token includes:</p>
     * <ul>
     *   <li>Subject set to the user's email</li>
     *   <li>Email claim</li>
     *   <li>Issue timestamp</li>
     *   <li>Expiration timestamp based on configured validity</li>
     * </ul>
     *
     * @param email the user's email to include in the token
     * @return the generated JWT token as a string
     */
    public String generateToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("email", email);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates a JWT token.
     *
     * <p>This method attempts to parse the token and returns true if the token
     * is valid (properly signed, not expired, and well-formed).</p>
     *
     * @param token the JWT token to validate
     * @return {@code true} if the token is valid, {@code false} otherwise
     */
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (TokenInvalidException e) {
            logger.debug("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Parses and validates the claims from a JWT token.
     *
     * <p>This method validates the token's signature, expiration, and format,
     * throwing appropriate exceptions for different failure scenarios.</p>
     *
     * @param token the JWT token to parse
     * @return the {@link Claims} extracted from the token
     * @throws TokenInvalidException if the token is null, empty, expired, malformed,
     *                               has an invalid signature, or is unsupported
     */
    public Claims parseClaims(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new TokenInvalidException("Token is null or empty");
        }

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenInvalidException("Token expired");

        } catch (UnsupportedJwtException e) {
            throw new TokenInvalidException("Unsupported token");

        } catch (MalformedJwtException e) {
            throw new TokenInvalidException("Malformed token");

        } catch (SignatureException e) {
            throw new TokenInvalidException("Invalid token signature");

        } catch (IllegalArgumentException e) {
            throw new TokenInvalidException("Token string is empty or has illegal arguments");

        }

    }

    /**
     * Extracts the user email from a JWT token.
     *
     * <p>The email is extracted from either the "email" claim or the subject claim.</p>
     *
     * @param token the JWT token
     * @return the user's email extracted from the token
     * @throws TokenInvalidException if the token is invalid or does not contain an email
     */
    public String getUserEmail(String token) {
        Claims claims = parseClaims(token);

        String email = claims.get("email", String.class);
        if (email == null || email.trim().isEmpty()) {
            email = claims.getSubject();
        }

        if (email == null || email.trim().isEmpty()) {
            throw new TokenInvalidException("Email not present in token claims");
        }

        return email;
    }
}