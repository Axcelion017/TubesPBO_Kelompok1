package tubespbo.presentation.command.staff;

import java.util.Optional;
import java.util.Scanner;
import tubespbo.domain.Pelanggan;
import tubespbo.presentation.command.Command;
import tubespbo.service.PelangganService;

public class CariPelangganCommand implements Command {
    private Scanner scanner;
    private PelangganService pelangganService;

    public CariPelangganCommand(Scanner scanner, PelangganService pelangganService) {
        this.scanner = scanner;
        this.pelangganService = pelangganService;
    }

    @Override
    public void execute() {
        System.out.println("\n========================================");
        System.out.println("MENU PENCARIAN PELANGGAN");
        System.out.println("========================================");

        System.out.print("Masukkan Nomor KTP : ");
        String ktp = scanner.nextLine();

        if (ktp.equals("0")) {
            return;
        }

        Optional<Pelanggan> pelanggan = pelangganService.cariByNomorKtp(ktp);

        if (pelanggan.isPresent()) {
            cetakDetailPelanggan(pelanggan.get());
        } else {
            System.out.println("[GAGAL] Data pelanggan tidak ditemukan.");
        }

        tekanEnter();
    }

    private void cetakDetailPelanggan(Pelanggan p) {
        System.out.println("\n[DATA DITEMUKAN]");
        System.out.println("Nama Lengkap    : " + p.getNamaLengkap());
        System.out.println("Nomor KTP       : " + p.getNomorKtp());
        System.out.println("No Telepon      : " + p.getNomorTelepon());
        System.out.println("Total Transaksi : " + p.getJumlahTransaksi() + " kali");
        System.out.println("Status Member   : " + p.getLevelMembership());
        System.out.println("Diskon Aktif    : " + (p.getPersentaseDiskon() * 100) + "%");
        System.out.println("========================================");
    }

    private void tekanEnter() {
        System.out.println("\nTekan ENTER untuk kembali...");
        scanner.nextLine();
    }
}
