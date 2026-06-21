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
        Optional<Pelanggan> pelangganLama = pelangganRepository.cariByNomorKtp(nomorKtp);

        if (pelangganLama.isPresent()) {
            throw new IllegalArgumentException(
                    "Pelanggan " + namaLengkap + " gagal Didaftarkan (KTP: " + nomorKtp + ") sudah terdaftar!");
        }

        Pelanggan pelangganBaru = new Pelanggan(nomorKtp, namaLengkap, nomorTelepon, 0);

        // Hanya simpan pelanggan baru ke database
        pelangganRepository.simpanSemua(List.of(pelangganBaru));

        return pelangganBaru;
    }

    public Optional<Pelanggan> cariByNomorKtp(String nomorKtp) {
        return pelangganRepository.cariByNomorKtp(nomorKtp);
    }

    public void simpanSemua(List<Pelanggan> pelangganList) {
        pelangganRepository.simpanSemua(pelangganList);
    }
}
