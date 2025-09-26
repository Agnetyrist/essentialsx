package agnes.ua.essentialsx.Commands.misc;

import agnes.ua.essentialsx.Config.EssentialsConfig;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class HelpCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("help")
                .executes(ctx -> {
                    CommandSourceStack src = ctx.getSource();

                    src.sendSuccess(() -> Component.literal("§e=== EssentialsX Help ==="), false);

                    if (EssentialsConfig.data.enableMisc) {
                        src.sendSuccess(() -> Component.literal("§a/back §7— возврат назад"), false);
                        src.sendSuccess(() -> Component.literal("§a/fly §7— включить/выключить полёт"), false);
                        src.sendSuccess(() -> Component.literal("§a/tpa <игрок> §7— запрос телепорта"), false);
                        src.sendSuccess(() -> Component.literal("§a/msg <игрок> <сообщение> §7— отправить сообщение"), false);
                    }

                    if (EssentialsConfig.data.enableModeration) {
                        src.sendSuccess(() -> Component.literal("§a/kick <игрок> §7— кикнуть игрока"), false);
                        src.sendSuccess(() -> Component.literal("§a/ban <игрок> [время] §7— забанить игрока"), false);
                        src.sendSuccess(() -> Component.literal("§a/unban <игрок> §7— разбанить игрока"), false);
                        src.sendSuccess(() -> Component.literal("§a/mute <игрок> [время] §7— замутить игрока"), false);
                        src.sendSuccess(() -> Component.literal("§a/jail <игрок> §7— посадить игрока в тюрьму"), false);
                    }

                    if (EssentialsConfig.data.enableTeleport) {
                        src.sendSuccess(() -> Component.literal("§a/tpaccept §7— принять запрос телепорта"), false);
                        src.sendSuccess(() -> Component.literal("§a/tpdeny §7— отклонить запрос телепорта"), false);
                        src.sendSuccess(() -> Component.literal("§a/spawn §7— телепортироваться на спаун"), false);
                        src.sendSuccess(() -> Component.literal("§a/home §7— телепортироваться домой"), false);
                        src.sendSuccess(() -> Component.literal("§a/warp <название> §7— телепортироваться на варп"), false);
                    }

                    return 1;
                }));
    }
}
