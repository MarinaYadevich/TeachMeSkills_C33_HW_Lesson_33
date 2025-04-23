package servlet;

import dao.UserDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;

/**
 * Servlet that implements receiving information about a user by ID.
 */
@WebServlet("/get")
public class GetUserServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String idParam = req.getParameter("id");

        if (idParam == null) {
            resp.getWriter().write("ID не передан");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            User user = userDao.getUserById(id);

            if (user != null) {
                resp.getWriter().write("Пользователь найден:\n");
                resp.getWriter().write("ID: " + user.getId() + "\n");
                resp.getWriter().write("Login: " + user.getLogin() + "\n");
                resp.getWriter().write("Email: " + user.getEmail());
            } else {
                resp.getWriter().write("Пользователь с ID " + id + " не найден");
            }
        } catch (NumberFormatException e) {
            resp.getWriter().write("ID должен быть числом");
        }
    }
}
