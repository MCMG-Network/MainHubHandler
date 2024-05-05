package mcmgnetwork.main_hub_handler;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;

public class HubProperties
{
    // Main hub world spawn settings
    private static final int worldSpawnX = 7;
    private static final int worldSpawnY = 21;
    private static final int worldSpawnZ = 22;
    private static final float worldSpawnViewAngle = -45;

    /**
     * Sets the main hub server's game rules and properties to the preferred state.
     */
    public static void setGameRules()
    {
        try
        {
            // Retrieve and store the main hub server's world
            World world = Bukkit.getWorld("world");

            // Set world spawn
            world.setSpawnLocation(worldSpawnX, worldSpawnY, worldSpawnZ, worldSpawnViewAngle);

            // Set difficulty
            world.setDifficulty(Difficulty.PEACEFUL);

            // Set game rules
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
            world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
            world.setGameRule(GameRule.DO_LIMITED_CRAFTING, true);
            world.setGameRule(GameRule.DO_MOB_LOOT, false);
            world.setGameRule(GameRule.DROWNING_DAMAGE, false);
            world.setGameRule(GameRule.FALL_DAMAGE, false);
            world.setGameRule(GameRule.FIRE_DAMAGE, false);
            world.setGameRule(GameRule.FREEZE_DAMAGE, false);
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
            world.setGameRule(GameRule.SNOW_ACCUMULATION_HEIGHT, 0);
            world.setGameRule(GameRule.SPAWN_RADIUS, 0);

        } catch (NullPointerException ex)
        { Bukkit.getLogger().severe("ERROR: Could not update main hub game rules because no world file was found!"); }
    }
}