package tubespbo.repository;

import tubespbo.domain.Pelanggan;
import tubespbo.helper.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PelangganRepository {
    public PelangganRepository() {
    }

    public List<Pelanggan> ambilSemua() {
        List<Pelanggan> daftarPelanggan = new ArrayList<>();
        String sql = "SELECT nomor_ktp, nama_lengkap, nomor_telepon, jumlah_transaksi, level_membership "
                + "FROM pelanggan";

        try (
                // Membuka koneksi ke Oracle
                Connection connection = DatabaseConnection.getConnection();

                // Menyiapkan query SELECT semua pelanggan
                PreparedStatement statement = connection.prepareStatement(sql);

                // Menjalankan query dan menerima hasilnya
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                // Mengubah satu baris tabel pelanggan menjadi object Pelanggan
                daftarPelanggan.add(mapResultSetToPelanggan(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal membaca data pelanggan dari database: " + e.getMessage());
        }

        return daftarPelanggan;
    }

    public void simpanSemua(List<Pelanggan> daftarPelanggan) {
        String sql = "MERGE INTO pelanggan p "
                + "USING (SELECT ? AS nomor_ktp FROM dual) input "
                + "ON (p.nomor_ktp = input.nomor_ktp) "
                + "WHEN MATCHED THEN UPDATE SET "
                + "p.nama_lengkap = ?, "
                + "p.nomor_telepon = ?, "
                + "p.jumlah_transaksi = ?, "
                + "p.level_membership = ? "
                + "WHEN NOT MATCHED THEN INSERT "
                + "(nomor_ktp, nama_lengkap, nomor_telepon, jumlah_transaksi, level_membership) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                // Membuka satu koneksi untuk menyimpan seluruh daftar pelanggan
                Connection connection = DatabaseConnection.getConnection();

                // MERGE dipakai agar data bisa INSERT kalau baru, atau UPDATE kalau sudah ada
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            for (Pelanggan pelanggan : daftarPelanggan) {
                isiParameterSimpan(statement, pelanggan);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal menyimpan data pelanggan ke database: " + e.getMessage());
        }
    }

    public Optional<Pelanggan> cariByNomorKtp(String nomorKtp) {
        String sql = "SELECT nomor_ktp, nama_lengkap, nomor_telepon, jumlah_transaksi, level_membership "
                + "FROM pelanggan WHERE nomor_ktp = ?";

        try (
                // Membuka koneksi ke Oracle
                Connection connection = DatabaseConnection.getConnection();

                // Menyiapkan query pencarian berdasarkan nomor KTP
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, normalisasiTeks(nomorKtp));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Jika ditemukan, baris database diubah menjadi object Pelanggan
                    return Optional.of(mapResultSetToPelanggan(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal mencari pelanggan dari database: " + e.getMessage());
        }

        return Optional.empty();
    }

    private Pelanggan mapResultSetToPelanggan(ResultSet resultSet) throws SQLException {
        Pelanggan pelanggan = new Pelanggan(
                resultSet.getString("nomor_ktp"),
                resultSet.getString("nama_lengkap"),
                resultSet.getString("nomor_telepon"),
                resultSet.getInt("jumlah_transaksi")
        );

        return pelanggan;
    }

    private void isiParameterSimpan(PreparedStatement statement, Pelanggan pelanggan) throws SQLException {
        String nomorKtp = normalisasiTeks(pelanggan.getNomorKtp());

        // Parameter untuk bagian USING dan UPDATE
        statement.setString(1, nomorKtp);
        statement.setString(2, pelanggan.getNamaLengkap());
        statement.setString(3, pelanggan.getNomorTelepon());
        statement.setInt(4, pelanggan.getJumlahTransaksi());
        statement.setString(5, pelanggan.getLevelMembership().name());

        // Parameter untuk bagian INSERT
        statement.setString(6, nomorKtp);
        statement.setString(7, pelanggan.getNamaLengkap());
        statement.setString(8, pelanggan.getNomorTelepon());
        statement.setInt(9, pelanggan.getJumlahTransaksi());
        statement.setString(10, pelanggan.getLevelMembership().name());
    }

    private String normalisasiTeks(String value) {
        return value == null ? "" : value.trim();
    }
}
