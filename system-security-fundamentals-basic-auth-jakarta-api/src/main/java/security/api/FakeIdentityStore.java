package security.api;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import java.util.HashSet;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static java.util.Arrays.asList;

@ApplicationScoped
public class FakeIdentityStore implements IdentityStore {

    public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {
        System.out.println(usernamePasswordCredential.compareTo("admin", "admin"));
        if (usernamePasswordCredential.compareTo("admin", "admin")) {
            return new CredentialValidationResult("admin", new HashSet<>(asList("foo", "bar")));
        }

        return INVALID_RESULT;
    }

}
