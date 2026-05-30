package tubespbo.domain;

public class Pelanggan {
    private String nomorKtp;
    private String namaLengkap;
    private String nomorTelepon;
    private int jumlahTransaksi;
    private Membership levelMembership;

    public Pelanggan(String nomorKtp, String namaLengkap, String nomorTelepon) {
        this.nomorKtp = nomorKtp;
        this.namaLengkap = namaLengkap;
        this.nomorTelepon = nomorTelepon;
        this.jumlahTransaksi = 0;
        this.levelMembership = Membership.REGULER;
    }

    public void tambahJumlahTransaksi() {
        this.jumlahTransaksi++;
        updateMembershipStatus();
    }

    public void updateMembershipStatus() {
        if (jumlahTransaksi >= 10) {
            this.levelMembership = Membership.GOLD;
        } else if (jumlahTransaksi >= 5) {
            this.levelMembership = Membership.SILVER;
        } else {
            this.levelMembership = Membership.REGULER;
        }
    }

    public String getNomorKtp() {
        return nomorKtp;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public int getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public Membership getLevelMembership() {
        return levelMembership;
    }

    public void setLevelMembership(Membership levelMembership) {
        this.levelMembership = levelMembership;
    }

    public void setJumlahTransaksi(int jumlahTransaksi) {
        this.jumlahTransaksi = jumlahTransaksi;
    }
}
