package com.purge.kitpvp.managers;

import com.purge.kitpvp.profile.Profile;
import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class ProfileManager {
    @Getter public HashMap<UUID, Profile> profiles = new HashMap<>();

    public void load() {

    }

    public void setupFiles() {

    }
}