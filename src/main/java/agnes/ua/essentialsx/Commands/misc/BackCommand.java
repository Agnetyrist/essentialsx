package agnes.ua.essentialsx.Commands.misc;

import agnes.ua.essentialsx.Core.PermissionUtil;
import agnes.ua.essentialsx.events.PlayerEvents;
import agnes.ua.essentialsx.events.PlayerEvents.PositionRecord;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class BackCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("back")
                .executes(ctx -> {
                    CommandSourceStack src = ctx.getSource();
                    if (!(src.getEntity() instanceof ServerPlayer player)) {
                        src.sendFailure(Component.literal("Только игрок может использовать эту команду."));
                        return 0;
                    }
                    if (!PermissionUtil.checkOrDeny(player, "essentials.back")) return 0;

                    PositionRecord record = PlayerEvents.getLastDeath(player.getUUID());
                    if (record == null) {
                        src.sendFailure(Component.literal("Нет последнего места смерти."));
                        return 0;
                    }
                    ResourceKey<ServerLevel> key = ResourceKey.create(ServerLevel.RESOURCE_KEY, new ResourceLocation(record.dimension));
                    ServerLevel level = player.getServer().getLevel(key);
                    if (level == null) {
                        src.sendFailure(Component.literal("Мир последнего места смерти не найден."));
                        return 0;
                    }
                    player.teleportTo(level, record.x, record.y, record.z, record.yaw, record.pitch);
                    src.sendSuccess(() -> Component.literal("Вы возвращены к последнему месту смерти."), false);
                    return 1;
                }));
    }
}
