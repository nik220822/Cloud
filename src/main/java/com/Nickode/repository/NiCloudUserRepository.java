package com.Nickode.repository;

import com.Nickode.entity.NiCloudUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NiCloudUserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<NiCloudUser> findByUsername(String username) {
        Query query = entityManager.createNativeQuery("SELECT * FROM NiCloudDB.USERS WHERE USERS.username = :usernameToFind", NiCloudUser.class);
        query.setParameter("usernameToFind", username);
        List users = query.getResultList();
        NiCloudUser niCloudUser = (NiCloudUser) users.get(0);
        return Optional.of(niCloudUser);
    }
}
