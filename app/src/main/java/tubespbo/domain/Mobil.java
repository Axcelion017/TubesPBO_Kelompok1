package tubespbo.domain;

public class Mobil extends Kendaraan {
    private int jumlahPintu;

    public Mobil(String platNomor, String merk, int hargaSewaPerHari, int jumlahPintu) {
        super(platNomor, merk, hargaSewaPerHari);
        this.jumlahPintu = jumlahPintu;
    }

    @Override
    public double hitungDendaKeterlambatan(int hariTerlambat){
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