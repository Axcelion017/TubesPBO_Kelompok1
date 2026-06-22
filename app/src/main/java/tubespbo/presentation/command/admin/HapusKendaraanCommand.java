package tubespbo.presentation.command.admin;

import java.util.Scanner;
import tubespbo.exception.KendaraanSedangDisewaException;
import tubespbo.presentation.command.Command;
import tubespbo.service.InventarisService;

public class HapusKendaraanCommand implements Command {
    private Scanner scanner;
    private InventarisService inventarisService;

    public HapusKendaraanCommand(Scanner scanner, InventarisService inventarisService) {
        this.scanner = scanner;
        this.inventarisService = inventarisService;
    }

    @Override
    public void execute() {
        System.out.println("\n========================================");
        System.out.println("MENU HAPUS KENDARAAN");
        System.out.println("========================================");
        System.out.println("(ketik 0 untuk kembali)");

        System.out.print("Masukkan Plat Nomor yang ingin dihapus : ");
        String platNomor = scanner.nextLine();

        if (platNomor.equals("0")) {
            return;
        }

        try {
            boolean berhasil = inventarisService.hapusKendaraan(platNomor);
            if (berhasil) {
                System.out.println("[SUKSES] Kendaraan " + platNomor + " berhasil dihapus dari sistem.");
            } else {
                System.out.println("[GAGAL] Kendaraan " + platNomor + " tidak ditemukan.");
            }
        } catch (KendaraanSedangDisewaException e) {
            System.out.println("[GAGAL] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[GAGAL] Terjadi kesalahan: " + e.getMessage());
        }

        tekanEnter();
    }

    private void tekanEnter() {
        System.out.println("\nTekan ENTER untuk kembali ke menu utama...");
        scanner.nextLine();
    }
}
