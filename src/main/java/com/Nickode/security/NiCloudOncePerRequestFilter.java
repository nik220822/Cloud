package com.Nickode.security;

import com.Nickode.service.NiCloudUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class NiCloudOncePerRequestFilter extends OncePerRequestFilter {
    @Autowired
    private final NiCloudJSONwebTokenManager niCloudJSONwebTokenManager;
    @Autowired
    private final NiCloudUserService niCloudUserService;

    public NiCloudOncePerRequestFilter(NiCloudJSONwebTokenManager niCloudJSONwebTokenManager, NiCloudUserService niCloudUserService) {
        this.niCloudJSONwebTokenManager = niCloudJSONwebTokenManager;
        this.niCloudUserService = niCloudUserService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final String requestHeader = request.getHeader("auth-token");
        final String token = requestHeader.substring(7);
        final String username = niCloudJSONwebTokenManager.getAuthentication(token);
        if (requestHeader == null || !requestHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (username == null) {
            filterChain.doFilter(request, response);
            return;
        }
        final UserDetails userDetails = niCloudUserService.loadUserByUsername(username);
        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}
