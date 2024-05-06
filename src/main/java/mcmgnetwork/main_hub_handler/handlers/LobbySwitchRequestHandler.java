package mcmgnetwork.main_hub_handler.handlers;
import mcmgnetwork.main_hub_handler.LobbySwitcher;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Description: <p>
 *  Handles main hub block interactions and transfers the interacting player to the
 *  corresponding server lobby.
 *
 *  <p>Author(s): Miles Bovero, Vee Sutton, Lawrence Ponce
 *  <p>Date Created: 5/5/24
 */
public class LobbySwitchRequestHandler implements Listener
{
    // Lobby transfer button locations
    private final static Location KOTHButtonLoc = new Location(Bukkit.getWorld("world"), 40, 19, 14);
    private final static Location MMButtonLoc1 = new Location(Bukkit.getWorld("world"), 32, 21, 64);
    private final static Location MMButtonLoc2 = new Location(Bukkit.getWorld("world"), 30, 21, 64);


    /**
     * Detects when players interact with blocks and sends them to another server lobby
     * if they interacted with a lobby transfer button.
     * @param e The PlayerInteractEvent to be handled
     */
    @EventHandler
    public void onLobbyButtonInteract(PlayerInteractEvent e)
    {
        // Ensure the event was attached to a block
        if (!e.hasBlock()) return;

        // Detect when player right-clicks on a button
        String clickedBlock = e.getClickedBlock().toString().toLowerCase();
        if (clickedBlock.contains("button") && e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            // Store player reference
            Player player = e.getPlayer();
            // Initialize string to hold name of server to transfer to
            String serverName = "";

            // Detect which button was pressed and assign the correlating lobby server
            if (e.getClickedBlock().getLocation().equals(KOTHButtonLoc))
                serverName = "KOTH_lobby";
            else if (e.getClickedBlock().getLocation().equals(MMButtonLoc1) || e.getClickedBlock().getLocation().equals(MMButtonLoc2))
                serverName = "MM_lobby";

            // If a lobby transferring button was pressed, transfer the player to that lobby
            if (!serverName.equals(""))
                LobbySwitcher.Transfer(serverName, player);
        }
    }
}
