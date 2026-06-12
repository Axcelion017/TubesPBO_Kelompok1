package tubespbo.presentation;

import java.util.Scanner;
import tubespbo.domain.User;
import tubespbo.repository.UserRepository;
import tubespbo.service.AuthService;

public class Main {
    public static void main(String[] args) {
        // 1. COMPOSITION ROOT (Setup Dependency Injection)
        // Di sinilah semua object dibuat dan dirakit sebelum aplikasi berjalan
        Scanner scanner = new Scanner(System.in);
        UserRepository userRepository = new UserRepository();

        // Perhatikan tipe datanya adalah Interface LoginService (DIP)
        AuthService authService = new AuthService(userRepository);

        // 2. MENAMPILKAN LOGIN VIEW
        // Urusan UI login diserahkan ke class LoginView (SRP)
        MenuHandler loginView = new MenuHandler(authService, scanner);
        User loggedInUser = loginView.tampilkanLayarLogin();

        // 3. MASUK KE DASHBOARD (Nantinya dibuatkan DashboardView terpisah)
        System.out.println("\n--- Masuk ke Dashboard " + loggedInUser.getRole() + " ---");

        // switch (loggedInUser.getRole()) {
        // case ADMIN: showMenuAdmin(); break;
        // case STAFF: showMenuStaff(); break;
        // case OWNER: showMenuOwner(); break;
        // }
    }
}