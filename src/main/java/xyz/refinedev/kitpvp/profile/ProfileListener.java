package xyz.refinedev.kitpvp.profile;

import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class ProfileListener implements Listener {

    private final ProfileHandler profileHandler;

    /**
     * Event triggers when a player queues up to join a server
     * @param event
     */

    @EventHandler
    public void onPlayerJoinEvent(AsyncPlayerPreLoginEvent event) {

        if (!profileHandler.hasProfile(event.getUniqueId())) {
            new Profile(event.getUniqueId()).save(true);
        }

    }

    /**
     * Event triggers when a player leaves/quits the server
     * @param event
     */

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        profileHandler.getProfile(event.getPlayer().getUniqueId()).save(true);
    }

}
