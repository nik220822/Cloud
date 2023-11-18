package com.Nickode.repository;

import com.Nickode.entity.NiCloudUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NiCloudUserRepository extends JpaRepository<NiCloudUser, String> {
    Optional<NiCloudUser> findByUserName(String username);
    List<NiCloudUser> findByNameIsNotNull();
}
