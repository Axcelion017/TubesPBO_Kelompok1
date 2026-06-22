package tubespbo.presentation.command.owner;

import java.util.List;
import java.util.Scanner;
import tubespbo.domain.Transaksi;
import tubespbo.presentation.command.Command;
import tubespbo.service.RentalService;

public class LaporanPendapatanCommand implements Command {
    private Scanner scanner;
    private RentalService rentalService;

    public LaporanPendapatanCommand(Scanner scanner, RentalService rentalService) {
        this.scanner = scanner;
        this.rentalService = rentalService;
    }

    @Override
    public void execute() {
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
