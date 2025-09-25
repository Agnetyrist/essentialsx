package agnes.ua.essentialsx.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerEvents {
    private static final Map<UUID, PositionRecord> lastDeaths = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        lastDeaths.put(player.getUUID(), new PositionRecord(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot(), player.level().dimension().location().toString()));
    }

    public static PositionRecord getLastDeath(UUID uuid) {
        return lastDeaths.get(uuid);
    }

    public static class PositionRecord {
        public double x, y, z;
        public float yaw, pitch;
        public String dimension;

        public PositionRecord(double x, double y, double z, float yaw, float pitch, String dimension) {
            this.x = x; this.y = y; this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.dimension = dimension;
        }
    }
}
