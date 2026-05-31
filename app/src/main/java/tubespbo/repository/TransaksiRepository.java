package tubespbo.repository;

import tubespbo.domain.Kendaraan;
import tubespbo.domain.Pelanggan;
import tubespbo.domain.Transaksi;
import tubespbo.helper.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransaksiRepository {
    private final PelangganRepository pelangganRepository;
    private final KendaraanRepository kendaraanRepository;

    public TransaksiRepository() {
        this(new PelangganRepository(), new KendaraanRepository());
    }

    public TransaksiRepository(PelangganRepository pelangganRepository, KendaraanRepository kendaraanRepository) {
        this.pelangganRepository = pelangganRepository;
        this.kendaraanRepository = kendaraanRepository;
    }

    public List<Transaksi> ambilSemua() {
        List<Transaksi> daftarTransaksi = new ArrayList<>();
        String sql = "SELECT id_transaksi, nomor_ktp, plat_nomor, durasi_sewa_hari, "
                + "hari_keterlambatan, total_bayar, selesai FROM transaksi";

        try (
                // Membuka koneksi ke Oracle
                Connection connection = DatabaseConnection.getConnection();

                // Menyiapkan query SELECT semua transaksi
                PreparedStatement statement = connection.prepareStatement(sql);

                // Menjalankan query dan menerima hasilnya
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                // Mengubah satu baris transaksi menjadi object Transaksi jika relasinya valid
                mapResultSetToTransaksi(resultSet).ifPresent(daftarTransaksi::add);
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal membaca data transaksi dari database: " + e.getMessage());
        }

        return daftarTransaksi;
    }

    public void simpanSemua(List<Transaksi> daftarTransaksi) {
        String sql = "MERGE INTO transaksi t "
                + "USING (SELECT ? AS id_transaksi FROM dual) input "
                + "ON (t.id_transaksi = input.id_transaksi) "
                + "WHEN MATCHED THEN UPDATE SET "
                + "t.nomor_ktp = ?, "
                + "t.plat_nomor = ?, "
                + "t.durasi_sewa_hari = ?, "
                + "t.hari_keterlambatan = ?, "
                + "t.total_bayar = ?, "
                + "t.selesai = ? "
                + "WHEN NOT MATCHED THEN INSERT "
                + "(id_transaksi, nomor_ktp, plat_nomor, durasi_sewa_hari, hari_keterlambatan, total_bayar, selesai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                // Membuka satu koneksi untuk menyimpan seluruh daftar transaksi
                Connection connection = DatabaseConnection.getConnection();

                // MERGE dipakai agar data bisa INSERT kalau baru, atau UPDATE kalau sudah ada
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            for (Transaksi transaksi : daftarTransaksi) {
                isiParameterSimpan(statement, transaksi);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal menyimpan data transaksi ke database: " + e.getMessage());
        }
    }

    public Optional<Transaksi> cariByIdTransaksi(String idTransaksi) {
        String sql = "SELECT id_transaksi, nomor_ktp, plat_nomor, durasi_sewa_hari, "
                + "hari_keterlambatan, total_bayar, selesai FROM transaksi WHERE id_transaksi = ?";

        try (
                // Membuka koneksi ke Oracle
                Connection connection = DatabaseConnection.getConnection();

                // Menyiapkan query pencarian berdasarkan ID transaksi
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, normalisasiTeks(idTransaksi));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Jika ditemukan, baris database diubah menjadi object Transaksi
                    return mapResultSetToTransaksi(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal mencari transaksi dari database: " + e.getMessage());
        }

        return Optional.empty();
    }

    private Optional<Transaksi> mapResultSetToTransaksi(ResultSet resultSet) throws SQLException {
        // Transaksi menyimpan relasi ke Pelanggan dan Kendaraan, jadi keduanya dicari dulu
        Optional<Pelanggan> pelanggan = pelangganRepository.cariByNomorKtp(resultSet.getString("nomor_ktp"));
        Optional<Kendaraan> kendaraan = kendaraanRepository.cariByPlatNomor(resultSet.getString("plat_nomor"));

        if (pelanggan.isEmpty() || kendaraan.isEmpty()) {
            return Optional.empty();
        }

        Transaksi transaksi = new Transaksi(
                resultSet.getString("id_transaksi"),
                pelanggan.get(),
                kendaraan.get(),
                resultSet.getInt("durasi_sewa_hari")
        );

        // Data lanjutan dari tabel transaksi diset setelah object Transaksi dibuat
        transaksi.setHariKeterlambatan(resultSet.getInt("hari_keterlambatan"));
        transaksi.setTotalBayar(resultSet.getDouble("total_bayar"));
        transaksi.setSelesai(resultSet.getInt("selesai") == 1);
        return Optional.of(transaksi);
    }

    private void isiParameterSimpan(PreparedStatement statement, Transaksi transaksi) throws SQLException {
        String idTransaksi = normalisasiTeks(transaksi.getIdTransaksi());
        String nomorKtp = normalisasiTeks(transaksi.getPelanggan().getNomorKtp());
        String platNomor = normalisasiPlat(transaksi.getKendaraan().getPlatNomor());
        int selesai = transaksi.isSelesai() ? 1 : 0;

        // Parameter untuk bagian USING dan UPDATE
        statement.setString(1, idTransaksi);
        statement.setString(2, nomorKtp);
        statement.setString(3, platNomor);
        statement.setInt(4, transaksi.getDurasiSewaHari());
        statement.setInt(5, transaksi.getHariKeterlambatan());
        statement.setDouble(6, transaksi.getTotalBayar());
        statement.setInt(7, selesai);

        // Parameter untuk bagian INSERT
        statement.setString(8, idTransaksi);
        statement.setString(9, nomorKtp);
        statement.setString(10, platNomor);
        statement.setInt(11, transaksi.getDurasiSewaHari());
        statement.setInt(12, transaksi.getHariKeterlambatan());
        statement.setDouble(13, transaksi.getTotalBayar());
        statement.setInt(14, selesai);
    }

    private String normalisasiTeks(String value) {
        return value == null ? "" : value.trim();
    }

    private String normalisasiPlat(String platNomor) {
        return normalisasiTeks(platNomor).replaceAll("\\s+", " ").toUpperCase();
    }
}
