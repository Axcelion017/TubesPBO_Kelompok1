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
    public double hitungTotalBayarSetelahDiskon(Transaksi transaksi, int hariTerlambat){
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
        double biayaDasar = transaksi.hitungBiayaDasar;

        //Menghitung denda keterlambatan
        double denda = hitungDendaKeterlambatan(kendaraan, hariTerlambat);

        //Menghitung diskon berdasarkan membership pelanggan 
        double jumlahDiskon = pelanggan.hitungDiskon(biayaDasar);

        //Memanggil total biaya akhir
        return biayaDasar - jumlahDiskon + denda;
    } 

    public List<Kendaraan> ambilKendaraanTersedia() {
    // Mengambil semua data kendaraan dari InventarisService.
    // Data ini masih berisi semua kendaraan, baik TERSEDIA maupun SEDANG_DISEWA.
    List<Kendaraan> semuaKendaraan = inventarisService.ambilSemuaKendaraan();

    // Membuat list kosong untuk menampung kendaraan yang boleh ditampilkan.
    // Nantinya hanya kendaraan dengan status TERSEDIA yang dimasukkan ke list ini.
    List<Kendaraan> kendaraanTersedia = new java.util.ArrayList<>();

    // Melakukan pengecekan satu per satu terhadap semua kendaraan.
    for (Kendaraan kendaraan : semuaKendaraan) {

        // Jika status kendaraan TERSEDIA,
        // maka kendaraan boleh ditampilkan di menu peminjaman.
        if (kendaraan.getStatus() == StatusKendaraan.TERSEDIA) {
            kendaraanTersedia.add(kendaraan);
        }

        //Jika status kendaraan SEDANG_DISEWA,
        //maka kendaraan tidak dimasukkan ke list kendaraanTersedia.
        //Jadi kendaraan tersebut tidak akan muncul di menu peminjaman.
    }

    //Mengembalikan daftar kendaraan yang hanya berisi kendaraan TERSEDIA.
    return kendaraanTersedia;
}


}
