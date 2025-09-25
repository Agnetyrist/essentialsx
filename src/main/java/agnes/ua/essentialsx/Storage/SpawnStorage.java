package agnes.ua.essentialsx.Storage;

import agnes.ua.essentialsx.Core.StorageManager;
import net.minecraft.resources.ResourceLocation;

public class SpawnStorage {
    private static SpawnEntry spawn = null;

    public static SpawnEntry loadSpawn() {
        spawn = StorageManager.loadFile(StorageManager.spawnFile(), SpawnEntry.class, null);
        return spawn;
    }

    public static void saveSpawn() {
        StorageManager.saveFile(StorageManager.spawnFile(), spawn);
    }

    public static void setSpawn(double x, double y, double z, float yaw, float pitch, String dimension) {
        spawn = new SpawnEntry(x, y, z, yaw, pitch, dimension);
        saveSpawn();
    }

    public static SpawnEntry getSpawn() {
        return spawn;
    }

    public static class SpawnEntry {
        public double x, y, z;
        public float yaw, pitch;
        public String dimension;

        public SpawnEntry() {}

        public SpawnEntry(double x, double y, double z, float yaw, float pitch, String dimension) {
            this.x = x; this.y = y; this.z = z;
            this.yaw = yaw; this.pitch = pitch;
            this.dimension = dimension;
        }
    }
}
