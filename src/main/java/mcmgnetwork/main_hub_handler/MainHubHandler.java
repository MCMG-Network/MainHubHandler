package mcmgnetwork.main_hub_handler;

import lombok.Getter;
import mcmgnetwork.main_hub_handler.listeners.LobbyTransferRequestHandler;
import mcmgnetwork.main_hub_handler.listeners.PlayerGriefPreventionHandler;
import mcmgnetwork.main_hub_handler.listeners.VoidHandler;
import mcmgnetwork.main_hub_handler.protocols.ChannelNames;
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

    @Getter
    private static MainHubHandler plugin;

    /**
     * Registers event handlers and manages game attributes
     */
    @Override
    public void onEnable()
    {
        plugin = this;

        // Register incoming plugin channels
        getServer().getMessenger().registerIncomingPluginChannel(this, ChannelNames.MCMG, new LobbyTransferHandler());
        // Register outgoing plugin channels
        getServer().getMessenger().registerOutgoingPluginChannel(this, ChannelNames.MCMG);
        getServer().getMessenger().registerOutgoingPluginChannel(this, ChannelNames.PROXY);

        // Register event listeners/handlers
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new LobbyTransferRequestHandler(), this);
        pluginManager.registerEvents(new PlayerGriefPreventionHandler(), this);
        pluginManager.registerEvents(new VoidHandler(), this);

        // Set world game rules/attributes
        PlayerGriefPreventionHandler.setGameRules();
        HubPropertiesHandler.setGameRules();
    }

    /**
     * Shuts down events handlers and removes game attributes?
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}