package agnes.ua.essentialsx.Commands.Moderation;

import agnes.ua.essentialsx.Core.PermissionUtil;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;

import java.util.Date;

public class BanCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("ban")
                .requires(src -> src.hasPermission(2))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> {
                            CommandSourceStack src = ctx.getSource();
                            if (!(src.getEntity() instanceof ServerPlayer)) return 0;
                            ServerPlayer player = (ServerPlayer) src.getEntity();
                            if (!PermissionUtil.checkOrDeny(player, "essentials.ban")) return 0;

                            ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
                            UserBanList banList = src.getServer().getPlayerList().getBans();

                            banList.add(new UserBanListEntry(target.getGameProfile(), new Date(), player.getName().getString(), null, "Banned by moderator"));

                            target.connection.disconnect(Component.literal("§cВы были забанены."));
                            src.sendSuccess(() -> Component.literal("§aИгрок " + target.getName().getString() + " был забанен."), true);

                            return 1;
                        })));
    }
}
