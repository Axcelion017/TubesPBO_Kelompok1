package tubespbo.domain;

public class Pelanggan {
    private String nomorKtp;
    private String namaLengkap;
    private String nomorTelepon;
    private int jumlahTransaksi;
    private Membership Membership;

    public Pelanggan(String nomorKtp, String namaLengkap, String nomorTelepon){
        this.nomorKtp = nomorKtp;
        this.namaLengkap = namaLengkap;
        this.nomorTelepon = nomorTelepon;
        this.jumlahTransaksi = 0;
        this.Membership = Membership.REGULER; // default, akan naik jika Pelangagan mendapatk poin setiap ia menyewa
    }


    public void tambahJumlahTransaksi(){
        this.jumlahTransaksi++;
        updateMembershipStatus();

    }

    public void updateMembershipStatus(){
        if (jumlahTransaksi >= 10) {
            Membership = Membership.GOLD;
        } else if (jumlahTransaksi >= 5 && jumlahTransaksi < 10) {
            Membership = Membership.SILVER;
        } else {
            Membership = Membership.REGULER;
        }

    }

    public String getNomorKtp() {
        return nomorKtp;
    }

    public String getNamaLengkap() { 
        return namaLengkap;
    }

    public int getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public Membership getMembership() {
        return Membership;
    }

    public void setLevelMembership(Membership membership) {
        Membership = membership;
    }


    
}
