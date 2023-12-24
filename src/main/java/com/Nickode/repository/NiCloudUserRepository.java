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
        Query query = entityManager.createNativeQuery("SELECT * FROM NiCloudDB.users WHERE users.username = :usernameToFind", NiCloudUser.class);
        query.setParameter("usernameToFind", username);
        List<NiCloudUser> users = query.getResultList();
        NiCloudUser niCloudUser = users.get(0);
        return Optional.of(niCloudUser);
    }
}
