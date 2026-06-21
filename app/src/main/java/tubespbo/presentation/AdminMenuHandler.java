package tubespbo.presentation;

import java.util.Scanner;

import tubespbo.domain.Mobil;
import tubespbo.domain.Motor;
import tubespbo.domain.User;
import tubespbo.exception.KendaraanSedangDisewaException;
import tubespbo.exception.PlatNomorDuplikatException;
import tubespbo.service.InventarisService;

public class AdminMenuHandler {
    private Scanner scanner;
    private InventarisService inventarisService;

    public AdminMenuHandler(Scanner scanner, InventarisService inventarisService) {
        this.scanner = scanner;
        this.inventarisService = inventarisService;
    }

    public void tampilkanDashboardAdmin(String username) {
        int pilihan;

        do {

            // Menampilkan header dashboard
            System.out.println("\n========================================");
            System.out.println("DASHBOARD - ADMIN");
            System.out.println("========================================");
            System.out.println("Selamat Datang, " + username + "!");
            System.out.println("Silahkan pilih menu:");
            System.out.println("1. Tambah Kendaraan Baru");
            System.out.println("2. Lihat Semua Kendaraan");
            System.out.println("3. Hapus Kendaraan");
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");

            try {

                // Membaca pilihan user, membaca satu baris input teks dari pengguna dan
                // mengubahnya (mengonversi) menjadi nilai angka bilangan bulat
                pilihan = Integer.parseInt(scanner.nextLine());

                // Menentukan menu yang dipilih
                switch (pilihan) {
                    case 1:
                        menuTambahKendaraan();
                        break;
                    case 2:
                        menuLihatKendaraan();
                        break;
                    case 3:
                        menuHapusKendaraan();
                        break;
                    case 0:
                        System.out.println("[INFO] Logout berhasil.");
                        break;
                    default:
                        System.out.println("[ERROR] Pilihan tidak valid.");
                }

            } catch (NumberFormatException e) {
                // Menangani jika input bukan angka
                System.out.println("[ERROR] Input harus berupa angka.");
                pilihan = -1;
            }

        } while (pilihan != 0);
    }

    private void menuTambahKendaraan() {

        System.out.println("\n========================================");
        System.out.println("MENU TAMBAH KENDARAAN BARU");
        System.out.println("========================================");

        // Menampilkan pilihan jenis kendaraan
        System.out.println("Pilih Jenis Kendaraan:");
        System.out.println("1. Mobil");
        System.out.println("2. Motor");
        System.out.println("0. Kembali");
        System.out.print("Pilihan Anda > ");

        String pilihan = scanner.nextLine();

        switch (pilihan) {

            // Jika memilih Mobil
            case "1":
                try {
                    System.out.print("Masukkan Plat Nomor : ");
                    String platMobil = scanner.nextLine();

                    System.out.print("Masukkan Harga Sewa/Hari : ");
                    int hargaMobil = Integer.parseInt(scanner.nextLine());

                    System.out.print("Masukkan Merk Kendaraan : ");
                    String merkMobil = scanner.nextLine();

                    System.out.print("Masukkan Jumlah Pintu : ");
                    int jumlahPintu = Integer.parseInt(scanner.nextLine());

                    Mobil mobil = inventarisService.tambahMobil(platMobil, merkMobil, hargaMobil, jumlahPintu);
                    System.out.println("\n[SUKSES] Mobil dengan plat " + mobil.getPlatNomor()
                            + " berhasil ditambahkan ke dalam sistem dengan status TERSEDIA.");
                } catch (PlatNomorDuplikatException e) {
                    System.out.println("[GAGAL] " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println("[GAGAL] Input tidak valid: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("[GAGAL] Terjadi kesalahan: " + e.getMessage());
                }
                tekanEnter();
                break;

            case "2":
                try {
                    System.out.print("Masukkan Plat Nomor : ");
                    String platMotor = scanner.nextLine();

                    System.out.print("Masukkan Harga Sewa/Hari : ");
                    int hargaMotor = Integer.parseInt(scanner.nextLine());

                    System.out.print("Masukkan Merk Kendaraan : ");
                    String merkMotor = scanner.nextLine();

                    System.out.print("Masukkan Jenis Transmisi (Manual/Matic) : ");
                    String transmisi = scanner.nextLine();

                    Motor motor = inventarisService.tambahMotor(platMotor, merkMotor, hargaMotor, transmisi);
                    System.out.println("\n[SUKSES] Motor dengan plat " + motor.getPlatNomor()
                            + " berhasil ditambahkan ke dalam sistem dengan status TERSEDIA.");
                } catch (PlatNomorDuplikatException e) {
                    System.out.println("[GAGAL] " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println("[GAGAL] Input tidak valid: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("[GAGAL] Terjadi kesalahan: " + e.getMessage());
                }

                tekanEnter();
                break;

            // Kembali ke dashboard
            case "0":
                return;

            default:
                System.out.println("[ERROR] Pilihan tidak valid.");
        }
    }

    private void menuLihatKendaraan() {

        System.out.println("\n=================================================================================");
        System.out.println("DAFTAR SELURUH KENDARAAN");
        System.out.println("=================================================================================");

        // Menampilkan seluruh data kendaraan
        inventarisService.cetakDaftarKendaraan();

        tekanEnter();
    }

    private void menuHapusKendaraan() {

        System.out.println("\n========================================");
        System.out.println("MENU HAPUS KENDARAAN");
        System.out.println("========================================");
        System.out.println("(ketik 0 untuk kembali)");

        // Input plat nomor yang ingin dihapus
        System.out.print("Masukkan Plat Nomor yang ingin dihapus : ");
        String platNomor = scanner.nextLine();

        // Jika user ingin kembali
        if (platNomor.equals("0")) {
            return;
        }

        // Memanggil service untuk menghapus kendaraan
        try {
            boolean berhasil = inventarisService.hapusKendaraan(platNomor);
            if (berhasil) {
                System.out.println("[SUKSES] Kendaraan " + platNomor + " berhasil dihapus dari sistem.");
            } else {
                System.out.println("[GAGAL] Kendaraan " + platNomor + " tidak ditemukan.");
            }
        } catch (tubespbo.exception.KendaraanSedangDisewaException e) {
            System.out.println("[GAGAL] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[GAGAL] Terjadi kesalahan: " + e.getMessage());
        }

        tekanEnter();
    }

    private void tekanEnter() {
        System.out.println("\nTekan ENTER untuk kembali ke menu utama...");
        scanner.nextLine();
    }
}