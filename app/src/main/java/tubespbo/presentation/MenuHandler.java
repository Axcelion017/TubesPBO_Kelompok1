package tubespbo.presentation;

import tubespbo.domain.Role;
import tubespbo.domain.User;
import tubespbo.exception.FiturBelumTersediaException; // Import exception buatanmu

import java.util.Scanner;

public class MenuHandler {
    private final Scanner scanner;

    // Constructor asli bawaan temanmu dipertahankan agar tidak break Main.java
    public MenuHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    // Method utama bawaan temanmu dipertahankan
    public void tampilkanDashboard(User user) {
        if (user.getRole() == Role.ADMIN) {
            tampilkanDashboardAdmin(user);
        } else if (user.getRole() == Role.STAFF) {
            tampilkanDashboardStaff(user);
        } else if (user.getRole() == Role.OWNER) {
            tampilkanDashboardOwner(user);
        } else {
            System.out.println("[ERROR] Role tidak dikenali.");
        }
    }

    /**
     * FUNGSI GATEWAY PUSAT (Sistem Validasi Gateway)
     * Memeriksa ketersediaan modul menggunakan try-catch sebelum dieksekusi
     */
    private void tampilkanFitur(String namaFitur) {
        try {
            // Validasi terpusat ketuk gerbang Exception
            FiturBelumTersediaException.cekStatusFitur(namaFitur);
            
            // JIKA LOLOS (Berarti fitur sudah siap / tidak masuk daftar maintenance):
            System.out.println("\n[GATEWAY] Akses Diberikan. Membuka " + namaFitur + "...");
            System.out.println("-> Berhasil mengeksekusi modul '" + namaFitur + "'.");
            tekanEnter();

            // NANTI KEDEPANNYA: Jika fitur sudah dikoding asli, hubungkan di sini:
            /*
            if (namaFitur.equals("Tambah Kendaraan Baru")) {
                inventarisService.tambahKendaraan();
            }
            */

        } catch (FiturBelumTersediaException e) {
            // JIKA TERKENA EXCEPTION (Fitur belum siap atau sedang maintenance)
            System.out.println("\n[⚠️ SYSTEM ALERT] " + e.getMessage());
            tekanEnter();
        }
    }

    private void tampilkanDashboardAdmin(User user) {
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
                case "1":
                    tampilkanFitur("Tambah Kendaraan Baru"); // Menggunakan Gateway Pusat
                    break;
                case "2":
                    tampilkanFitur("Lihat Semua Kendaraan"); // Menggunakan Gateway Pusat
                    break;
                case "3":
                    tampilkanFitur("Hapus Kendaraan");       // Menggunakan Gateway Pusat
                    break;
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

    private void tampilkanDashboardStaff(User user) {
        boolean berjalan = true;

        while (berjalan) {
            System.out.println("\n========================================");
            System.out.println("          DASHBOARD - STAFF");
            System.out.println("========================================");
            System.out.println("Selamat Datang, " + user.getUsername() + "!");
            System.out.println("Silahkan pilih menu:");
            System.out.println("1. Daftar Pelanggan Baru");
            System.out.println("2. Cari Data Pelanggan");
            System.out.println("3. Cek Kendaraan Tersedia");
            System.out.println("4. Proses Peminjaman (Sewa)");
            System.out.println("5. Proses Pengembalian");
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");

            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    tampilkanFitur("Daftar Pelanggan Baru"); // Menggunakan Gateway Pusat
                    break;
                case "2":
                    tampilkanFitur("Cari Data Pelanggan");   // Menggunakan Gateway Pusat
                    break;
                case "3":
                    tampilkanFitur("Cek Kendaraan Tersedia"); // Menggunakan Gateway Pusat
                    break;
                case "4":
                    tampilkanFitur("Proses Peminjaman");     // Menggunakan Gateway Pusat
                    break;
                case "5":
                    tampilkanFitur("Proses Pengembalian");   // Menggunakan Gateway Pusat
                    break;
                case "0":
                    berjalan = false;
                    System.out.println("[LOGOUT] Anda keluar dari Dashboard Staff.");
                    break;
                default:
                    System.out.println("[ERROR] Pilihan tidak valid.");
                    tekanEnter();
            }
        }
    }

    private void tampilkanDashboardOwner(User user) {
        boolean berjalan = true;

        while (berjalan) {
            System.out.println("\n========================================");
            System.out.println("          DASHBOARD - OWNER");
            System.out.println("========================================");
            System.out.println("Selamat Datang, " + user.getUsername() + "!");
            System.out.println("Silahkan pilih menu:");
            System.out.println("1. Lihat Laporan Pendapatan & Riwayat");
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");

            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    tampilkanFitur("Lihat Laporan Pendapatan & Riwayat"); // Menggunakan Gateway Pusat
                    break;
                case "0":
                    berjalan = false;
                    System.out.println("[LOGOUT] Anda keluar dari Dashboard Owner.");
                    break;
                default:
                    System.out.println("[ERROR] Pilihan tidak valid.");
                    tekanEnter();
            }
        }
    }

    // Method pembantu asli temanmu dipertahankan/disesuaikan teksnya
    private void tekanEnter() {
        System.out.print("Tekan ENTER untuk kembali...");
        scanner.nextLine();
    }
}