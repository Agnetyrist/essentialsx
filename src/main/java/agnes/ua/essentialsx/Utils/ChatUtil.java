package agnes.ua.essentialsx.Utils;

import agnes.ua.essentialsx.Core.PermissionUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;

public class ChatUtil {

    public static Component createPlayerMenu(ServerPlayer viewer, String targetName) {
        Component menu = Component.literal("§e=== Меню действий для §a" + targetName + " §e===\n");

        if (PermissionUtil.hasPermission(viewer, "essentials.msg")) {
            ((net.minecraft.network.chat.MutableComponent) menu).append(Component.literal(" §a[Сообщение] ")
                    .withStyle(Style.EMPTY
                            .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + targetName + " "))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Component.literal("§7Отправить сообщение игроку")))));
        }

        if (PermissionUtil.hasPermission(viewer, "essentials.tpa")) {
            ((net.minecraft.network.chat.MutableComponent) menu).append(Component.literal(" §a[Tpa] ")
                    .withStyle(Style.EMPTY
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa " + targetName))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Component.literal("§7Запросить телепорт к игроку")))));
        }

        if (PermissionUtil.hasPermission(viewer, "essentials.kick")) {
            ((net.minecraft.network.chat.MutableComponent) menu).append(Component.literal(" §c[Kick] ")
                    .withStyle(Style.EMPTY
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kick " + targetName))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Component.literal("§7Кикнуть игрока")))));
        }

        if (PermissionUtil.hasPermission(viewer, "essentials.ban")) {
            ((net.minecraft.network.chat.MutableComponent) menu).append(Component.literal(" §c[Ban] ")
                    .withStyle(Style.EMPTY
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ban " + targetName))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Component.literal("§7Забанить игрока")))));
        }

        if (PermissionUtil.hasPermission(viewer, "essentials.mute")) {
            ((net.minecraft.network.chat.MutableComponent) menu).append(Component.literal(" §c[Mute] ")
                    .withStyle(Style.EMPTY
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mute " + targetName))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Component.literal("§7Замутить игрока")))));
        }

        if (PermissionUtil.hasPermission(viewer, "essentials.jail")) {
            ((net.minecraft.network.chat.MutableComponent) menu).append(Component.literal(" §c[Jail] ")
                    .withStyle(Style.EMPTY
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/jail " + targetName))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Component.literal("§7Посадить игрока в тюрьму")))));
        }

        return menu;
    }
}
