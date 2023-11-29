package com.Nickode.service;

import com.Nickode.entity.NiCloudUser;
import com.Nickode.security.NiCloudJSONwebTokenManager;
import com.Nickode.security.NiCloudAuthRequest;
import com.Nickode.security.NiCloudAuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NiCloudUserService {
    @Autowired
    private final NiCloudJSONwebTokenManager niCloudJSONwebTokenManager;
    @Autowired
    private final UserDetailsService userDetailsService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final HttpServletRequest httpServletRequest;

    public NiCloudUserService(NiCloudJSONwebTokenManager niCloudJSONwebTokenManager,
                              UserDetailsService userDetailsService,
                              AuthenticationManager authenticationManager,
                              HttpServletRequest httpServletRequest) {
        this.niCloudJSONwebTokenManager = niCloudJSONwebTokenManager;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.httpServletRequest = httpServletRequest;
    }

    public NiCloudAuthResponse login(NiCloudAuthRequest authenticationRequest) {
        try {
            String username = authenticationRequest.getUsername();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, authenticationRequest.getPassword()));
            NiCloudUser niCloudUser = (NiCloudUser) userDetailsService.loadUserByUsername(username);

            if (niCloudUser == null) {
                throw new UsernameNotFoundException(username + "Failed to find");
            }

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad credentials");
        }
        UserDetails niCloudUser = NiCloudUser.builder().username(authenticationRequest.getUsername()).build();
        var token = niCloudJSONwebTokenManager.generateToken(niCloudUser);
        return NiCloudAuthResponse.builder()
                .authToken(token)
                .build();
    }

    public void logout(String token) {
        HttpSession httpSession = httpServletRequest.getSession(false);
        if (httpSession != null) {
            httpSession.removeAttribute(token);
            httpSession.invalidate();
        }
    }
}
