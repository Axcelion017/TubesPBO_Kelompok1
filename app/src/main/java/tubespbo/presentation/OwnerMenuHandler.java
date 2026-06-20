package tubespbo.presentation;

import java.util.List;

import tubespbo.domain.Transaksi;

public class OwnerMenuHandler {
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    public void menuLaporanTransaksi() {
        System.out.println("\n========================================");
        System.out.println("        LAPORAN PENDAPATAN BISNIS       ");
        System.out.println("========================================");

        // 1. Mengambil seluruh data transaksi dari RentalService
        List<Transaksi> daftarTransaksi = rentalService.ambilSemuaTransaksi();
        
        double totalPendapatan = 0;
        int jumlahTransaksiSelesai = 0;

        System.out.println("\nRIWAYAT TRANSAKSI SELESAI:");
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-15s %-15s %-12s %-15s\n", "ID Transaksi", "No KTP", "Plat Nomor", "Total Bayar");
        System.out.println("----------------------------------------------------------------");

        // 2. Melakukan looping untuk menyaring transaksi yang sudah selesai
        for (Transaksi t : daftarTransaksi) {
            if (t.isSelesai()) { // Memastikan hanya menghitung transaksi yang sukses/selesai
                System.out.printf("%-15s %-15s %-12s Rp %-13.0f\n", 
                    t.getIdTransaksi(), 
                    t.getPelanggan().getNomorKtp(), 
                    t.getKendaraan().getPlatNomor(), 
                    t.getTotalBayar()
                );
                totalPendapatan += t.getTotalBayar();
                jumlahTransaksiSelesai++;
            }
        }

        // 3. Mencetak kesimpulan total pendapatan ke layar Owner
        System.out.println("----------------------------------------------------------------");
        System.out.println("Total Transaksi Selesai : " + jumlahTransaksiSelesai + " Transaksi");
        System.out.println("TOTAL PENDAPATAN BERSIH : Rp " + totalPendapatan);
        System.out.println("========================================");

        tekanEnter(); 
    }
    
        private void tekanEnter() {
        System.out.println("\nTekan Enter untuk kembali...");
        //.nextLine();
    }

    
    
    
    
    
}