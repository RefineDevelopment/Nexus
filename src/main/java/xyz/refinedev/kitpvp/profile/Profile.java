package xyz.refinedev.kitpvp.profile;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import xyz.refinedev.kitpvp.KitPvP;
import xyz.refinedev.kitpvp.kit.Kit;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class Profile {

    private ProfileState state;
    private List<Kit> ownedKits = new ArrayList<>();
    private String name;
    private UUID uuid;
    private int coins;
    private int kills;
    private int deaths;
    private int killstreak;

    public Profile(UUID uuid, String name) {
        this.state = ProfileState.LOBBY;
        this.uuid = uuid;
        this.name = name;
        this.coins = 0;
        this.kills = 0;
        this.deaths = 0;
        this.killstreak = 0;

        this.save();
        KitPvP.getInstance().getProfileManager().getProfiles().put(uuid, this);
    }

    public void save() {
        Document document = new Document();
        MongoCollection<Document> profiles = KitPvP.getInstance().getMongoDatabase().getCollection("profiles");

        document.put("uuid", uuid);
        document.put("name", name);
        document.put("coins", coins);
        document.put("kills", kills);
        document.put("deaths", deaths);
        document.put("killstreak", killstreak);
        document.put("ownedKits", this.ownedKits.stream().map(Kit::getName).collect(Collectors.toList()));

        profiles.replaceOne(Filters.eq("uuid", uuid), document, new ReplaceOptions().upsert(true));
    }

    public Player getPlayer() {
        if (!Bukkit.getPlayer(uuid).isOnline()) {
            return (Player) Bukkit.getOfflinePlayer(uuid);
        }

        return Bukkit.getPlayer(uuid);
    }

    public static Profile getByUUID(UUID uuid) {
        for (Profile profile : KitPvP.getInstance().getProfileManager().getProfiles().values()) {
            if (profile.getUuid().equals(uuid)) return profile;
        }

        return new Profile(uuid, Bukkit.getPlayer(uuid).getName());
    }

    public static Profile getByPlayer(Player player) {
        for (Profile profile : KitPvP.getInstance().getProfileManager().getProfiles().values()) {
            if (profile.getUuid().equals(player.getUniqueId())) return profile;
        }

        return new Profile(player.getUniqueId(), player.getName());
    }
}