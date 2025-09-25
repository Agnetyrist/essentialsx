package agnes.ua.essentialsx.Commands.misc;

import agnes.ua.essentialsx.Core.PermissionUtil;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class MsgCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("msg")
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("message", MessageArgument.message())
                                .executes(ctx -> {
                                    CommandSourceStack src = ctx.getSource();
                                    if (!(src.getEntity() instanceof ServerPlayer sender)) {
                                        src.sendFailure(Component.literal("Только игрок может использовать эту команду."));
                                        return 0;
                                    }
                                    if (!PermissionUtil.checkOrDeny(sender, "essentials.msg")) return 0;

                                    ServerPlayer target = EntityArgument.getPlayer(ctx, "player");
                                    String message = MessageArgument.getMessage(ctx, "message").getString();

                                    target.sendSystemMessage(Component.literal("[ЛС] " + sender.getName().getString() + ": " + message));
                                    sender.sendSystemMessage(Component.literal("[ЛС] Вы → " + target.getName().getString() + ": " + message));
                                    return 1;
                                }))));
    }
}
