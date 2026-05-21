package tubespbo.domain;

public class Mobil extends Kendaraan {
    private int jumlahPintu;

    public Mobil() {
        super();
    }

    public Mobil(String platNomor, int hargaSewaPerHari, String merk, int jumlahPintu) {
        super(platNomor, hargaSewaPerHari, merk);
        this.jumlahPintu = jumlahPintu;
    }

    @Override
    public String getJenis() {
        return "Mobil";
    }

    @Override
    public String getInfoTambahan() {
        return jumlahPintu + " pintu";
    }

    @Override
    public int hitungDendaKeterlambatan(int hariTerlambat) {
        return hitungDenda(hariTerlambat, 50000);
    }

    public int getJumlahPintu() {
        return jumlahPintu;
    }

    public void setJumlahPintu(int jumlahPintu) {
        this.jumlahPintu = jumlahPintu;
    }
}