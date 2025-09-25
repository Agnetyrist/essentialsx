package agnes.ua.essentialsx.Commands.home;

import agnes.ua.essentialsx.Core.PermissionUtil;
import agnes.ua.essentialsx.Storage.HomesStorage;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class SetHomeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sethome")
                .then(Commands.argument("name", net.minecraft.commands.arguments.MessageArgument.message())
                        .executes(ctx -> {
                            CommandSourceStack src = ctx.getSource();
                            if (!(src.getEntity() instanceof ServerPlayer player)) {
                                src.sendFailure(Component.literal("Только игрок может использовать эту команду."));
                                return 0;
                            }
                            if (!PermissionUtil.checkOrDeny(player, "essentials.home.set")) return 0;

                            String name = net.minecraft.commands.arguments.MessageArgument.getMessage(ctx, "name").getString().trim();
                            HomesStorage.setHome(player.getUUID(), name, player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot(), player.level().dimension().location().toString());
                            src.sendSuccess(() -> Component.literal("Дом '" + name + "' установлен."), false);
                            return 1;
                        })));
    }
}
