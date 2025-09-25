package agnes.ua.essentialsx.Commands.warp;

import agnes.ua.essentialsx.Core.PermissionUtil;
import agnes.ua.essentialsx.Storage.WarpsStorage;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetWarpCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("setwarp")
                .then(Commands.argument("name", net.minecraft.commands.arguments.MessageArgument.message())
                        .executes(ctx -> {
                            CommandSourceStack src = ctx.getSource();
                            if (!(src.getEntity() instanceof ServerPlayer player)) {
                                src.sendFailure(Component.literal("Только игрок может использовать эту команду."));
                                return 0;
                            }
                            if (!PermissionUtil.checkOrDeny(player, "essentials.warp.set")) return 0;

                            String name = net.minecraft.commands.arguments.MessageArgument.getMessage(ctx, "name").getString().trim();
                            WarpsStorage.setWarp(name, player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot(), player.level().dimension().location().toString());
                            src.sendSuccess(() -> Component.literal("Варп '" + name + "' установлен."), false);
                            return 1;
                        })));
    }
}
