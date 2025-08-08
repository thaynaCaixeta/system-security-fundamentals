package db;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@DatabaseIdentityStoreDefinition(
        // To configure a specific database, add the property:
        // dataSourceLookup = "jdbc/test-mysql",
     callerQuery = "select password from users where name = ?",
     groupsQuery = "select group_name from user_groups where username = ?"
)

@BasicAuthenticationMechanismDefinition

@DeclareRoles( { "foo", "bar" })

@ApplicationScoped
public class ApplicationConfiguration {
}
