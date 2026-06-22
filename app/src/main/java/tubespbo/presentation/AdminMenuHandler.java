package tubespbo.presentation;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import tubespbo.domain.User;
import tubespbo.presentation.command.Command;
import tubespbo.presentation.command.admin.HapusKendaraanCommand;
import tubespbo.presentation.command.admin.LihatKendaraanCommand;
import tubespbo.presentation.command.admin.TambahKendaraanCommand;
import tubespbo.service.InventarisService;

public class AdminMenuHandler {
    private Scanner scanner;
    private Map<Integer, Command> menuMap;

    public AdminMenuHandler(Scanner scanner, InventarisService inventarisService) {
        this.scanner = scanner;
        this.menuMap = new HashMap<>();

        // Mendaftarkan (Register) command ke dalam map sesuai urutan menu
        menuMap.put(1, new TambahKendaraanCommand(scanner, inventarisService));
        menuMap.put(2, new LihatKendaraanCommand(scanner, inventarisService));
        menuMap.put(3, new HapusKendaraanCommand(scanner, inventarisService));
    }

    public void tampilkanDashboardAdmin(String username) {
        int pilihan;

        do {
            System.out.println("\n========================================");
            System.out.println("DASHBOARD - ADMIN");
            System.out.println("========================================");
            System.out.println("Selamat Datang, " + username + "!");
            System.out.println("Silahkan pilih menu:");
            System.out.println("1. Tambah Kendaraan Baru");
            System.out.println("2. Lihat Semua Kendaraan");
            System.out.println("3. Hapus Kendaraan");
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");

            try {
                pilihan = Integer.parseInt(scanner.nextLine());

                if (pilihan == 0) {
                    System.out.println("[INFO] Logout berhasil.");
                    break;
                }

                // Mengeksekusi command berdasarkan input user
                Command command = menuMap.get(pilihan);
                if (command != null) {
                    command.execute();
                } else {
                    System.out.println("[ERROR] Pilihan tidak valid.");
                }

            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Input harus berupa angka.");
                pilihan = -1;
            }

        } while (pilihan != 0);
    }
}