package com.Nickode.service;

import com.Nickode.entity.NiCloudUser;
import com.Nickode.repository.NiCloudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return niCloudUser;
    }
}
