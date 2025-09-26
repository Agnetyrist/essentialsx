package agnes.ua.essentialsx.Commands.misc;

import agnes.ua.essentialsx.Core.PermissionUtil;
import agnes.ua.essentialsx.Core.TpaManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
                            TpaManager.addRequest(player, target);

                            MutableComponent accept = Component.literal("§a[Принять]")
                                    .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept")));
                            MutableComponent deny = Component.literal(" §c[Отклонить]")
                                    .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny")));

                            target.sendSystemMessage(Component.literal("§eИгрок §6" + player.getName().getString() + " §eзапросил телепорт к вам. ")
                                    .append(accept).append(deny));

                            player.sendSystemMessage(Component.literal("§aЗапрос на телепорт отправлен игроку " + target.getName().getString() + "."));
                            return 1;
                        })));
    }
}
