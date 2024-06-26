package mcmgnetwork.main_hub_handler.listeners;
import mcmgnetwork.main_hub_handler.LobbyTransferHandler;
import mcmgnetwork.main_hub_handler.ServerNames;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Description: <p>
 *  Handles/filters main hub block interactions and transfers players interacting with transfer buttons to the
 *  corresponding server lobby using the LobbySwitcher class.
 *
 *  <p>Author(s): Miles Bovero, Vee Sutton, Lawrence Ponce
 *  <p>Date Created: 5/5/24
 */
public class LobbyTransferRequestHandler implements Listener
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

        Block clickedBlock = e.getClickedBlock();
        // Detect when player right-clicks on a button
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock.toString().toLowerCase().contains("button"))
        {
            Player player = e.getPlayer();
            // Retrieve server name based on button location
            String serverName = getServerName(clickedBlock.getLocation());

            // If a lobby transferring button was pressed, transfer the player to that lobby
            if (serverName != null)
                LobbyTransferHandler.Transfer(serverName, player);
        }
    }

    /**
     * @param location The Location in the Main Hub server's world
     * @return The name of the lobby server corresponding to the provided Location; returns null if no corresponding
     * server name was found
     */
    private static String getServerName(Location location) {
        if (location.equals(KOTHButtonLoc))
            return ServerNames.KOTH_Lobby;
        else if (location.equals(MMButtonLoc1) || location.equals(MMButtonLoc2))
            return ServerNames.MM_Lobby;

        return null;
    }
}
