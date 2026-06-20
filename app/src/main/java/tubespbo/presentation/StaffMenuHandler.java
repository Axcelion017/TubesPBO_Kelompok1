package tubespbo.presentation;

import tubespbo.domain.Pelanggan;
import tubespbo.service.PelangganService;
import java.util.*;

public class StaffMenuHandler {

    Scanner input = new Scanner(System.in);
    private PelangganService pelangganService;

    public void menuCariPelanggan() {
        System.out.println("\n=== MENU CARI DATA PELANGGAN ===");
        System.out.print("Masukkan Nomor KTP Pelanggan: ");
        String inputKtp = input.nextLine().trim();

        // Memanggil service yang menghasilkan Optional
        Optional<Pelanggan> hasilCari = pelangganService.cariByNomorKtp(inputKtp);

        // Memeriksa isi Optional sesuai standar Java modern
        if (hasilCari.isPresent()) {
            cetakDetailPelanggan(hasilCari.get()); // Ambil objek Pelanggan di dalamnya
        } else {
            System.out.println("[INFO] Data pelanggan dengan KTP tersebut tidak ditemukan.");
        }

        tekanEnter();
    }

    private void cetakDetailPelanggan(Pelanggan pelanggan) {
        System.out.println("\n------------------------------------------------");
        System.out.println("           DETAIL DATA PELANGGAN                ");
        System.out.println("------------------------------------------------");
        System.out.println("Nomor KTP         : " + pelanggan.getKtp());
        System.out.println("Nama Lengkap      : " + pelanggan.getNama());
        System.out.println("No. Telepon       : " + pelanggan.getNoTelepon());
        System.out.println("Total Transaksi   : " + pelanggan.getJumlahTransaksi() + " kali");
        System.out.println("Status Membership : " + pelanggan.getStatusMembership());
        System.out.println("Potongan Diskon   : " + (pelanggan.getPersentaseDiskon() * 100) + "%");
        System.out.println("------------------------------------------------");
    }

    private void tekanEnter() {
        System.out.println("\nTekan Enter untuk kembali...");
        input.nextLine();
    }
}