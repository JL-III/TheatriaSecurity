package com.playtheatria.jliii.theatriasecurity.player;

import com.playtheatria.jliii.generalutils.utils.CustomLogger;

import java.util.UUID;

public class PlayerSecurity {
    private CustomLogger customLogger;
    private UUID playerUUID;
    private boolean isAuthenticated;
    private boolean isEnforced;

    public PlayerSecurity(CustomLogger customLogger, UUID playerUUID, boolean isEnforced, boolean isAuthenticated) {
        this.customLogger = customLogger;
        this.playerUUID = playerUUID;
        this.isAuthenticated = isAuthenticated;
        this.isEnforced = isEnforced;
        customLogger.sendLog("PlayerSecurity object created for " + playerUUID.toString());
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
        customLogger.sendLog("PlayerSecurity object for " + playerUUID.toString() + " has been authenticated.");
    }
}
