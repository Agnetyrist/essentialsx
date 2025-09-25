package agnes.ua.essentialsx.Commands.spawn;

import agnes.ua.essentialsx.Core.PermissionUtil;
import agnes.ua.essentialsx.Storage.SpawnStorage;
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

public class SpawnCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("spawn")
                .executes(ctx -> {
                    CommandSourceStack src = ctx.getSource();
                    if (!(src.getEntity() instanceof ServerPlayer player)) {
                        src.sendFailure(Component.literal("Только игрок может использовать эту команду."));
                        return 0;
                    }
                    if (!PermissionUtil.checkOrDeny(player, "essentials.spawn")) return 0;

                    var spawn = SpawnStorage.getSpawn();
                    if (spawn == null) {
                        src.sendFailure(Component.literal("Спавн не установлен."));
                        return 0;
                    }
                    ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(spawn.dimension));
                    ServerLevel level = player.getServer().getLevel(key);
                    if (level == null) {
                        src.sendFailure(Component.literal("Мир спавна не найден: " + spawn.dimension));
                        return 0;
                    }
                    player.teleportTo(level, spawn.x + 0.5, spawn.y, spawn.z + 0.5, spawn.yaw, spawn.pitch);
                    src.sendSuccess(() -> Component.literal("Телепорт на спавн..."), false);
                    return 1;
                }));
    }
}
