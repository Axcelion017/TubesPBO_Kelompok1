package tubespbo.domain;

public class Motor extends Kendaraan {
    private String jenisTransmisi;

    public Motor(String platNomor, String merek, int hargaSewaPerHari, String jenisTransmisi) {
        super(platNomor, merek, hargaSewaPerHari);

        if (jenisTransmisi == null || jenisTransmisi.trim().isEmpty()) {
            throw new IllegalArgumentException("Jenis transmisi wajib diisi.");
        }

        this.jenisTransmisi = jenisTransmisi.trim();
    }

    @Override
    public double hitungDendaKeterlambatan(int hariTerlambat) {
        // Tidak ada denda jika kendaraan tidak terlambat dikembalikan.
        if (hariTerlambat <= 0) {
            return 0;
        }

        return hariTerlambat * (getHargaSewaPerHari() * 0.05);
    }

    @Override
    public String getJenis() {
        return "Motor";
    }

    @Override
    public String getInfoTambahan() {
        return jenisTransmisi;
    }

    public String getJenisTransmisi() {
        return jenisTransmisi;
    }
}
