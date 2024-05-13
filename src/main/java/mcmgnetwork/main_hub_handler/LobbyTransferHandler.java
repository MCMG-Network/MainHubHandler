package mcmgnetwork.main_hub_handler;


import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import mcmgnetwork.main_hub_handler.protocols.ChannelNames;
import mcmgnetwork.main_hub_handler.protocols.MessageTypes;
import mcmgnetwork.main_hub_handler.protocols.ServerStatuses;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Description: <p>
 *  Utilizes plugin messaging to send requests to the proxy server to transfer players to other servers. Plugin
 *  messaging is also utilized to inform players of the requested server's status.
 *
 *  <p>Author(s): Miles Bovero, Vee Sutton, Lawrence Ponce
 *  <p>Date Created: 5/5/24
 */
public class LobbyTransferHandler
{

    /**
     * Attempts to send the specified player to an instance of the specified server type.
     * <p>
     * If none of the requested server types are online, the requesting player is informed, and the proxy will attempt
     * to initialize a new server instance for the player.
     * @param serverType The name of the server to transfer the player to
     * @param player The player to be transferred
     */
    public static void transferPlayerToServer(String serverType, Player player)
    {
        // Prepare plugin message bytes: MessageType, Argument1, Argument2
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(MessageTypes.LOBBY_TRANSFER_REQUEST);
        out.writeUTF(player.getName());
        out.writeUTF(serverType);

        // Request the status of the provided server type from the proxy server
        MainHubHandler.getPlugin().getServer().sendPluginMessage(MainHubHandler.getPlugin(), ChannelNames.MCMG, out.toByteArray());

        // The remaining functionality described in the method header is handled by the PluginMessageHandler class
        // and the handleServerTransferResponse method
    }

    /**
     * Handles returned information (a SERVER_TRANSFER_RESPONSE) from the SERVER_TRANSFER_REQUEST sent to the proxy
     * server by the transferPlayerToServer method call.
     * <p>
     * Upon receiving a SERVER_TRANSFER_RESPONSE, the message contents are read and handled as follows:
     * <p>
     * The heard response message is only handled if the message's specified player exists within this plugin's server.
     * <p>
     * Depending on the response message's specified server status, various actions are taken: alert the player of the
     * server transfer request status, transfer the player, etc.
     * @param in The ByteArrayDataInput containing LOBBY_TRANSFER_RESPONSE plugin message data
     */
    public static void handleServerTransferResponse(ByteArrayDataInput in)
    {
        String serverStatus = in.readUTF();
        String playerName = in.readUTF();
        Player player = MainHubHandler.getPlugin().getServer().getPlayer(playerName);
        String serverName = in.readUTF();

        // Only handle the message if the specified player is in this server (prevents duplicate handling)
        if (!MainHubHandler.getPlugin().getServer().getOnlinePlayers().contains(player)) return;

        // If there is no active server to transfer to; alert the player
        switch (serverStatus)
        {
            case ServerStatuses.FULL:
                player.sendMessage(ChatColor.YELLOW +
                        "[Warning] Servers are full! " +
                        "\nPlease attempt reconnecting soon...");
                break;
            case ServerStatuses.BEGAN_INITIALIZATION:
                player.sendMessage(ChatColor.YELLOW +
                        "[Warning] No servers were available, so we started a new one for you! " +
                        "\nPlease attempt reconnecting soon...");
                break;
            case ServerStatuses.INITIALIZING:
                player.sendMessage(ChatColor.YELLOW +
                        "The requested server is currently booting up!" +
                        "\nPlease attempt reconnecting soon...");
                break;
            case ServerStatuses.FAILED_INITIALIZATION:
                player.sendMessage(ChatColor.RED +
                        "[ERROR] Critical failures occurred while attempting to start a new server...");
            case ServerStatuses.TRANSFERABLE:
                // Otherwise, there is an active server, so transfer the player there
                player.sendMessage(ChatColor.GREEN + "Transferring you to another server...");
                sendServerTransferInstruction(serverName, playerName);
                break;

        }
    }

    /**
     * Handles the actual transfer of a player from one server to another via plugin messaging to the Velocity proxy
     * server.
     * @param serverName The official server name - recognized by Velocity - that the player will be transferred to
     * @param playerName The name of the player that is being transferred
     */
    private static void sendServerTransferInstruction(String serverName, String playerName)
    {
        // Prepare plugin message bytes: MessageType, Argument1, Argument2
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(MessageTypes.TRANSFER);
        out.writeUTF(playerName);
        out.writeUTF(serverName);

        // Send the Player to the respective lobby
        MainHubHandler.getPlugin().getServer().sendPluginMessage(MainHubHandler.getPlugin(), ChannelNames.BUNGEE_CORD, out.toByteArray());
    }
}