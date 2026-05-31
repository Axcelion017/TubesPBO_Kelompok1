package tubespbo.domain;

public class Mobil extends Kendaraan {
    private int jumlahPintu;

    public Mobil(String platNomor, String merek, int hargaSewaPerHari, int jumlahPintu) {
        super(platNomor, merek, hargaSewaPerHari);

        if (jumlahPintu <= 0) {
            throw new IllegalArgumentException("Jumlah pintu harus lebih dari 0.");
        }

        this.jumlahPintu = jumlahPintu;
    }

    @Override
    public double hitungDendaKeterlambatan(int hariTerlambat){
        // Tidak ada denda jika kendaraan tidak terlambat dikembalikan.
        if (hariTerlambat <= 0) {
            return 0;
        }

        return hariTerlambat * (getHargaSewaPerHari() * 0.10);
    }

    @Override
    public String getJenis() {
        return "Mobil";
    }

    @Override
    public String getInfoTambahan() {
        return "Jumlah Pintu: " + jumlahPintu;
    }

    public int getJumlahPintu() {
        return jumlahPintu;
    }
}
