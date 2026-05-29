package tubespbo.domain;

public class Transaksi {
    private String idTransaksi;
    private Pelanggan pelanggan;
    private Kendaraan kendaraan;
    private int durasiSewaHari;
    private int hariKeterlambatan;
    private double totalBayar;
    private boolean selesai;

    public Transaksi(String idTransaksi, Pelanggan pelanggan, Kendaraan kendaraan, int durasiSewaHari) {
        if (idTransaksi == null || idTransaksi.trim().isEmpty()) {
            throw new IllegalArgumentException("ID transaksi wajib diisi.");
        }

        if (pelanggan == null) {
            throw new IllegalArgumentException("Pelanggan wajib diisi.");
        }

        if (kendaraan == null) {
            throw new IllegalArgumentException("Kendaraan wajib diisi.");
        }

        if (durasiSewaHari <= 0) {
            throw new IllegalArgumentException("Durasi sewa harus lebih dari 0 hari.");
        }

        this.idTransaksi = idTransaksi.trim();
        this.pelanggan = pelanggan;
        this.kendaraan = kendaraan;
        this.durasiSewaHari = durasiSewaHari;
        this.hariKeterlambatan = 0;
        this.totalBayar = kendaraan.getHargaSewaPerHari() * durasiSewaHari;
        this.selesai = false;
    }

    public void setHariKeterlambatan(int hari) {
        if (hari < 0) {
            throw new IllegalArgumentException("Hari keterlambatan tidak boleh negatif.");
        }

        this.hariKeterlambatan = hari;
    }

    public void setTotalBayar(double total) {
        if (total < 0) {
            throw new IllegalArgumentException("Total bayar tidak boleh negatif.");
        }

        this.totalBayar = total;
    }

    public void setSelesai(boolean status) {
        this.selesai = status;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public Kendaraan getKendaraan() {
        return kendaraan;
    }

    public int getDurasiSewaHari() {
        return durasiSewaHari;
    }

    public int getHariKeterlambatan() {
        return hariKeterlambatan;
    }

    public double getTotalBayar() {
        return totalBayar;
    }

    public boolean isSelesai() {
        return selesai;
    }
}