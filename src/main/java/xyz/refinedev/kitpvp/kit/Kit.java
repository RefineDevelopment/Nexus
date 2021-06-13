package xyz.refinedev.kitpvp.kit;

import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.refinedev.kitpvp.KitPvP;
import xyz.refinedev.kitpvp.util.Serializer;

import java.io.IOException;

@Getter
@Setter
public class Kit {

    private final String name;
    private ItemStack[] armorContents, inventoryContents = new ItemStack[]{};

    /**
     * Kit Object For Kits
     * Of Which Are Used In The GameMode
     *
     * @param name Name of the Kit
     */

    public Kit(String name) {
        this.name = name;
        KitPvP.getInstance().getKitHandler().getKitMap().put(name.toLowerCase(), this);
    }

    /**
     * Applying The Kit For A Player/Target/User
     *
     * @param target Player of which the kit will be applied to
     */

    public void apply(Player target) {
        target.getInventory().setArmorContents(armorContents);
        target.getInventory().setContents(armorContents);
    }

    /**
     * Update Method To Update
     * The Kit's Contents To The
     * Contents Of A Specific User
     *
     * @param player Player/User to update the conetnts to
     */

    public void update(Player player) {
        this.armorContents = player.getInventory().getArmorContents();
        this.inventoryContents = player.getInventory().getContents();
    }

    /**
     * Load Method To Load In The Kit
     * From The Mongo Database
     */

    public void load() {
        Bukkit.getScheduler().runTaskAsynchronously(KitPvP.getInstance(), () -> {
            Document document = KitPvP.getInstance().getMongoHandler().getKits()
                    .find(new Document("_id", name)).first();

            if (document == null) return;

            try {
                this.inventoryContents = Serializer.itemStackArrayFromBase64(document.getString("contents"));
                this.armorContents = Serializer.itemStackArrayFromBase64(document.getString("armor"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Save Method To Save
     * The Kit To The Mongo
     * Database
     *
     * @param async Either if the saving is done async or not
     */

    public void save(boolean async) {

        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(KitPvP.getInstance(), () -> save(false));
            return;
        }

        Document document = KitPvP.getInstance().getMongoHandler().getKits()
                .find(new Document("_id", name)).first();

        if (document == null) {
            KitPvP.getInstance().getMongoHandler().getKits().insertOne(toBson());
            return;
        }

        KitPvP.getInstance().getMongoHandler().getKits().replaceOne(document, toBson(), new ReplaceOptions().upsert(true));
    }

    /**
     * Deletes Kit From The Both
     * Ram, Database
     *
     * @param async Either the task is ran async or not
     */

    public void remove(boolean async) {

        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(KitPvP.getInstance(), () -> remove(false));
            return;
        }

        KitPvP.getInstance().getKitHandler().getKitMap().remove(name.toLowerCase(), this);
        Document document = KitPvP.getInstance().getMongoHandler().getKits()
                .find(new Document("_id", name)).first();

        if (document != null)
            KitPvP.getInstance().getMongoHandler().getKits().deleteOne(document);
    }

    /**
     * Turns The Kit's Data Into
     * A MongoDatabase/Bson Document
     *
     * @return
     */

    public Document toBson() {
        return new Document()
                .append("_id", name)
                .append("contents", Serializer.itemStackArrayToBase64(inventoryContents))
                .append("armor", Serializer.itemStackArrayToBase64(armorContents));
    }

}
