package tubespbo.exception;

public class PlatNomorDuplikatException extends Exception {
    public PlatNomorDuplikatException(String platNomor) {
        super("Plat nomor " + platNomor + " sudah terdaftar di sistem.");
    }
}