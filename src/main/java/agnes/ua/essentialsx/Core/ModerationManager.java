package agnes.ua.essentialsx.Core;

import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ModerationManager {
    private static final Map<UUID, Long> mutedPlayers = new HashMap<>();
    private static final Map<UUID, Long> bannedPlayers = new HashMap<>();

    public static void mute(ServerPlayer player, long durationMillis) {
        mutedPlayers.put(player.getUUID(), System.currentTimeMillis() + durationMillis);
    }

    public static void unmute(ServerPlayer player) {
        mutedPlayers.remove(player.getUUID());
    }

    public static boolean isMuted(ServerPlayer player) {
        if (!mutedPlayers.containsKey(player.getUUID())) return false;
        if (System.currentTimeMillis() > mutedPlayers.get(player.getUUID())) {
            mutedPlayers.remove(player.getUUID());
            return false;
        }
        return true;
    }


    public static void ban(ServerPlayer player, long durationMillis) {
        bannedPlayers.put(player.getUUID(), System.currentTimeMillis() + durationMillis);
    }

    public static void unban(ServerPlayer player) {
        bannedPlayers.remove(player.getUUID());
    }

    public static boolean isBanned(ServerPlayer player) {
        if (!bannedPlayers.containsKey(player.getUUID())) return false;
        if (System.currentTimeMillis() > bannedPlayers.get(player.getUUID())) {
            bannedPlayers.remove(player.getUUID());
            return false;
        }
        return true;
    }
}
