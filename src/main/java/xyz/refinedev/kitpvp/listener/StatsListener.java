package xyz.refinedev.kitpvp.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import xyz.refinedev.kitpvp.KitPvP;
import xyz.refinedev.kitpvp.profile.Profile;
import xyz.refinedev.kitpvp.util.CC;

public class StatsListener implements Listener {

    /**
     * Event triggers whenever a player's dies
     * - Sends Out Custom Death Message
     * - Gives a kill to the killer
     * - Gives a death to the target
     * @param event
     */

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        event.setDeathMessage(null);

        Player target = event.getEntity();
        Profile targetProfile = KitPvP.getInstance().getProfileHandler().getProfile(target.getUniqueId());

        targetProfile.setDeaths(targetProfile.getDeaths() - 1);

        if (!(target.getKiller() instanceof Player)) return;

        Player killer = target.getKiller();
        Profile killerProfile = KitPvP.getInstance().getProfileHandler().getProfile(killer.getUniqueId());

        killerProfile.setKills(killerProfile.getKills() + 1);

        String weapon = killer.getItemInHand().getItemMeta().getDisplayName() != null ?
                killer.getItemInHand().getItemMeta().getDisplayName() :
                "Air";

        Bukkit.broadcastMessage(CC.translate("&b" + target.getName() + "&7 was killed by &b" +
                killer.getName() + " &7using &b " + weapon));
    }

}
