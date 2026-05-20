package tubespbo.presentation;

import tubespbo.domain.Role;
import tubespbo.domain.User;
import tubespbo.repository.KendaraanRepository;
import tubespbo.repository.UserRepository;
import tubespbo.service.AuthService;
import tubespbo.service.InventarisService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepo = new UserRepository();
        AuthService authService = new AuthService(userRepo);
        KendaraanRepository kendaraanRepository = new KendaraanRepository();
        InventarisService inventarisService = new InventarisService(kendaraanRepository);

        Scanner scanner = new Scanner(System.in);
        User userLogin = null;

        System.out.println("========================================");
        System.out.println("     SELAMAT DATANG DI TUBES PBO");
        System.out.println("========================================");
        System.out.println("Akun default: admin/admin123, staff/staff123, owner/owner123");

        while (userLogin == null && !authService.isTerkunci()) {
            System.out.print("\nMasukkan Username: ");
            String username = scanner.nextLine();

            System.out.print("Masukkan Password: ");
            String password = scanner.nextLine();

            userLogin = authService.login(username, password);
        }

        if (userLogin != null) {
            System.out.println("\n[SUKSES] Login berhasil sebagai " + userLogin.getRole() + ".");

            MenuHandler menuHandler = new MenuHandler(scanner, inventarisService);
            if (userLogin.getRole() == Role.ADMIN) {
                menuHandler.tampilkanDashboardAdmin(userLogin.getUsername());
            } else {
                System.out.println("Dashboard " + userLogin.getRole() + " belum dikerjakan di Epic 2.");
            }
        } else {
            System.out.println("\n[KUNCI] Aplikasi dihentikan karena terlalu banyak kesalahan.");
        }

        scanner.close();
    }
}