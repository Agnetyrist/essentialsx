package agnes.ua.essentialsx.Listeners;

import agnes.ua.essentialsx.Commands.Moderation.MuteCommand;
import agnes.ua.essentialsx.Core.ModerationManager;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MuteListener {

    @SubscribeEvent
    public static void onPlayerChat(ServerChatEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer)) return;
        ServerPlayer player = (ServerPlayer) event.getPlayer();

        if (ModerationManager.isMuted(player))
        {
            player.sendSystemMessage(Component.literal("§cВы замучены и не можете писать в чат."));
            event.setCanceled(true);
        }
    }
}
