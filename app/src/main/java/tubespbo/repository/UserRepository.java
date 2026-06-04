package tubespbo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import tubespbo.domain.Role;
import tubespbo.domain.User;
import tubespbo.helper.DatabaseConnection;

// Repository ini khusus membaca akun login dari tabel users.
// Validasi berhasil/gagal login tetap menjadi tanggung jawab AuthService.
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

    public Optional<User> cariByUsername(String username) {
        // Query memakai parameter (?) agar input username tidak digabung langsung ke SQL.
        String sql = "SELECT username, password, role FROM users WHERE username = ?";

        try (
                // Membuka koneksi ke Oracle
                Connection connection = DatabaseConnection.getConnection();

                // Menyiapkan query dengan parameter username
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            // Username dirapikan dulu agar input seperti " admin " tetap dicari sebagai "admin".
            statement.setString(1, normalisasiTeks(username));

            try (
                    // Menjalankan query setelah parameter diisi
                    ResultSet resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    String usernameDb = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Role role = Role.valueOf(resultSet.getString("role"));

                    // Jika user ditemukan, bungkus object User ke Optional agar AuthService tahu login bisa dilanjutkan
                    User user = new User(usernameDb, password, role);
                    return Optional.<User>of(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal mencari user dari database: " + e.getMessage());
        }

        // Jika username tidak ditemukan, AuthService akan menangani hasil kosong sebagai login gagal
        return Optional.empty();
    }

    private String normalisasiTeks(String value) {
        return value == null ? "" : value.trim();
    }
}
