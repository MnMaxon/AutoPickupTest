package me.MnMaxon.AutoTest;

import me.MnMaxon.AutoPickup.API.AutoPickupMethods;
import me.MnMaxon.AutoPickup.API.DropToInventoryEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoTest extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    /*
        Smelts Inventory when clicking on a stone button
        Blocks Inventory when clicking on a wood button
        Gives 64 Diamond Ore when interacting with  a stone plate
          - This will smelt the ore if they have autosmelt enabled
          - This will block the diamonds if autosmelt and autoblock are enabled
    */
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        switch (e.getClickedBlock().getType()) {
            case STONE_BUTTON:
                AutoPickupMethods.smeltInventory(e.getPlayer());
                break;
            case WOOD_BUTTON:
                AutoPickupMethods.blockInventory(e.getPlayer());
                break;
            case STONE_PLATE:
                AutoPickupMethods.autoGive(e.getPlayer(), new ItemStack(Material.DIAMOND_ORE, 64));
                break;
        }
    }

    // Cancels autopickup effects when using it with dirt
    // Cancels autopickup effect, and deletes item with cobble/stone
    @EventHandler
    public void onDropToInventory(DropToInventoryEvent e) {
        Bukkit.broadcastMessage("DROPPED");
        for (ItemStack itemStack : e.getItems())
            if (itemStack.getType() == Material.COBBLESTONE || itemStack.getType() == Material.STONE) {
                e.setCancelled(true);
                e.getItems().clear();
                break;
            } else if (itemStack.getType() == Material.DIRT) {
                e.setCancelled(true);
            }
    }
}