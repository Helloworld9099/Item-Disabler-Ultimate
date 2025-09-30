package itemcontrol.Itemcontrol.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;  
import org.bukkit.inventory.ItemStack;
import itemcontrol.Itemcontrol.Itemcontrol;

import java.util.List;



public class PlayerListener implements Listener {

    private final Itemcontrol plugin;

    public PlayerListener(Itemcontrol plugin) {
        this.plugin = plugin;
    }

    private boolean isDisabled(ItemStack item) {
        if (item == null || item.getType() == null) return false;

        List<String> disabledItems = plugin.getConfig().getStringList("disabled-items");
        String typeName = item.getType().toString().toUpperCase().trim();

        for (String s : disabledItems) {
            if (s != null && s.toUpperCase().trim().equals(typeName)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasDisabledItems(Player player) {
        return isDisabled(player.getInventory().getItemInMainHand())
            || isDisabled(player.getInventory().getItemInOffHand());
    }

    private boolean shouldCancelAction(ItemStack item, Player player) {
        return isDisabled(item) || hasDisabledItems(player);
    }

    @EventHandler
    public void onPlayerResurrect(EntityResurrectEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (hasDisabledItems(player)) event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player && hasDisabledItems(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (shouldCancelAction(event.getItem(), event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (isDisabled(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player player && hasDisabledItems(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (isDisabled(event.getItemInHand())) {
            event.setCancelled(true);
        }
    }


}