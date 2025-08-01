import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;

@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class.getName());

    private static final String PROTECTED_AREA = "/pages";

    private final SecureRandom random = new SecureRandom();

    private static OAuth20Service oAuth20Service;

    private String contextPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        contextPath = filterConfig.getServletContext().getContextPath();
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        boolean allowedToProceed = true;

        if (oAuth20Service == null) {
            defineOAuth20Service(req);
        }

        // Continue the filter
        chain.doFilter(request, response);
    }

    private void defineOAuth20Service(HttpServletRequest request) {
        String callbackURL = defineCallbackURL(request);
        LOGGER.info("Callback URL is calculated as  " + callbackURL);

        oAuth20Service = new ServiceBuilder(Constants.CLIENT_ID)
                .apiSecret(Constants.SECRET_ID)
                .defaultScope("email https://www.googleapis.com/auth/calendar.readonly")
                .callback(callbackURL)
                .build(GoogleApi20.instance());
    }

    private String defineCallbackURL(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();

        String callbackURLFirstPart = requestURL.substring(0, requestURL.length() - requestURI.length());
        return callbackURLFirstPart + contextPath + Constants.CALLBACK_SERVLET_PATH;
    }

    public static OAuth20Service getOAuth20Service() {
        return oAuth20Service;
    }

    private String nextCSRFToken() {
        return new BigInteger(130, random).toString(32);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
