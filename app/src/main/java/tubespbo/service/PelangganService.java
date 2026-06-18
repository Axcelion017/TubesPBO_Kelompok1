package tubespbo.service;

import java.util.List;
import java.util.Optional;

import tubespbo.domain.Pelanggan;
import tubespbo.repository.PelangganRepository;

public class PelangganService {
    private PelangganRepository pelangganRepository;

    public PelangganService(PelangganRepository pelangganRepository) {
        this.pelangganRepository = pelangganRepository;
    }

    public Pelanggan daftarPelanggan(String nomorKtp, String namaLengkap, String nomorTelepon) {

        // Cari dalam database kemudian disimpan dalam fungsi optional yang sama dengan pengembalian method dalam pelangganRepository
        Optional<Pelanggan> pelangganLama = pelangganRepository.cariByNomorKtp(nomorKtp);

        if (pelangganLama.isPresent()) {
            throw new IllegalArgumentException("[GAGAL] Pelanggan "+ namaLengkap +" gagal Didaftarkan (KTP: " + nomorKtp + ") sudah terdaftar!");
        }

        // Pembuatan objek Pelanggan baru menggunakan parameter yang dikirim. Pelanggan baru selalu mulai dari 0 transaksi.
        Pelanggan pelangganBaru = new Pelanggan(nomorKtp, namaLengkap, nomorTelepon, 0);

        // Ambil semua pelanggan saat ini untuk digabungkan dengan yang baru, karena method di PelangganRepository menerima List (simpanSemua)
        List<Pelanggan> semuaPelanggan = pelangganRepository.ambilSemua();

        semuaPelanggan.add(pelangganBaru);

        return pelangganBaru;
    }


}
