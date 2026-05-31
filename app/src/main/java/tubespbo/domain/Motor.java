package tubespbo.domain;

public class Motor extends Kendaraan {
    private String jenisTransmisi;

    public Motor(String platNomor, String merk, int hargaSewaPerHari, String jenisTransmisi) {
        super(platNomor, merk, hargaSewaPerHari);
        this.jenisTransmisi = jenisTransmisi;
    }

    @Override
    public double hitungDendaKeterlambatan(int hariTerlambat) {
        return hariTerlambat * (getHargaSewaPerHari() * 0.05);
    }

    @Override
    public String getJenis() {
        return "Motor";
    }

    @Override
    public String getInfoTambahan() {
        return "Transmisi: " + jenisTransmisi;
    }

    public String getJenisTransmisi() {
        return jenisTransmisi;
    }
}