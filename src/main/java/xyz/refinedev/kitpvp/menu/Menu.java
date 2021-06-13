package xyz.refinedev.kitpvp.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.refinedev.kitpvp.KitPvP;
import xyz.refinedev.kitpvp.util.ItemBuilder;

import java.util.Map;

@Getter
@Setter
public abstract class Menu {

    /**
     * Map To Store ItemStacks
     *
     * @param player
     * @return
     */

    public abstract Map<Integer, ItemStack> getMap(Player player);

    /**
     * Click Method
     *
     * @param player
     * @param event
     */

    public void click(Player player, InventoryClickEvent event) {
    }

    private final Player player;
    private ItemStack fillerBlock
            = new ItemBuilder(Material.STAINED_GLASS_PANE).data(DyeColor.BLACK.getData()).name(" ").build();

    public Menu(Player player) {
        this.player = player;
    }

    /**
     * Updates the menu
     */

    public void updateMenu() {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getTitle());

        if (useFiller()) {
            for (int i = 0; i < getSize(); i++) {
                getMap(player).put(i, fillerBlock);
            }
        }

        getMap(player).keySet().forEach(integer -> {
            inventory.setItem(integer, getMap(player).get(integer));
        });

        if (player.getOpenInventory().equals(inventory)) {
            player.updateInventory();
            return;
        }

        player.openInventory(inventory);
        KitPvP.getInstance().getMenuHandler().getMenuHashMap().put(player.getUniqueId(), this);
    }

    /**
     * Menu Size
     *
     * @return
     */

    public int getSize() {
        return 9;
    }

    /**
     * Uses Filler
     *
     * @return
     */

    public boolean useFiller() {
        return false;
    }

    /**
     * Title Of The Menu
     *
     * @return
     */

    public String getTitle() {
        return "Title";
    }

}
