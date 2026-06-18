package tubespbo.service;

import tubespbo.domain.Kendaraan;
import java.util.*;
// penggunaan solid : metode SRP, class ini hanya mengurus logika inventaris format & cetak data
// urusan input/oputput menu sengaja tidak dimasukkan kesini dan dipisah ke handler.


public class InventarisService {    

    // SOLID PRINCIPLE DIGUNAKAN: 
    // DIP : Menggunakan abstrak List<kendaraan>(Interface), bukan Arraylis<> (class yang konkrit)
    public void cetakDaftarKendaraan(List<Kendaraan> daftarKendaraan){
        
        // validasi data jika program data kosong agar program tidak crash (error handling)
        if(daftarKendaraan == null || daftarKendaraan.isEmpty()) {
            System.out.println("[INFROMASI]-DATA KENDARAAN TIDAK ADA DI INVENTARIS SAAT INI");
            return;
        }

        //desain header table data kendaraan agar table rapi dan trestruk tur
        System.out.println("================================================================================");
        System.out.println("                          DAFTAR SELURUH KENDARAAN                              ");
        System.out.println("================================================================================");

        String formatHeader = "| %-15s | %-12s | %-20s | %-10s | %-15s | %-12s |\n";
        String formatBaris  = "| %-3d | %-12s | %,13.2f | %-10s | %-15s | %-12s |\n";

        // cetak judul 
        System.out.format(formatHeader, "Plat Nomor", "Jenis", "Harga/Hari", "Merek", "Info Tambahan", "Status");
        System.out.println("--------------------------------------------------------------------------------");

        int nomor = 1;
        for (Kendaraan kendaraan : daftarKendaraan){


            // SOLID PRINCIPLE DIGUNAKAN
            // OCP: menghindari penggunaan if kebanyakan
            String namaKendaraan = kendaraan.getMerek() + " " + kendaraan.getJenis();
            String jenisKendaraan = kendaraan.getClass().getSimpleName(); 

            // Mencetak baris sesuai urutan getter dari UML
            System.out.printf(formatBaris, 
                nomor++, 
                kendaraan.getPlatNomor(), 
                namaKendaraan, 
                jenisKendaraan, 
                kendaraan.getHargaSewaPerHari(),
                kendaraan.getStatus() 
            );
        }
        
        System.out.println("=================================================================================================================\n");
    
        }


    }




