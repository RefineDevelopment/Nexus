package xyz.refinedev.kitpvp.commands.provider;

import lombok.AllArgsConstructor;
import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.refinedev.kitpvp.KitPvP;
import xyz.refinedev.kitpvp.kit.Kit;

@AllArgsConstructor
public class KitCommandProvider implements BladeProvider<Kit> {

    private final KitPvP kitPvP;

    @Nullable
    @Override
    public Kit provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        Kit kit = kitPvP.getKitHandler().getKit(s);

        if (kit == null || s == null)
            throw new BladeExitMessage("The kit " + ChatColor.YELLOW + s + ChatColor.RED + " does not exist!");

        return kit;
    }
}
