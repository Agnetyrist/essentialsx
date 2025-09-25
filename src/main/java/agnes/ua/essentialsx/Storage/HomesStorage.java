package agnes.ua.essentialsx.Storage;

import agnes.ua.essentialsx.Core.StorageManager;
import net.minecraft.core.BlockPos;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class HomesStorage {
    private static final Map<UUID, Map<String, HomeEntry>> homes = new HashMap<>();

    public static Map<UUID, Map<String, HomeEntry>> loadAllHomes() {
        return StorageManager.loadFile(StorageManager.warpsFile(), new com.google.gson.reflect.TypeToken<Map<UUID, Map<String, HomeEntry>>>() {}.getType(), Collections.emptyMap());
    }

    public static void saveAllHomes() {
        StorageManager.saveFile(StorageManager.warpsFile(), homes);
    }

    public static void setHome(UUID playerUUID, String name, double x, double y, double z, float yaw, float pitch, String dimension) {
        homes.computeIfAbsent(playerUUID, k -> new HashMap<>())
                .put(name.toLowerCase(), new HomeEntry(name, x, y, z, yaw, pitch, dimension));
        saveAllHomes();
    }

    public static Optional<HomeEntry> getHome(UUID playerUUID, String name) {
        if (homes.containsKey(playerUUID)) {
            return Optional.ofNullable(homes.get(playerUUID).get(name.toLowerCase()));
        }
        return Optional.empty();
    }

    public static boolean removeHome(UUID playerUUID, String name) {
        if (homes.containsKey(playerUUID)) {
            if (homes.get(playerUUID).remove(name.toLowerCase()) != null) {
                saveAllHomes();
                return true;
            }
        }
        return false;
    }

    public static Map<String, HomeEntry> getHomes(UUID playerUUID) {
        return homes.getOrDefault(playerUUID, Collections.emptyMap());
    }

    public static class HomeEntry {
        public String name;
        public double x, y, z;
        public float yaw, pitch;
        public String dimension;

        public HomeEntry() {}

        public HomeEntry(String name, double x, double y, double z, float yaw, float pitch, String dimension) {
            this.name = name;
            this.x = x; this.y = y; this.z = z;
            this.yaw = yaw; this.pitch = pitch;
            this.dimension = dimension;
        }
    }
}
