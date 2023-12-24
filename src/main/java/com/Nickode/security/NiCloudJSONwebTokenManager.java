package com.Nickode.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class NiCloudJSONwebTokenManager {
    private static final List<String> blackTokens = new ArrayList<>();
    private static final Duration duration = Duration.ofMinutes(60);
    private final Algorithm auth0JwtAlgorithm;
    private final JWTVerifier auth0JwtJWTVerifier;

    public NiCloudJSONwebTokenManager(@Value("${jwt.secret}") final String jwtSecret) {
        this.auth0JwtAlgorithm = Algorithm.HMAC384(jwtSecret);
        this.auth0JwtJWTVerifier = JWT.require(this.auth0JwtAlgorithm).build();
    }

    public String generateToken(UserDetails userDetails) {
        final Instant now = Instant.now();
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer("nik")
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(duration.toMillis()))
                .sign(this.auth0JwtAlgorithm);
    }

    public String getAuthentication(final String token) {
        if (blackTokens.contains(token)) {
            return null;
        }
        try {
            return auth0JwtJWTVerifier.verify(token).getSubject();
        } catch (final JWTVerificationException verificationException) {
            return null;
        }
    }

    public void addBlackTokens(String token) {
        blackTokens.add(token);
    }

    public List<String> getBlackTokens() {
        return blackTokens;
    }

}
