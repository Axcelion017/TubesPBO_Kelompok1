package tubespbo.domain;

public abstract class Kendaraan {
    private String platNomor;
    private int hargaSewaPerHari;
    private String merk;
    private StatusKendaraan status;

    public Kendaraan() {
        this.status = StatusKendaraan.TERSEDIA;
    }

    public Kendaraan(String platNomor, int hargaSewaPerHari, String merk) {
        this.platNomor = platNomor;
        this.hargaSewaPerHari = hargaSewaPerHari;
        this.merk = merk;
        this.status = StatusKendaraan.TERSEDIA;
    }

    public abstract String getJenis();

    public abstract String getInfoTambahan();

    public abstract int hitungDendaKeterlambatan(int hariTerlambat);

    protected int hitungDenda(int hariTerlambat, int dendaPerHari) {
        if (hariTerlambat <= 0) {
            return 0;
        }
        return hariTerlambat * dendaPerHari;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public int getHargaSewaPerHari() {
        return hargaSewaPerHari;
    }

    public void setHargaSewaPerHari(int hargaSewaPerHari) {
        this.hargaSewaPerHari = hargaSewaPerHari;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public StatusKendaraan getStatus() {
        return status == null ? StatusKendaraan.TERSEDIA : status;
    }

    public void setStatus(StatusKendaraan status) {
        this.status = status == null ? StatusKendaraan.TERSEDIA : status;
    }

    public boolean isTersedia() {
        return getStatus() == StatusKendaraan.TERSEDIA;
    }
}