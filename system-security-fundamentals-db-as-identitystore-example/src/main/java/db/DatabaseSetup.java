package db;

import db.model.User;
import db.model.UserGroup;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.util.HashMap;
import java.util.Map;

@Singleton
@Startup
public class DatabaseSetup {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @PostConstruct
    public void init() {
        // Populate "database"
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "100000");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);

        entityManager.persist(newUser("rudy", passwordHash.generate("secret1".toCharArray())));
        entityManager.persist(newUser("user", passwordHash.generate("pw".toCharArray())));

        entityManager.persist(newUserGroup("rudy", "foo"));
        entityManager.persist(newUserGroup("rudy", "bar"));
        entityManager.persist(newUserGroup("user", "bar"));
    }

    private UserGroup newUserGroup(String username, String groupName) {
        UserGroup result = new UserGroup();
        result.setUsername(username);
        result.setGroupName(groupName);
        return result;
    }

    private User newUser(String name, String password) {
        User result = new User();
        result.setName(name);
        result.setPassword(password);
        return result;
    }

}
