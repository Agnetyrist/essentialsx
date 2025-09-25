package agnes.ua.essentialsx.Commands.home;

import agnes.ua.essentialsx.Core.PermissionUtil;
import agnes.ua.essentialsx.Storage.HomesStorage;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class HomeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home")
                .then(Commands.argument("name", net.minecraft.commands.arguments.MessageArgument.message())
                        .executes(ctx -> {
                            CommandSourceStack src = ctx.getSource();
                            if (!(src.getEntity() instanceof ServerPlayer player)) {
                                src.sendFailure(Component.literal("Только игрок может использовать эту команду."));
                                return 0;
                            }
                            if (!PermissionUtil.checkOrDeny(player, "essentials.home.use")) return 0;

                            String name = net.minecraft.commands.arguments.MessageArgument.getMessage(ctx, "name").getString().trim();
                            var homeOpt = HomesStorage.getHome(player.getUUID(), name);
                            if (homeOpt.isEmpty()) {
                                src.sendFailure(Component.literal("Дом не найден: " + name));
                                return 0;
                            }
                            var home = homeOpt.get();
                            ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(home.dimension));
                            ServerLevel level = player.getServer().getLevel(key);
                            if (level == null) {
                                src.sendFailure(Component.literal("Мир дома не найден: " + home.dimension));
                                return 0;
                            }
                            player.teleportTo(level, home.x + 0.5, home.y, home.z + 0.5, home.yaw, home.pitch);
                            src.sendSuccess(() -> Component.literal("Телепорт на дом '" + name + "'..."), false);
                            return 1;
                        })));
    }
}
