package agnes.ua.essentialsx.Core;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

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
        if (luckPerms == null) {
            // fallback — OP права
            return player.hasPermissions(2);
        }
        User user = luckPerms.getPlayerAdapter(ServerPlayer.class).getUser(player);
        return user.getCachedData().getPermissionData(QueryOptions.defaultContextualOptions()).checkPermission(permission).asBoolean();
    }

    public static boolean checkOrDeny(ServerPlayer player, String permission) {
        if (!hasPermission(player, permission)) {
            player.sendSystemMessage(Component.literal("§cУ вас нет прав: " + permission));
            return false;
        }
        return true;
    }
}
