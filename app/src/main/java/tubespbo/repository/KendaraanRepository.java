package tubespbo.repository;

import tubespbo.domain.Kendaraan;
import tubespbo.domain.Mobil;
import tubespbo.domain.Motor;
import tubespbo.domain.StatusKendaraan;
import tubespbo.helper.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Repository ini menjadi penghubung antara object Kendaraan/Mobil/Motor dan tabel kendaraan.
// Service cukup memanggil method repository; detail SQL disimpan di class ini.
public class KendaraanRepository {
    public KendaraanRepository() {
    }

    public List<Kendaraan> ambilSemua() {
        List<Kendaraan> daftarKendaraan = new ArrayList<>();

        String sql = "SELECT plat_nomor, merek, harga_sewa_per_hari, status, jenis, jumlah_pintu, jenis_transmisi "
                + "FROM kendaraan";

        try (
                // Membuka koneksi ke Oracle
                Connection connection = DatabaseConnection.getConnection();

                // Menyiapkan query SELECT semua kendaraan
                PreparedStatement statement = connection.prepareStatement(sql);

                // Menjalankan query dan menerima hasilnya
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                // Mengubah satu baris tabel menjadi object Mobil/Motor
                Kendaraan kendaraan = mapResultSetToKendaraan(resultSet);
                daftarKendaraan.add(kendaraan);
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal membaca data kendaraan dari database: " + e.getMessage());
        }

        return daftarKendaraan;
    }

    public void simpanSemua(List<Kendaraan> daftarKendaraan) {
        // MERGE membuat satu query bisa menangani dua kondisi:
        // plat_nomor sudah ada -> UPDATE, plat_nomor belum ada -> INSERT.
        String sql = "MERGE INTO kendaraan k "
                + "USING (SELECT ? AS plat_nomor FROM dual) input "
                + "ON (k.plat_nomor = input.plat_nomor) "
                + "WHEN MATCHED THEN UPDATE SET "
                + "k.merek = ?, "
                + "k.harga_sewa_per_hari = ?, "
                + "k.status = ?, "
                + "k.jenis = ?, "
                + "k.jumlah_pintu = ?, "
                + "k.jenis_transmisi = ? "
                + "WHEN NOT MATCHED THEN INSERT "
                + "(plat_nomor, merek, harga_sewa_per_hari, status, jenis, jumlah_pintu, jenis_transmisi) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                // Membuka satu koneksi untuk menyimpan seluruh daftar kendaraan
                Connection connection = DatabaseConnection.getConnection();

                // MERGE dipakai agar data bisa INSERT kalau baru, atau UPDATE kalau sudah ada
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            for (Kendaraan kendaraan : daftarKendaraan) {
                isiParameterSimpan(statement, kendaraan);
                // Menambahkan parameter kendaraan saat ini ke antrian batch.
                statement.addBatch();
            }

            // Menjalankan seluruh query yang sudah dikumpulkan dalam satu batch.
            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal menyimpan data kendaraan ke database: " + e.getMessage());
        }
    }

    public Optional<Kendaraan> cariByPlatNomor(String platNomor) {
        String sql = "SELECT plat_nomor, merek, harga_sewa_per_hari, status, jenis, jumlah_pintu, jenis_transmisi "
                + "FROM kendaraan WHERE plat_nomor = ?";

        try (
                // Membuka koneksi ke Oracle
                Connection connection = DatabaseConnection.getConnection();

                // Menyiapkan query pencarian berdasarkan plat nomor
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, normalisasiPlat(platNomor));

            try (
                    // Menjalankan query setelah parameter plat nomor diisi
                    ResultSet resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    Kendaraan kendaraan = mapResultSetToKendaraan(resultSet);
                    return Optional.of(kendaraan);
                }
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal mencari kendaraan dari database: " + e.getMessage());
        }

        return Optional.empty();
    }

    public boolean hapusByPlatNomor(String platNomor) {
        String sql = "DELETE FROM kendaraan WHERE plat_nomor = ?";

        try (
                // Membuka koneksi ke Oracle
                Connection connection = DatabaseConnection.getConnection();

                // Menyiapkan query DELETE berdasarkan plat nomor
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, normalisasiPlat(platNomor));

            int jumlahTerhapus = statement.executeUpdate();
            return jumlahTerhapus > 0;
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal menghapus kendaraan dari database: " + e.getMessage());
            return false;
        }
    }

    private Kendaraan mapResultSetToKendaraan(ResultSet resultSet) throws SQLException {
        // Method ini menerjemahkan satu baris tabel kendaraan menjadi object turunan yang tepat.
        String platNomor = resultSet.getString("plat_nomor");
        String merek = resultSet.getString("merek");
        int hargaSewaPerHari = resultSet.getInt("harga_sewa_per_hari");
        StatusKendaraan status = StatusKendaraan.valueOf(resultSet.getString("status"));
        String jenis = resultSet.getString("jenis");

        Kendaraan kendaraan;

        if ("Mobil".equalsIgnoreCase(jenis)) {
            // Kolom jumlah_pintu hanya relevan untuk Mobil.
            int jumlahPintu = resultSet.getInt("jumlah_pintu");
            kendaraan = new Mobil(platNomor, merek, hargaSewaPerHari, jumlahPintu);
        } else if ("Motor".equalsIgnoreCase(jenis)) {
            // Kolom jenis_transmisi hanya relevan untuk Motor.
            String jenisTransmisi = resultSet.getString("jenis_transmisi");
            kendaraan = new Motor(platNomor, merek, hargaSewaPerHari, jenisTransmisi);
        } else {
            throw new SQLException("Jenis kendaraan tidak dikenali: " + jenis);
        }

        // Constructor kendaraan memberi status default, lalu status asli dari database dipasang ulang.
        kendaraan.setStatus(status);
        return kendaraan;
    }

    private void isiParameterSimpan(PreparedStatement statement, Kendaraan kendaraan) throws SQLException {
        // Method ini adalah kebalikan dari mapResultSetToKendaraan: object Java -> parameter SQL.
        String platNomor = normalisasiPlat(kendaraan.getPlatNomor());
        String jenis = kendaraan.getJenis();

        // Karena Mobil dan Motor memakai satu tabel, field yang tidak relevan disimpan sebagai NULL.
        Integer jumlahPintu = null;
        String jenisTransmisi = null;

        if (kendaraan instanceof Mobil) {
            jumlahPintu = ((Mobil) kendaraan).getJumlahPintu();
        } else if (kendaraan instanceof Motor) {
            jenisTransmisi = ((Motor) kendaraan).getJenisTransmisi();
        }

        // Parameter untuk bagian USING dan UPDATE
        statement.setString(1, platNomor);
        statement.setString(2, kendaraan.getMerek());
        statement.setInt(3, kendaraan.getHargaSewaPerHari());
        statement.setString(4, kendaraan.getStatus().name());
        statement.setString(5, jenis);
        setNullableInteger(statement, 6, jumlahPintu);
        statement.setString(7, jenisTransmisi);

        // Parameter untuk bagian INSERT
        statement.setString(8, platNomor);
        statement.setString(9, kendaraan.getMerek());
        statement.setInt(10, kendaraan.getHargaSewaPerHari());
        statement.setString(11, kendaraan.getStatus().name());
        statement.setString(12, jenis);
        setNullableInteger(statement, 13, jumlahPintu);
        statement.setString(14, jenisTransmisi);
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        // setInt tidak bisa menerima null, jadi angka opsional harus ditangani manual.
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }

    private String normalisasiPlat(String platNomor) {
        // Plat dinormalisasi agar " b   1234 abc " tersimpan/dicari sebagai "B 1234 ABC".
        if (platNomor == null) {
            return "";
        }

        return platNomor.trim().replaceAll("\\s+", " ").toUpperCase();
    }
}
