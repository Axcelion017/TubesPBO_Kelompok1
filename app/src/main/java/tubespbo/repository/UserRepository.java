package tubespbo.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tubespbo.domain.Role;
import tubespbo.domain.User;
import tubespbo.helper.JsonHelper;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final String FILE_PATH = "data/user.json";
    private final Gson gson = JsonHelper.getGson();

    public UserRepository() {
        // 1. Buat folder 'data' otomatis jika belum ada
        File folder = new File("data");
        if (!folder.exists()) {
            folder.mkdir();
        }
        // 2. Buat data akun default jika file user.json belum ada
        initDefaultUsers();
    }

    // Fungsi untuk membaca semua user dari file JSON (On-Demand)
    public List<User> ambilSemua() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            List<User> daftarUser = gson.fromJson(reader, listType);
            return daftarUser != null ? daftarUser : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal membaca data user: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Fungsi untuk menyimpan seluruh daftar user kembali ke file JSON
    public void simpanSemua(List<User> daftarUser) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(daftarUser, writer);
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal menyimpan data user: " + e.getMessage());
        }
    }

    // Mengisi akun dummy awal jika file database JSON masih kosong
    private void initDefaultUsers() {
        File file = new File(FILE_PATH);
        if (!file.exists() || ambilSemua().isEmpty()) {
            List<User> defaultUsers = new ArrayList<>();
            // Format: Username, Password, Role (Enum)
            defaultUsers.add(new User("admin", "admin123", Role.ADMIN));
            defaultUsers.add(new User("staff", "staff123", Role.STAFF));
            defaultUsers.add(new User("owner", "owner123", Role.OWNER));
            
            simpanSemua(defaultUsers);
        }
    }
}