package tubespbo.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHelper {
    // Menggunakan singleton-style agar tidak membuat objek Gson berulang-ulang di memori
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting() // Membuat format JSON rapi (ada indentasi/spasi)
            .serializeNulls()    // Tetap menulis atribut ke JSON meskipun nilainya null
            .create();

    public static Gson getGson() {
        return gson;
    }
}