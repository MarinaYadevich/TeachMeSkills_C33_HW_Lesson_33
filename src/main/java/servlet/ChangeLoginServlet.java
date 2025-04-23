package servlet;

import dao.UserDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet that implements login change.
 */
@WebServlet("/change-login")
public class ChangeLoginServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("id");
        String loginParam = req.getParameter("login");

        if (idParam == null || loginParam == null) {
            resp.getWriter().write("Ошибка: передайте параметры id и login");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            userDao.updateLoginById(id, loginParam);
            resp.getWriter().write("Логин пользователя с ID " + id + " успешно изменён на " + loginParam);
        } catch (NumberFormatException e) {
            resp.getWriter().write("Ошибка: ID должен быть числом");
        } catch (RuntimeException e) {
            resp.getWriter().write("Ошибка: " + e.getMessage());
        }
    }
}
