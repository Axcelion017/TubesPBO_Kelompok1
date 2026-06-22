package tubespbo.presentation;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import tubespbo.presentation.command.Command;
import tubespbo.presentation.command.staff.BuatPeminjamanCommand;
import tubespbo.presentation.command.staff.CariPelangganCommand;
import tubespbo.presentation.command.staff.CekKendaraanTersediaCommand;
import tubespbo.presentation.command.staff.DaftarPelangganCommand;
import tubespbo.presentation.command.staff.ProsesPengembalianCommand;
import tubespbo.service.PelangganService;
import tubespbo.service.RentalService;

public class StaffMenuHandler {
    private final Scanner scanner;
    private final Map<Integer, Command> menuMap;

    public StaffMenuHandler(Scanner scanner, PelangganService pelangganService, RentalService rentalService) {
        this.scanner = scanner;
        this.menuMap = new HashMap<>();

        menuMap.put(1, new DaftarPelangganCommand(scanner, pelangganService));
        menuMap.put(2, new CariPelangganCommand(scanner, pelangganService));
        menuMap.put(3, new CekKendaraanTersediaCommand(scanner, rentalService));
        menuMap.put(4, new BuatPeminjamanCommand(scanner, rentalService));
        menuMap.put(5, new ProsesPengembalianCommand(scanner, rentalService));
    }

    public void tampilkanDashboardStaff(String username) {
        int pilihan = -1;
        do {
            try {
                System.out.println("\n================================================");
                System.out.println("                DASHBOARD - STAFF");
                System.out.println("================================================");
                System.out.println("Selamat Datang, " + username + "!");
                System.out.println("Silahkan pilih menu:");
                System.out.println("1. Daftar Pelanggan Baru");
                System.out.println("2. Cari Data Pelanggan");
                System.out.println("3. Cek Kendaraan Tersedia");
                System.out.println("4. Proses Peminjaman (Sewa)");
                System.out.println("5. Proses Pengembalian");
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