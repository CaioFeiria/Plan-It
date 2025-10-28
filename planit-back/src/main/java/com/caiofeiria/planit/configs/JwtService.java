package com.caiofeiria.planit.configs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-minutes:120}")
    private long expirationMinutes;

    public String gerarToken(String subject, String role) {
        Instant agora = Instant.now();
        Instant expiracao = agora.plus(expirationMinutes, ChronoUnit.MINUTES);
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(subject)
                .claim("role", role)
                .claim("authorities", "ROLE_" + role)
                .issuedAt(Date.from(agora))
                .expiration(Date.from(expiracao))
                .signWith(key)
                .compact();
    }

    public String gerarToken(String subject) {
        return gerarToken(subject, null);
    }

    public String validarETrazerSubject(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException ex) {
            return null;
        }
    }

    public String extrairRole(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("role", String.class);
        } catch (JwtException | IllegalArgumentException ex) {
            return null;
        }
    }

    public String extrairAuthorities(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("authorities", String.class);
        } catch (JwtException | IllegalArgumentException ex) {
            return null;
        }
    }
}


