package tubespbo.presentation;

import tubespbo.domain.Role;
import tubespbo.domain.User;

import java.util.Scanner;

public class MenuHandler {
    private final Scanner scanner;

    public MenuHandler(Scanner scanner) {
        this.scanner = scanner;
    }

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
                    tampilkanFiturBelumTersedia("Tambah Kendaraan Baru");
                    break;
                case "2":
                    tampilkanFiturBelumTersedia("Lihat Semua Kendaraan");
                    break;
                case "3":
                    tampilkanFiturBelumTersedia("Hapus Kendaraan");
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
                    tampilkanFiturBelumTersedia("Daftar Pelanggan Baru");
                    break;
                case "2":
                    tampilkanFiturBelumTersedia("Cari Data Pelanggan");
                    break;
                case "3":
                    tampilkanFiturBelumTersedia("Cek Kendaraan Tersedia");
                    break;
                case "4":
                    tampilkanFiturBelumTersedia("Proses Peminjaman");
                    break;
                case "5":
                    tampilkanFiturBelumTersedia("Proses Pengembalian");
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
                    tampilkanFiturBelumTersedia("Lihat Laporan Pendapatan & Riwayat");
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

    private void tampilkanFiturBelumTersedia(String namaFitur) {
        System.out.println("\n[FITUR BELUM TERSEDIA] " + namaFitur);
        System.out.println("Fitur ini akan dihubungkan setelah modul terkait selesai.");
        tekanEnter();
    }

    private void tekanEnter() {
        System.out.print("Tekan ENTER untuk kembali...");
        scanner.nextLine();
    }
}