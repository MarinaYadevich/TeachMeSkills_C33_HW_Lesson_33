package dao;

import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database Manager
 */
public class UserDao {
    private final String url="jdbc:postgresql://localhost:5432/postgres";
    private final String username="postgres";
    private final String password="postgres";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL driver not found!", e);
        }
    }


    public void createUser(User user){
        String sql = "INSERT INTO users(login,email) VALUES(?,?)";

        try(Connection conn = DriverManager.getConnection(url,username,password);
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getEmail());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setEmail(rs.getString("email"));
                return user;
            } else {
                return null; // пользователь не найден
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLoginById(int id, String newLogin) {
        String sql = "UPDATE users SET login = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newLogin);
            ps.setInt(2, id);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new RuntimeException("Пользователь с ID " + id + " не найден");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUserById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted == 0) {
                throw new RuntimeException("Пользователь с ID " + id + " не найден");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
