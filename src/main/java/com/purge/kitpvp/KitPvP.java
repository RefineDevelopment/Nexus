package com.purge.kitpvp;

import com.purge.kitpvp.managers.KitManager;
import com.purge.kitpvp.managers.ProfileManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class KitPvP extends JavaPlugin {

    @Getter public static KitPvP instance;

    // Managers
    @Getter public KitManager kitManager;
    @Getter public ProfileManager profileManager;

    // Threads
    @Getter public Executor mainThread;
    @Getter public Executor mongoThread;
    @Getter public Executor taskThread;

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        instance = this;

        // Threads
        this.mainThread = Executors.newSingleThreadExecutor();
        this.mongoThread = Executors.newSingleThreadExecutor();
        this.taskThread = Executors.newSingleThreadExecutor();

        mainThread.execute(() -> {
            kitManager = new KitManager();
            profileManager = new ProfileManager();

            
        });
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
