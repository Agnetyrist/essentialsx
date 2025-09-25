package agnes.ua.essentialsx.Commands.warp;

import agnes.ua.essentialsx.Core.PermissionUtil;
import agnes.ua.essentialsx.Storage.WarpsStorage;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class WarpsListCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("warps")
                .executes(ctx -> {
                    CommandSourceStack src = ctx.getSource();
                    if (!PermissionUtil.hasPermission(src.getPlayerOrException(), "essentials.warp.list")) {
                        src.sendFailure(Component.literal("§cУ вас нет прав: essentials.warp.list"));
                        return 0;
                    }

                    StringBuilder sb = new StringBuilder("Варпы: ");
                    WarpsStorage.getWarps().keySet().forEach(name -> sb.append(name).append(", "));
                    src.sendSuccess(() -> Component.literal(sb.toString()), false);
                    return 1;
                }));
    }
}
