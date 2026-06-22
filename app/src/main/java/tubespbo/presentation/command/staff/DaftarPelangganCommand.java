package tubespbo.presentation.command.staff;

import java.util.Scanner;
import tubespbo.domain.Pelanggan;
import tubespbo.presentation.command.Command;
import tubespbo.service.PelangganService;

public class DaftarPelangganCommand implements Command {
    private Scanner scanner;
    private PelangganService pelangganService;

    public DaftarPelangganCommand(Scanner scanner, PelangganService pelangganService) {
        this.scanner = scanner;
        this.pelangganService = pelangganService;
    }

    @Override
    public void execute() {
        System.out.println("\n========================================");
        System.out.println("MENU PENDAFTARAN PELANGGAN");
        System.out.println("========================================");
        System.out.println("(ketik 0 untuk kembali)");

        System.out.print("Masukkan Nomor KTP : ");
        String ktp = scanner.nextLine();

        if (ktp.equals("0")) {
            return;
        }

        System.out.print("Masukkan Nama Lengkap : ");
        String nama = scanner.nextLine();

        System.out.print("Masukkan No Telepon : ");
        String telepon = scanner.nextLine();

        try {
            Pelanggan pelanggan = pelangganService.daftarPelanggan(ktp, nama, telepon);
            System.out.println("\n[SUKSES] Pelanggan " + pelanggan.getNamaLengkap() + " (KTP: "
                    + pelanggan.getNomorKtp() + ") berhasil didaftarkan.");
        } catch (Exception e) {
            System.out.println("[GAGAL] " + e.getMessage());
        }

        tekanEnter();
    }

    private void tekanEnter() {
        System.out.println("\nTekan ENTER untuk kembali...");
        scanner.nextLine();
    }
}
