package salt;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet("/servlet")
public class ServletDemo extends HttpServlet {

    @Inject
    private HashWithSaltDemo hashWithSaltDemo;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringToHash = request.getParameter("stringToHash");
        if (stringToHash == null) {
            request.setAttribute("error", "Missing parameter 'stringToHash'.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        try {
            String hashedString = hashWithSaltDemo.getHashedValue(stringToHash);

            // Store in request attributes
            request.setAttribute("originalString", stringToHash);
            request.setAttribute("hashedString", hashedString);

            // Forward to JSP
            request.getRequestDispatcher("/index.jsp").forward(request, response);

            System.out.println("Original string: " + stringToHash);
            System.out.println("Hashed string: " + hashedString);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
