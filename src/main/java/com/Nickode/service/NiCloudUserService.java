package com.Nickode.service;

import com.Nickode.entity.NiCloudUser;
import com.Nickode.repository.NiCloudUserRepository;
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
public class NiCloudUserService implements UserDetailsService {
    @Autowired
    private final NiCloudJSONwebTokenManager niCloudJSONwebTokenManager;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final HttpServletRequest httpServletRequest;
    @Autowired
    private final NiCloudUserRepository niCloudUserRepository;

    public NiCloudUserService(NiCloudJSONwebTokenManager niCloudJSONwebTokenManager,
                              AuthenticationManager authenticationManager,
                              HttpServletRequest httpServletRequest,
                              NiCloudUserRepository niCloudUserRepository) {
        this.niCloudJSONwebTokenManager = niCloudJSONwebTokenManager;
        this.authenticationManager = authenticationManager;
        this.httpServletRequest = httpServletRequest;
        this.niCloudUserRepository = niCloudUserRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NiCloudUser niCloudUser = niCloudUserRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException(String.format("User '%s' not found", username));
        });
        return niCloudUser;
    }

    public NiCloudAuthResponse login(NiCloudAuthRequest niCloudAuthRequest) {
        try {
            String username = niCloudAuthRequest.getUsername();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, niCloudAuthRequest.getPassword()));
            NiCloudUser niCloudUser = (NiCloudUser) loadUserByUsername(username);

            if (niCloudUser == null) {
                throw new UsernameNotFoundException(username + "Failed to find");
            }

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad credentials");
        }
        UserDetails niCloudUser = NiCloudUser.builder().username(niCloudAuthRequest.getUsername()).build();
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
