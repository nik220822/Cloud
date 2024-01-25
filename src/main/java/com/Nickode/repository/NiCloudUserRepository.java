package com.Nickode.repository;

import com.Nickode.entity.NiCloudUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class NiCloudUserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final Map<String, String> tokensAndUsernames = new ConcurrentHashMap<>();

    public Optional<NiCloudUser> findByUsername(String username) {
        Query query = entityManager.createNativeQuery("SELECT * FROM NiCloudDB.users WHERE users.username = :usernameToFind", NiCloudUser.class);
        query.setParameter("usernameToFind", username);
        List users = query.getResultList();
        NiCloudUser niCloudUser = (NiCloudUser) users.get(0);
        return Optional.of(niCloudUser);
    }

    public void putTokenAndUsername(String token, String username) {
        tokensAndUsernames.put(token, username);
    }

    public void removeTokenAndUsername(String token) {
        tokensAndUsernames.remove(token);
    }

    public String getUserName(String token) {
        return tokensAndUsernames.get(token);
    }
}
