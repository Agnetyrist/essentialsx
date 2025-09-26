package agnes.ua.essentialsx.Core;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

import java.util.concurrent.CompletableFuture;

public class PermissionUtil {
    private static LuckPerms luckPerms;

    public static void init() {
        try {
            luckPerms = LuckPermsProvider.get();
        } catch (Exception e) {
            luckPerms = null;
        }
    }

    public static boolean hasPermission(ServerPlayer player, String permission) {
        // Если LP нет — fallback по OP уровню
        if (luckPerms == null) {
            return player.hasPermissions(2);
        }

        // Пробуем достать юзера из кэша
        User user = luckPerms.getUserManager().getUser(player.getUUID());
        if (user != null) {
            return user.getCachedData()
                    .getPermissionData(QueryOptions.defaultContextualOptions())
                    .checkPermission(permission)
                    .asBoolean();
        }

        // Если в кэше нет — подгружаем асинхронно (права будут доступны чуть позже)
        CompletableFuture<User> futureUser = luckPerms.getUserManager().loadUser(player.getUUID());
        futureUser.thenAcceptAsync(u -> {
            // Сообщение можно закомментить, если мешает
            player.sendSystemMessage(Component.literal("§eВаши права были подгружены, попробуйте ещё раз."));
        });

        // Пока прав нет — fallback
        return player.hasPermissions(2);
    }

    public static boolean checkOrDeny(ServerPlayer player, String permission) {
        if (!hasPermission(player, permission)) {
            player.sendSystemMessage(Component.literal("§cУ вас нет прав: " + permission));
            return false;
        }
        return true;
    }
}
