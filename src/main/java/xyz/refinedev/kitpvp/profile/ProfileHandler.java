package xyz.refinedev.kitpvp.profile;

import lombok.Getter;
import org.bson.Document;
import xyz.refinedev.kitpvp.KitPvP;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ProfileHandler {

    @Getter private final Map<UUID, Profile> profileMap = new ConcurrentHashMap<>();
    private final KitPvP kitPvP;

    /**
     * Constructor For The Profile Handler
     * This Handler Manages The Profiles
     * And How They Are Stored
     * @param kitPvP A constrcutor paramater connecting to the main clas
     */

    public ProfileHandler(KitPvP kitPvP) {
        this.kitPvP = kitPvP;
        this.load();

        kitPvP.getServer().getPluginManager().registerEvents(new ProfileListener(this), kitPvP);
    }

    /**
     * Load Method For The Profile
     */

    public void load() {
        kitPvP.getServer().getScheduler().runTaskAsynchronously(kitPvP, () -> {
            kitPvP.getMongoHandler().getProfiles().find().forEach((Consumer<? super Document>) document -> {
                new Profile(UUID.fromString(document.getString("_id")));
            });
        });
    }

    /**
     * Gets A Valid Profile For A User
     * @param uuid UUID Of User Specified
     * @return
     */

    public final Profile getProfile(UUID uuid) {
        return profileMap.get(uuid);
    }

    /**
     * Gets a collection of profiles or a list/All Profiles
     * @return
     */

    public final Collection<Profile> getProfiles() {
        return profileMap.values();

}
    /**
     * Checks if a certain UUID
     * Has A Profile
     * @param uuid UUID of the user
     * @return
     */

    public final boolean hasProfile(UUID uuid) {
        return profileMap.containsKey(uuid);
    }

}
