package tubespbo.presentation;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import tubespbo.presentation.command.Command;
import tubespbo.presentation.command.owner.LaporanPendapatanCommand;
import tubespbo.service.RentalService;

public class OwnerMenuHandler {
    private final Scanner scanner;
    private final Map<Integer, Command> menuMap;

    public OwnerMenuHandler(Scanner scanner, RentalService rentalService) {
        this.scanner = scanner;
        this.menuMap = new HashMap<>();

        menuMap.put(1, new LaporanPendapatanCommand(scanner, rentalService));
    }

    public void tampilkanDashboardOwner(String username) {
        int pilihan = -1;
        do {
            try {
                System.out.println("\n================================================");
                System.out.println("                DASHBOARD - OWNER");
                System.out.println("================================================");
                System.out.println("Selamat Datang, " + username + "!");
                System.out.println("Silahkan pilih menu:");
                System.out.println("1. Laporan Pendapatan (Transaksi Selesai)");
                System.out.println("0. Logout");
                System.out.print("Pilihan Anda > ");

                pilihan = Integer.parseInt(scanner.nextLine());

                if (pilihan == 0) {
                    System.out.println("Logout berhasil.");
                    break;
                }

                Command command = menuMap.get(pilihan);
                if (command != null) {
                    command.execute();
                } else {
                    System.out.println("Menu tidak tersedia.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[GAGAL] Input harus berupa angka.");
            }
        } while (pilihan != 0);
    }
}