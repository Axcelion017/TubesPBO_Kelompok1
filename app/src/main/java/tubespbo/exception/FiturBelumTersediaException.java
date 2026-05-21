package tubespbo.exception;

import java.util.HashSet;
import java.util.Set;

public class FiturBelumTersediaException extends Exception {
    
    // Set penampung string fitur yang berstatus maintenance / dikunci
    private static final Set<String> daftarMaintenance = new HashSet<>();

    static {
        // Daftarkan semua menu fungsional di sini agar masuk status maintenance/belum tersedia
        /*daftarMaintenance.add("Tambah Kendaraan Baru");
        daftarMaintenance.add("Lihat Semua Kendaraan");
        daftarMaintenance.add("Hapus Kendaraan");
        daftarMaintenance.add("Daftar Pelanggan Baru");
        daftarMaintenance.add("Cari Data Pelanggan");
        daftarMaintenance.add("Cek Kendaraan Tersedia");
        daftarMaintenance.add("Proses Peminjaman");
        daftarMaintenance.add("Proses Pengembalian");
        daftarMaintenance.add("Lihat Laporan Pendapatan & Riwayat");*/
    }

    public FiturBelumTersediaException(String namaFitur) {
        super("Akses Ditolak: Fitur '" + namaFitur + "' saat ini sedang dalam pemeliharaan (MAINTENANCE) atau belum tersedia.");
    }

    public static void cekStatusFitur(String namaFitur) throws FiturBelumTersediaException {
        if (daftarMaintenance.contains(namaFitur)) {
            throw new FiturBelumTersediaException(namaFitur);
        }
    }
}