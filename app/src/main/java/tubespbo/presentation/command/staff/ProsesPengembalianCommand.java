package tubespbo.presentation.command.staff;

import java.util.Optional;
import java.util.Scanner;
import tubespbo.domain.Transaksi;
import tubespbo.presentation.command.Command;
import tubespbo.service.RentalService;

public class ProsesPengembalianCommand implements Command {
    private Scanner scanner;
    private RentalService rentalService;

    public ProsesPengembalianCommand(Scanner scanner, RentalService rentalService) {
        this.scanner = scanner;
        this.rentalService = rentalService;
    }

    @Override
    public void execute() {
        System.out.println("\n========================================");
        System.out.println("MENU PENGEMBALIAN KENDARAAN");
        System.out.println("========================================");

        System.out.print("Masukkan ID Transaksi : ");
        String idTransaksi = scanner.nextLine();

        if (idTransaksi.equals("0")) {
            return;
        }

        System.out.print("Durasi Keterlambatan (Hari, isi 0 jika tepat waktu): ");
        try {
            int hariTerlambat = Integer.parseInt(scanner.nextLine());

            System.out.println("\nMenghitung tagihan ...\n");

            Optional<Transaksi> optTrx = rentalService.ambilSemuaTransaksi().stream()
                    .filter(t -> t.getIdTransaksi().equalsIgnoreCase(idTransaksi))
                    .findFirst();

            if (optTrx.isEmpty()) {
                System.out.println("[GAGAL] ID Transaksi tidak ditemukan.");
                tekanEnter();
                return;
            }

            Transaksi t = optTrx.get();
            if (t.isSelesai()) {
                System.out.println("[GAGAL] Transaksi sudah diselesaikan sebelumnya.");
                tekanEnter();
                return;
            }

            double biayaDasar = t.hitungBiayaDasar();
            double diskon = t.getPelanggan().hitungDiskon(biayaDasar);

            double total = rentalService.prosesPengembalian(idTransaksi, hariTerlambat);
            double denda = total - (biayaDasar - diskon);

            System.out.println("--- STRUK TAGIHAN AKHIR ---");
            System.out.println("ID Transaksi : " + t.getIdTransaksi());
            System.out.println("Pelanggan : " + t.getPelanggan().getNamaLengkap() + " ("
                    + t.getPelanggan().getLevelMembership() + ")");
            System.out.println(
                    "Kendaraan : " + t.getKendaraan().getJenis() + " (" + t.getKendaraan().getPlatNomor() + ")");
            System.out.println("Biaya Dasar : Rp " + biayaDasar + " (" + t.getDurasiSewaHari() + " Hari)");
            if (diskon > 0) {
                System.out.println("Diskon Member: -Rp " + diskon);
            }
            System.out.println("Denda Telat : Rp " + denda);
            System.out.println("---------------------------------- +");
            System.out.println("TOTAL BAYAR : Rp " + total);
            System.out.println("[SUKSES] Pengembalian berhasil. Status kendaraan berubah menjadi TERSEDIA");

        } catch (NumberFormatException e) {
            System.out.println("[GAGAL] Durasi keterlambatan harus berupa angka.");
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
