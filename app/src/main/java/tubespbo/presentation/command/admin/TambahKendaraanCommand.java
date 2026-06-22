package tubespbo.presentation.command.admin;

import java.util.Scanner;
import tubespbo.domain.Mobil;
import tubespbo.domain.Motor;
import tubespbo.exception.PlatNomorDuplikatException;
import tubespbo.presentation.command.Command;
import tubespbo.service.InventarisService;

public class TambahKendaraanCommand implements Command {
    private Scanner scanner;
    private InventarisService inventarisService;

    public TambahKendaraanCommand(Scanner scanner, InventarisService inventarisService) {
        this.scanner = scanner;
        this.inventarisService = inventarisService;
    }

    @Override
    public void execute() {
        System.out.println("\n========================================");
        System.out.println("MENU TAMBAH KENDARAAN BARU");
        System.out.println("========================================");

        System.out.println("Pilih Jenis Kendaraan:");
        System.out.println("1. Mobil");
        System.out.println("2. Motor");
        System.out.println("0. Kembali");
        System.out.print("Pilihan Anda > ");

        String pilihan = scanner.nextLine();

        switch (pilihan) {
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

            case "0":
                return;

            default:
                System.out.println("[ERROR] Pilihan tidak valid.");
        }
    }

    private void tekanEnter() {
        System.out.println("\nTekan ENTER untuk kembali ke menu utama...");
        scanner.nextLine();
    }
}
