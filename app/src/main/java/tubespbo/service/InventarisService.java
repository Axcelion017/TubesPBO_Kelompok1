package tubespbo.service;

import tubespbo.domain.Kendaraan;
import tubespbo.domain.Mobil;
import tubespbo.domain.Motor;
import tubespbo.domain.StatusKendaraan;
import tubespbo.exception.KendaraanSedangDisewaException;
import tubespbo.exception.PlatNomorDuplikatException;
import tubespbo.repository.KendaraanRepository;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class InventarisService {
    private final KendaraanRepository kendaraanRepository;

    public InventarisService(KendaraanRepository kendaraanRepository) {
        this.kendaraanRepository = kendaraanRepository;
    }

    public Mobil tambahMobil(String platNomor, int hargaSewaPerHari, String merk, int jumlahPintu)
            throws PlatNomorDuplikatException {
        validasiDataDasar(platNomor, hargaSewaPerHari, merk);
        if (jumlahPintu <= 0) {
            throw new IllegalArgumentException("Jumlah pintu harus lebih dari 0.");
        }

        String plat = normalisasiPlat(platNomor);
        pastikanPlatUnik(plat);

        Mobil mobil = new Mobil(plat, hargaSewaPerHari, merk.trim(), jumlahPintu);
        List<Kendaraan> daftarKendaraan = kendaraanRepository.ambilSemua();
        daftarKendaraan.add(mobil);
        kendaraanRepository.simpanSemua(daftarKendaraan);
        return mobil;
    }

    public Motor tambahMotor(String platNomor, int hargaSewaPerHari, String merk, String jenisTransmisi)
            throws PlatNomorDuplikatException {
        validasiDataDasar(platNomor, hargaSewaPerHari, merk);
        if (jenisTransmisi == null || jenisTransmisi.trim().isEmpty()) {
            throw new IllegalArgumentException("Jenis transmisi wajib diisi.");
        }

        String plat = normalisasiPlat(platNomor);
        pastikanPlatUnik(plat);

        Motor motor = new Motor(plat, hargaSewaPerHari, merk.trim(), jenisTransmisi.trim());
        List<Kendaraan> daftarKendaraan = kendaraanRepository.ambilSemua();
        daftarKendaraan.add(motor);
        kendaraanRepository.simpanSemua(daftarKendaraan);
        return motor;
    }

    public List<Kendaraan> ambilSemuaKendaraan() {
        return kendaraanRepository.ambilSemua();
    }

    public boolean hapusKendaraan(String platNomor) throws KendaraanSedangDisewaException {
        String plat = normalisasiPlat(platNomor);
        Optional<Kendaraan> kendaraan = kendaraanRepository.cariByPlatNomor(plat);

        if (kendaraan.isEmpty()) {
            return false;
        }

        if (kendaraan.get().getStatus() == StatusKendaraan.SEDANG_DISEWA) {
            throw new KendaraanSedangDisewaException(plat);
        }

        return kendaraanRepository.hapusByPlatNomor(plat);
    }

    public void cetakDaftarKendaraan() {
        List<Kendaraan> daftarKendaraan = kendaraanRepository.ambilSemua();

        if (daftarKendaraan.isEmpty()) {
            System.out.println("Data kendaraan masih kosong.");
            return;
        }

        System.out.println("====================================================================================");
        System.out.println("                         DAFTAR SELURUH KENDARAAN");
        System.out.println("====================================================================================");
        System.out.printf("| %-12s | %-6s | %-12s | %-10s | %-16s | %-13s |%n",
                "Plat Nomor", "Jenis", "Harga/Hari", "Merek", "Info Tambahan", "Status");
        System.out.println("------------------------------------------------------------------------------------");

        for (Kendaraan kendaraan : daftarKendaraan) {
            System.out.printf("| %-12s | %-6s | %-12s | %-10s | %-16s | %-13s |%n",
                    kendaraan.getPlatNomor(),
                    kendaraan.getJenis(),
                    formatRupiah(kendaraan.getHargaSewaPerHari()),
                    kendaraan.getMerk(),
                    kendaraan.getInfoTambahan(),
                    formatStatus(kendaraan.getStatus()));
        }

        System.out.println("------------------------------------------------------------------------------------");
    }

    private void validasiDataDasar(String platNomor, int hargaSewaPerHari, String merk) {
        if (platNomor == null || platNomor.trim().isEmpty()) {
            throw new IllegalArgumentException("Plat nomor wajib diisi.");
        }
        if (hargaSewaPerHari <= 0) {
            throw new IllegalArgumentException("Harga sewa per hari harus lebih dari 0.");
        }
        if (merk == null || merk.trim().isEmpty()) {
            throw new IllegalArgumentException("Merk kendaraan wajib diisi.");
        }
    }

    private void pastikanPlatUnik(String platNomor) throws PlatNomorDuplikatException {
        if (kendaraanRepository.cariByPlatNomor(platNomor).isPresent()) {
            throw new PlatNomorDuplikatException(platNomor);
        }
    }

    private String normalisasiPlat(String platNomor) {
        return platNomor.trim().replaceAll("\\s+", " ").toUpperCase();
    }

    private String formatRupiah(int nominal) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID"));
        return "Rp " + format.format(nominal);
    }

    private String formatStatus(StatusKendaraan status) {
        if (status == null) {
            return StatusKendaraan.TERSEDIA.name();
        }
        return status.name().replace("_", " ");
    }
}
