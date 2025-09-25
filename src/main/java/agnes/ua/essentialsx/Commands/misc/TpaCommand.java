package agnes.ua.essentialsx.Commands.misc;

import agnes.ua.essentialsx.Core.PermissionUtil;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class TpaCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tpa")
                .then(Commands.argument("player", EntityArgument.player())
                        .executes(ctx -> {
                            CommandSourceStack src = ctx.getSource();
                            if (!(src.getEntity() instanceof ServerPlayer player)) {
                                src.sendFailure(Component.literal("Только игрок может использовать эту команду."));
                                return 0;
                            }
                            if (!PermissionUtil.checkOrDeny(player, "essentials.tpa")) return 0;

                            ServerPlayer target = EntityArgument.getPlayer(ctx, "player");
                            target.sendSystemMessage(Component.literal(player.getName().getString() + " запрашивает телепорт к вам. Используйте /tpaccept или /tpdeny."));
                            player.sendSystemMessage(Component.literal("Запрос на телепорт отправлен " + target.getName().getString() + "."));
                            return 1;
                        })));
    }
}
