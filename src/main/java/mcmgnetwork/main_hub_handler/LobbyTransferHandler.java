package mcmgnetwork.main_hub_handler;


import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import mcmgnetwork.main_hub_handler.protocols.ChannelNames;
import mcmgnetwork.main_hub_handler.protocols.MessageTypes;
import mcmgnetwork.main_hub_handler.protocols.ServerStatuses;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

/**
 * Description: <p>
 *  Utilizes plugin messaging to send requests to the proxy server to transfer players to other servers. Plugin
 *  messaging is also utilized to inform players of inactive, initializing, and online servers.
 *
 *  <p>Author(s): Miles Bovero, Vee Sutton, Lawrence Ponce
 *  <p>Date Created: 5/5/24
 */
public class LobbyTransferHandler implements PluginMessageListener
{

    /**
     * Attempts to send the specified player to an instance of the specified server type.
     * <p>
     * If none of the requested server types are online, the requesting player is informed, and the proxy will attempt
     * to initialize a new server instance for the player.
     * @param serverType The name of the server to transfer the player to
     * @param player The player to be transferred
     */
    public static void transfer(String serverType, Player player)
    {
        // Prepare plugin message bytes: MessageType, Argument1, Argument2
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(MessageTypes.LOBBY_TRANSFER_REQUEST);
        out.writeUTF(player.getName());
        out.writeUTF(serverType);

        // Request the status of the provided server type from the proxy server
        MainHubHandler.getPlugin().getServer().sendPluginMessage(MainHubHandler.getPlugin(), ChannelNames.MCMG, out.toByteArray());

        // !!! The onPluginMessageReceived method handles the rest of the functionality described in the method header.
    }

    /**
     * Handles returned information from the "transfer" method's request to the proxy server
     * (a SERVER_TRANSFER_RESPONSE).
     * <p>
     * Upon receiving a SERVER_TRANSFER_RESPONSE, the message contents are read and handled as follows:
     * <p>
     * If an active server of the specified type was found (the message's first argument == true), the relayed
     * player (the second argument) shall be sent to the relayed server (the third argument).
     * <p>
     * If no active server was found, assume the proxy is working to start one up, and alert the requesting player
     * that this is happening.
     * @param channel The channel that the received message was sent over
     * @param player Ignore
     * @param bytes The received message's data/contents
     */
    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] bytes)
    {
        // Only handle messages on the MCMG channel
        if (!channel.equals(ChannelNames.MCMG)) return;

        // Read message data/contents
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();

        // Only handle SERVER_TRANSFER_RESPONSE message types
        if (subChannel.equals(MessageTypes.LOBBY_TRANSFER_RESPONSE)) {
            String serverStatus = in.readUTF();
            String playerName = in.readUTF();
            player = MainHubHandler.getPlugin().getServer().getPlayer(playerName);
            String serverName = in.readUTF();

            // Only handle the message if the specified player is in this server (prevents duplicate handling)
            if (!MainHubHandler.getPlugin().getServer().getOnlinePlayers().contains(player)) return;

            // If there is no active server to transfer to; alert the player
            switch (serverStatus) {
                case ServerStatuses.FULL:
                    player.sendMessage(ChatColor.YELLOW +
                            "[Warning] Servers are full! " +
                            "\nPlease attempt reconnecting soon...");
                    break;
                case ServerStatuses.INITIALIZING:
                    player.sendMessage(ChatColor.YELLOW +
                            "[Warning] No servers were available, so we'll start a new one for you! " +
                            "\nPlease attempt reconnecting soon...");
                    break;
                case ServerStatuses.TRANSFERABLE:
                    // Otherwise, there is an active server, so transfer the player there
                    player.sendMessage(ChatColor.GREEN + "Transferring you to another server...");
                    proxyTransfer(serverName, playerName);
                    break;
            }
        }
    }

    /**
     * Handles the actual transfer of a player from one server to another via plugin messaging to the Velocity proxy
     * server.
     * @param serverName The official server name - recognized by Velocity - that the player will be transferred to
     * @param playerName The name of the player that is being transferred
     */
    private static void proxyTransfer(String serverName, String playerName)
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