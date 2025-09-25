package agnes.ua.essentialsx.Commands.warp;

import agnes.ua.essentialsx.Core.PermissionUtil;
import agnes.ua.essentialsx.Storage.WarpsStorage;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class WarpCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("warp")
                .then(Commands.argument("name", net.minecraft.commands.arguments.MessageArgument.message())
                        .executes(ctx -> {
                            CommandSourceStack src = ctx.getSource();
                            if (!(src.getEntity() instanceof ServerPlayer player)) {
                                src.sendFailure(Component.literal("Только игрок может использовать эту команду."));
                                return 0;
                            }
                            if (!PermissionUtil.checkOrDeny(player, "essentials.warp.use")) return 0;

                            String name = net.minecraft.commands.arguments.MessageArgument.getMessage(ctx, "name").getString().trim();
                            var warpOpt = WarpsStorage.getWarp(name);
                            if (warpOpt.isEmpty()) {
                                src.sendFailure(Component.literal("Варп не найден: " + name));
                                return 0;
                            }
                            var warp = warpOpt.get();
                            ResourceKey<ServerLevel> key = ResourceKey.create(ServerLevel.RESOURCE_KEY, new ResourceLocation(warp.dimension));
                            ServerLevel level = player.getServer().getLevel(key);
                            if (level == null) {
                                src.sendFailure(Component.literal("Мир варпа не найден: " + warp.dimension));
                                return 0;
                            }
                            player.teleportTo(level, warp.x + 0.5, warp.y, warp.z + 0.5, warp.yaw, warp.pitch);
                            src.sendSuccess(() -> Component.literal("Телепорт на варп '" + name + "'..."), false);
                            return 1;
                        })));
    }
}
