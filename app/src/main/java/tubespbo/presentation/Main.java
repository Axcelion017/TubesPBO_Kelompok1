package tubespbo.presentation;

import tubespbo.domain.User;
import tubespbo.repository.UserRepository;
import tubespbo.service.AuthService;

import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        // 1. Inisialisasi Repository dan Service
        UserRepository userRepo = new UserRepository();
        AuthService authService = new AuthService(userRepo);
        
        Scanner scanner = new Scanner(System.in);
        User userLogin = null;

        System.out.println("=== SISTEM TESTING AUTHENTICATION ===");
        System.out.println("Database default otomatis terbuat di data/user.json");
        System.out.println("Akun default: admin/admin123, staff/staff123, owner/owner123");
        System.out.println("=====================================");

        // 2. Loop login selama belum sukses dan sistem belum dikunci
        while (userLogin == null && !authService.isTerkunci()) {
            System.out.print("\nMasukkan Username: ");
            String username = scanner.nextLine();
            
            System.out.print("Masukkan Password: ");
            String password = scanner.nextLine();

            // Panggil service auth yang kamu buat tadi
            userLogin = authService.login(username, password);
        }

        // 3. Cek hasil akhir setelah keluar dari loop
        if (userLogin != null) {
            System.out.println("\n[SUKSES] Selamat Datang, " + userLogin.getUsername() + "!");
            System.out.println("Hak Akses Anda (Role): " + userLogin.getRole());
        } else {
            System.out.println("\n[KUNCI] Aplikasi dihentikan karena terlalu banyak kesalahan.");
        }

        scanner.close();
    }
}