package agnes.ua.essentialsx.Commands.spawn;

import agnes.ua.essentialsx.Core.PermissionUtil;
import agnes.ua.essentialsx.Storage.SpawnStorage;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetSpawnCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("setspawn")
                .executes(ctx -> {
                    CommandSourceStack src = ctx.getSource();
                    if (!(src.getEntity() instanceof ServerPlayer player)) {
                        src.sendFailure(Component.literal("Только игрок может использовать эту команду."));
                        return 0;
                    }
                    if (!PermissionUtil.checkOrDeny(player, "essentials.setspawn")) return 0;

                    SpawnStorage.setSpawn(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot(), player.level().dimension().location().toString());
                    src.sendSuccess(() -> Component.literal("Спавн установлен."), false);
                    return 1;
                }));
    }
}
