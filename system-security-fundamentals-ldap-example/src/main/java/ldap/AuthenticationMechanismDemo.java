package ldap;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ApplicationScoped
public class AuthenticationMechanismDemo implements HttpAuthenticationMechanism {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @PostConstruct
    public void init() {
        System.out.println("CDI started. identityStoreHandler = " + identityStoreHandler);
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        // Get the name and password from the request
        // WARNING: This is not suitable for production as putting the password in a request query is highly insecure
        String name = request.getParameter("name");
        String pwd = request.getParameter("password");
        if (name == null || pwd == null) {
            throw new AuthenticationException("Missing credentials");
        }
        Password password = new Password(pwd);
        CredentialValidationResult result = identityStoreHandler.validate(
                new UsernamePasswordCredential(name, password)
        );
        if (result.getStatus() != CredentialValidationResult.Status.VALID) {
            throw new AuthenticationException("Invalid credentials");
        }
        // Communicate the details of the authenticated user to the
        // container. In many cases the underlying handler will just store the details,
        // and the container will actually handle the login after we return from this method
        return httpMessageContext.notifyContainerAboutLogin(result.getCallerPrincipal(), result.getCallerGroups());
    }

    @Override
    public AuthenticationStatus secureResponse(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        return HttpAuthenticationMechanism.super.secureResponse(request, response, httpMessageContext);
    }

    @Override
    public void cleanSubject(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        HttpAuthenticationMechanism.super.cleanSubject(request, response, httpMessageContext);
    }
}
