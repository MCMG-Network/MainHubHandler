package mcmgnetwork.main_hub_handler.listeners;
import io.papermc.paper.event.player.PlayerOpenSignEvent;
import org.bukkit.*;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.*;

/**
 * Description: <p>
 *  This class listens for any game events that may lead to the degradation of the MCMG network's
 *  main hub and cancels them.
 *
 *  <p>Author(s): Miles Bovero
 *  <p>Date Created: 5/4/24
 */
public class PlayerGriefPreventionHandler implements Listener {

    /**
     * Updates the main hub server's world game rules to prevent/mitigate world change and griefing.
     */
    public static void setGameRules()
    {
        try
        {
            // Retrieve and store the main hub server's world
            World world = Bukkit.getWorld("world");

            // Set game rules
            world.setGameRule(GameRule.DISABLE_RAIDS, false);
            world.setGameRule(GameRule.DO_FIRE_TICK, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_INSOMNIA, false);
            world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
            world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
            world.setGameRule(GameRule.DO_VINES_SPREAD, false);
            world.setGameRule(GameRule.DO_WARDEN_SPAWNING, false);
            world.setGameRule(GameRule.MOB_GRIEFING, false);
            world.setGameRule(GameRule.PROJECTILES_CAN_BREAK_BLOCKS, false);
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);

        } catch (NullPointerException ex)
        { Bukkit.getLogger().severe("ERROR: Could not update main hub game rules because no world file was found!"); }
    }

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

    /**
     * Prevents entities from exploding.
     * @param e The event to be cancelled
     */
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) { e.setCancelled(true); }

    /**
     * Prevents entities, notable endermen, from changing blocks.
     * @param e The event to be cancelled
     */
    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent e) { e.setCancelled(true); }

    /**
     * Detects when players interact with a door and cancels that interaction.
     * @param e The event to be analyzed and possibly canceled.
     */
    @EventHandler
    public void onTrapDoorInteract(PlayerInteractEvent e)
    {
        // Ensure the event was attached to a block
        if (!e.hasBlock()) return;
        if (e.getClickedBlock().getBlockData() instanceof TrapDoor)
            e.setCancelled(true);
    }

}
