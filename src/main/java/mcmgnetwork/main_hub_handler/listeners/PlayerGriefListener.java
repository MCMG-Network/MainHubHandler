package mcmgnetwork.main_hub_handler.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class PlayerGriefListener implements Listener {
    @EventHandler
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent e)
    {
        Player player = e.getPlayer();
        player.sendMessage(ChatColor.RED + "Hey! Don't touch that!");
        e.setCancelled(true);
    }
}
