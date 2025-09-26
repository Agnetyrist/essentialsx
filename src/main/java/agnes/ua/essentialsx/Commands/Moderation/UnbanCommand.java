package agnes.ua.essentialsx.Commands.Moderation;

import agnes.ua.essentialsx.Core.ModerationManager;
import agnes.ua.essentialsx.Core.PermissionUtil;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

public class UnbanCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("unban")
                .requires(src -> src.hasPermission(2))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> {
                            CommandSourceStack src = ctx.getSource();
                            if (!(src.getEntity() instanceof ServerPlayer player)) return 0;
                            if (!PermissionUtil.checkOrDeny(player, "essentials.unban")) return 0;

                            ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
                            ModerationManager.unban(target);
                            src.sendSuccess(() -> Component.literal("§aИгрок " + target.getName().getString() + " был разбанен."), true);
                            return 1;
                        })));
    }
}
