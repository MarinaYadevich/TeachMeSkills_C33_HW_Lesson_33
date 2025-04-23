package servlet;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;

/**
 * Servlet that implements user creation.
 */
@WebServlet("/create")
public class CreateUserServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String email = req.getParameter("email");

        User user = new User();
        user.setLogin(login);
        user.setEmail(email);

        userDao.createUser(user);

        resp.setContentType("text/html");
        resp.getWriter().write("Пользователь создан!");
    }
}
