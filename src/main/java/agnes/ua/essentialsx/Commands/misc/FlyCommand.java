package agnes.ua.essentialsx.Commands.misc;

import agnes.ua.essentialsx.Core.PermissionUtil;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class FlyCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fly")
                .executes(ctx -> {
                    CommandSourceStack src = ctx.getSource();
                    if (!(src.getEntity() instanceof ServerPlayer player)) {
                        src.sendFailure(Component.literal("Только игрок может использовать эту команду."));
                        return 0;
                    }
                    if (!PermissionUtil.checkOrDeny(player, "essentials.fly")) return 0;

                    boolean newState = !player.getAbilities().mayfly;
                    player.getAbilities().mayfly = newState;
                    player.getAbilities().flying = newState;
                    player.onUpdateAbilities();
                    src.sendSuccess(() -> Component.literal("Режим полёта " + (newState ? "включён" : "выключён")), false);
                    return 1;
                }));
    }
}
