package tubespbo.service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import tubespbo.domain.Kendaraan;
import tubespbo.domain.Mobil;
import tubespbo.domain.Motor;
import tubespbo.domain.StatusKendaraan;
import tubespbo.exception.KendaraanSedangDisewaException;
import tubespbo.exception.PlatNomorDuplikatException;
import tubespbo.repository.KendaraanRepository;

public class InventarisService {
    private final KendaraanRepository kendaraanRepository;

    public InventarisService(KendaraanRepository kendaraanRepository) {
        if (kendaraanRepository == null) {
            throw new IllegalArgumentException("KendaraanRepository wajib diisi.");
        }
        this.kendaraanRepository = kendaraanRepository;
    }

    public Mobil tambahMobil(String platNomor, String merek, int hargaSewaPerHari, int jumlahPintu)
            throws PlatNomorDuplikatException {
        String plat = validasiDanNormalisasiDataDasar(platNomor, merek, hargaSewaPerHari);
        pastikanPlatUnik(plat);

        Mobil mobil = new Mobil(plat, merek.trim(), hargaSewaPerHari, jumlahPintu);
        kendaraanRepository.simpanSemua(List.of(mobil));
        return mobil;
    }

    public Mobil tambahMobil(String platNomor, int hargaSewaPerHari, String merek, int jumlahPintu)
            throws PlatNomorDuplikatException {
        return tambahMobil(platNomor, merek, hargaSewaPerHari, jumlahPintu);
    }

    public Motor tambahMotor(String platNomor, String merek, int hargaSewaPerHari, String jenisTransmisi)
            throws PlatNomorDuplikatException {
        String plat = validasiDanNormalisasiDataDasar(platNomor, merek, hargaSewaPerHari);
        pastikanPlatUnik(plat);

        Motor motor = new Motor(plat, merek.trim(), hargaSewaPerHari, jenisTransmisi);
        kendaraanRepository.simpanSemua(List.of(motor));
        return motor;
    }

    public Motor tambahMotor(String platNomor, int hargaSewaPerHari, String merek, String jenisTransmisi)
            throws PlatNomorDuplikatException {
        return tambahMotor(platNomor, merek, hargaSewaPerHari, jenisTransmisi);
    }

    public List<Kendaraan> ambilSemuaKendaraan() {
        return kendaraanRepository.ambilSemua();
    }

    public List<Kendaraan> ambilKendaraanTersedia() {
        return kendaraanRepository.ambilSemua().stream()
                .filter(kendaraan -> kendaraan.getStatus() == StatusKendaraan.TERSEDIA)
                .toList();
    }

    public Optional<Kendaraan> cariByPlatNomor(String platNomor) {
        return kendaraanRepository.cariByPlatNomor(normalisasiPlat(platNomor));
    }

    public void simpanKendaraan(Kendaraan kendaraan) {
        if (kendaraan == null) {
            throw new IllegalArgumentException("Kendaraan wajib diisi.");
        }
        kendaraanRepository.simpanSemua(List.of(kendaraan));
    }

    public boolean hapusKendaraan(String platNomor) throws KendaraanSedangDisewaException {
        Optional<Kendaraan> kendaraan = cariByPlatNomor(platNomor);

        if (kendaraan.isEmpty()) {
            return false;
        }

        if (kendaraan.get().getStatus() == StatusKendaraan.SEDANG_DISEWA) {
            throw new KendaraanSedangDisewaException("Kendaraan " + platNomor + " sedang disewa, tidak bisa dihapus.");
        }

        return kendaraanRepository.hapusByPlatNomor(kendaraan.get().getPlatNomor());
    }

    public void cetakDaftarKendaraan() {
        List<Kendaraan> daftarKendaraan = ambilSemuaKendaraan();

        if (daftarKendaraan.isEmpty()) {
            System.out.println("Data kendaraan masih kosong.");
            return;
        }

        System.out.println("====================================================================================");
        System.out.println("                           DAFTAR SELURUH KENDARAAN");
        System.out.println("====================================================================================");
        System.out.printf("| %-12s | %-6s | %-25s | %-15s | %-10s |%n",
                "Plat Nomor", "Jenis", "Atribut Khusus", "Harga Sewa", "Status");
        System.out.println("------------------------------------------------------------------------------------");

        for (Kendaraan kendaraan : daftarKendaraan) {
            System.out.printf("| %-12s | %-6s | %-25s | %-15s | %-10s |%n",
                    kendaraan.getPlatNomor(),
                    kendaraan.getJenis(),
                    kendaraan.getInfoTambahan(),
                    formatRupiah(kendaraan.getHargaSewaPerHari()),
                    formatStatus(kendaraan.getStatus()));
        }

        System.out.println("------------------------------------------------------------------------------------");
    }

    private String validasiDanNormalisasiDataDasar(
            String platNomor, String merek, int hargaSewaPerHari) {
        if (merek == null || merek.trim().isEmpty()) {
            throw new IllegalArgumentException("Merek kendaraan wajib diisi.");
        }
        if (hargaSewaPerHari <= 0) {
            throw new IllegalArgumentException("Harga sewa per hari harus lebih dari 0.");
        }
        return normalisasiPlat(platNomor);
    }

    private void pastikanPlatUnik(String platNomor) throws PlatNomorDuplikatException {
        if (kendaraanRepository.cariByPlatNomor(platNomor).isPresent()) {
            throw new PlatNomorDuplikatException(platNomor);
        }
    }

    private String normalisasiPlat(String platNomor) {
        if (platNomor == null || platNomor.trim().isEmpty()) {
            throw new IllegalArgumentException("Plat nomor wajib diisi.");
        }
        return platNomor.trim().replaceAll("\\s+", " ").toUpperCase();
    }

    private String formatRupiah(int nominal) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID"));
        return "Rp " + format.format(nominal);
    }

    private String formatStatus(StatusKendaraan status) {
        return status == StatusKendaraan.TERSEDIA ? "Tersedia" : "Disewa";
    }
}
