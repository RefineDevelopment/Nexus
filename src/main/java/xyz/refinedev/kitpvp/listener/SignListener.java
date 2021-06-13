package xyz.refinedev.kitpvp.listener;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.refinedev.kitpvp.KitPvP;
import xyz.refinedev.kitpvp.kit.Kit;
import xyz.refinedev.kitpvp.util.CC;

public class SignListener implements Listener {

    /**
     * Event triggers when a sign is written
     * This event helps to create kit-signs
     *
     * @param event
     */

    @EventHandler
    public void onSighChangeEvent(SignChangeEvent event) {
        String lineOne = event.getLine(0);
        String[] args = lineOne.split(":");

        if (!lineOne.contains(":")) return;
        if (args == null || args[0] == null) return;

        if (!args[0].equalsIgnoreCase("[kit]")) return;

        Kit kit = KitPvP.getInstance().getKitHandler().getKit(args[1]);

        if (kit == null) {
            event.getPlayer().sendMessage(CC.translate("&7The kit &b" + args[1] + "&7 does not exist!"));
            return;
        }

        event.setLine(0, CC.translate("&7[&bKit&7]"));
        event.setLine(1, CC.translate("&b&l" + kit.getName()));
    }

    /**
     * Event triggers when a kit sign is clicked
     * Event applies kit items to player's inventory
     *
     * @param event
     */

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block == null || !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!(block.getState() instanceof Sign)) return;

        Sign sign = (Sign) block.getState();

        if (sign.getLine(0) == null ||
                !ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase("[kit]")) return;

        Kit kit = KitPvP.getInstance().getKitHandler().getKit(ChatColor.stripColor(sign.getLine(1)));

        if (kit == null) return;

        if (!KitPvP.getInstance().getProfileHandler().getProfile(player.getUniqueId()).getOwnedKits().contains(kit)
                && !player.hasPermission("kitpvp.kit")) {
            player.sendMessage(CC.translate("&cYou do not own this kit to claim it!"));
            return;
        }

        kit.apply(player);
    }

}
