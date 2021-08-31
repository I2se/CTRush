package fr.lyrania.ctrush.listeners;

import fr.lyrania.ctrush.managers.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class Cancels implements Listener {

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        if(GameState.isState(GameState.GAME)) {

        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteractInventory(InventoryClickEvent event) {
        if(GameState.isState(GameState.GAME)) {

        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        if(GameState.isState(GameState.GAME)) {

        } else {
            event.setCancelled(true);
        }
    }
}
