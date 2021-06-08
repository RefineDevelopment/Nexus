package xyz.refinedev.kitpvp;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import xyz.refinedev.kitpvp.managers.KitManager;
import xyz.refinedev.kitpvp.managers.ProfileManager;
import lombok.Getter;
import org.bson.Document;
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

    @Getter private MongoClient mongoClient;
    @Getter private MongoDatabase mongoDatabase;
    @Getter private static MongoCollection<Document> profiles;

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

        mongoThread.execute(this::connect);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public void connect() {
        MongoClientURI mongoClientURI = new MongoClientURI(getConfig().getString("mongo-uri"));
        mongoClient = new MongoClient(mongoClientURI);
        mongoDatabase = mongoClient.getDatabase("kitpvp");
        profiles = mongoDatabase.getCollection("profiles");

    }
}
