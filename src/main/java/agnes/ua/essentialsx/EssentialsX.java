package agnes.ua.essentialsx;

import agnes.ua.essentialsx.Commands.home.DelHomeCommand;
import agnes.ua.essentialsx.Commands.home.HomeCommand;
import agnes.ua.essentialsx.Config.EssentialsConfig;
import agnes.ua.essentialsx.Commands.home.SetHomeCommand;
import agnes.ua.essentialsx.Commands.warp.DelWarpCommand;
import agnes.ua.essentialsx.Commands.warp.SetWarpCommand;
import agnes.ua.essentialsx.Commands.warp.WarpCommand;
import agnes.ua.essentialsx.Commands.warp.WarpsListCommand;
import agnes.ua.essentialsx.Commands.spawn.SetSpawnCommand;
import agnes.ua.essentialsx.Commands.spawn.SpawnCommand;
import agnes.ua.essentialsx.Commands.misc.BackCommand;
import agnes.ua.essentialsx.Commands.misc.FlyCommand;
import agnes.ua.essentialsx.Commands.misc.TpaCommand;
import agnes.ua.essentialsx.Commands.misc.MsgCommand;
import agnes.ua.essentialsx.Core.LuckPermsIntegration;
import agnes.ua.essentialsx.Core.PermissionUtil;
import agnes.ua.essentialsx.events.PlayerEvents;
import net.minecraft.commands.Commands;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(EssentialsX.MOD_ID)
public class EssentialsX {
    public static final String MOD_ID = "essentialsx";
    private static final Logger LOGGER = LogUtils.getLogger();

    public EssentialsX() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());

        PermissionUtil.init();
        EssentialsConfig.loadConfig();

        LOGGER.info("EssentialsX загружен!");

        if (LuckPermsIntegration.isLuckPermsLoaded()) {
            System.out.println("[EssentialsX] LuckPerms найден, интеграция активирована.");
        }

    }

    @net.minecraftforge.eventbus.api.SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event) {
        LOGGER.info("Регистрация команд EssentialsX...");

        if (EssentialsConfig.data.enableHomes) {
            SetHomeCommand.register(event.getDispatcher());
            HomeCommand.register(event.getDispatcher());
            DelHomeCommand.register(event.getDispatcher());
        }

        if (EssentialsConfig.data.enableWarps) {
            SetWarpCommand.register(event.getDispatcher());
            WarpCommand.register(event.getDispatcher());
            DelWarpCommand.register(event.getDispatcher());
            WarpsListCommand.register(event.getDispatcher());
        }

        if (EssentialsConfig.data.enableSpawn) {
            SetSpawnCommand.register(event.getDispatcher());
            SpawnCommand.register(event.getDispatcher());
        }

        if (EssentialsConfig.data.enableMisc) {
            BackCommand.register(event.getDispatcher());
            FlyCommand.register(event.getDispatcher());
            TpaCommand.register(event.getDispatcher());
            MsgCommand.register(event.getDispatcher());
        }


        LOGGER.info("Все команды EssentialsX зарегистрированы!");
    }
}
