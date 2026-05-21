package tubespbo.presentation;

import tubespbo.domain.User;
import tubespbo.exception.FiturBelumTersediaException;
import java.util.Scanner;

public class OwnerMenuHandler {
    private final Scanner scanner;

    public OwnerMenuHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void tampilkanDashboardOwner(User user) {
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
                case "1": tampilkanFitur("Lihat Laporan Pendapatan & Riwayat"); break;
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

    private void tampilkanFitur(String namaFitur) {
        try {
            FiturBelumTersediaException.cekStatusFitur(namaFitur);
            // Nanti jika Epic 5 sudah beres, hubungkan servicenya di sini
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