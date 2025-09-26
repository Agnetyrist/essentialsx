package agnes.ua.essentialsx.Commands.misc;

import agnes.ua.essentialsx.Core.PermissionUtil;
import agnes.ua.essentialsx.Core.TpaManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class TpDenyCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tpdeny")
                .executes(ctx -> {
                    if (!(ctx.getSource().getEntity() instanceof ServerPlayer target)) {
                        ctx.getSource().sendFailure(Component.literal("Только игрок может использовать эту команду."));
                        return 0;
                    }
                    if (!PermissionUtil.checkOrDeny(target, "essentials.tpdeny")) return 0;

                    if (!TpaManager.hasRequest(target)) {
                        target.sendSystemMessage(Component.literal("§cУ вас нет активных запросов на телепорт."));
                        return 0;
                    }

                    ServerPlayer requester = TpaManager.getRequester(target);
                    if (requester != null) {
                        requester.sendSystemMessage(Component.literal("§cВаш запрос на телепорт был отклонён."));
                    }

                    target.sendSystemMessage(Component.literal("§eВы отклонили запрос на телепорт."));
                    TpaManager.removeRequest(target);
                    return 1;
                }));
    }
}
