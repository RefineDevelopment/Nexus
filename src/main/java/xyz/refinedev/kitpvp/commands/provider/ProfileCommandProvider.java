package xyz.refinedev.kitpvp.commands.provider;

import lombok.AllArgsConstructor;
import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.refinedev.kitpvp.KitPvP;
import xyz.refinedev.kitpvp.profile.Profile;

@AllArgsConstructor
public class ProfileCommandProvider implements BladeProvider<Profile> {

    private final KitPvP kitPvP;

    @Nullable
    @Override
    public Profile provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        Profile profile = kitPvP.getProfileHandler().getProfile(
                kitPvP.getServer().getOfflinePlayer(s).getUniqueId());

        if (profile == null || s == null)
            throw new BladeExitMessage("The user " + ChatColor.YELLOW + s + ChatColor.RED + " never joined the server before!");

        return profile;
    }
}
