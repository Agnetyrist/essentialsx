package agnes.ua.essentialsx.Core;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class TpaManager {
    private static final Map<UUID, UUID> requests = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void addRequest(ServerPlayer requester, ServerPlayer target) {
        requests.put(target.getUUID(), requester.getUUID());

        // автоудаление через 60 секунд
        scheduler.schedule(() -> {
            if (requests.containsKey(target.getUUID())) {
                requests.remove(target.getUUID());
                requester.sendSystemMessage(net.minecraft.network.chat.Component.literal("§cВаш запрос к " + target.getName().getString() + " истёк."));
                target.sendSystemMessage(net.minecraft.network.chat.Component.literal("§eЗапрос на телепорт от " + requester.getName().getString() + " истёк."));
            }
        }, 60, TimeUnit.SECONDS);
    }

    public static ServerPlayer getRequester(ServerPlayer target) {
        UUID requesterId = requests.get(target.getUUID());
        if (requesterId == null) return null;
        return target.getServer().getPlayerList().getPlayer(requesterId);
    }

    public static void removeRequest(ServerPlayer target) {
        requests.remove(target.getUUID());
    }

    public static boolean hasRequest(ServerPlayer target) {
        return requests.containsKey(target.getUUID());
    }

    // Телепорт игрока правильно
    public static void teleportTo(ServerPlayer requester, ServerPlayer target) {
        MinecraftServer server = target.getServer();
        if (server == null) return;

        ResourceKey<Level> targetWorld = target.level().dimension();
        requester.teleportTo(server.getLevel(targetWorld), target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
    }
}
