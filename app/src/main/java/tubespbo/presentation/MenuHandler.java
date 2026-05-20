package tubespbo.presentation;

import tubespbo.domain.Kendaraan;
import tubespbo.exception.KendaraanSedangDisewaException;
import tubespbo.exception.PlatNomorDuplikatException;
import tubespbo.service.InventarisService;

import java.util.Scanner;

public class MenuHandler {
    private final Scanner scanner;
    private final InventarisService inventarisService;

    public MenuHandler(Scanner scanner, InventarisService inventarisService) {
        this.scanner = scanner;
        this.inventarisService = inventarisService;
    }

    public void tampilkanDashboardAdmin(String username) {
        boolean berjalan = true;

        while (berjalan) {
            System.out.println("\n========================================");
            System.out.println("          DASHBOARD - ADMIN");
            System.out.println("========================================");
            System.out.println("Selamat Datang, " + username + "!");
            System.out.println("1. Tambah Kendaraan Baru");
            System.out.println("2. Lihat Semua Kendaraan");
            System.out.println("3. Hapus Kendaraan");
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");

            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    menuTambahKendaraan();
                    break;
                case "2":
                    inventarisService.cetakDaftarKendaraan();
                    tungguEnter();
                    break;
                case "3":
                    menuHapusKendaraan();
                    break;
                case "0":
                    berjalan = false;
                    break;
                default:
                    System.out.println("[ERROR] Pilihan tidak valid.");
            }
        }
    }

    private void menuTambahKendaraan() {
        System.out.println("\n========================================");
        System.out.println("      MENU TAMBAH KENDARAAN BARU");
        System.out.println("========================================");
        System.out.println("1. Mobil");
        System.out.println("2. Motor");
        System.out.println("0. Kembali");
        System.out.print("Pilihan Anda > ");

        String pilihan = scanner.nextLine();
        if ("0".equals(pilihan)) {
            return;
        }

        try {
            String platNomor = bacaTeksWajib("Masukkan Plat Nomor      : ");
            int harga = bacaAngkaPositif("Masukkan Harga Sewa/Hari : ");
            String merk = bacaTeksWajib("Masukkan Merk Kendaraan  : ");
            Kendaraan kendaraan;

            if ("1".equals(pilihan)) {
                int jumlahPintu = bacaAngkaPositif("Masukkan Jumlah Pintu    : ");
                kendaraan = inventarisService.tambahMobil(platNomor, harga, merk, jumlahPintu);
            } else if ("2".equals(pilihan)) {
                String transmisi = bacaTeksWajib("Masukkan Jenis Transmisi : ");
                kendaraan = inventarisService.tambahMotor(platNomor, harga, merk, transmisi);
            } else {
                System.out.println("[ERROR] Pilihan jenis kendaraan tidak valid.");
                return;
            }

            System.out.println("[SUKSES] " + kendaraan.getJenis() + " dengan plat "
                    + kendaraan.getPlatNomor() + " berhasil ditambahkan dengan status TERSEDIA.");
        } catch (PlatNomorDuplikatException | IllegalArgumentException e) {
            System.out.println("[GAGAL] " + e.getMessage());
        }

        tungguEnter();
    }

    private void menuHapusKendaraan() {
        System.out.println("\n========================================");
        System.out.println("          MENU HAPUS KENDARAAN");
        System.out.println("========================================");
        System.out.print("Masukkan Plat Nomor yang ingin dihapus (ketik 0 untuk keluar): ");
        String platNomor = scanner.nextLine();

        if ("0".equals(platNomor)) {
            return;
        }

        try {
            boolean terhapus = inventarisService.hapusKendaraan(platNomor);
            if (terhapus) {
                System.out.println("[SUKSES] Kendaraan " + platNomor + " berhasil dihapus dari sistem.");
            } else {
                System.out.println("[GAGAL] Kendaraan dengan plat " + platNomor + " tidak ditemukan.");
            }
        } catch (KendaraanSedangDisewaException e) {
            System.out.println("[GAGAL] " + e.getMessage());
        }

        tungguEnter();
    }

    private String bacaTeksWajib(String pesan) {
        while (true) {
            System.out.print(pesan);
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("[ERROR] Input wajib diisi.");
        }
    }

    private int bacaAngkaPositif(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                int nilai = Integer.parseInt(scanner.nextLine());
                if (nilai > 0) {
                    return nilai;
                }
                System.out.println("[ERROR] Angka harus lebih dari 0.");
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Input harus berupa angka.");
            }
        }
    }

    private void tungguEnter() {
        System.out.print("Tekan ENTER untuk kembali ke menu utama...");
        scanner.nextLine();
    }
}