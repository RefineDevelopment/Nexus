package com.purge.kitpvp.kit;

import com.purge.kitpvp.KitPvP;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Kit {

    private String name;
    private boolean defaultKit;

    public Kit(String name) {
        this.name = name;
        this.defaultKit = false;

        KitPvP.getInstance().getKitManager().getKits().add(this);
    }

    public static Kit getDefault() {
        for (Kit kit : KitPvP.getInstance().getKitManager().getKits()) {
            if (kit.isDefaultKit()) {
                return kit;
            }
        }

        return null;
    }

    public static Kit getByName(String name) {
        Kit kit = new Kit(name);

        for (Kit kit1 : KitPvP.getInstance().getKitManager().getKits()) {
            if (kit1.getName().equalsIgnoreCase(name)) {
                return kit;
            }
        }

        return null;
    }
}