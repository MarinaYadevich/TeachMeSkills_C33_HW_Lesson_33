package servlet;

import dao.UserDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet that implements user deletion by ID.
 */
@WebServlet("/delete")
public class DeleteUserServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("id");

        if (idParam == null) {
            resp.getWriter().write("Ошибка: параметр id не передан");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            userDao.deleteUserById(id);
            resp.getWriter().write("Пользователь с ID " + id + " успешно удалён");
        } catch (NumberFormatException e) {
            resp.getWriter().write("Ошибка: ID должен быть числом");
        } catch (RuntimeException e) {
            resp.getWriter().write("Ошибка: " + e.getMessage());
        }
    }
}

