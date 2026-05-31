package tubespbo.domain;

public abstract class Kendaraan {
    private String platNomor;
    private String merek;
    private int hargaSewaPerHari;
    private StatusKendaraan status;

    public Kendaraan(String platNomor, String merek, int hargaSewaPerHari) {
        // Validasi di domain menjaga object kendaraan selalu punya data dasar yang layak.
        if (platNomor == null || platNomor.trim().isEmpty()) {
            throw new IllegalArgumentException("Plat nomor wajib diisi.");
        }

        if (merek == null || merek.trim().isEmpty()) {
            throw new IllegalArgumentException("Merek kendaraan wajib diisi.");
        }

        if (hargaSewaPerHari <= 0) {
            throw new IllegalArgumentException("Harga sewa per hari harus lebih dari 0.");
        }

        this.platNomor = platNomor.trim();
        this.merek = merek.trim();
        this.hargaSewaPerHari = hargaSewaPerHari;
        this.status = StatusKendaraan.TERSEDIA;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public String getMerek() {
        return merek;
    }

    // Alias untuk kode lama yang masih memakai istilah "merk".
    public String getMerk() {
        return merek;
    }

    public int getHargaSewaPerHari() {
        return hargaSewaPerHari;
    }

    public StatusKendaraan getStatus() {
        return status;
    }

    public void setStatus(StatusKendaraan status) {
        if (status == null) {
            throw new IllegalArgumentException("Status kendaraan wajib diisi.");
        }

        this.status = status;
    }

    public abstract String getJenis();

    public abstract String getInfoTambahan();

    public abstract double hitungDendaKeterlambatan(int hariTerlambat);

}
