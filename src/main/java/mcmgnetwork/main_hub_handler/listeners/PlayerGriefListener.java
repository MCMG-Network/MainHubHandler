package mcmgnetwork.main_hub_handler.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

/**
 * Description: <p>
 *  This class listens for any game events that may lead to the degradation of the MCMG network's
 *  main hub and cancels them.
 *
 *  <p>Author(s): Miles Bovero
 *  <p>Date Created: 5/4/24
 */
public class PlayerGriefListener implements Listener {

    /**
     * Prevents players from manipulating armor stands.
     * @param e The event to be cancelled
     */
    @EventHandler
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent e)
    {
        Player player = e.getPlayer();
        player.sendMessage(ChatColor.RED + "Hey! Don't touch that!");
        e.setCancelled(true);
    }

    /**
     * Prevents any entities from being damaged - notably armor stands.
     * @param e The event ot be cancelled
     */
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) { e.setCancelled(true); }
}
