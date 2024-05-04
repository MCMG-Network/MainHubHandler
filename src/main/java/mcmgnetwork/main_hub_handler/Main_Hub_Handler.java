package mcmgnetwork.main_hub_handler;

import mcmgnetwork.main_hub_handler.listeners.PlayerGriefListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main_Hub_Handler extends JavaPlugin {

    @Override
    public void onEnable()
    {
        // Register event listeners/handlers
        getServer().getPluginManager().registerEvents(new PlayerGriefListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
