package tubespbo.presentation;

import tubespbo.domain.User;
import tubespbo.domain.Mobil;
import tubespbo.domain.Motor;
import tubespbo.exception.FiturBelumTersediaException;
import tubespbo.repository.KendaraanRepository;
import tubespbo.service.InventarisService;
import java.util.Scanner;

public class AdminMenuHandler {
    private final Scanner scanner;
    private final InventarisService inventarisService;

    public AdminMenuHandler(Scanner scanner) {
        this.scanner = scanner;
        this.inventarisService = new InventarisService(new KendaraanRepository());
    }

    public void tampilkanDashboardAdmin(User user) {
        boolean berjalan = true;
        while (berjalan) {
            System.out.println("\n========================================");
            System.out.println("          DASHBOARD - ADMIN");
            System.out.println("========================================");
            System.out.println("Selamat Datang, " + user.getUsername() + "!");
            System.out.println("Silahkan pilih menu:");
            System.out.println("1. Tambah Kendaraan Baru");
            System.out.println("2. Lihat Semua Kendaraan");
            System.out.println("3. Hapus Kendaraan");
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");

            String pilihan = scanner.nextLine();
            switch (pilihan) {
                case "1": tampilkanFitur("Tambah Kendaraan Baru"); break;
                case "2": tampilkanFitur("Lihat Semua Kendaraan"); break;
                case "3": tampilkanFitur("Hapus Kendaraan"); break;
                case "0":
                    berjalan = false;
                    System.out.println("[LOGOUT] Anda keluar dari Dashboard Admin.");
                    break;
                default:
                    System.out.println("[ERROR] Pilihan tidak valid.");
                    tekanEnter();
            }
        }
    }

    private void tampilkanFitur(String namaFitur) {
        try {
            FiturBelumTersediaException.cekStatusFitur(namaFitur);
            switch (namaFitur) {
                case "Tambah Kendaraan Baru": eksekusiMenuTambah(); break;
                case "Lihat Semua Kendaraan":
                    // Fungsi cetak milik temanmu sudah menyediakan struktur tabel internal
                    inventarisService.cetakDaftarKendaraan(); 
                    tekanEnter();
                    break;
                case "Hapus Kendaraan": eksekusiMenuHapus(); break;
            }
        } catch (FiturBelumTersediaException e) {
            System.out.println("\n[⚠️ SYSTEM ALERT] " + e.getMessage());
            tekanEnter();
        }
    }

    private void eksekusiMenuTambah() {
        System.out.println("\n========================================");
        System.out.println("       MENU TAMBAH KENDARAAN BARU       ");
        System.out.println("========================================");
        System.out.println("Pilih Jenis Kendaraan: \n1. Mobil \n2. Motor \n0. Batal");
        System.out.print("Pilihan Anda > ");
        String jenis = scanner.nextLine();
        if (jenis.equals("0")) return;

        System.out.print("Masukkan Plat Nomor        : "); String plat = scanner.nextLine();
        System.out.print("Masukkan Merek Kendaraan   : "); String merek = scanner.nextLine();
        System.out.print("Masukkan Harga Sewa / Hari : ");
        
        // DISESUAIKAN: Menggunakan tipe int mengikuti InventarisService temanmu
        int harga; 
        try {
            harga = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Input harga harus berupa angka bulat!");
            tekanEnter(); return;
        }

        try {
            if (jenis.equals("1")) {
                System.out.print("Masukkan Jumlah Pintu      : ");
                int pintu = Integer.parseInt(scanner.nextLine());
                
                // Memanggil method tambahMobil asli temanmu
                Mobil mobilBaru = inventarisService.tambahMobil(plat, harga, merek, pintu);
                System.out.println("\n[SUKSES] Mobil " + mobilBaru.getMerk() + " (" + mobilBaru.getPlatNomor() + ") berhasil disimpan!");
            } else if (jenis.equals("2")) {
                System.out.print("Masukkan Jenis Transmisi   : ");
                String transmisi = scanner.nextLine();
                
                // Memanggil method tambahMotor asli temanmu
                Motor motorBaru = inventarisService.tambahMotor(plat, harga, merek, transmisi);
                System.out.println("\n[SUKSES] Motor " + motorBaru.getMerk() + " (" + motorBaru.getPlatNomor() + ") berhasil disimpan!");
            } else {
                System.out.println("[ERROR] Pilihan jenis kendaraan tidak valid.");
            }
        } catch (Exception e) {
            // Menangkap IllegalArgumentException atau PlatNomorDuplikatException
            System.out.println("\n[⚠️ GAGAL] " + e.getMessage());
        }
        tekanEnter();
    }

    private void eksekusiMenuHapus() {
        System.out.println("\n========================================");
        System.out.println("          MENU HAPUS KENDARAAN          ");
        System.out.println("========================================");
        System.out.print("Masukkan Plat Nomor yang ingin dihapus (ketik 0 untuk batal): ");
        String plat = scanner.nextLine();
        if (plat.equals("0")) return;
        
        try {
            // DISESUAIKAN: Method hapusKendaraan temanmu mengembalikan nilai boolean
            boolean berhasilHapus = inventarisService.hapusKendaraan(plat);
            if (berhasilHapus) {
                System.out.println("\n[SUKSES] Kendaraan dengan plat " + plat.toUpperCase() + " berhasil dihapus.");
            } else {
                System.out.println("\n[⚠️ GAGAL] Data tidak ditemukan. Pastikan plat nomor benar.");
            }
        } catch (Exception e) {
            // Menangkap KendaraanSedangDisewaException
            System.out.println("\n[⚠️ GAGAL] " + e.getMessage());
        }
        tekanEnter();
    }

    private void tekanEnter() {
        System.out.print("Tekan ENTER untuk melanjutkan...");
        scanner.nextLine();
    }
}