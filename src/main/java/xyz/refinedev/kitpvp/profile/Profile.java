package xyz.refinedev.kitpvp.profile;

import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import xyz.refinedev.kitpvp.KitPvP;
import xyz.refinedev.kitpvp.kit.Kit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class Profile {

    private final UUID uuid;
    private int coins, kills, deaths, streak;
    private List<Kit> ownedKits = new ArrayList<>();
    private boolean build;

    /**
     * Profile Constructors Of Which Uses
     * The UUID To Create An Object To Store The Data
     * For a certain user/player
     *
     * @param uuid UUID of the user/player
     */

    public Profile(UUID uuid) {
        this.uuid = uuid;

        KitPvP.getInstance().getProfileHandler().getProfileMap().put(uuid, this);
        this.load();
    }

    /**
     * Load Method For Profile
     */

    public void load() {
        Bukkit.getScheduler().runTaskAsynchronously(KitPvP.getInstance(), () -> {
            Document document = KitPvP.getInstance().getMongoHandler().getProfiles()
                    .find(new Document("_id", uuid.toString())).first();

            if (document == null) return;

            this.coins = document.getInteger("coins");
            this.kills = document.getInteger("kills");
            this.deaths = document.getInteger("deaths");
            this.streak = document.getInteger("streak");
            this.ownedKits = document.getList("kits", String.class)
                    .stream().map(string -> KitPvP.getInstance().getKitHandler().getKit(string)).collect(Collectors.toList());
        });
    }

    /**
     * Save Method For The Profile
     * Saves To The Mongo Database
     *
     * @param async To save the profile async or not
     */

    public void save(boolean async) {

        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(KitPvP.getInstance(), () -> save(false));
            return;
        }

        Document document = KitPvP.getInstance().getMongoHandler().getProfiles()
                .find(new Document("_id", uuid.toString())).first();

        if (document == null) {
            KitPvP.getInstance().getMongoHandler().getProfiles().insertOne(toBson());
            return;
        }

        KitPvP.getInstance().getMongoHandler().getProfiles().replaceOne(document, toBson(), new ReplaceOptions().upsert(true));
    }

    /**
     * Turns the profile's data into a document
     *
     * @return
     */

    public Document toBson() {
        return new Document()
                .append("_id", uuid.toString())
                .append("coins", coins)
                .append("kills", kills)
                .append("deaths", deaths)
                .append("streak", kills)
                .append("kits", ownedKits.stream().map(Kit::getName).collect(Collectors.toList()));
    }

    /**
     * Get's the online-player of the profile
     *
     * @return
     */

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    /**
     * Get's the offline-player of the profile
     *
     * @return
     */

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

}