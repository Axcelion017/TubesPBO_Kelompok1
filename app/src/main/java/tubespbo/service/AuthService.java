package tubespbo.service;

import tubespbo.domain.User;
import tubespbo.repository.UserRepository;

import java.util.List;

public class AuthService {
    private final UserRepository userRepo;
    private int percobaanGagal = 0;
    private final int MAKS_PERCOBAAN = 3;

    // Constructor menerima UserRepository (Dependency Injection)
    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Fungsi untuk memvalidasi login user
     * @param username input dari UI
     * @param password input dari UI
     * @return Objek User jika berhasil, null jika gagal atau diblokir
     */
    public User login(String username, String password) {
        // 1. Cek apakah batas percobaan sudah habis
        if (percobaanGagal >= MAKS_PERCOBAAN) {
            System.out.println("\n[PERINGATAN] Akses ditolak! Anda telah salah memasukkan password sebanyak " + MAKS_PERCOBAAN + " kali.");
            return null;
        }

        // 2. Ambil semua data user dari database JSON via Repository
        List<User> daftarUser = userRepo.ambilSemua();

        // 3. Cari user yang cocok
        for (User user : daftarUser) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                // Login sukses: reset counter percobaan gagal
                percobaanGagal = 0;
                return user;
            }
        }

        // 4. Jika tidak ada yang cocok, naikkan counter kesalahan
        percobaanGagal++;
        int sisaKesempatan = MAKS_PERCOBAAN - percobaanGagal;
        
        System.out.println("\n[GAGAL] Username atau Password salah!");
        if (sisaKesempatan > 0) {
            System.out.println("Sisa kesempatan mencoba: " + sisaKesempatan + " kali lagi.");
        } else {
            System.out.println("Sistem dikunci karena batas percobaan habis.");
        }
        
        return null;
    }

    // Getter untuk mengecek status apakah sistem sedang terkunci atau tidak
    public boolean isTerkunci() {
        return percobaanGagal >= MAKS_PERCOBAAN;
    }
}