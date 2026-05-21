package tubespbo.presentation;

import tubespbo.domain.Kendaraan;
import tubespbo.domain.Role;
import tubespbo.domain.User;
import tubespbo.exception.FiturBelumTersediaException;
import tubespbo.exception.KendaraanSedangDisewaException;
import tubespbo.exception.PlatNomorDuplikatException;
import tubespbo.service.InventarisService;

import java.util.Scanner;

public class MenuHandler {
    private final Scanner scanner;
    private final InventarisService inventarisService;

    public MenuHandler(Scanner scanner) {
        this.scanner = scanner;
        this.inventarisService = null;
    }

    public MenuHandler(Scanner scanner, InventarisService inventarisService) {
        this.scanner = scanner;
        this.inventarisService = inventarisService;
    }

    public void tampilkanDashboard(User user) {
        if (user.getRole() == Role.ADMIN) {
            tampilkanDashboardAdmin(user);
        } else if (user.getRole() == Role.STAFF) {
            tampilkanDashboardStaff(user);
        } else if (user.getRole() == Role.OWNER) {
            tampilkanDashboardOwner(user);
        } else {
            System.out.println("[ERROR] Role tidak dikenali.");
        }
    }

    private void tampilkanFiturBelumTersedia(String namaFitur) {
        try {
            FiturBelumTersediaException.cekStatusFitur(namaFitur);
            System.out.println("\n[GATEWAY] Akses diberikan. Membuka " + namaFitur + "...");
            System.out.println("[INFO] Modul ini belum memiliki implementasi menu pada branch saat ini.");
        } catch (FiturBelumTersediaException e) {
            System.out.println("\n[SYSTEM ALERT] " + e.getMessage());
        }

        tekanEnter();
    }

    private void tampilkanDashboardAdmin(User user) {
        boolean berjalan = true;

        while (berjalan) {
            System.out.println("\n========================================");
            System.out.println("          DASHBOARD - ADMIN");
            System.out.println("========================================");
            System.out.println("Selamat Datang, " + user.getUsername() + "!");
            System.out.println("Silahkan pilih menu:");
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
                    menuLihatKendaraan();
                    break;
                case "3":
                    menuHapusKendaraan();
                    break;
                case "0":
                    berjalan = false;
                    System.out.println("[LOGOUT] Anda keluar dari Dashboard Admin.");
                    break;
                default:
                    System.out.println("[ERROR] Pilihan tidak valid.");
                    tekanEnter();
            }
        }
    }

    private void menuTambahKendaraan() {
        if (inventarisService == null) {
            tampilkanFiturBelumTersedia("Tambah Kendaraan Baru");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("      MENU TAMBAH KENDARAAN BARU");
        System.out.println("========================================");
        System.out.println("Pilih Jenis Kendaraan:");
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
                tekanEnter();
                return;
            }

            System.out.println("[SUKSES] " + kendaraan.getJenis() + " dengan plat "
                    + kendaraan.getPlatNomor() + " berhasil ditambahkan ke dalam sistem dengan status TERSEDIA.");
        } catch (PlatNomorDuplikatException | IllegalArgumentException e) {
            System.out.println("[GAGAL] " + e.getMessage());
        }

        tekanEnter();
    }

    private void menuLihatKendaraan() {
        if (inventarisService == null) {
            tampilkanFiturBelumTersedia("Lihat Semua Kendaraan");
            return;
        }

        inventarisService.cetakDaftarKendaraan();
        tekanEnter();
    }

    private void menuHapusKendaraan() {
        if (inventarisService == null) {
            tampilkanFiturBelumTersedia("Hapus Kendaraan");
            return;
        }

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

        tekanEnter();
    }

    private void tampilkanDashboardStaff(User user) {
        boolean berjalan = true;

        while (berjalan) {
            System.out.println("\n========================================");
            System.out.println("          DASHBOARD - STAFF");
            System.out.println("========================================");
            System.out.println("Selamat Datang, " + user.getUsername() + "!");
            System.out.println("Silahkan pilih menu:");
            System.out.println("1. Daftar Pelanggan Baru");
            System.out.println("2. Cari Data Pelanggan");
            System.out.println("3. Cek Kendaraan Tersedia");
            System.out.println("4. Proses Peminjaman (Sewa)");
            System.out.println("5. Proses Pengembalian");
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");

            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    tampilkanFiturBelumTersedia("Daftar Pelanggan Baru");
                    break;
                case "2":
                    tampilkanFiturBelumTersedia("Cari Data Pelanggan");
                    break;
                case "3":
                    tampilkanFiturBelumTersedia("Cek Kendaraan Tersedia");
                    break;
                case "4":
                    tampilkanFiturBelumTersedia("Proses Peminjaman");
                    break;
                case "5":
                    tampilkanFiturBelumTersedia("Proses Pengembalian");
                    break;
                case "0":
                    berjalan = false;
                    System.out.println("[LOGOUT] Anda keluar dari Dashboard Staff.");
                    break;
                default:
                    System.out.println("[ERROR] Pilihan tidak valid.");
                    tekanEnter();
            }
        }
    }

    private void tampilkanDashboardOwner(User user) {
        boolean berjalan = true;

        while (berjalan) {
            System.out.println("\n========================================");
            System.out.println("          DASHBOARD - OWNER");
            System.out.println("========================================");
            System.out.println("Selamat Datang, " + user.getUsername() + "!");
            System.out.println("Silahkan pilih menu:");
            System.out.println("1. Lihat Laporan Pendapatan & Riwayat");
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");

            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    tampilkanFiturBelumTersedia("Lihat Laporan Pendapatan & Riwayat");
                    break;
                case "0":
                    berjalan = false;
                    System.out.println("[LOGOUT] Anda keluar dari Dashboard Owner.");
                    break;
                default:
                    System.out.println("[ERROR] Pilihan tidak valid.");
                    tekanEnter();
            }
        }
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

    private void tekanEnter() {
        System.out.print("Tekan ENTER untuk kembali...");
        scanner.nextLine();
    }
}
