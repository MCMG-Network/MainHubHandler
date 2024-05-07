package mcmgnetwork.main_hub_handler;


import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import static mcmgnetwork.main_hub_handler.MainHubHandler.transferChannelName;

/**
 * Description: <p>
 *  Utilizes plugin messaging to send requests to the proxy server to transfer players to other servers.
 *
 *  <p>Author(s): Miles Bovero, Vee Sutton, Lawrence Ponce
 *  <p>Date Created: 5/5/24
 */
public class LobbyTransferHandler implements Listener
{

    private static final String transferMessageType = "ConnectOther";
    private static JavaPlugin plugin;

    /**
     * Setter method for the JavaPlugin (MainHubHandler) reference necessary for transferring players to other lobbies.
     * @param plugin The MainHubHandler JavaPlugin reference
     */
    public static void SetJavaPlugin (JavaPlugin plugin) { LobbyTransferHandler.plugin = plugin; }

    /**
     * Sends the specified Player to the specified server.
     * @param serverName The name of the server to transfer the player to
     * @param player The Player object to be transferred
     */
    public static void Transfer (String serverName, Player player)
    {
        // Prepare plugin message: Message Type = "ConnectOther"; Arguments = "[playerName]", "[serverName]"
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(transferMessageType);
        out.writeUTF(player.getName());
        out.writeUTF(serverName);

        // Send the Player to the respective lobby
        plugin.getServer().sendPluginMessage(plugin, transferChannelName, out.toByteArray());
    }
}