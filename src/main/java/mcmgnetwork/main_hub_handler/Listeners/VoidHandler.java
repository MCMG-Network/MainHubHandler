package mcmgnetwork.main_hub_handler.Listeners;

import mcmgnetwork.main_hub_handler.HubProperties;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VoidHandler implements Listener
{

    public static final int voidHeight = 0;
    public static final int levitationDuration = 20;
    public static final int levitationAmplifier = 22;

    /**
     * Gives any player below the defined void height the levitation effect.
     * @param e The event specifying player movement and related info.
     */
    @EventHandler
    public void onEnterVoid(PlayerMoveEvent e)
    {
        Player player = e.getPlayer();

        // Give the player levitation if they fall into the void
        if (e.getTo().getY() < voidHeight)
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, levitationDuration, levitationAmplifier));

        try
        {
            // Initialize world spawn location
            Location spawn = new Location(Bukkit.getWorld("world"), HubProperties.worldSpawnX, HubProperties.worldSpawnY, HubProperties.worldSpawnZ);

            // If players fall too far into the void, tp them back to the world spawn

            // If players venture too far in the X direction, tp them back to the world spawn

            // If players venture too far in the Z direction, tp them back to the world spawn
        }catch (NullPointerException ex)
        { Bukkit.getLogger().severe("ERROR: Could not update main hub game rules because no world file was found!"); }
    }
}
