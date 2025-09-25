package agnes.ua.essentialsx.Storage;

import agnes.ua.essentialsx.Core.StorageManager;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WarpsStorage {
    private static final Map<String, WarpEntry> warps = new HashMap<>();

    public static Map<String, WarpEntry> loadAllWarps() {
        return StorageManager.loadFile(StorageManager.warpsFile(), new com.google.gson.reflect.TypeToken<Map<String, WarpEntry>>() {}.getType(), Collections.emptyMap());
    }

    public static void saveAllWarps() {
        StorageManager.saveFile(StorageManager.warpsFile(), warps);
    }

    public static void setWarp(String name, double x, double y, double z, float yaw, float pitch, String dimension) {
        warps.put(name.toLowerCase(), new WarpEntry(name, x, y, z, yaw, pitch, dimension));
        saveAllWarps();
    }

    public static Optional<WarpEntry> getWarp(String name) {
        return Optional.ofNullable(warps.get(name.toLowerCase()));
    }

    public static boolean removeWarp(String name) {
        if (warps.remove(name.toLowerCase()) != null) {
            saveAllWarps();
            return true;
        }
        return false;
    }

    public static Map<String, WarpEntry> getWarps() {
        return Collections.unmodifiableMap(warps);
    }

    public static class WarpEntry {
        public String name;
        public double x, y, z;
        public float yaw, pitch;
        public String dimension;

        public WarpEntry() {}

        public WarpEntry(String name, double x, double y, double z, float yaw, float pitch, String dimension) {
            this.name = name;
            this.x = x; this.y = y; this.z = z;
            this.yaw = yaw; this.pitch = pitch;
            this.dimension = dimension;
        }
    }
}
