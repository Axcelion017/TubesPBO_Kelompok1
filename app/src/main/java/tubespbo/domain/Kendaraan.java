package tubespbo.domain;

public abstract class Kendaraan {
    private String platNomor, merk;
    private int hargaSewaPerHari;
    private StatusKendaraan status;

    public Kendaraan(String platNomor, String merk, int hargaSewaPerHari) {
        this.platNomor = platNomor;
        this.merk = merk;
        this.hargaSewaPerHari = hargaSewaPerHari;
        this.status = StatusKendaraan.TERSEDIA;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public String getMerk() {
        return merk;
    }

    public int getHargaSewaPerHari() {
        return hargaSewaPerHari;
    }

    public StatusKendaraan getStatus() {
        return status;
    }

    public void setStatus(StatusKendaraan status) {
        this.status = status;
    }

    public abstract String getJenis();

    public abstract String getInfoTambahan();

    public abstract double hitungDendaKeterlambatan(int hariTerlambat);

}