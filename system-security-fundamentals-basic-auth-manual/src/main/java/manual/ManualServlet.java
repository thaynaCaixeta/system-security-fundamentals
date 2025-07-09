package manual;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/protected/manual")
public class ManualServlet extends HttpServlet {

    // Represents allowed users
    private Map<String, String> users;

    @Override
    public void init() throws ServletException {
        users = new HashMap<>();
        users.put("tackr-admin", "tackr-admin");
        users.put("admin", "admin");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authorizationHeader = req.getHeader("Authorization");
        if (!isAuthorized(authorizationHeader)) {
            // Not allowed, report unauthorized
            resp.setHeader("WWW-Authenticate", "BASIC realm=\"Encoding\"");
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            resp.getWriter().println("Highly sensitive content");
        }
    }

    private boolean isAuthorized(String authorizationHeader) {
        if (authorizationHeader == null) {
            return false;
        }
        // Only supports basic auth for this example
        if (!authorizationHeader.toUpperCase().startsWith("BASIC")) {
            return false;
        }
        // Decode user and password sent throw the header
        String encodedUserPw = authorizationHeader.substring(6); // Comes after "BASIC"
        byte[] decodedUserPw = Base64.getDecoder().decode(encodedUserPw);
        String[] decodedParts = new String(decodedUserPw).split(":");
        String password = users.get(decodedParts[0]);

        // Check if the password retrieved from the header is equal to the expected
        return password != null && password.equals(decodedParts[1]);
    }
}
