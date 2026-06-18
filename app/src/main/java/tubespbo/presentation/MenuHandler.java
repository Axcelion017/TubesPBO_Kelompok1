package tubespbo.presentation;

import java.util.Scanner;
import tubespbo.domain.User;
import tubespbo.exception.LoginGagalException;
import tubespbo.service.AuthService; // <-- Bergantung pada Interface (DIP)

// Class ini HANYA bertanggung jawab untuk urusan layar login (SRP)
public class MenuHandler{
    private final AuthService authService;
    private final Scanner scanner;

    // Dependency Injection
    public MenuHandler(AuthService authService, Scanner scanner) {
        this.authService = authService;
        this.scanner = scanner;
    }

    public User tampilkanLayarLogin() {
        System.out.println("========================================");
        System.out.println("SELAMAT DATANG DI SISTEM RENTAL KENDARAAN");
        System.out.println("========================================");

        User loggedInUser = null;
        int maxPercobaan = 3;
        int percobaan = 0;

        while (percobaan < maxPercobaan && loggedInUser == null) {
            System.out.print("Username : > ");
            String username = scanner.nextLine();

            System.out.print("Password : > ");
            String password = scanner.nextLine();

            try {
                loggedInUser = authService.login(username, password);

                System.out.println("\n[SUKSES] Login berhasil sebagai " + loggedInUser.getRole() + ".");
                System.out.println("Tekan ENTER untuk masuk ke Dashboard...");
                scanner.nextLine();

                return loggedInUser; // Langsung kembalikan user jika berhasil

            } catch (LoginGagalException e) {
                percobaan++;
                System.out.println("[GAGAL] " + e.getMessage());
                if (percobaan < maxPercobaan) {
                    System.out.println("Sisa percobaan: " + (maxPercobaan - percobaan) + "\n");
                }
            }
        }

        System.out.println("Anda telah gagal login 3 kali. Sistem otomatis keluar.");
        System.exit(0);
        return null;
    }
}
