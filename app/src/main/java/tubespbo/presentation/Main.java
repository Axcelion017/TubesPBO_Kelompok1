package tubespbo.presentation;

import tubespbo.domain.User;
import tubespbo.repository.UserRepository;
import tubespbo.service.AuthService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepo = new UserRepository();
        AuthService authService = new AuthService(userRepo);
        Scanner scanner = new Scanner(System.in);

        boolean aplikasiBerjalan = true;

        while (aplikasiBerjalan) {
            User userLogin = tampilkanLogin(scanner, authService);

            if (userLogin != null) {
                MenuHandler menuHandler = new MenuHandler(scanner);
                menuHandler.tampilkanDashboard(userLogin);
            } else {
                aplikasiBerjalan = false;
            }

            if (authService.isTerkunci()) {
                aplikasiBerjalan = false;
            }
        }

        System.out.println("\nTerima kasih telah menggunakan aplikasi.");
        scanner.close();
    }

    private static User tampilkanLogin(Scanner scanner, AuthService authService) {
        User userLogin = null;

        System.out.println("========================================");
        System.out.println("     SELAMAT DATANG DI KELOMPOK 1");
        System.out.println("========================================");
        System.out.println("Akun default:");
        System.out.println("- admin/admin123");
        System.out.println("- staff/staff123");
        System.out.println("- owner/owner123");

        while (userLogin == null && !authService.isTerkunci()) {
            System.out.print("\nMasukkan Username: ");
            String username = scanner.nextLine();

            System.out.print("Masukkan Password: ");
            String password = scanner.nextLine();

            userLogin = authService.login(username, password);
        }

        if (userLogin != null) {
            System.out.println("\n[SUKSES] Login berhasil sebagai " + userLogin.getRole() + ".");
            tekanEnter(scanner);
        } else {
            System.out.println("\n[KUNCI] Aplikasi dihentikan karena terlalu banyak kesalahan.");
        }

        return userLogin;
    }

    private static void tekanEnter(Scanner scanner) {
        System.out.print("Tekan ENTER untuk masuk ke dashboard...");
        scanner.nextLine();
    }
}