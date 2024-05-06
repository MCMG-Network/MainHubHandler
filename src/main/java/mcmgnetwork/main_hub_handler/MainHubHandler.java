package mcmgnetwork.main_hub_handler;

import mcmgnetwork.main_hub_handler.handlers.LobbySwitchRequestHandler;
import mcmgnetwork.main_hub_handler.handlers.PlayerGriefPreventionHandler;
import mcmgnetwork.main_hub_handler.handlers.VoidHandler;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Description: <p>
 *  The main class for the Main Hub Handler plugin. It initializes listener and handler classes
 *  and sets the server world game rules and properties.
 *
 *  <p>Author(s): Miles Bovero, Lawrence Ponce, Vee Sutton
 *  <p>Date Created: 5/4/24
 */
public final class MainHubHandler extends JavaPlugin {

    /**
     * Registers event handlers and manages game attributes
     */
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

    /**
     * Shuts down events handlers and removes game attributes?
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}