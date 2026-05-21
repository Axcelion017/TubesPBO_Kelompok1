package tubespbo.repository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import tubespbo.domain.Kendaraan;
import tubespbo.domain.Mobil;
import tubespbo.domain.Motor;
import tubespbo.helper.JsonHelper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KendaraanRepository {
    private static final String FILE_PATH = "data/kendaraan.json";
    private final Gson gson = JsonHelper.getGson();

    public KendaraanRepository() {
        File folder = new File("data");
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public List<Kendaraan> ambilSemua() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            JsonArray array = gson.fromJson(reader, JsonArray.class);
            List<Kendaraan> daftarKendaraan = new ArrayList<>();

            if (array == null) {
                return daftarKendaraan;
            }

            for (JsonElement element : array) {
                JsonObject object = element.getAsJsonObject();
                String jenis = object.has("jenis") ? object.get("jenis").getAsString() : "";

                if ("Mobil".equalsIgnoreCase(jenis)) {
                    daftarKendaraan.add(gson.fromJson(object, Mobil.class));
                } else if ("Motor".equalsIgnoreCase(jenis)) {
                    daftarKendaraan.add(gson.fromJson(object, Motor.class));
                }
            }

            return daftarKendaraan;
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal membaca data kendaraan: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void simpanSemua(List<Kendaraan> daftarKendaraan) {
        JsonArray array = new JsonArray();

        for (Kendaraan kendaraan : daftarKendaraan) {
            JsonObject object = gson.toJsonTree(kendaraan).getAsJsonObject();
            object.addProperty("jenis", kendaraan.getJenis());
            array.add(object);
        }

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(array, writer);
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal menyimpan data kendaraan: " + e.getMessage());
        }
    }

    public Optional<Kendaraan> cariByPlatNomor(String platNomor) {
        String target = normalisasiPlat(platNomor);

        for (Kendaraan kendaraan : ambilSemua()) {
            if (normalisasiPlat(kendaraan.getPlatNomor()).equals(target)) {
                return Optional.of(kendaraan);
            }
        }

        return Optional.empty();
    }

    public boolean hapusByPlatNomor(String platNomor) {
        String target = normalisasiPlat(platNomor);
        List<Kendaraan> daftarKendaraan = ambilSemua();
        boolean terhapus = daftarKendaraan.removeIf(
                kendaraan -> normalisasiPlat(kendaraan.getPlatNomor()).equals(target)
        );

        if (terhapus) {
            simpanSemua(daftarKendaraan);
        }

        return terhapus;
    }

    private String normalisasiPlat(String platNomor) {
        if (platNomor == null) {
            return "";
        }
        return platNomor.trim().replaceAll("\\s+", " ").toUpperCase();
    }
}