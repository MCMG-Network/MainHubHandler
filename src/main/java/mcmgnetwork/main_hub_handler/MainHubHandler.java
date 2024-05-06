package mcmgnetwork.main_hub_handler;

import mcmgnetwork.main_hub_handler.handlers.LobbySwitchRequestHandler;
import mcmgnetwork.main_hub_handler.handlers.PlayerGriefPreventionHandler;
import mcmgnetwork.main_hub_handler.handlers.VoidHandler;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainHubHandler extends JavaPlugin {

    @Override
    public void onEnable()
    {
        // Register event listeners/handlers
        PluginManager pluginManager = getServer().getPluginManager();
        LobbySwitcher.SetJavaPlugin(this);
        pluginManager.registerEvents(new LobbySwitchRequestHandler(), this);
        pluginManager.registerEvents(new PlayerGriefPreventionHandler(), this);
        pluginManager.registerEvents(new VoidHandler(), this);

        // Set world game rules/attributes
        PlayerGriefPreventionHandler.setGameRules();
        HubProperties.setGameRules();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
