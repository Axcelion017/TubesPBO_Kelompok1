package tubespbo.exception;

public class KendaraanSedangDisewaException extends Exception {
    public KendaraanSedangDisewaException(String platNomor) {
        super("Kendaraan " + platNomor + " masih berstatus SEDANG DISEWA, data tidak dapat dihapus!");
    }
}