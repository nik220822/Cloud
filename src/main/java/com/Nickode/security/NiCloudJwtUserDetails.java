package com.Nickode.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class NiCloudJwtUserDetails extends User {

    public final String id;

    public NiCloudJwtUserDetails(final String id, final String username, final String hash,
                                 final Collection<? extends GrantedAuthority> authorities) {
        super(username, hash, authorities);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

