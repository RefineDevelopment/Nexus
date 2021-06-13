package xyz.refinedev.kitpvp.handler;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import xyz.refinedev.kitpvp.KitPvP;

@Getter
public class MongoHandler {

    private final KitPvP kitPvP;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private MongoCollection<Document> profiles;
    private MongoCollection<Document> kits;

    /**
     * MongoHandler Used To Connect To The
     * MongoDatabase
     *
     * @param kitPvP
     */

    public MongoHandler(KitPvP kitPvP) {
        this.kitPvP = kitPvP;
        this.connect();
    }


    /**
     * Connect Method To
     * Connect To The Mongo Datbase
     */

    private void connect() {
        MongoClientURI mongoClientURI = new MongoClientURI(kitPvP.getConfig().getString("mongo-uri"));
        mongoClient = new MongoClient(mongoClientURI);

        mongoDatabase = mongoClient.getDatabase("kitpvp");
        profiles = mongoDatabase.getCollection("profiles");
        kits = mongoDatabase.getCollection("kits");
    }

}
