package agnes.ua.essentialsx.Core;

import agnes.ua.essentialsx.Config.EssentialsConfig;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PermissionNode;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.ModList;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.CompletableFuture;

public class LuckPermsIntegration {
    private static LuckPerms luckPerms;

    public static void init(LuckPerms lpApi) {
        luckPerms = lpApi;
    }

    public static void applyPermissions(ServerPlayer player) {
        if (luckPerms == null) return;

        User user = luckPerms.getUserManager().getUser(player.getUUID());
        if (user == null) return;

        EssentialsConfig.data.permissions.forEach((key, value) -> {
            PermissionNode node = PermissionNode.builder(value).build();
            user.data().add(node);
        });

        @NonNull CompletableFuture<Void> future = luckPerms.getUserManager().saveUser(user);
        future.thenRun(() -> System.out.println("[EssentialsX] Права для " + player.getName().getString() + " применены."));
    }

    public static boolean isLuckPermsLoaded() {
        return ModList.get().isLoaded("luckperms");
    }
}
