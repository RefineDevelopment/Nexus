package xyz.refinedev.kitpvp;

import lombok.Getter;
import me.vaperion.blade.Blade;
import me.vaperion.blade.command.bindings.impl.BukkitBindings;
import me.vaperion.blade.command.bindings.impl.DefaultBindings;
import me.vaperion.blade.command.container.impl.BukkitCommandContainer;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.refinedev.kitpvp.commands.EssentialCommands;
import xyz.refinedev.kitpvp.commands.KitCommand;
import xyz.refinedev.kitpvp.commands.provider.KitCommandProvider;
import xyz.refinedev.kitpvp.commands.provider.ProfileCommandProvider;
import xyz.refinedev.kitpvp.handler.MongoHandler;
import xyz.refinedev.kitpvp.kit.Kit;
import xyz.refinedev.kitpvp.kit.KitHandler;
import xyz.refinedev.kitpvp.listener.ServerListener;
import xyz.refinedev.kitpvp.listener.SignListener;
import xyz.refinedev.kitpvp.listener.StatsListener;
import xyz.refinedev.kitpvp.menu.MenuHandler;
import xyz.refinedev.kitpvp.profile.Profile;
import xyz.refinedev.kitpvp.profile.ProfileHandler;
import xyz.refinedev.kitpvp.tasks.MongoSaveTask;

@Getter
public final class KitPvP extends JavaPlugin {

    @Getter
    private static KitPvP instance;

    private ProfileHandler profileHandler;
    private MongoHandler mongoHandler;
    private KitHandler kitHandler;
    private MenuHandler menuHandler;

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
        this.menuHandler = new MenuHandler(this);

        this.registerPlugin();
        new MongoSaveTask(this).runTaskTimerAsynchronously(this, 500 * 20L, 500 * 20L);
    }

    /**
     * Void method to register the plugin's
     * necessary classes to work
     */

    public void registerPlugin() {
        this.getServer().getPluginManager().registerEvents(new SignListener(), this);
        this.getServer().getPluginManager().registerEvents(new StatsListener(), this);
        this.getServer().getPluginManager().registerEvents(new ServerListener(), this);

        Blade.of().binding(new DefaultBindings()).binding(new BukkitBindings()).containerCreator(BukkitCommandContainer.CREATOR).fallbackPrefix("kitpvp")
                .bind(Kit.class, new KitCommandProvider(this))
                .bind(Profile.class, new ProfileCommandProvider(this)).build()
                .register(new KitCommand())
                .register(new EssentialCommands());
    }

    /**
     * Event triggered on the disabling on the plugin
     */

    @Override
    public void onDisable() {
        profileHandler.getProfiles().forEach(profile -> profile.save(false));
        kitHandler.getKits().forEach(kit -> kit.save(false));
    }

}
