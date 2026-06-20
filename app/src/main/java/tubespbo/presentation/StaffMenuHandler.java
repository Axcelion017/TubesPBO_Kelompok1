package tubespbo.presentation;

import tubespbo.domain.Pelanggan;

public class StaffMenuHandler {







































    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void menuCariPelanggan() {
        System.out.println("\n========================================");
        System.out.println("MENU PENCARIAN PELANGGAN");
        System.out.println("========================================");

        System.out.print("Masukkan Nomor KTP : ");
        String ktp =/*  */.nextLine();

        if (ktp.equals("0")) {
            return;
        }

        // Service mengembalikan Optional
        Optional<Pelanggan> pelanggan = pelangganService.cariByNomorKtp(ktp);

        // Mengecek apakah data ditemukan
        if (pelanggan.isPresent()) {
            // Mengambil object Pelanggan dari Optional
            Pelanggan p = pelanggan.get();

            System.out.println("\n[DATA DITEMUKAN]");
            System.out.println("Nama Lengkap    : " + p.getNamaLengkap());   
            System.out.println("Nomor KTP       : " + p.getNomorKtp());      
            System.out.println("No Telepon      : " + p.getNomorTelepon());  
            //Tambahan Fitur Kelompok 1 (Membership & Diskon)
            System.out.println("Total Transaksi : " + p.getJumlahTransaksi() + " kali");
            System.out.println("Status Member   : " + p.getStatusMembership());
            System.out.println("Diskon Aktif    : " + (p.getPersentaseDiskon() * 100) + "%");
            System.out.println("========================================");
        } 
        else {
            System.out.println("[GAGAL] Data pelanggan tidak ditemukan.");
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

    

    
}