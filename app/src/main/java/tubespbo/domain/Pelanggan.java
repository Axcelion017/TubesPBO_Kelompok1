package tubespbo.domain;

public class Pelanggan {
    private String nomorKtp;
    private String namaLengkap;
    private String nomorTelepon;
    private int jumlahTransaksi;
    private Membership membership;

    public Pelanggan(String nomorKtp, String namaLengkap, String nomorTelepon){
        this.nomorKtp = nomorKtp;
        this.namaLengkap = namaLengkap;
        this.nomorTelepon = nomorTelepon;
        this.jumlahTransaksi = 0;
        this.membership = Membership.REGULER; // default, akan naik jika Pelangagan mendapatk poin setiap ia menyewa
    }


    public void tambahJumlahTransaksi(){
        this.jumlahTransaksi++;
        updateMembershipStatus();

    }

    public void updateMembershipStatus(){
        if (jumlahTransaksi >= 10) {
            this.membership = Membership.GOLD;
        } else if (jumlahTransaksi >= 5 && jumlahTransaksi < 10) {
            this.membership = Membership.SILVER;
        } else {
            this.membership = Membership.REGULER;
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
        return membership;
    }

    public void setLevelMembership(Membership membership) {
        this.membership = membership;
    }


    
}
