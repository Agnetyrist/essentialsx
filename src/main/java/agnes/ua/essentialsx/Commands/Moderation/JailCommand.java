package agnes.ua.essentialsx.Commands.Moderation;

import agnes.ua.essentialsx.Core.PermissionUtil;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class JailCommand {
    private static final double JAIL_X = 0;
    private static final double JAIL_Y = 64;
    private static final double JAIL_Z = 0;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("jail")
                .requires(src -> src.hasPermission(2))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> {
                            CommandSourceStack src = ctx.getSource();
                            if (!(src.getEntity() instanceof ServerPlayer player)) return 0;
                            if (!PermissionUtil.checkOrDeny(player, "essentials.jail")) return 0;

                            ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
                            ServerLevel level = (ServerLevel) target.getCommandSenderWorld();

                            target.teleportTo(level, JAIL_X, JAIL_Y, JAIL_Z, target.getYRot(), target.getXRot());
                            target.sendSystemMessage(Component.literal("§cВы были посажены в тюрьму."));
                            src.sendSuccess(() -> Component.literal("§eИгрок " + target.getName().getString() + " был отправлен в тюрьму."), true);

                            return 1;
                        })));
    }
}
