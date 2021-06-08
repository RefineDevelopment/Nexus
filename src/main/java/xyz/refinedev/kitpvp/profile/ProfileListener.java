package xyz.refinedev.kitpvp.profile;

import xyz.refinedev.kitpvp.KitPvP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ProfileListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Profile profile;

        if (!KitPvP.getInstance().getProfileManager().getProfiles().containsKey(player.getUniqueId())) {
            profile = new Profile(player.getUniqueId(), player.getName());
        }
    }
}
