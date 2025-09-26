package agnes.ua.essentialsx.Commands.misc;

import agnes.ua.essentialsx.Utils.ChatUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class ShowMenuCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("showmenu")
                .then(Commands.argument("player", StringArgumentType.word())
                        .executes(ctx -> {
                            String targetName = StringArgumentType.getString(ctx, "player");
                            CommandSourceStack src = ctx.getSource();

                            if (!(src.getEntity() instanceof ServerPlayer viewer)) {
                                src.sendFailure(net.minecraft.network.chat.Component.literal("Только игрок может использовать эту команду."));
                                return 0;
                            }

                            viewer.sendSystemMessage(ChatUtil.createPlayerMenu(viewer, targetName));
                            return 1;
                        })));
    }
}
