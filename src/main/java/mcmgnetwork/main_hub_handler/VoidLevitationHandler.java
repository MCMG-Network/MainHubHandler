package mcmgnetwork.main_hub_handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VoidLevitationHandler implements Listener
{

    public static final int voidHeight = 3;
    public static final int levitationDuration = 20;
    public static final int levitationAmplifier = 22;
    public static final boolean showEffectParticles = false;

    /**
     * Gives any player below the defined void height the levitation effect.
     * @param e The event specifying player movement and related info.
     */
    @EventHandler
    public void onEnterVoid(PlayerMoveEvent e)
    {
        Player player = e.getPlayer();
        if (e.getTo().getY() < voidHeight)
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, levitationDuration, levitationAmplifier, showEffectParticles));
    }
}
