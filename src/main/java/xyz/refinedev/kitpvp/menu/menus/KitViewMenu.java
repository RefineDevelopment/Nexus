package xyz.refinedev.kitpvp.menu.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.refinedev.kitpvp.kit.Kit;
import xyz.refinedev.kitpvp.menu.Menu;
import xyz.refinedev.kitpvp.util.CC;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KitViewMenu extends Menu {

    private final Kit kit;

    public KitViewMenu(Player player, Kit kit) {
        super(player);
        this.kit = kit;
    }

    @Override
    public void click(Player player, InventoryClickEvent event) {
        if (event.getCurrentItem() != null)
            event.setCancelled(true);
    }

    @Override
    public Map<Integer, ItemStack> getMap(Player player) {
        final Map<Integer, ItemStack> buttons = new ConcurrentHashMap<>();

        int content = 0;

        for (ItemStack stack : kit.getInventoryContents()) {
            buttons.put(content, stack);
            content++;
        }

        int armor = 45;

        for (ItemStack stack : kit.getArmorContents()) {
            buttons.put(armor, stack);
            armor++;
        }


        return buttons;
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public String getTitle() {
        return CC.translate("&b" + kit.getName() + "&7's Inventory");
    }
}
