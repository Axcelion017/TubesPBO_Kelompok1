package tubespbo.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tubespbo.domain.User;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final String filepath = "app/data/user.json"; 
    private final Gson gson = new Gson();

    // Membaca seluruh data user dari file JSON menggunakan Gson
    private List<User> getAllUsers() {
        try (Reader reader = new FileReader(filepath)) {
            
            // Membaca sekumpulan data pada JSON menjadi objek
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            
            List<User> users = gson.fromJson(reader, userListType);
            
            // Jika file json kosong, agar tidak return null
            return users != null ? users : new ArrayList<>();
            
        } catch (IOException e) {
            System.err.println("Gagal membaca file JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public User cariByUsername(String username) {
        List<User> users = getAllUsers();
        
        for (User user : users) {
            // User.getusername belum di define di User.java
            if (User.getUsername().equalsIgnoreCase(username)) { 
                return user;
            }
        }
        return null;
    }
}