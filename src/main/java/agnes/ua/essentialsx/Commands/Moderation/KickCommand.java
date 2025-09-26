package agnes.ua.essentialsx.Commands.Moderation;

import agnes.ua.essentialsx.Core.PermissionUtil;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class KickCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("kick")
                .requires(src -> src.hasPermission(2))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> {
                            CommandSourceStack src = ctx.getSource();
                            if (!(src.getEntity() instanceof ServerPlayer player)) return 0;
                            if (!PermissionUtil.checkOrDeny(player, "essentials.kick")) return 0;

                            ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
                            target.connection.disconnect(Component.literal("§cВы были кикнуты с сервера."));
                            src.sendSuccess(() -> Component.literal("§aИгрок " + target.getName().getString() + " был кикнут."), true);
                            return 1;
                        })));
    }
}
