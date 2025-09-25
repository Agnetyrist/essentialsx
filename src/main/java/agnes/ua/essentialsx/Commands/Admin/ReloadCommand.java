package agnes.ua.essentialsx.Commands.Admin;

import agnes.ua.essentialsx.Config.EssentialsConfig;
import agnes.ua.essentialsx.Storage.HomesStorage;
import agnes.ua.essentialsx.Storage.WarpsStorage;
import agnes.ua.essentialsx.Storage.SpawnStorage;
import agnes.ua.essentialsx.Core.PermissionUtil;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ReloadCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("essentials")
                .then(Commands.literal("reload")
                        .executes(ctx -> {
                            CommandSourceStack src = ctx.getSource();
                            if (!(src.getEntity() instanceof ServerPlayer player)) {
                                src.sendFailure(Component.literal("Только игрок может использовать эту команду."));
                                return 0;
                            }
                            if (!PermissionUtil.checkOrDeny(player, "essentials.reload")) return 0;

                            EssentialsConfig.loadConfig();
                            SpawnStorage.loadSpawn();

                            src.sendSuccess(() -> Component.literal("EssentialsX конфиг и данные перезагружены."), true);
                            return 1;
                        })));
    }
}
