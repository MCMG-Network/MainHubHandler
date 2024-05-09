package mcmgnetwork.main_hub_handler.listeners;
import mcmgnetwork.main_hub_handler.HubPropertiesHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Description: <p>
 *  This class listens for any player events that where the user is no longer in bounds
 *
 *  <p>Author(s): Miles Bovero, Vee Sutton
 *  <p>Date Created: 5/4/24
 */
public class VoidHandler implements Listener
{

    // Void levitation settings
    private static final int voidHeight = 0;
    private static final int levitationDuration = 20;
    private static final int levitationAmplifier = 22;

    // Hub boundary settings
    private static final double posXBound = 100;
    private static final double negXBound = -50;
    private static final double YBound = -30;
    private static final double posZBound = 125;
    private static final double negZBound = -50;


    /**
     * Gives any player below the defined void height the levitation effect.
     * @param e The event specifying player movement and related info.
     */
    @EventHandler
    public void onEnterVoid(PlayerMoveEvent e)
    {
        Player player = e.getPlayer();
        Location playerLocation = player.getLocation();

        // Give the player levitation if they fall into the void
        if (e.getTo().getY() < voidHeight)
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, levitationDuration, levitationAmplifier));

        try
        {
            // Initialize world spawn location
            Location spawn = new Location(Bukkit.getWorld("world"), HubPropertiesHandler.worldSpawnX, HubPropertiesHandler.worldSpawnY, HubPropertiesHandler.worldSpawnZ);

            // If players fall too far into the void, tp them back to the world spawn
            if (playerLocation.getY() < YBound)
                player.teleport(spawn);

            // If players venture too far in the X direction, tp them back to the world spawn
            if (playerLocation.getX() > posXBound || playerLocation.getX() < negXBound)
                player.teleport(spawn);

            // If players venture too far in the Z direction, tp them back to the world spawn
            if (playerLocation.getZ() > posZBound || playerLocation.getZ() < negZBound)
                player.teleport(spawn);

        }catch (NullPointerException ex)
        { Bukkit.getLogger().severe("ERROR: Could not update main hub game rules because no world file was found!"); }
    }
}
