package tubespbo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import tubespbo.domain.Kendaraan;
import tubespbo.domain.Pelanggan;
import tubespbo.domain.StatusKendaraan;
import tubespbo.domain.Transaksi;
import tubespbo.repository.KendaraanRepository;
import tubespbo.repository.TransaksiRepository;

public class RentalService {
    private final TransaksiRepository transaksiRepository;
    private final PelangganService pelangganService;
    private final KendaraanRepository kendaraanRepository;

    public RentalService(TransaksiRepository transaksiRepository, PelangganService pelangganService,
            KendaraanRepository kendaraanRepository) {
        this.transaksiRepository = transaksiRepository;
        this.pelangganService = pelangganService;
        this.kendaraanRepository = kendaraanRepository;
    }

    public List<Kendaraan> ambilKendaraanTersedia() {
        List<Kendaraan> semua = kendaraanRepository.ambilSemua();
        List<Kendaraan> tersedia = new ArrayList<>();
        for (Kendaraan k : semua) {
            if (k.getStatus() == StatusKendaraan.TERSEDIA) {
                tersedia.add(k);
            }
        }
        return tersedia;
    }

    public Transaksi buatPeminjamanBaru(String ktp, String plat, int durasi) throws Exception {
        Optional<Pelanggan> pOpt = pelangganService.cariByNomorKtp(ktp);
        if (pOpt.isEmpty()) {
            throw new Exception("Pelanggan dengan KTP " + ktp + " tidak ditemukan.");
        }

        Optional<Kendaraan> kOpt = kendaraanRepository.cariByPlatNomor(plat);
        if (kOpt.isEmpty()) {
            throw new Exception("Kendaraan dengan plat " + plat + " tidak ditemukan.");
        }

        Kendaraan kendaraan = kOpt.get();
        if (kendaraan.getStatus() != StatusKendaraan.TERSEDIA) {
            throw new Exception("Kendaraan sedang tidak tersedia (Status: " + kendaraan.getStatus() + ").");
        }

        String idTrx = "TRX-" + System.currentTimeMillis();
        Transaksi trx = new Transaksi(idTrx, pOpt.get(), kendaraan, durasi);

        kendaraan.setStatus(StatusKendaraan.SEDANG_DISEWA);

        kendaraanRepository.simpanSemua(List.of(kendaraan));
        transaksiRepository.simpanSemua(List.of(trx));

        return trx;
    }

    public List<Transaksi> ambilSemuaTransaksi() {
        return transaksiRepository.ambilSemua();
    }

    public double prosesPengembalian(String idTransaksi, int hariTerlambat) throws Exception {
        Optional<Transaksi> transaksiOptional = transaksiRepository.cariByIdTransaksi(idTransaksi);

        if (transaksiOptional.isEmpty()) {
            throw new Exception("Transaksi dengan ID " + idTransaksi + " tidak ditemukan!");
        }

        Transaksi transaksi = transaksiOptional.get();
        if (transaksi.isSelesai()) {
            throw new Exception("Transaksi ini sudah diselesaikan sebelumnya.");
        }

        Pelanggan pelanggan = transaksi.getPelanggan();
        Kendaraan kendaraan = transaksi.getKendaraan();

        double biayaDasar = transaksi.hitungBiayaDasar();
        double denda = kendaraan.hitungDendaKeterlambatan(hariTerlambat);
        double jumlahDiskon = pelanggan.hitungDiskon(biayaDasar);

        double totalBiayaAkhir = biayaDasar - jumlahDiskon + denda;

        transaksi.setHariKeterlambatan(hariTerlambat);
        transaksi.setTotalBayar(totalBiayaAkhir);
        transaksi.setSelesai(true);

        kendaraan.setStatus(StatusKendaraan.TERSEDIA);
        pelanggan.tambahJumlahTransaksi();

        transaksiRepository.simpanSemua(List.of(transaksi));
        kendaraanRepository.simpanSemua(List.of(kendaraan));
        pelangganService.simpanSemua(List.of(pelanggan));

        return totalBiayaAkhir;
    }
}
