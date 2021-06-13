package xyz.refinedev.kitpvp.tasks;

import lombok.AllArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.refinedev.kitpvp.KitPvP;

@AllArgsConstructor
public class MongoSaveTask extends BukkitRunnable {

    private final KitPvP kitPvP;

    /**
     * Saves All Data That Needs To Be Saved
     */

    @Override
    public void run() {
        kitPvP.getKitHandler().getKits().forEach(kit -> kit.save(false));
    }

}
