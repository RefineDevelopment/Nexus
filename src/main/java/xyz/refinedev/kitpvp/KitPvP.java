package xyz.refinedev.kitpvp;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.refinedev.kitpvp.handler.MongoHandler;
import xyz.refinedev.kitpvp.kit.KitHandler;
import xyz.refinedev.kitpvp.profile.ProfileHandler;

@Getter
public final class KitPvP extends JavaPlugin {

    @Getter
    public static KitPvP instance;

    private ProfileHandler profileHandler;
    private MongoHandler mongoHandler;
    private KitHandler kitHandler;

    /**
     * Event triggered on the load of the plugin
     */

    @Override
    public void onLoad() {
        instance = this;
        this.saveDefaultConfig();
    }

    /**
     * Event triggered on the enabling of the plugin
     */

    @Override
    public void onEnable() {
        this.mongoHandler = new MongoHandler(this);
        this.kitHandler = new KitHandler(this);
        this.profileHandler = new ProfileHandler(this);
    }

    /**
     * Event triggered on the disabling on the plugin
     */

    @Override
    public void onDisable() {
        profileHandler.getProfiles().forEach(profile -> profile.save(false));
    }

}
