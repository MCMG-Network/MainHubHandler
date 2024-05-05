package mcmgnetwork.main_hub_handler.listeners;

import io.papermc.paper.event.player.PlayerOpenSignEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

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

    /**
     * Prevents any blocks from being broken.
     * @param e The event to be cancelled
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) { e.setCancelled(true); }

    /**
     * Prevents any blocks from being placed.
     * @param e The event to be cancelled
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) { e.setCancelled(true); }

    /**
     * Prevents any blocks, notably plants, from being harvested.
     * @param e The event to be cancelled
     */
    @EventHandler
    public void onHarvestBlock(PlayerHarvestBlockEvent e) { e.setCancelled(true); }

    /**
     * Prevents players from fertilizing blocks (like using bone meal to grow plants).
     * @param e The event to be cancelled.
     */
    @EventHandler
    public void onBlockFertilize(BlockFertilizeEvent e) { e.setCancelled(true); }

    /**
     * Prevents players from emptying buckets.
     * @param e The event to be cancelled.
     */
    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent e) { e.setCancelled(true); }

    /**
     * Prevents players from filling buckets (picking up liquids).
     * @param e The event to be cancelled.
     */
    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent e) { e.setCancelled(true); }

    /**
     * Prevents players from opening signs to edit them.
     * @param e The event to be cancelled
     */
    @EventHandler
    public void onSignOpen(PlayerOpenSignEvent e) { e.setCancelled(true); }

    /**
     * Prevents signs from being changed.
     * @param e The event to be cancelled
     */
    @EventHandler
    public void onSignChange(SignChangeEvent e) { e.setCancelled(true); }

    /**
     * Prevents TNT from being primed/ignited.
     * @param e The event to be cancelled
     */
    @EventHandler
    public void onTNTPrime(TNTPrimeEvent e) { e.setCancelled(true); }

    /**
     * Prevents blocks (TNT, Respawn Anchor, etc.) from exploding.
     * @param e The event to be cancelled
     */
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) { e.setCancelled(true); }
}
