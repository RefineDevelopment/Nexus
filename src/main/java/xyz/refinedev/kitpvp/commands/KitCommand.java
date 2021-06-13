package xyz.refinedev.kitpvp.commands;

import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Name;
import me.vaperion.blade.command.annotation.Permission;
import me.vaperion.blade.command.annotation.Sender;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.refinedev.kitpvp.KitPvP;
import xyz.refinedev.kitpvp.kit.Kit;
import xyz.refinedev.kitpvp.menu.menus.KitViewMenu;
import xyz.refinedev.kitpvp.profile.Profile;
import xyz.refinedev.kitpvp.util.CC;
import xyz.refinedev.kitpvp.util.TextBuilder;

import java.util.Arrays;
import java.util.List;

public class KitCommand {

    private final List<String> helpMessage = Arrays.asList(
            "&7&m----------&b&lKit Help Commands&7&m----------",
            "&b/kit create <name> &7- Creates a kit",
            "&b/kit edit <kit> &7- Edits A Kit To Your Inventory",
            "&b/kit delete <kit> &7- Deletes A Kit",
            "&b/kit view <kit> &7- Views a kit",
            "&b/kit load <kit> &7- Loads a kit",
            "&b/kit list &7- Sends a list of all kits",
            "&b/kit add <player> <kit> &7- Adds a kit for a player",
            "&b/kit remove <player> <kit> &7- Removes a kit from a player",
            "&7&m----------&b&lKit Help Commands&7&m----------"
    );


    /**
     * Kit Help Command
     *
     * @param player Player to send help message to
     */

    @Command(value = "kit", quoted = false, async = true, description = "Kit Command")
    @Permission(value = "kitpvp.kit", message = "You must have the 'kitpvp.kit' permission to do this command!")
    public void kitCommand(@Sender Player player) {
        helpMessage.forEach(s -> player.sendMessage(CC.translate(s)));
    }

    /**
     * Kit Create Command
     *
     * @param player Sender/Creator
     * @param name   Name of kit
     */

    @Command(value = "kit create", quoted = false, async = true, description = "Kit Create Command")
    @Permission(value = "kitpvp.kit", message = "You must have the 'kitpvp.kit' permission to do this command!")
    public void kitCreateCommand(@Sender Player player, @Name("name") String name) {
        Kit other = KitPvP.getInstance().getKitHandler().getKit(name);

        if (other != null) {
            player.sendMessage(CC.translate("&cThere is already a kit with the name &e" + other.getName() + "&c!"));
            return;
        }

        Kit kit = new Kit(name);

        kit.update(player);
        kit.save(true);

        player.sendMessage(CC.translate("&7You have created the kit &b" + kit.getName() + "&7!"));
    }

    /**
     * Kit Delete Command
     *
     * @param player Sender
     * @param kit    Kit To Delete
     */

    @Command(value = "kit delete", quoted = false, async = true, description = "Kit Delete Command")
    @Permission(value = "kitpvp.kit", message = "You must have the 'kitpvp.kit' permission to do this command!")
    public void kitDeleteCommand(@Sender Player player, @Name("kit") Kit kit) {
        kit.remove(true);
        player.sendMessage(CC.translate("&7You have &bdeleted &7the kit &b" + kit.getName() + "&7!"));
    }

    /**
     * Kit Edit Command
     *
     * @param player Sender
     * @param kit    Kit To Edit
     */

    @Command(value = "kit edit", quoted = false, async = true, description = "Kit Edit Command")
    @Permission(value = "kitpvp.kit", message = "You must have the 'kitpvp.kit' permission to do this command!")
    public void kitEditCommand(@Sender Player player, @Name("kit") Kit kit) {
        kit.update(player);
        player.sendMessage(CC.translate("&7You have &bedited &7the kit &b" + kit.getName() + "&7 to your inventory!"));
    }

    /**
     * Kit View Command
     *
     * @param player Sender
     * @param kit    Kit To View
     */

    @Command(value = "kit view", quoted = false, async = true, description = "Kit View Command")
    @Permission(value = "kitpvp.kit", message = "You must have the 'kitpvp.kit' permission to do this command!")
    public void kitViewCommand(@Sender Player player, @Name("kit") Kit kit) {
        new KitViewMenu(player, kit).updateMenu();
    }

    /**
     * Kit Load Command
     *
     * @param player Sender
     * @param kit    Kit To Load
     */

    @Command(value = "kit load", quoted = false, async = true, description = "Kit Load Command")
    @Permission(value = "kitpvp.kit", message = "You must have the 'kitpvp.kit' permission to do this command!")
    public void kitLoadCommand(@Sender Player player, @Name("kit") Kit kit) {
        kit.apply(player);
        player.sendMessage(CC.translate("&7Applied the kit &b" + kit.getName() + "&7!"));
    }

    /**
     * Kit List Command
     *
     * @param player Sender
     */

    @Command(value = "kit list", quoted = false, async = true, description = "Kit List Command")
    @Permission(value = "kitpvp.kit", message = "You must have the 'kitpvp.kit' permission to do this command!")
    public void kitListCommand(@Sender Player player) {

        KitPvP.getInstance().getKitHandler().getKits().forEach(kit -> {
            TextBuilder textBuilder = new TextBuilder().
                    setText("- ").setColor(ChatColor.GRAY);

            TextBuilder kitName = new TextBuilder()
                    .setText(kit.getName() + " ").setColor(ChatColor.AQUA);

            TextBuilder viewKit = new TextBuilder()
                    .setText("(Click Here To View").setColor(ChatColor.YELLOW)
                    .setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/view " + kit.getName()));

            textBuilder.addExtra(kitName.build()).addExtra(viewKit.build());

            player.spigot().sendMessage(textBuilder.build());
        });
    }

    /**
     * Kit Apply Command
     *
     * @param player Sender
     * @param target Player Targeted
     * @param kit    To Apply
     */

    @Command(value = "kit apply", quoted = false, async = true, description = "Kit Apply Command")
    @Permission(value = "kitpvp.kit", message = "You must have the 'kitpvp.kit' permission to do this command!")
    public void kitApplyCommand(@Sender Player player, @Name("player") Player target, @Name("kit") Kit kit) {
        kit.apply(target);
        player.sendMessage(CC.translate("&7Applied the kit &b" + kit.getName() + "&7 to the player &b" +
                target.getName() + "&7!"));
    }

    /**
     * Kit Add Command
     *
     * @param commandSender Sender
     * @param profile       Player
     * @param kit           Kit To Add
     */


    @Command(value = "kit add", quoted = false, async = true, description = "Kit Add Command")
    @Permission(value = "kitpvp.kit", message = "You must have the 'kitpvp.kit' permission to do this command!")
    public void kitAddCommand(@Sender CommandSender commandSender, @Name("player") Profile profile, @Name("kit") Kit kit) {
        if (profile.getOwnedKits().contains(kit)) {
            commandSender.sendMessage(CC.translate("&cThat user already owns the kit &e" + kit.getName() + "&c!"));
            return;
        }

        profile.getOwnedKits().add(kit);
        commandSender.sendMessage(CC.translate("&7Added the kit &b" + kit.getName() + " &7to the player &b" + profile.getOfflinePlayer().getName()));
    }

    /**
     * Kit Remove Command
     *
     * @param commandSender Sender
     * @param profile       Player
     * @param kit           Kit To Remove
     */

    @Command(value = "kit remove", quoted = false, async = true, description = "Kit Remove Command")
    @Permission(value = "kitpvp.kit", message = "You must have the 'kitpvp.kit' permission to do this command!")
    public void kitRemoveCommand(@Sender CommandSender commandSender, @Name("player") Profile profile, @Name("kit") Kit kit) {
        if (!profile.getOwnedKits().contains(kit)) {
            commandSender.sendMessage(CC.translate("&cThat user does not own the kit &e" + kit.getName() + "&c!"));
            return;
        }

        profile.getOwnedKits().remove(kit);
        commandSender.sendMessage(CC.translate("&7Remove the kit &b" + kit.getName() + " &7from the player &b" + profile.getOfflinePlayer().getName()));
    }

}
