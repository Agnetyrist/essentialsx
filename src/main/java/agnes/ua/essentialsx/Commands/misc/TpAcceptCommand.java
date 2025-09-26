package agnes.ua.essentialsx.Commands.misc;

import agnes.ua.essentialsx.Core.PermissionUtil;
import agnes.ua.essentialsx.Core.TpaManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class TpAcceptCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tpaccept")
                .executes(ctx -> {
                    CommandSourceStack src = ctx.getSource();
                    if (!(src.getEntity() instanceof ServerPlayer target)) {
                        src.sendFailure(Component.literal("Только игрок может использовать эту команду."));
                        return 0;
                    }

                    if (!PermissionUtil.checkOrDeny(target, "essentials.tpaccept")) return 0;

                    if (!TpaManager.hasRequest(target)) {
                        target.sendSystemMessage(Component.literal("§cУ вас нет активных запросов на телепорт."));
                        return 0;
                    }

                    ServerPlayer requester = TpaManager.getRequester(target);
                    if (requester == null) {
                        target.sendSystemMessage(Component.literal("§cИгрок, запросивший телепорт, вышел с сервера."));
                        TpaManager.removeRequest(target);
                        return 0;
                    }

                    // Телепортируем
                    TpaManager.teleportTo(requester, target);

                    requester.sendSystemMessage(Component.literal("§aВаш запрос был принят. Телепортируемся к " + target.getName().getString() + "!"));
                    target.sendSystemMessage(Component.literal("§aВы приняли запрос на телепорт от " + requester.getName().getString() + "."));

                    TpaManager.removeRequest(target);
                    return 1;
                }));
    }
}
