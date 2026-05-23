package tubespbo.domain;

import java.util.UUID;

public class Transaksi {
    private String idTransaksi;
    private Pelanggan pelanggan;
    private Kendaraan kendaraan;
    private int durasiSewaHari;
    private int hariKeterlambatan;
    private double totalBayar;
    private boolean selesai;

    public Transaksi() {
        this.idTransaksi = generateIdTransaksi();
        this.hariKeterlambatan = 0;
        this.totalBayar = 0.0;
        this.selesai = false;
    }

    public Transaksi(Pelanggan pelanggan, Kendaraan kendaraan, int durasiSewaHari) {
        this();
        setPelanggan(pelanggan);
        setKendaraan(kendaraan);
        setDurasiSewaHari(durasiSewaHari);
        this.totalBayar = hitungBiayaDasar();
    }

    private String generateIdTransaksi() {
        return "TRX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public double hitungBiayaDasar() {
        if (kendaraan == null || durasiSewaHari <= 0) {
            return 0.0;
        }

        return kendaraan.getHargaSewaPerHari() * durasiSewaHari;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        if (idTransaksi == null || idTransaksi.trim().isEmpty()) {
            throw new IllegalArgumentException("ID transaksi wajib diisi.");
        }

        this.idTransaksi = idTransaksi.trim();
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        if (pelanggan == null) {
            throw new IllegalArgumentException("Pelanggan wajib diisi.");
        }

        this.pelanggan = pelanggan;
    }

    public Kendaraan getKendaraan() {
        return kendaraan;
    }

    public void setKendaraan(Kendaraan kendaraan) {
        if (kendaraan == null) {
            throw new IllegalArgumentException("Kendaraan wajib diisi.");
        }

        this.kendaraan = kendaraan;
    }

    public int getDurasiSewaHari() {
        return durasiSewaHari;
    }

    public void setDurasiSewaHari(int durasiSewaHari) {
        if (durasiSewaHari <= 0) {
            throw new IllegalArgumentException("Durasi sewa harus lebih dari 0 hari.");
        }

        this.durasiSewaHari = durasiSewaHari;
        this.totalBayar = hitungBiayaDasar();
    }

    public int getHariKeterlambatan() {
        return hariKeterlambatan;
    }

    public void setHariKeterlambatan(int hariKeterlambatan) {
        if (hariKeterlambatan < 0) {
            throw new IllegalArgumentException("Hari keterlambatan tidak boleh negatif.");
        }

        this.hariKeterlambatan = hariKeterlambatan;
    }

    public double getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(double totalBayar) {
        if (totalBayar < 0) {
            throw new IllegalArgumentException("Total bayar tidak boleh negatif.");
        }

        this.totalBayar = totalBayar;
    }

    public boolean isSelesai() {
        return selesai;
    }

    public void setSelesai(boolean selesai) {
        this.selesai = selesai;
    }
}