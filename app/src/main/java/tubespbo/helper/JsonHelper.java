package tubespbo.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHelper {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting() 
            .serializeNulls() 
            .create();

    public static Gson getGson() {
        return gson;
    }
}
