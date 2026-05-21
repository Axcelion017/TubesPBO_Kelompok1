package tubespbo.presentation;

import tubespbo.domain.Role;
import tubespbo.domain.User;
import java.util.Scanner;

public class MenuHandler {
    private final Scanner scanner;

    // Constructor asli bawaan temanmu tetap dipertahankan 100% agar tidak break Main.java
    public MenuHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void tampilkanDashboard(User user) {
        if (user.getRole() == Role.ADMIN) {
            AdminMenuHandler adminMenu = new AdminMenuHandler(scanner);
            adminMenu.tampilkanDashboardAdmin(user);
        } else if (user.getRole() == Role.STAFF) {
            StaffMenuHandler staffMenu = new StaffMenuHandler(scanner);
            staffMenu.tampilkanDashboardStaff(user);
        } else if (user.getRole() == Role.OWNER) {
            OwnerMenuHandler ownerMenu = new OwnerMenuHandler(scanner);
            ownerMenu.tampilkanDashboardOwner(user);
        } else {
            System.out.println("[ERROR] Role tidak dikenali.");
        }
    }
}