package tubespbo.presentation;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import tubespbo.domain.Kendaraan;
import tubespbo.domain.Pelanggan;
import tubespbo.domain.StatusKendaraan;
import tubespbo.domain.Transaksi;
import tubespbo.service.PelangganService;
import tubespbo.service.RentalService;

public class StaffMenuHandler {
    private final Scanner scanner;
    private final PelangganService pelangganService;
    private final RentalService rentalService;

    public StaffMenuHandler(Scanner scanner, PelangganService pelangganService, RentalService rentalService) {
        this.scanner = scanner;
        this.pelangganService = pelangganService;
        this.rentalService = rentalService;
    }

    public void tampilkanDashboardStaff(String username) {
        int pilihan = -1;
        do {
            try {
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

                pilihan = Integer.parseInt(scanner.nextLine());

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
            } catch (NumberFormatException e) {
                System.out.println("[GAGAL] Input harus berupa angka.");
            }
        } while (pilihan != 0);
    }

    private void menuDaftarPelanggan() {
        System.out.println("\n========================================");
        System.out.println("MENU PENDAFTARAN PELANGGAN");
        System.out.println("========================================");
        System.out.println("(ketik 0 untuk kembali)");

        System.out.print("Masukkan Nomor KTP : ");
        String ktp = scanner.nextLine();

        if (ktp.equals("0")) {
            return;
        }

        System.out.print("Masukkan Nama Lengkap : ");
        String nama = scanner.nextLine();

        System.out.print("Masukkan No Telepon : ");
        String telepon = scanner.nextLine();

        try {
            Pelanggan pelanggan = pelangganService.daftarPelanggan(ktp, nama, telepon);
            System.out.println("\n[SUKSES] Pelanggan " + pelanggan.getNamaLengkap() + " (KTP: "
                    + pelanggan.getNomorKtp() + ") berhasil didaftarkan.");
        } catch (Exception e) {
            System.out.println("[GAGAL] " + e.getMessage());
        }

        tekanEnter();
    }

    private void menuCariPelanggan() {
        System.out.println("\n========================================");
        System.out.println("MENU PENCARIAN PELANGGAN");
        System.out.println("========================================");

        System.out.print("Masukkan Nomor KTP : ");
        String ktp = scanner.nextLine();

        if (ktp.equals("0")) {
            return;
        }

        Optional<Pelanggan> pelanggan = pelangganService.cariByNomorKtp(ktp);

        if (pelanggan.isPresent()) {
            cetakDetailPelanggan(pelanggan.get());
        } else {
            System.out.println("[GAGAL] Data pelanggan tidak ditemukan.");
        }

        tekanEnter();
    }

    private void cetakDetailPelanggan(Pelanggan p) {
        System.out.println("\n[DATA DITEMUKAN]");
        System.out.println("Nama Lengkap    : " + p.getNamaLengkap());
        System.out.println("Nomor KTP       : " + p.getNomorKtp());
        System.out.println("No Telepon      : " + p.getNomorTelepon());
        System.out.println("Total Transaksi : " + p.getJumlahTransaksi() + " kali");
        System.out.println("Status Member   : " + p.getLevelMembership());
        System.out.println("Diskon Aktif    : " + (p.getPersentaseDiskon() * 100) + "%");
        System.out.println("========================================");
    }

    private void menuCekKendaraanTersedia() {
        System.out.println("\n======================================================");
        System.out.println("DAFTAR KENDARAAN TERSEDIA");
        System.out.println("======================================================");

        List<Kendaraan> daftar = rentalService.ambilKendaraanTersedia();

        if (daftar.isEmpty()) {
            System.out.println("Tidak ada kendaraan tersedia saat ini.");
        } else {
            System.out.printf("%-15s %-10s %-15s %-15s\n", "Plat", "Jenis", "Harga/Hari", "Status");
            for (Kendaraan k : daftar) {
                System.out.printf("%-15s %-10s Rp %-12d %-15s\n", k.getPlatNomor(), k.getJenis(),
                        k.getHargaSewaPerHari(), k.getStatus());
            }
        }

        tekanEnter();
    }

    private void menuPeminjaman() {
        System.out.println("\n========================================");
        System.out.println("MENU PEMINJAMAN KENDARAAN");
        System.out.println("========================================");

        System.out.print("Masukkan Nomor KTP Pelanggan : ");
        String ktp = scanner.nextLine();

        if (ktp.equals("0")) {
            return;
        }

        System.out.print("Masukkan Plat Nomor Kendaraan : ");
        String plat = scanner.nextLine();

        System.out.print("Rencana Durasi Sewa (Hari) : ");
        try {
            int durasi = Integer.parseInt(scanner.nextLine());

            Transaksi trx = rentalService.buatPeminjamanBaru(ktp, plat, durasi);

            System.out.println("\n--- STRUK PEMINJAMAN SEMENTARA ---");
            System.out.println("ID Transaksi : " + trx.getIdTransaksi());
            System.out.println("Nama Pelanggan : " + trx.getPelanggan().getNamaLengkap());
            System.out.println("Kendaraan : " + trx.getKendaraan().getPlatNomor());
            System.out.println("Durasi Sewa : " + durasi + " Hari");
            System.out.println("Estimasi Biaya : Rp " + trx.hitungBiayaDasar());
            System.out.println("----------------------------------");
            System.out.println("[SUKSES] Transaksi berhasil dicatat. Status kendaraan berubah menjadi "
                    + StatusKendaraan.SEDANG_DISEWA);

        } catch (NumberFormatException e) {
            System.out.println("[GAGAL] Durasi sewa harus berupa angka.");
        } catch (Exception e) {
            System.out.println("[GAGAL] " + e.getMessage());
        }

        tekanEnter();
    }

    private void menuPengembalian() {
        System.out.println("\n========================================");
        System.out.println("MENU PENGEMBALIAN KENDARAAN");
        System.out.println("========================================");

        System.out.print("Masukkan ID Transaksi : ");
        String idTransaksi = scanner.nextLine();

        if (idTransaksi.equals("0")) {
            return;
        }

        System.out.print("Durasi Keterlambatan (Hari, isi 0 jika tepat waktu): ");
        try {
            int hariTerlambat = Integer.parseInt(scanner.nextLine());

            System.out.println("\nMenghitung tagihan ...\n");

            // Mengambil biaya sebelum denda dari service (opsional: ambil transaksi dulu
            // untuk detail struk)
            Optional<Transaksi> optTrx = rentalService.ambilSemuaTransaksi().stream()
                    .filter(t -> t.getIdTransaksi().equalsIgnoreCase(idTransaksi))
                    .findFirst();

            if (optTrx.isEmpty()) {
                System.out.println("[GAGAL] ID Transaksi tidak ditemukan.");
                tekanEnter();
                return;
            }

            Transaksi t = optTrx.get();
            if (t.isSelesai()) {
                System.out.println("[GAGAL] Transaksi sudah diselesaikan sebelumnya.");
                tekanEnter();
                return;
            }

            double biayaDasar = t.hitungBiayaDasar();
            double diskon = t.getPelanggan().hitungDiskon(biayaDasar);

            double total = rentalService.prosesPengembalian(idTransaksi, hariTerlambat);

            double denda = total - (biayaDasar - diskon);

            System.out.println("--- STRUK TAGIHAN AKHIR ---");
            System.out.println("ID Transaksi : " + t.getIdTransaksi());
            System.out.println("Pelanggan : " + t.getPelanggan().getNamaLengkap() + " ("
                    + t.getPelanggan().getLevelMembership() + ")");
            System.out.println(
                    "Kendaraan : " + t.getKendaraan().getJenis() + " (" + t.getKendaraan().getPlatNomor() + ")");
            System.out.println("Biaya Dasar : Rp " + biayaDasar + " (" + t.getDurasiSewaHari() + " Hari)");
            if (diskon > 0) {
                System.out.println("Diskon Member: -Rp " + diskon);
            }
            System.out.println("Denda Telat : Rp " + denda);
            System.out.println("---------------------------------- +");
            System.out.println("TOTAL BAYAR : Rp " + total);
            System.out.println("[SUKSES] Pengembalian berhasil. Status kendaraan berubah menjadi TERSEDIA");

        } catch (NumberFormatException e) {
            System.out.println("[GAGAL] Durasi keterlambatan harus berupa angka.");
        } catch (Exception e) {
            System.out.println("[GAGAL] " + e.getMessage());
        }

        tekanEnter();
    }

    private void tekanEnter() {
        System.out.println("\nTekan ENTER untuk kembali...");
        scanner.nextLine();
    }
}