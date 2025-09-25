package agnes.ua.essentialsx.Config;

import agnes.ua.essentialsx.Core.StorageManager;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class EssentialsConfig {
    public static ConfigData data;
    private static final Path CONFIG_PATH = StorageManager.getConfigDir().resolve("config.json");

    public static class ConfigData {
        public boolean enableHomes = true;
        public boolean enableWarps = true;
        public boolean enableSpawn = true;
        public boolean enableMisc = true;

        public Map<String, String> permissions = new HashMap<>();

        public ConfigData() {
            permissions.put("essentials.home.set", "essentials.home.set");
            permissions.put("essentials.home.use", "essentials.home.use");
            permissions.put("essentials.home.del", "essentials.home.del");
            permissions.put("essentials.warp.set", "essentials.warp.set");
            permissions.put("essentials.warp.use", "essentials.warp.use");
            permissions.put("essentials.warp.del", "essentials.warp.del");
            permissions.put("essentials.warp.list", "essentials.warp.list");
            permissions.put("essentials.setspawn", "essentials.setspawn");
            permissions.put("essentials.spawn", "essentials.spawn");
            permissions.put("essentials.back", "essentials.back");
            permissions.put("essentials.fly", "essentials.fly");
            permissions.put("essentials.tpa", "essentials.tpa");
            permissions.put("essentials.msg", "essentials.msg");
        }
    }

    public static void loadConfig() {
        if (!CONFIG_PATH.toFile().exists()) {
            data = new ConfigData();
            saveConfig();
            System.out.println("[EssentialsX] Генерация нового config.json");
        } else {
            data = StorageManager.loadFile(CONFIG_PATH, ConfigData.class, new ConfigData());
        }
    }

    public static void saveConfig() {
        StorageManager.saveFile(CONFIG_PATH, data);
    }
}
