package tubespbo.presentation;

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