package db;

import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@ServletSecurity(@HttpConstraint(rolesAllowed = "foo"))
@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("This is a servlet \n");
        String webName = null;
        if (req.getUserPrincipal() != null) {
            webName = req.getUserPrincipal().getName();
        }

        resp.getWriter().write("web username: " + webName + "\n");
    }
}
