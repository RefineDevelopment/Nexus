package xyz.refinedev.kitpvp.profile;

import org.bukkit.event.player.PlayerQuitEvent;
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
            profile = Profile.getByPlayer(player);
            profile.save();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Profile profile = Profile.getByUUID(player.getUniqueId());

        profile.save();
    }
}