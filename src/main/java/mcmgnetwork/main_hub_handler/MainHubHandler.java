package mcmgnetwork.main_hub_handler;

import org.bukkit.plugin.java.JavaPlugin;

public final class MainHubHandler extends JavaPlugin {

    @Override
    public void onEnable()
    {
        // Register event listeners/handlers
        getServer().getPluginManager().registerEvents(new PlayerGriefPrevention(), this);

        // Set world game rules/attributes
        PlayerGriefPrevention.setGameRules();
        HubProperties.setGameRules();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
