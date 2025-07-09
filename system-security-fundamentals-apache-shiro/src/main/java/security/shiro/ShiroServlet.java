package security.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/protected/shiro")
public class ShiroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Subject currentUser = SecurityUtils.getSubject();

        Object principal = currentUser.getPrincipal();
        if (principal != null) {
            resp.getWriter().write("User: " + principal);
        }
    }
}
