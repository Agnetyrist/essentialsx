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
        public boolean enableModeration = true;
        public boolean enableTeleport = true;
        public boolean enableHelp = true;

        public Map<String, String> permissions = new HashMap<>();

        public ConfigData() {
            // Home
            permissions.put("essentials.home.set", "essentials.home.set");
            permissions.put("essentials.home.use", "essentials.home.use");
            permissions.put("essentials.home.del", "essentials.home.del");

            // Warps
            permissions.put("essentials.warp.set", "essentials.warp.set");
            permissions.put("essentials.warp.use", "essentials.warp.use");
            permissions.put("essentials.warp.del", "essentials.warp.del");
            permissions.put("essentials.warp.list", "essentials.warp.list");

            // Spawn
            permissions.put("essentials.setspawn", "essentials.setspawn");
            permissions.put("essentials.spawn", "essentials.spawn");

            // Misc
            permissions.put("essentials.back", "essentials.back");
            permissions.put("essentials.fly", "essentials.fly");
            permissions.put("essentials.tpa", "essentials.tpa");
            permissions.put("essentials.msg", "essentials.msg");

            // Moderation
            permissions.put("essentials.kick", "essentials.kick");
            permissions.put("essentials.ban", "essentials.ban");
            permissions.put("essentials.unban", "essentials.unban");
            permissions.put("essentials.mute", "essentials.mute");
            permissions.put("essentials.jail", "essentials.jail");

            // Teleport
            permissions.put("essentials.tpaccept", "essentials.tpaccept");
            permissions.put("essentials.tpdeny", "essentials.tpdeny");

            // Help
            permissions.put("essentials.help", "essentials.help");
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
