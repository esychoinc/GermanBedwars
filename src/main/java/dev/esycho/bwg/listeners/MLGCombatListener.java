package dev.esycho.bwg.listeners;

import dev.esycho.bwg.GermenBedwars;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class MLGCombatListener implements Listener {

    private final GermenBedwars plugin = GermenBedwars.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player attacker = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();

        // Check korchi attacker hate Stick ache kina (MLG Rush style)
        if (attacker.getItemInHand() != null && attacker.getItemInHand().getType() == Material.STICK) {
            
            // Config theke multiplier value neya
            double horizontal = plugin.getConfig().getDouble("knockback-settings.stick.horizontal-multiplier", 2.2);
            double vertical = plugin.getConfig().getDouble("knockback-settings.stick.vertical-multiplier", 1.4);

            // Knockback calculation
            Vector velocity = victim.getLocation().getDirection().multiply(-1).setY(0).normalize().multiply(horizontal);
            velocity.setY(vertical);

            // Velocity set kora (Player ke dhakka deya)
            victim.setVelocity(velocity);
            
            // Hit sound (German server er moton satisfying sound)
            attacker.playSound(attacker.getLocation(), org.bukkit.Sound.ITEM_BREAK, 1.0f, 1.5f);
        }
    }
}