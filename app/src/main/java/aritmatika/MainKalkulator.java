package aritmatika;

import java.util.Scanner;

public class MainKalkulator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== Program Kalkulator Pembagian ===");

        try {
            System.out.print("Masukkan angka pembilang: ");
            int pembilang = input.nextInt();

            System.out.print("Masukkan angka penyebut: ");
            int penyebut = input.nextInt();

            int hasil = pembilang / penyebut;

            System.out.println("Hasil pembagian: " + hasil);
        } catch (ArithmeticException e) {
            System.out.println("Terjadi kesalahan!");
            System.out.println("Penyebut tidak boleh bernilai 0.");
        } finally {
            System.out.println("Program selesai dijalankan.");
            input.close();
        }
    }
}