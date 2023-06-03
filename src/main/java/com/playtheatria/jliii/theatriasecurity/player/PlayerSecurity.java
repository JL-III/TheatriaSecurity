package com.playtheatria.jliii.theatriasecurity.player;

import com.playtheatria.jliii.generalutils.utils.Console;

import java.util.UUID;

public class PlayerSecurity {
    private UUID playerUUID;
    private boolean isAuthenticated;
    private boolean isEnforced;

    public PlayerSecurity(UUID playerUUID, boolean isEnforced, boolean isAuthenticated) {
        this.playerUUID = playerUUID;
        this.isAuthenticated = isAuthenticated;
        this.isEnforced = isEnforced;
        Console.sendLog("PlayerSecurity object created for " + playerUUID.toString());
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }
    public boolean getIsAuthenticated() {
        return isAuthenticated;
    }

    public boolean getIsEnforced() {
        return isEnforced;
    }

    public void setIsAuthenticated(boolean b) {
        isAuthenticated = b;
    }
}
