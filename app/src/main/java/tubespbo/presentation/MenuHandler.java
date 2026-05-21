package tubespbo.presentation;

import tubespbo.domain.User;
import java.util.Scanner;

public class MenuHandler {
    private final Scanner scanner;

    public MenuHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void tampilkanDashboard(User user) {
        switch (user.getRole()) {
            case ADMIN:
                // Delegasikan langsung ke file khusus admin
                new AdminMenuHandler(scanner).tampilkanMenu(user);
                break;
            case STAFF:
                // new StaffMenuHandler(scanner).tampilkanMenu(user);
                break;
            case OWNER:
                // new OwnerMenuHandler(scanner).tampilkanMenu(user);
                break;
            default:
                System.out.println("[ERROR] Role tidak dikenali.");
        }
    }
}