package tubespbo.repository;

import tubespbo.domain.Role;
import tubespbo.domain.User;
import tubespbo.helper.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public UserRepository() {
    }

    public List<User> ambilSemua() {
        List<User> daftarUser = new ArrayList<>();

        String sql = "SELECT username, password, role FROM users";

        try (
                // Membuka koneksi ke Oracle
                Connection connection = DatabaseConnection.getConnection();

                // Menyiapkan query SELECT
                PreparedStatement statement = connection.prepareStatement(sql);

                // Menjalankan query dan menerima hasilnya
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Role role = Role.valueOf(resultSet.getString("role"));

                // Mengubah satu baris tabel users menjadi object User
                User user = new User(username, password, role);
                daftarUser.add(user);
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal membaca data user dari database: " + e.getMessage());
        }

        return daftarUser;
    }

    public User cariByUsername(String username) {
        String sql = "SELECT username, password, role FROM users WHERE username = ?";

        try (
                // Membuka koneksi ke Oracle
                Connection connection = DatabaseConnection.getConnection();

                // Menyiapkan query dengan parameter username
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, username);

            try (
                    // Menjalankan query setelah parameter diisi
                    ResultSet resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    String usernameDb = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Role role = Role.valueOf(resultSet.getString("role"));

                    return new User(usernameDb, password, role);
                }
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal mencari user dari database: " + e.getMessage());
        }

        return null;
    }
}