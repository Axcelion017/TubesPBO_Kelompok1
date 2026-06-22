package tubespbo.presentation.command.admin;

import java.util.Scanner;
import tubespbo.presentation.command.Command;
import tubespbo.service.InventarisService;

public class LihatKendaraanCommand implements Command {
    private Scanner scanner;
    private InventarisService inventarisService;

    public LihatKendaraanCommand(Scanner scanner, InventarisService inventarisService) {
        this.scanner = scanner;
        this.inventarisService = inventarisService;
    }

    @Override
    public void execute() {
        System.out.println("\n=================================================================================");
        System.out.println("DAFTAR SELURUH KENDARAAN");
        System.out.println("=================================================================================");

        inventarisService.cetakDaftarKendaraan();

        tekanEnter();
    }

    private void tekanEnter() {
        System.out.println("\nTekan ENTER untuk kembali ke menu utama...");
        scanner.nextLine();
    }
}
