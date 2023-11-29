package com.Nickode.service;

import com.Nickode.entity.NiCloudUser;
import com.Nickode.repository.NiCloudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final NiCloudUserRepository niCloudUserRepository;

    public UserDetailsServiceImpl(NiCloudUserRepository niCloudUserRepository) {
        this.niCloudUserRepository = niCloudUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NiCloudUser niCloudUser = niCloudUserRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException(String.format("User '%s' not found", username));
        });
        return niCloudUser;
    }
}
