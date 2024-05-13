package mcmgnetwork.main_hub_handler.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import mcmgnetwork.main_hub_handler.LobbyTransferHandler;
import mcmgnetwork.main_hub_handler.protocols.ChannelNames;
import mcmgnetwork.main_hub_handler.protocols.MessageTypes;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

/**
 * Description: <p>
 *  Handles all incoming and outgoing plugin messages heard by this plugin.
 *
 *  <p>Author(s): Miles Bovero
 *  <p>Date Created: 5/13/24
 */
public class PluginMessageHandler  implements PluginMessageListener
{
    /**
     * Listens for incoming plugin messages, identifies recognized messages, and handles them.
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

        // Only handle known message types
        if (subChannel.equals(MessageTypes.LOBBY_TRANSFER_RESPONSE))
            LobbyTransferHandler.handleServerTransferResponse(in);
    }
}
