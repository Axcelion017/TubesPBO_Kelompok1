package tubespbo.presentation;

import tubespbo.domain.User;
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

    public void tampilkanMenu(User user) {
        boolean berjalan = true;
        while (berjalan) {
            System.out.println("\n========================================");
            System.out.println("          DASHBOARD - ADMIN");
            System.out.println("========================================");
            System.out.println("1. Tambah Kendaraan Baru");
            System.out.println("2. Lihat Semua Kendaraan");
            System.out.println("3. Hapus Kendaraan");
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");

            String pilihan = scanner.nextLine();
            // Panggil try-catch Gateway dan Logika Inventaris milik temanmu di sini
            if (pilihan.equals("0")) berjalan = false;
        }
    }
}