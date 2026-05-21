package tubespbo.presentation;

import tubespbo.domain.User;
import tubespbo.exception.FiturBelumTersediaException;
import java.util.Scanner;

public class StaffMenuHandler {
    private final Scanner scanner;

    public StaffMenuHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void tampilkanDashboardStaff(User user) {
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
                case "1": tampilkanFitur("Daftar Pelanggan Baru"); break;
                case "2": tampilkanFitur("Cari Data Pelanggan"); break;
                case "3": tampilkanFitur("Cek Kendaraan Tersedia"); break;
                case "4": tampilkanFitur("Proses Peminjaman"); break;
                case "5": tampilkanFitur("Proses Pengembalian"); break;
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

    private void tampilkanFitur(String namaFitur) {
        try {
            FiturBelumTersediaException.cekStatusFitur(namaFitur);
            // Nanti jika Epic 3 & 4 sudah beres, hubungkan servicenya di sini
            System.out.println("\n[GATEWAY] Akses Diberikan untuk: " + namaFitur);
            tekanEnter();
        } catch (FiturBelumTersediaException e) {
            System.out.println("\n[⚠️ SYSTEM ALERT] " + e.getMessage());
            tekanEnter();
        }
    }

    private void tekanEnter() {
        System.out.print("Tekan ENTER untuk kembali...");
        scanner.nextLine();
    }
}