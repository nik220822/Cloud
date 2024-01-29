package com.Nickode.service;

import com.Nickode.entity.NiCloudUser;
import com.Nickode.repository.NiCloudUserRepository;
import com.Nickode.security.NiCloudJwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Transactional
@Service
public class NiCloudUserService implements UserDetailsService {

    @Autowired
    private final NiCloudUserRepository niCloudUserRepository;

    public NiCloudUserService(NiCloudUserRepository niCloudUserRepository) {
        this.niCloudUserRepository = niCloudUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        NiCloudUser niCloudUser = niCloudUserRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not found"));
        String theOnlyOneRole = "ROLE_USER";
        final List<SimpleGrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority(theOnlyOneRole));
        return new NiCloudJwtUserDetails(niCloudUser.getId(), username, niCloudUser.getPassword(), roles);
    }

    public void putTokenAndUsername(String token, String userName) {
        niCloudUserRepository.putTokenAndUsername(token, userName);
    }

    public String getUserNameFromHeader(String header) {
        String token;
        if (header.startsWith("Bearer ")) {
            token = header.substring(7);
        } else {
            token = header;
        }
        return niCloudUserRepository.getUserName(token);
    }
}
