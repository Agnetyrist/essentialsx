package agnes.ua.essentialsx.Listeners;

import agnes.ua.essentialsx.Core.ModerationManager;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerJoinListener {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer)) return;
        ServerPlayer player = (ServerPlayer) event.getEntity();

        // Проверка бана
        if (ModerationManager.isBanned(player)) {
            player.connection.disconnect(Component.literal("§cВы забанены на сервере."));
            return;
        }

        // Проверка мута
        if (ModerationManager.isMuted(player)) {
            player.sendSystemMessage(Component.literal("§cВы замучены и не можете писать в чат."));
        }
    }
}
