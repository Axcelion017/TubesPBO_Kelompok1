package tubespbo.service;

import java.util.List;
import java.util.Optional;

import tubespbo.domain.Pelanggan;
import tubespbo.domain.StatusKendaraan;
import tubespbo.domain.Transaksi;
import tubespbo.domain.Kendaraan;
import tubespbo.repository.TransaksiRepository;
import tubespbo.repository.PelangganRepository;
import tubespbo.repository.KendaraanRepository;

public class RentalService {
    private final TransaksiRepository transaksiRepository;
    private final PelangganService pelangganService;
    private final InventarisService inventarisService;

    public RentalService(TransaksiRepository transaksiRepository, PelangganService pelangganService, InventarisService inventarisService) {
        this.transaksiRepository = transaksiRepository;
        this.pelangganService = pelangganService;
        this.inventarisService = inventarisService;
    }

    public double prosesPengembalian(String idTransaksi, int hariTerlambat) {
        Optional<Transaksi> transaksiOptional = transaksiRepository.cariByIdTransaksi(idTransaksi);
        
        if (transaksiOptional.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] Transaksi dengan ID " + idTransaksi + " tidak ditemukan!");
        }

        // Penyimpanan sebagai objek dari domain tertentu
        Transaksi transaksi = transaksiOptional.get();
        Pelanggan pelanggan = transaksi.getPelanggan();
        Kendaraan kendaraan = transaksi.getKendaraan();

        // Hitung Biaya Sewa Dasar
        double biayaDasar = transaksi.hitungBiayaDasar();

        // Logika if-else denda mobil/motor otomatis berjalan di balik layar objek kendaraan masing-masing
        double denda = kendaraan.hitungDendaKeterlambatan(hariTerlambat);

        // Hitung Diskon Memanfaatkan Rich Domain Pelanggan
        double jumlahDiskon = pelanggan.hitungDiskon(biayaDasar);

        // Hitung Total Akhir
        double totalBiayaAkhir = biayaDasar - jumlahDiskon + denda;

        // Update Status Transaksi
        transaksi.setHariKeterlambatan(hariTerlambat);
        transaksi.setTotalBayar(totalBiayaAkhir);
        transaksi.setSelesai(true); 

        // PERBAIKAN: Menggunakan Enum StatusKendaraan yang Sesuai
        kendaraan.setStatus(StatusKendaraan.TERSEDIA);

        // Trigger Transaksi & Membership Loyalty Program
        pelanggan.tambahJumlahTransaksi(); 

        transaksiRepository.simpanSemua(List.of(transaksi)); 
        pelangganService.simpanSemuaPelanggan(List.of(pelanggan)); // Fungsi ini belum ditambahkan

        return totalBiayaAkhir;
    }
}
