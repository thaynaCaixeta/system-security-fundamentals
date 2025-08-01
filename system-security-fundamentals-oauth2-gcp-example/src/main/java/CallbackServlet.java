import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@WebServlet(Constants.CALLBACK_SERVLET_PATH)
public class CallbackServlet extends HttpServlet {

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String originalCsrfToken = (String) session.getAttribute(Constants.CSRF_TOKEN);
        String csrfToken = request.getParameter("state");

        if (!originalCsrfToken.equals(csrfToken)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token doesn't match");
        }

        String code = request.getParameter("code");
        OAuth20Service authService = AuthenticationFilter.getOAuth20Service();

        try {
            OAuth2AccessToken token = authService.getAccessToken(code);
            session.setAttribute(Constants.USER_TOKEN, token);

            String originalURL = session.getAttribute(Constants.ORIGINAL_URL).toString();

            response.sendRedirect(originalURL);
        } catch (InterruptedException | ExecutionException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
