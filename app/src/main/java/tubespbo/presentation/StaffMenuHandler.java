package tubespbo.presentation;

import java.util.Scanner;

import tubespbo.service.PelangganService;
import tubespbo.service.RentalService;

public class StaffMenuHandler {
    private Scanner scanner;
    private PelangganService pelangganService;
    private RentalService rentalService;
    
    public StaffMenuHandler(Scanner scanner, PelangganService pelangganService, RentalService rentalService) {
        this.scanner = scanner;
        this.pelangganService = pelangganService;
        this.rentalService = rentalService;
    }
    
}