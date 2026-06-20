package tubespbo.presentation;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import tubespbo.domain.Kendaraan;
import tubespbo.domain.Pelanggan;
import tubespbo.domain.Transaksi;
import tubespbo.domain.StatusKendaraan;
import tubespbo.service.PelangganService;
import tubespbo.service.RentalService;

public class StaffMenuHandler {
    private Scanner scanner;
    private PelangganService pelangganService;
    private RentalService rentalService;
    
    public StaffMenuHandler(Scanner scanner, PelangganService pelangganService, RentalService rentalService) {
        this.scanner = scanner;
        this.pelangganService = pelangganService;
        this.rentalService = rentalService;
    }
    
     public void tampilkanDashboardStaff(String username) {
        int pilihan;

    try {
        do {
            System.out.println("\n================================================");
            System.out.println("                DASHBOARD - STAFF");
            System.out.println("================================================");
            System.out.println("Selamat Datang, " + username + "!");
            System.out.println("Silahkan pilih menu:");
            System.out.println("1. Daftar Pelanggan Baru");
            System.out.println("2. Cari Data Pelanggan");
            System.out.println("3. Cek Kendaraan Tersedia");
            System.out.println("4. Proses Peminjaman (Sewa)");
            System.out.println("5. Proses Pengembalian");
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");
            
            // Membaca pilihan menu dari user
            pilihan = scanner.nextInt();
            

            // Menentukan aksi berdasarkan menu yang dipilih
            switch (pilihan) {

                case 1:
                    menuDaftarPelanggan();
                    break;

                case 2:
                    menuCariPelanggan();
                    break;

                case 3:
                    menuCekKendaraanTersedia();
                    break;

                case 4:
                    menuPeminjaman();
                    break;

                case 5:
                    menuPengembalian();
                    break;

                case 0:
                    System.out.println("Logout berhasil.");
                    break;

                default:
                    System.out.println("Menu tidak tersedia.");
            }
         } while (pilihan != 0);

        } catch (NumberFormatException e) {
            System.out.println("[GAGAL]" + e.getMessage());
        }
    }

    /** Menu untuk mendaftarkan pelanggan baru.*/
    private void menuDaftarPelanggan() {

        System.out.println("\n========================================");
        System.out.println("MENU PENDAFTARAN PELANGGAN");
        System.out.println("========================================");
        System.out.println("(ketik 0 untuk kembali)");

        // Input nomor KTP
        System.out.print("Masukkan Nomor KTP : ");
        String ktp = scanner.nextLine();

        // Jika user mengetik 0 maka kembali ke dashboard
        if (ktp.equals("0")) {
            return;
        }

        // Input nama lengkap pelanggan
        System.out.print("Masukkan Nama Lengkap : ");
        String nama = scanner.nextLine();

        // Input nomor telepon pelanggan
        System.out.print("Masukkan No Telepon : ");
        String telepon = scanner.nextLine();

        try {
            // Memanggil service untuk mendaftarkan pelanggan
            Pelanggan pelanggan = pelangganService.daftarPelanggan(ktp, nama, telepon);

            System.out.println("\n[SUKSES] Pelanggan "+ pelanggan.getNamaLengkap()+ " (KTP: " + pelanggan.getNomorKtp() + ") berhasil didaftarkan.");

        } catch (Exception e) {

            // Menampilkan pesan error dari service
            System.out.println("[GAGAL] " + e.getMessage());
        }

        tekanEnter();
    }

    









































    private void menuCekKendaraanTersedia() {

        System.out.println("\n======================================================");
        System.out.println("DAFTAR KENDARAAN");
        System.out.println("======================================================");

        // Mengambil daftar kendaraan tersedia dari service
        List<Kendaraan> daftar = rentalService.ambilKendaraanTersedia(); // fungsi belum ditambahkan

        // Jika list kosong
        if (daftar.isEmpty()) {
            System.out.println("Tidak ada kendaraan tersedia.");

        } 
        else {
            // Header tabel
            System.out.printf( "%-15s %-10s %-15s %-15s\n", "Plat", "Jenis", "Harga/Hari", "Status");

            // Menampilkan setiap kendaraan
            for (Kendaraan k : daftar) {
                System.out.printf("%-15s %-10s %-15.0f %-15s\n", k.getPlatNomor(), k.getJenis(), k.getHargaSewaPerHari(), k.getStatus());
            }
        }

        tekanEnter();
    }

    

    private void menuPeminjaman() {
        StatusKendaraan status;

        System.out.println("\n========================================");
        System.out.println("MENU PEMINJAMAN KENDARAAN");
        System.out.println("========================================");

        System.out.print("Masukkan Nomor KTP Pelanggan : ");
        String ktp = scanner.nextLine();

        if (ktp.equals("0")) {
            return;
        }
        //Input plat nomor kendaraan
        System.out.print("Masukkan Plat Nomor Kendaraan : ");
        String plat = scanner.nextLine();
        //Input durasi sewa kendaraan
        System.out.print("Rencana Durasi Sewa (Hari) : ");
        int durasi = Integer.parseInt(scanner.nextLine());

        try {
            // Membuat transaksi peminjaman baru
            Transaksi trx = rentalService.buatPeminjamanBaru(ktp, plat, durasi); // fungsi belum ditambahkan

            // Menampilkan struk sementara
            System.out.println("\n--- STRUK PEMINJAMAN SEMENTARA ---");
            System.out.println("ID Transaksi : " + trx.getIdTransaksi());
            System.out.println("Nama Pelanggan : " + trx.getPelanggan().getNamaLengkap());
            System.out.println("Kendaraan : "+ trx.getKendaraan().getPlatNomor());
            System.out.println("Durasi Sewa : " + durasi + " Hari");
            System.out.println("Estimasi Biaya : Rp " + trx.hitungBiayaDasar());
            System.out.println("----------------------------------");
            System.out.println("[SUKSES] Transaksi berhasil dicatat. Status kendaraan berubah menjadi " + StatusKendaraan.SEDANG_DISEWA);
             System.out.println("[INFO] Status kendaraan berubah menjadi " + StatusKendaraan.SEDANG_DISEWA);
        }  catch (NumberFormatException e) {
            System.out.println("[GAGAL] Durasi sewa harus berupa angka.");
        } catch (Exception e) {
            System.out.println("[GAGAL] " + e.getMessage());
        }
        
        tekanEnter();
    }



    private void menuPengembalian() {
        StatusKendaraan status;
        
        System.out.println("\n========================================");
        System.out.println("MENU PENGEMBALIAN KENDARAAN");
        System.out.println("========================================");

        System.out.print("Masukkan ID Transaksi : ");
        String idTransaksi = scanner.nextLine();

        if (idTransaksi.equals("0")) {
            return;
        }
        
        List<Transaksi> daftarTransaksi = rentalService.ambilSemuaTransaksi();

        // Variabel untuk menyimpan transaksi yang dicari
        Transaksi transaksiDitemukan = null;

        // Mencari transaksi berdasarkan ID
        for (Transaksi transaksi : daftarTransaksi) {
            if (transaksi.getIdTransaksi()
                    .equalsIgnoreCase(idTransaksi)) {

                transaksiDitemukan = transaksi;
                break;
            }
        }

        // Jika transaksi tidak ditemukan
        if (transaksiDitemukan == null) {

            System.out.println(
                    "[GAGAL] ID Transaksi tidak ditemukan.");

            tekanEnter();
            return;
        }


        System.out.print("Durasi Keterlambatan (Hari, isi 0 jika tepat waktu): ");
        int hariTerlambat = Integer.parseInt(scanner.nextLine());

        System.out.println("\nMenghitung tagihan ...\n");

        // Biaya dasar transaksi
        double biayaDasar = transaksiDitemukan.hitungBiayaDasar();

        // Service menghitung total biaya
        double total = rentalService.prosesPengembalian(idTransaksi, hariTerlambat);

        // Menghitung denda untuk ditampilkan pada struk
        double denda = total - biayaDasar;

        System.out.println("--- STRUK TAGIHAN AKHIR ---");
        System.out.println("ID Transaksi : "+ transaksiDitemukan.getIdTransaksi());
        System.out.println("Pelanggan : "+ transaksiDitemukan.getPelanggan().getNamaLengkap());
        System.out.println("Kendaraan : "+ transaksiDitemukan.getKendaraan().getJenis() + " (" + transaksiDitemukan.getKendaraan().getPlatNomor() + ")");
        System.out.println("Biaya Dasar : Rp " + biayaDasar + " (" + transaksiDitemukan.getDurasiSewaHari() + " Hari)");

        System.out.println("Denda Telat : Rp "+ denda);
        System.out.println("---------------------------------- +");

        System.out.println("\nTOTAL BAYAR : Rp " + total);
        System.out.println("[SUKSES] Pengembalian berhasil. Status kendaraan berubah menjadi " + status.TERSEDIA);

        tekanEnter();
    }















    




    private void tekanEnter() {

        System.out.println(
                "\nTekan ENTER untuk kembali...");

        scanner.nextLine();
    }
}