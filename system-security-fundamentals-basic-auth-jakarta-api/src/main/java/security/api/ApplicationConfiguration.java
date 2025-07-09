package security.api;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

@BasicAuthenticationMechanismDefinition(
        realmName = "security"
)
@DeclareRoles({"foo", "bar", "kaz"})
@ApplicationScoped
public class ApplicationConfiguration {
}
