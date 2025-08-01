import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public final class Constants {

    private static final Config config = ConfigProvider.getConfig();

    // Loaded from environment variables through MicroProfile Config
    public static final String CLIENT_ID = config.getValue("oauth.client.id", String.class);
    public static final String SECRET_ID = config.getValue("oauth.secret.id", String.class);

    public static final String CALLBACK_SERVLET_PATH = "/oauth2callback";

    // Session keys
    public static final String USER_TOKEN = "user_OAuth2_token";
    public static final String ORIGINAL_URL = "orginalURL";
    public static final String CSRF_TOKEN = "csrf_token";

    private Constants() {
        throw new IllegalStateException("Unable to instantiate");
    }
}
