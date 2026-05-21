package tubespbo.domain;

public class Motor extends Kendaraan {
    private String jenisTransmisi;

    public Motor() {
        super();
    }

    public Motor(String platNomor, int hargaSewaPerHari, String merk, String jenisTransmisi) {
        super(platNomor, hargaSewaPerHari, merk);
        this.jenisTransmisi = jenisTransmisi;
    }

    @Override
    public String getJenis() {
        return "Motor";
    }

    @Override
    public String getInfoTambahan() {
        return jenisTransmisi;
    }

    @Override
    public int hitungDendaKeterlambatan(int hariTerlambat) {
        return hitungDenda(hariTerlambat, 20000);
    }

    public String getJenisTransmisi() {
        return jenisTransmisi;
    }

    public void setJenisTransmisi(String jenisTransmisi) {
        this.jenisTransmisi = jenisTransmisi;
    }
}