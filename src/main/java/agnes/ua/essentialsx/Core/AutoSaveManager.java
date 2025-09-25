package agnes.ua.essentialsx.Core;

import agnes.ua.essentialsx.Storage.HomesStorage;
import agnes.ua.essentialsx.Storage.WarpsStorage;
import agnes.ua.essentialsx.Storage.SpawnStorage;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AutoSaveManager {
    private static int tickCounter = 0;
    private static final int SAVE_INTERVAL_TICKS = 20 * 60 * 5; // каждые 5 минут

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        tickCounter++;
        if (tickCounter >= SAVE_INTERVAL_TICKS) {
            saveAll();
            tickCounter = 0;
        }
    }

    public static void saveAll() {
        HomesStorage.saveAllHomes();
        WarpsStorage.saveAllWarps();
        SpawnStorage.saveSpawn();
        System.out.println("[EssentialsX] Все данные сохранены.");
    }
}
