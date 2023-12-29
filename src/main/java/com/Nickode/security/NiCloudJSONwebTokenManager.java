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
    private static final Duration duration = Duration.ofMinutes(60);
    private final List<String> blackTokens = new ArrayList<>();
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public NiCloudJSONwebTokenManager(@Value("${jwt.secret}") final String jwtSecret) {
        this.algorithm = Algorithm.HMAC384(jwtSecret);
        this.verifier = JWT.require(this.algorithm).build();
    }

    public String generateToken(UserDetails userDetails) {
        final Instant now = Instant.now();
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer("nikolaiCloudTokenIssuer")
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(duration.toMillis()))
                .sign(this.algorithm);
    }

    public String getAuthentication(final String token) {
        if (blackTokens.contains(token)) {
            return null;
        }
        try {
            return verifier.verify(token).getSubject();
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
