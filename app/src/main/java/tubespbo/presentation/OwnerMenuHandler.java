package tubespbo.presentation;

import java.util.List;
import java.util.Scanner;

import tubespbo.domain.Transaksi;
import tubespbo.service.RentalService;

public class OwnerMenuHandler {
    private final Scanner scanner;
    private final RentalService rentalService;

    public OwnerMenuHandler(Scanner scanner, RentalService rentalService) {
        this.scanner = scanner;
        this.rentalService = rentalService;
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

                switch (pilihan) {
                    case 1:
                        menuLaporanPendapatan();
                        break;
                    case 0:
                        System.out.println("Logout berhasil.");
                        break;
                    default:
                        System.out.println("Menu tidak tersedia.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[GAGAL] Input harus berupa angka.");
            }
        } while (pilihan != 0);
    }

    private void menuLaporanPendapatan() {
        System.out.println("\n===========================================================================");
        System.out.println("                     LAPORAN PENDAPATAN & RIWAYAT");
        System.out.println("===========================================================================");
        System.out.println("| ID Transaksi | Pelanggan          | Kendaraan    | Total Tagihan |");
        System.out.println("---------------------------------------------------------------------------");

        List<Transaksi> daftarTransaksi = rentalService.ambilSemuaTransaksi();
        double totalPendapatan = 0;
        int jumlahSelesai = 0;

        for (Transaksi t : daftarTransaksi) {
            if (t.isSelesai()) {
                System.out.printf("| %-12s | %-18s | %-12s | Rp %-11.0f |\n",
                        t.getIdTransaksi(),
                        t.getPelanggan().getNamaLengkap(),
                        t.getKendaraan().getPlatNomor(),
                        t.getTotalBayar());
                totalPendapatan += t.getTotalBayar();
                jumlahSelesai++;
            }
        }

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Total Transaksi Selesai : " + jumlahSelesai + " Transaksi");
        System.out.println("TOTAL PENDAPATAN BERSIH : Rp " + totalPendapatan);
        System.out.println("===========================================================================");

        tekanEnter();
    }

    private void tekanEnter() {
        System.out.println("\nTekan ENTER untuk kembali...");
        scanner.nextLine();
    }
}