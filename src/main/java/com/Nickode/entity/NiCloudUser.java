package com.Nickode.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users")
public class NiCloudUser implements UserDetails {
    @Id
    @GeneratedValue(generator = "NickIDgenerator")
    @GenericGenerator(
            name = "NickIDgenerator",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<NiCloudFile> niCloudFiles;

    @Autowired
    public NiCloudUser(String id, String username, String password, List<NiCloudFile> niCloudFiles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.niCloudFiles = niCloudFiles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
