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

    
    
    
    
    
import java.util.Scanner;

public class OwnerMenuHandler {
    private Scanner input;

    //Constructor default.
    //Digunakan ketika OwnerMenuHandler dibuat tanpa mengirim Scanner dari class lain.
    public OwnerMenuHandler() {
        this.input = new Scanner(System.in);
    }

    //Constructor dengan parameter Scanner.
    //Digunakan jika Scanner sudah dibuat di class lain, supaya input tidak dibuat berulang.
    public OwnerMenuHandler(Scanner input) {
        this.input = input;
    }

    //Method utama untuk menampilkan dashboard Owner.
    //Owner dapat memilih menu laporan pendapatan dan riwayat, atau logout.
    public void tampilkanDashboardOwner(String username) {
        boolean logout = false;

        //Perulangan berjalan selama Owner belum memilih logout.
        while (!logout) {
            System.out.println("========================================");
            System.out.println("DASHBOARD - OWNER");
            System.out.println("========================================");
            System.out.println("Selamat Datang, " + username + ".");
            System.out.println("Silahkan pilih menu:");
            System.out.println("1. Lihat Laporan Pendapatan & Riwayat");
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");

            //Membaca input pilihan dari user.
            String pilihan = input.nextLine().trim();

            //Mengecek pilihan menu yang dimasukkan oleh Owner.
            switch (pilihan) {
                case "1":
                    //Jika memilih 1, tampilkan halaman laporan.
                    tampilkanLaporanRiwayatDanPendapatan();
                    break;

                case "0":
                    //Jika memilih 0, keluar dari dashboard Owner.
                    logout = true;
                    System.out.println("Logout berhasil. Kembali ke layar login awal.");
                    break;

                default:
                    //Jika input tidak sesuai menu, tampilkan pesan error.
                    System.out.println("[ERROR] Pilihan tidak valid.");
                    tekanEnter();
                    break;
            }
        }
    }

    //Method untuk menampilkan tampilan awal laporan Owner.
    //Bagian ini masih fokus pada UI/tampilan laporan.
    //Data asli laporan akan dihubungkan oleh bagian integrasi laporan.
    private void tampilkanLaporanRiwayatDanPendapatan() {
        System.out.println("===========================================================================");
        System.out.println("LAPORAN RIWAYAT & PENDAPATAN");
        System.out.println("===========================================================================");
        System.out.println("| ID Transaksi | Pelanggan | Kendaraan | Status | Total Tagihan |");
        System.out.println("---------------------------------------------------------------------------");

        ///Gio
        System.out.println("TOTAL PENDAPATAN (Hanya dari Transaksi Selesai): Rp " + totalPendapatan);

        System.out.println("---------------------------------------------------------------------------");

        ///Gio

        System.out.println("TOTAL PENDAPATAN (Hanya dari Transaksi Selesai): Rp 0");
        System.out.println("===========================================================================");

        tekanEnter();
    }

    //Method untuk menahan tampilan sebelum kembali ke menu utama.
    //Program akan menunggu user menekan ENTER.
    private void tekanEnter() {
        System.out.println("Tekan ENTER untuk kembali ke menu utama...");
        input.nextLine();
    }
}