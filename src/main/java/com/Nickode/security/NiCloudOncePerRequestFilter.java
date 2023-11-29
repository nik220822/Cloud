package com.Nickode.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class NiCloudOncePerRequestFilter extends OncePerRequestFilter {
    @Autowired
    private final NiCloudJSONwebTokenManager jSONwebTokenManager;

    public NiCloudOncePerRequestFilter(NiCloudJSONwebTokenManager jSONwebTokenManager) {
        this.jSONwebTokenManager = jSONwebTokenManager;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = jSONwebTokenManager.resolveToken(request);

        if (token != null && jSONwebTokenManager.validateToken(token)) {
            Authentication auth = jSONwebTokenManager.getAuthentication(token);

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
