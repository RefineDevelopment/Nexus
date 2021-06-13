package xyz.refinedev.kitpvp.commands;

import me.vaperion.blade.command.annotation.Combined;
import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Permission;
import me.vaperion.blade.command.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import xyz.refinedev.kitpvp.KitPvP;
import xyz.refinedev.kitpvp.profile.Profile;
import xyz.refinedev.kitpvp.util.CC;

public class EssentialCommands {

    /**
     * Creative GameMode Command
     *
     * @param player Sender
     */

    @Command(value = "gmc", quoted = false, description = "Gamemode Creative Command")
    @Permission(value = "kitpvp.gamemode", message = "You are not allowed to execute this command!")
    public void gmc(@Sender Player player) {
        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage(CC.translate("&aYou have been set in creative mode!"));
    }

    /**
     * Survival GameMode Command
     *
     * @param player Sender
     */

    @Command(value = "gms", quoted = false, description = "Gamemode Survival Command")
    @Permission(value = "kitpvp.gamemode", message = "You are not allowed to execute this command!")
    public void gms(@Sender Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.sendMessage(CC.translate("&aYou have been set in survival mode!"));
    }

    /**
     * Adventure GameMode Command
     *
     * @param player Sender
     */

    @Command(value = "gma", quoted = false, description = "Gamemode Adventure Command")
    @Permission(value = "kitpvp.gamemode", message = "You are not allowed to execute this command!")
    public void gma(@Sender Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.sendMessage(CC.translate("&aYou have been set in adventure mode!"));
    }

    /**
     * Clear Command
     *
     * @param player Sender
     */

    @Command(value = "clear", quoted = false, description = "Clear Command")
    @Permission(value = "kitpvp.clear", message = "You are not allowed to execute this command!")
    public void clear(@Sender Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.sendMessage(CC.translate("&aYou have cleared your inventory!"));
    }

    /**
     * More Command
     *
     * @param player Sender
     */

    @Command(value = "more", quoted = false, description = "More Command")
    @Permission(value = "kitpvp.more", message = "You are not allowed to execute this command!")
    public void more(@Sender Player player) {
        if (player.getItemInHand() == null) {
            player.sendMessage(CC.translate("&cPlease hold something to increase it's amount!"));
            return;
        }

        player.getItemInHand().setAmount(64);
        player.sendMessage(CC.translate("&aBuffed that amount for ya!"));
    }

    /**
     * BroadCast Command
     *
     * @param player    Sender
     * @param broadcast Message
     */

    @Command(value = "broadcast", quoted = false, description = "Broadcast Command")
    @Permission(value = "kitpvp.broadcast", message = "You are not allowed to execute this command!")
    public void broadcast(@Sender Player player, @Combined String broadcast) {
        Bukkit.getServer().broadcastMessage(CC.translate(broadcast));
    }

    /**
     * Build Command
     *
     * @param player Sender
     */

    @Command(value = "build", quoted = false, description = "Build Command")
    @Permission(value = "kitpvp.build", message = "You are not allowed to execute this command!")
    public void build(@Sender Player player) {
        Profile profile = KitPvP.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        if (profile.isBuild())
            profile.setBuild(false);
        else
            profile.setBuild(true);

        player.sendMessage(CC.translate("&7Set your build mode to: &b" + profile.isBuild()));
    }

}
