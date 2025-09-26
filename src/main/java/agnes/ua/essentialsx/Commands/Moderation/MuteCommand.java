package agnes.ua.essentialsx.Commands.Moderation;

import agnes.ua.essentialsx.Core.ModerationManager;
import agnes.ua.essentialsx.Core.PermissionUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.concurrent.TimeUnit;

public class MuteCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mute")
                .requires(src -> src.hasPermission(2))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> execute(ctx.getSource(), EntityArgument.getPlayer(ctx, "target"), -1)))
                .then(Commands.argument("target", EntityArgument.player())
                        .then(Commands.argument("time", StringArgumentType.string())
                                .executes(ctx -> {
                                    ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
                                    String timeStr = StringArgumentType.getString(ctx, "time");
                                    long duration = parseTime(timeStr);
                                    return execute(ctx.getSource(), target, duration);
                                }))));
    }

    private static int execute(CommandSourceStack src, ServerPlayer target, long durationMillis) {
        if (!(src.getEntity() instanceof ServerPlayer player)) return 0;
        if (!PermissionUtil.checkOrDeny(player, "essentials.mute")) return 0;

        if (durationMillis > 0) {
            ModerationManager.mute(target, durationMillis);
            target.sendSystemMessage(Component.literal("§cВы были замучены на " + durationMillis / 1000 + " секунд."));
            src.sendSuccess(() -> Component.literal("§eИгрок " + target.getName().getString() + " замучен на " + durationMillis / 1000 + " секунд."), true);
        } else {
            ModerationManager.mute(target, Long.MAX_VALUE);
            target.sendSystemMessage(Component.literal("§cВы были замучены навсегда."));
            src.sendSuccess(() -> Component.literal("§eИгрок " + target.getName().getString() + " замучен навсегда."), true);
        }
        return 1;
    }

    private static long parseTime(String time) {
        try {
            if (time.endsWith("m")) {
                return TimeUnit.MINUTES.toMillis(Long.parseLong(time.replace("m", "")));
            } else if (time.endsWith("s")) {
                return TimeUnit.SECONDS.toMillis(Long.parseLong(time.replace("s", "")));
            } else {
                return Long.parseLong(time) * 1000; // секунды по умолчанию
            }
        } catch (Exception e) {
            return -1;
        }
    }
}
