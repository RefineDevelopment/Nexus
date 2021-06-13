package xyz.refinedev.kitpvp.kit;

import lombok.Getter;
import org.bson.Document;
import xyz.refinedev.kitpvp.KitPvP;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class KitHandler {

    @Getter
    private final Map<String, Kit> kitMap = new ConcurrentHashMap<>();
    private final KitPvP kitPvP;

    /**
     * KitHandler Of Which Handles
     * Managing The Kit System Of
     * The Project
     *
     * @param kitPvP
     */

    public KitHandler(KitPvP kitPvP) {
        this.kitPvP = kitPvP;
        this.load();
    }

    /**
     * Load Method To Load In
     * All Available Kits
     */

    public void load() {
        kitPvP.getServer().getScheduler().runTaskAsynchronously(kitPvP, () -> {
            kitPvP.getMongoHandler().getKits().find().forEach((Consumer<? super Document>) document -> {
                new Kit(document.getString("_id"));
            });
        });
    }

    /**
     * Gets a kit specific to a name
     *
     * @param name Name Of The Kit
     * @return
     */

    public Kit getKit(String name) {
        return kitMap.get(name.toLowerCase());
    }

    /**
     * Gets all kits
     *
     * @return
     */

    public Collection<Kit> getKits() {
        return kitMap.values();
    }

}
