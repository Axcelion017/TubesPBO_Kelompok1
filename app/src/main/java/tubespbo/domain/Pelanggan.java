package tubespbo.domain;

public class Pelanggan {
    private String nomorKtp;
    private String namaLengkap;
    private String nomorTelepon;
    private int jumlahTransaksi;
    private Membership membership;

    public Pelanggan(String nomorKtp, String namaLengkap, String nomorTelepon){
        // Pelanggan baru selalu mulai dari 0 transaksi.
        this(nomorKtp, namaLengkap, nomorTelepon, 0);
    }

    public Pelanggan(String nomorKtp, String namaLengkap, String nomorTelepon, int jumlahTransaksi){
        // Constructor ini dipakai saat load data lama agar jumlah transaksi tidak reset.
        if (nomorKtp == null || nomorKtp.trim().isEmpty()) {
            throw new IllegalArgumentException("Nomor KTP wajib diisi.");
        }

        if (!nomorKtp.trim().matches("\\d+")) {
            throw new IllegalArgumentException("Nomor KTP hanya boleh berisi angka.");
        }

        if (namaLengkap == null || namaLengkap.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama lengkap wajib diisi.");
        }

        if (nomorTelepon == null || nomorTelepon.trim().isEmpty()) {
            throw new IllegalArgumentException("Nomor telepon wajib diisi.");
        }

        if (!nomorTelepon.trim().matches("\\d+")) {
            throw new IllegalArgumentException("Nomor telepon hanya boleh berisi angka.");
        }

        if (jumlahTransaksi < 0) {
            throw new IllegalArgumentException("Jumlah transaksi tidak boleh negatif.");
        }

        this.nomorKtp = nomorKtp.trim();
        this.namaLengkap = namaLengkap.trim();
        this.nomorTelepon = nomorTelepon.trim();
        this.jumlahTransaksi = jumlahTransaksi;
        updateMembershipStatus();
    }

    public void tambahJumlahTransaksi(){
        this.jumlahTransaksi++;
        updateMembershipStatus();
    }

    public void updateMembershipStatus(){
        // Aturan loyalty: 0-5 Reguler, 6-10 Silver, lebih dari 10 Gold.
        if (jumlahTransaksi > 10) {
            this.membership = Membership.GOLD;
        } else if (jumlahTransaksi > 5) {
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

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public int getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public Membership getLevelMembership() {
        return membership;
    }

    // Diskon dihitung dari level membership tanpa mengubah enum Membership.
    public double getPersentaseDiskon() {
        if (membership == Membership.GOLD) {
            return 0.20;
        }

        if (membership == Membership.SILVER) {
            return 0.10;
        }

        return 0;
    }

    public double hitungDiskon(double biayaDasar) {
        if (biayaDasar < 0) {
            throw new IllegalArgumentException("Biaya dasar tidak boleh negatif.");
        }

        return biayaDasar * getPersentaseDiskon();
    }

    public double hitungTotalSetelahDiskon(double biayaDasar) {
        return biayaDasar - hitungDiskon(biayaDasar);
    }

    public void setLevelMembership(Membership membership) {
        if (membership == null) {
            throw new IllegalArgumentException("Level membership wajib diisi.");
        }

        this.membership = membership;
    }
    
}
