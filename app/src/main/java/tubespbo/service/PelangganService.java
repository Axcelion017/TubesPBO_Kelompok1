package tubespbo.service;

import java.util.*;

import tubespbo.domain.Pelanggan;
import tubespbo.repository.PelangganRepository;

public class PelangganService {

    private PelangganRepository pelangganRepository;

    public Optional<Pelanggan> cariByNomorKtp(String nomorKtp) {
        List<Pelanggan> daftarPelanggan = pelangganRepository.getAllPelanggan();
        for (Pelanggan pelanggan : daftarPelanggan) {
            if (pelanggan.getKtp().equals(nomorKtp)) {
                return Optional.of(pelanggan); // Mengembalikan objek di dalam bungkusan Optional
            }
        }
        return Optional.empty(); // Mengembalikan bungkus kosong jika tidak ditemukan
    }

}
