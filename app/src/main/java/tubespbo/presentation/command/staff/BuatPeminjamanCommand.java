package tubespbo.presentation.command.staff;

import java.util.Scanner;
import tubespbo.domain.StatusKendaraan;
import tubespbo.domain.Transaksi;
import tubespbo.presentation.command.Command;
import tubespbo.service.RentalService;

public class BuatPeminjamanCommand implements Command {
    private Scanner scanner;
    private RentalService rentalService;

    public BuatPeminjamanCommand(Scanner scanner, RentalService rentalService) {
        this.scanner = scanner;
        this.rentalService = rentalService;
    }

    @Override
    public void execute() {
        System.out.println("\n========================================");
        System.out.println("MENU PEMINJAMAN KENDARAAN");
        System.out.println("========================================");

        System.out.print("Masukkan Nomor KTP Pelanggan : ");
        String ktp = scanner.nextLine();

        if (ktp.equals("0")) {
            return;
        }

        System.out.print("Masukkan Plat Nomor Kendaraan : ");
        String plat = scanner.nextLine();

        System.out.print("Rencana Durasi Sewa (Hari) : ");
        try {
            int durasi = Integer.parseInt(scanner.nextLine());

            Transaksi trx = rentalService.buatPeminjamanBaru(ktp, plat, durasi);

            System.out.println("\n--- STRUK PEMINJAMAN SEMENTARA ---");
            System.out.println("ID Transaksi : " + trx.getIdTransaksi());
            System.out.println("Nama Pelanggan : " + trx.getPelanggan().getNamaLengkap());
            System.out.println("Kendaraan : " + trx.getKendaraan().getPlatNomor());
            System.out.println("Durasi Sewa : " + durasi + " Hari");
            System.out.println("Estimasi Biaya : Rp " + trx.hitungBiayaDasar());
            System.out.println("----------------------------------");
            System.out.println("[SUKSES] Transaksi berhasil dicatat. Status kendaraan berubah menjadi "
                    + StatusKendaraan.SEDANG_DISEWA);

        } catch (NumberFormatException e) {
            System.out.println("[GAGAL] Durasi sewa harus berupa angka.");
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
