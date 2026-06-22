package tubespbo.presentation.command.staff;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import tubespbo.domain.Kendaraan;
import tubespbo.domain.StatusKendaraan;
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
        System.out.println("\n===================================================================================");
        System.out.println(" DAFTAR KENDARAAN");
        System.out.println("===================================================================================");

        List<Kendaraan> daftar = rentalService.ambilKendaraanTersedia();

        if (daftar.isEmpty()) {
            System.out.println("Tidak ada kendaraan tersedia saat ini.");
        } else {
            System.out.printf("| %-10s | %-5s | %-10s | %-10s | %-20s | %-13s |\n",
                    "Plat Nomor", "Jenis", "Harga/Hari", "Merek", "Info Tambahan", "Status");
            System.out.println("-----------------------------------------------------------------------------------");
            
            NumberFormat format = NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID"));

            for (Kendaraan k : daftar) {
                String status = k.getStatus() == StatusKendaraan.TERSEDIA ? "TERSEDIA" : "SEDANG DISEWA";
                String hargaFormat = "Rp " + format.format(k.getHargaSewaPerHari());
                
                System.out.printf("| %-10s | %-5s | %-10s | %-10s | %-20s | %-13s |\n", 
                        k.getPlatNomor(), 
                        k.getJenis(),
                        hargaFormat,
                        k.getMerek(),
                        k.getInfoTambahan(),
                        status);
            }
            System.out.println("-----------------------------------------------------------------------------------");
        }

        tekanEnter();
    }

    private void tekanEnter() {
        System.out.println("\nTekan ENTER untuk kembali...");
        scanner.nextLine();
    }
}
