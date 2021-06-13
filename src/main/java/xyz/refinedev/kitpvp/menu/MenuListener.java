package xyz.refinedev.kitpvp.menu;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class MenuListener implements Listener {

    private final MenuHandler menuHandler;

    /**
     * Event Triggeed When A User Clicks
     * On A Menu
     *
     * @param event
     */

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getClickedInventory() == null) return;

        Player player = (Player) event.getWhoClicked();
        Menu menu = menuHandler.getMenu(player.getUniqueId());

        if (menu == null) return;

        menu.click(player, event);
    }

    /**
     * Event Triggered When A User
     * Closes A Menu
     *
     * @param event
     */

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        if (event.getInventory() == null) return;

        Player player = (Player) event.getPlayer();
        Menu menu = menuHandler.getMenu(player.getUniqueId());

        if (menu == null) return;

        menuHandler.getMenuHashMap().remove(player.getUniqueId());
    }

    /**
     * Event Triggered When A User Quits
     * With A Menu
     *
     * @param event
     */

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Menu menu = menuHandler.getMenu(player.getUniqueId());

        if (menu == null) return;

        menuHandler.getMenuHashMap().remove(player.getUniqueId());
    }

}
