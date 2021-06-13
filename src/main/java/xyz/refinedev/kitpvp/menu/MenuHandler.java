package xyz.refinedev.kitpvp.menu;

import lombok.Getter;
import xyz.refinedev.kitpvp.KitPvP;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class MenuHandler {

    @Getter
    private final HashMap<UUID, Menu> menuHashMap = new HashMap<>();
    private final KitPvP kitPvP;

    /**
     * Custom Menu API Created by damt
     *
     * @param kitPvP
     */

    public MenuHandler(KitPvP kitPvP) {
        this.kitPvP = kitPvP;
        kitPvP.getServer().getPluginManager().registerEvents(new MenuListener(this), kitPvP);
    }

    /**
     * Gets the menu of a user/player
     *
     * @param uuid UUID of user/player
     * @return
     */

    public Menu getMenu(UUID uuid) {
        return menuHashMap.get(uuid);
    }

    /**
     * Gets all menus registered
     *
     * @return
     */

    public Collection<Menu> getMenus() {
        return menuHashMap.values();
    }


}
