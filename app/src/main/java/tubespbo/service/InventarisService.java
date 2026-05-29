package tubespbo.service;

import java.util.*;
import tubespbo.domain.Kendaraan;

public class InventarisService {    

















    public void cetakDaftarKendaraan(List<Kendaraan> daftarKendaraan) {
        // Validasi jika data kosong agar program tidak error
        if (daftarKendaraan == null || daftarKendaraan.isEmpty()) {
            System.out.println("[INFORMASI] Tidak ada kendaraan dalam inventaris.");
            return;
        }

        // Desain Header Tabel
        System.out.println("=================================================================================================================");
        System.out.println("                                               DAFTAR KENDARAAN");
        System.out.println("=================================================================================================================");

        // !!! NOTED !!! MOHON DIBACA !!!_
        /*
         penjelasan format printf/format
         * %-3s  -> Menampilkan String, rata kiri, memakan space minimal 3 karakter.
         * %-12s -> Menampilkan String, rata kiri, memakan space minimal 12 karakter.
         * %,13.2f -> Menampilkan angka desimal (double) tarif dengan tanda koma ribuan (contoh: Rp150,000.00)
        */
        String formatHeader = "| %-3s | %-12s | %-20s | %-10s | %-15s | %-12s |\n";
        String formatBaris  = "| %-3d | %-12s | %-20s | %-10s | Rp%,13.2f | %-12s |\n";

        // cetak judul kolom dalam bentuk model tabel
        System.out.format(formatHeader, "No", "Plat Nomor", "Nama Kendaraan", "Jenis", "Tarif Sewa", "Status");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");


        // cetak data kendaraan dengan perulangan
        int nomor = 1;
        for (Kendaraan k : daftarKendaraan) {
            // Logika ternary untuk status ketersediaan
            String status = k.isTersedia() ? "Tersedia" : "Disewa";  
            String jenisKendaraan = k.getJenisKendaraan(); // getter ini bisa menyesuaikan 

            // Menggabungkan merek dan model untuk kolom "Nama Kendaraan"
            String namaKendaraan = k.getMerek() + " " + k.getModel(); // getter ini bisa menyesuaikan 

            // Mencetak isi baris sesuai urutan placeholder pada formatBaris
            System.out.printf(formatBaris, 
                nomor++, 
                k.getPlatNomor(), // getter ini bisa menyesuaikan 
                namaKendaraan, 
                jenisKendaraan, 
                k.getHargaSewa(), // getter ini bisa menyesuaikan 
                status
            );
        }
        System.out.println("=================================================================================================================");
    }
}