package tubespbo.presentation.command.staff;

import java.util.List;
import java.util.Scanner;
import tubespbo.domain.Kendaraan;
import tubespbo.presentation.command.Command;
import tubespbo.service.RentalService;

public class CekKendaraanTersediaCommand implements Command {
    private Scanner scanner;
    private RentalService rentalService;

    public CekKendaraanTersediaCommand(Scanner scanner, RentalService rentalService) {
        this.scanner = scanner;
        this.rentalService = rentalService;
    }

    @Override
    public void execute() {
        System.out.println("\n======================================================");
        System.out.println("DAFTAR KENDARAAN TERSEDIA");
        System.out.println("======================================================");

        List<Kendaraan> daftar = rentalService.ambilKendaraanTersedia();

        if (daftar.isEmpty()) {
            System.out.println("Tidak ada kendaraan tersedia saat ini.");
        } else {
            System.out.printf("%-15s %-10s %-15s %-15s\n", "Plat", "Jenis", "Harga/Hari", "Status");
            for (Kendaraan k : daftar) {
                System.out.printf("%-15s %-10s Rp %-12d %-15s\n", k.getPlatNomor(), k.getJenis(),
                        k.getHargaSewaPerHari(), k.getStatus());
            }
        }

        tekanEnter();
    }

    private void tekanEnter() {
        System.out.println("\nTekan ENTER untuk kembali...");
        scanner.nextLine();
    }
}
