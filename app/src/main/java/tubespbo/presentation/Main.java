package tubespbo.presentation;

import java.util.Scanner;
import tubespbo.domain.User;
import tubespbo.repository.KendaraanRepository;
import tubespbo.repository.PelangganRepository;
import tubespbo.repository.TransaksiRepository;
import tubespbo.repository.UserRepository;
import tubespbo.service.AuthService;
import tubespbo.service.InventarisService;
import tubespbo.service.PelangganService;
import tubespbo.service.RentalService;

public class Main {
    public static void main(String[] args) {
        // 1. COMPOSITION ROOT (Setup Dependency Injection)
        Scanner scanner = new Scanner(System.in);

        // Repositories
        UserRepository userRepository = new UserRepository();
        KendaraanRepository kendaraanRepository = new KendaraanRepository();
        PelangganRepository pelangganRepository = new PelangganRepository();
        TransaksiRepository transaksiRepository = new TransaksiRepository();

        // Services
        AuthService authService = new AuthService(userRepository);
        InventarisService inventarisService = new InventarisService(kendaraanRepository);
        PelangganService pelangganService = new PelangganService(pelangganRepository);
        RentalService rentalService = new RentalService(transaksiRepository, pelangganService, kendaraanRepository);

        // Handlers
        MenuHandler loginView = new MenuHandler(authService, scanner);
        AdminMenuHandler adminMenuHandler = new AdminMenuHandler(scanner, inventarisService);
        StaffMenuHandler staffMenuHandler = new StaffMenuHandler(scanner, pelangganService, rentalService);
        OwnerMenuHandler ownerMenuHandler = new OwnerMenuHandler(scanner, rentalService);

        // 2. MENAMPILKAN LOGIN VIEW
        User loggedInUser = loginView.tampilkanLayarLogin();

        // 3. MASUK KE DASHBOARD
        System.out.println("\n--- Masuk ke Dashboard " + loggedInUser.getRole() + " ---");

        switch (loggedInUser.getRole()) {
            case ADMIN:
                adminMenuHandler.tampilkanDashboardAdmin(loggedInUser.getUsername());
                break;
            case STAFF:
                staffMenuHandler.tampilkanDashboardStaff(loggedInUser.getUsername());
                break;
            case OWNER:
                ownerMenuHandler.tampilkanDashboardOwner(loggedInUser.getUsername());
                break;
        }
    }
}