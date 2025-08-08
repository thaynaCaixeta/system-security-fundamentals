package ldap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/servlet")
public class ServletDemo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("This is a servlet \n");
        String name = null;
        if (request.getUserPrincipal() != null) {
            name = request.getUserPrincipal().getName();
        }
        response.getWriter().write("web username: " + name + "\n");
        response.getWriter().write("web user has role \"deptGRP1\":" + request.isUserInRole("deptGRP1") + "\n");
        response.getWriter().write("web user has role \"deptGRP2\":" + request.isUserInRole("deptGRP2") + "\n");
        response.getWriter().write("web user has role \"deptGRP3\":" + request.isUserInRole("deptGRP3") + "\n");
    }
}
