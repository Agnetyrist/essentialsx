package agnes.ua.essentialsx.Core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

public class StorageManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = FMLPaths.CONFIGDIR.get().resolve("essentialsx");

    public static Path warpsFile() {
        return CONFIG_DIR.resolve("warps.json");
    }

    public static Path spawnFile() {
        return CONFIG_DIR.resolve("spawn.json");
    }

    public static void ensureConfigDir() {
        try {
            if (!Files.exists(CONFIG_DIR)) {
                Files.createDirectories(CONFIG_DIR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T loadFile(Path file, Type typeOfT, T defaultValue) {
        ensureConfigDir();
        try {
            if (!Files.exists(file)) {
                saveFile(file, defaultValue);
                return defaultValue;
            }
            String json = Files.readString(file);
            return GSON.fromJson(json, typeOfT);
        } catch (IOException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static void saveFile(Path file, Object data) {
        ensureConfigDir();
        try {
            String json = GSON.toJson(data);
            Files.writeString(file, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
