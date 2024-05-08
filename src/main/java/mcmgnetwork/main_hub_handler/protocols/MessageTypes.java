package mcmgnetwork.main_hub_handler.protocols;

/**
 * Description: <p>
 *  Stores plugin messaging channel message types for easy reference across the plugin.
 *
 *  <p>Author(s): Miles Bovero
 *  <p>Date Created: 5/7/24
 */
public class MessageTypes
{
    /**
     * Connects the specified player to the specified server.
     */
    public static final String transfer = "ConnectOther";

    /**
     * Causes the proxy server to return a "ServerStatus" plugin message containing the status of the specified server
     * type and an online server's name if any server of that type is online.
     */
    public static final String serverStatusRequest = "ServerStatusRequest";
}
