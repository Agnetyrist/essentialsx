package agnes.ua.essentialsx.Commands.home;

import agnes.ua.essentialsx.Core.PermissionUtil;
import agnes.ua.essentialsx.Storage.HomesStorage;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class DelHomeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("delhome")
                .then(Commands.argument("name", net.minecraft.commands.arguments.MessageArgument.message())
                        .executes(ctx -> {
                            CommandSourceStack src = ctx.getSource();
                            if (!(src.getEntity() instanceof net.minecraft.server.level.ServerPlayer player)) {
                                src.sendFailure(Component.literal("Только игрок может использовать эту команду."));
                                return 0;
                            }
                            if (!PermissionUtil.checkOrDeny(player, "essentials.home.del")) return 0;

                            String name = net.minecraft.commands.arguments.MessageArgument.getMessage(ctx, "name").getString().trim();
                            if (HomesStorage.removeHome(player.getUUID(), name)) {
                                src.sendSuccess(() -> Component.literal("Дом '" + name + "' удалён."), false);
                                return 1;
                            } else {
                                src.sendFailure(Component.literal("Дом не найден: " + name));
                                return 0;
                            }
                        })));
    }
}
