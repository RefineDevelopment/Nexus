package xyz.refinedev.kitpvp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import xyz.refinedev.kitpvp.KitPvP;

public class ServerListener implements Listener {

    /**
     * Event Triggers When A Player Builds A Block
     *
     * @param event
     */

    @EventHandler
    public void onPlayerBlockBuildEvent(BlockPlaceEvent event) {
        if (!KitPvP.getInstance().getProfileHandler().getProfile(event.getPlayer().getUniqueId()).isBuild())
            event.setCancelled(true);
    }

    /**
     * Event Triggers When A Player Breaks A Block
     *
     * @param event
     */

    @EventHandler
    public void onPlayerBlockBreakEvent(BlockBreakEvent event) {
        if (!KitPvP.getInstance().getProfileHandler().getProfile(event.getPlayer().getUniqueId()).isBuild())
            event.setCancelled(true);
    }

}
