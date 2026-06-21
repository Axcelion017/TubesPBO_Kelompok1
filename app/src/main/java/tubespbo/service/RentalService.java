package tubespbo.service;

import java.util.List;
import java.util.Optional;

import tubespbo.domain.Pelanggan;
import tubespbo.domain.StatusKendaraan;
import tubespbo.domain.Transaksi;
import tubespbo.domain.Kendaraan;
import tubespbo.repository.TransaksiRepository;
import tubespbo.repository.PelangganRepository;
import tubespbo.repository.KendaraanRepository;

public class RentalService {
    private final TransaksiRepository transaksiRepository;
    private final PelangganService pelangganService;
    private final InventarisService inventarisService;

    public RentalService(TransaksiRepository transaksiRepository, PelangganService pelangganService, InventarisService inventarisService) {
        this.transaksiRepository = transaksiRepository;
        this.pelangganService = pelangganService;
        this.inventarisService = inventarisService;
    }

    public double prosesPengembalian(String idTransaksi, int hariTerlambat) {
        Optional<Transaksi> transaksiOptional = transaksiRepository.cariByIdTransaksi(idTransaksi);
        
        if (transaksiOptional.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] Transaksi dengan ID " + idTransaksi + " tidak ditemukan!");
        }

        // Penyimpanan sebagai objek dari domain tertentu
        Transaksi transaksi = transaksiOptional.get();
        Pelanggan pelanggan = transaksi.getPelanggan();
        Kendaraan kendaraan = transaksi.getKendaraan();

        // Hitung Biaya Sewa Dasar
        double biayaDasar = transaksi.hitungBiayaDasar();

        // Logika if-else denda mobil/motor otomatis berjalan di balik layar objek kendaraan masing-masing
        double denda = kendaraan.hitungDendaKeterlambatan(hariTerlambat);

        // Hitung Diskon Memanfaatkan Rich Domain Pelanggan
        double jumlahDiskon = pelanggan.hitungDiskon(biayaDasar);

        // Hitung Total Akhir
        double totalBiayaAkhir = biayaDasar - jumlahDiskon + denda;

        // Update Status Transaksi
        transaksi.setHariKeterlambatan(hariTerlambat);
        transaksi.setTotalBayar(totalBiayaAkhir);
        transaksi.setSelesai(true); 

        // PERBAIKAN: Menggunakan Enum StatusKendaraan yang Sesuai
        kendaraan.setStatus(StatusKendaraan.TERSEDIA);

        // Trigger Transaksi & Membership Loyalty Program
        pelanggan.tambahJumlahTransaksi(); 

        transaksiRepository.simpanSemua(List.of(transaksi)); 
        pelangganService.simpanSemuaPelanggan(List.of(pelanggan)); // Fungsi ini belum ditambahkan

        return totalBiayaAkhir;
    }

    public Transaksi buatPeminjamanBaru(String nomorKtp, String platNomor, int durasiSewaHari) {

    //Memalidasi nomor KTP tidak boleh kosong
    if (nomorKtp == null || nomorKtp.trim().isEmpty()) {
        throw new IllegalArgumentException("[ERROR] Nomor KTP pelanggan tidak boleh kosong!");
    }

    //Memalidasi plat nomor tidak boleh kosong
    if (platNomor == null || platNomor.trim().isEmpty()) {
        throw new IllegalArgumentException("[ERROR] Plat nomor kendaraan tidak boleh kosong!");
    }

    //Memvalidasi durasi sewa harus lebih dari 0 hari
    if (durasiSewaHari <= 0) {
        throw new IllegalArgumentException("[ERROR] Durasi sewa harus lebih dari 0 hari!");
    }

    // Membersihkan input agar tidak ada spasi berlebih
    String nomorKtpBersih = nomorKtp.trim();
    String platNomorBersih = platNomor.trim().toUpperCase();

    //Mencari pelanggan berdasarkan nomor KTP
    Optional<Pelanggan> pelangganOpt = pelangganService.cariByNomorKtp(nomorKtpBersih);

    if (pelangganOpt.isEmpty()) {
        throw new IllegalArgumentException("[ERROR] Pelanggan belum terdaftar!");
    }

    Pelanggan pelanggan = pelangganOpt.get();

    //Mencari kendaraan berdasarkan plat nomor
    Optional<Kendaraan> kendaraanOpt = inventarisService.cariByPlatNomor(platNomorBersih);

    if (kendaraanOpt.isEmpty()) {
        throw new IllegalArgumentException("[ERROR] Kendaraan tidak ditemukan!");
    }

    Kendaraan kendaraan = kendaraanOpt.get();

    //MemVvalidasi kendaraan hanya bisa dipinjam jika statusnya TERSEDIA
    if (kendaraan.getStatus() != StatusKendaraan.TERSEDIA) {
        throw new IllegalArgumentException("[ERROR] Kendaraan sedang disewa dan tidak tersedia!");
    }

    //Membuat ID transaksi otomatis
    String idTransaksi = "TRX-" + System.currentTimeMillis();

    //Membuat objek transaksi baru
    Transaksi transaksiBaru = new Transaksi(
            idTransaksi,
            pelanggan,
            kendaraan,
            durasiSewaHari
    );

    //Update status kendaraan menjadi SEDANG_DISEWA
    kendaraan.setStatus(StatusKendaraan.SEDANG_DISEWA);

    //Simpan transaksi baru
    transaksiRepository.simpanSemua(List.of(transaksiBaru));

    //Mengembalikan transaksi baru agar bisa dipakai di StaffMenuHandler
    return transaksiBaru;
}



    
    public double hitungDendaKeterlambatan(Kendaraan kendaraan, int hariTerlambat){
        //Memvalidasi jika ada data kendaraan yang kosong 
        //Jika kendaraan tidak ada, sistem tidak menghitung denda
        if(kendaraan == null){
            throw new IllegalArgumentException("[ERROR] Data kendaraan tidak boleh kosong!");
        }

        //Memvalidasi agar hari keterlembatan tidak bernilai negatif
        if(hariTerlambat < 0){
            throw new IllegalArgumentException("[ERROR] hari keterlembatan tidak boleh negatif!");
        }

        //Menghitung denda berdasarkan jenis kendaraan
        return kendaraan.hitungDendaKeterlambatan(hariTerlambat);
    }

    //method ini digunakan untuk menghitung total biaya yang harus dibayar oleh pelanggan
    public double hitungTotalBayarSetelahDiskon(Transaksi transaksi, int hariTerlambar){
        if(transaksi == null){
            throw new IllegalArgumentException("[ERROR] Data transaksi tidak boleh kosong!");
        }
        
        //Mengambil data pelanggan dari transaksi 
        //Data pelanggan digunakan untuk menghitung diskon membership 
        Pelanggan pelanggan = transaksi.getPelanggan();

        //Mengambil data kendaraan dari transaksi 
        //Data kendaraan digunakan untuk menghitung denda keterlambatan
        Kendaraan kendaraan = transaksi.getKendaraan();

        //Menghitung biaya dasar berdasarkan harga sewa per hari dikali dengan durasi sewa
        doube biayaDasar = transaksi.hitungBiayaDasar;

        //Menghitung denda keterlambatan
        double denda = hitungDendaKeterlambatan(kendaraan, hariTerlambat);

        //Menghitung diskon berdasarkan membership pelanggan 
        double jumlahDiskon = pelanggan.hitungDiskon(biayaDasar);

        //Memanggil total biaya akhir
        return biayaDasar - jumlahDiskon + denda;




















































































































































































































































































































 public List<Transaksi> ambilSemuaTransaksi() {
        return transaksiRepository.ambilSemua(); 
    }






    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public Transaksi buatPeminjamanBaru(String nomorKtp, String platNomor, int durasiSewaHari) {
        Optional<Kendaraan> kendaraanOpt = inventarisService.cariByPlatNomor(platNomor);
        if (kendaraanOpt.isEmpty()) {
            System.out.println("[ERROR] Kendaraan tidak ditemukan!");
            return null;
        }
        Kendaraan kendaraan = kendaraanOpt.get();

        Optional<Pelanggan> pelangganOpt = pelangganService.cariByNomorKtp(nomorKtp);
        if (pelangganOpt.isEmpty()) {
            System.out.println("[ERROR] Pelanggan belum terdaftar!");
            return null;
        }
        Pelanggan pelanggan = pelangganOpt.get();

        if (kendaraan.getStatus() != StatusKendaraan.TERSEDIA) {
            System.out.println("[ERROR] Kendaraan tidak tersedia!");
            return null;
        }
        kendaraan.setStatus(StatusKendaraan.SEDANG_DISEWA);

        String idTransaksi = "TRX-" + System.currentTimeMillis();
        Transaksi transaksiBaru = new Transaksi(idTransaksi, pelanggan, kendaraan, durasiSewaHari);

        transaksiRepository.simpanSemua(List.of(transaksiBaru));
        return transaksiBaru;
    }
}
