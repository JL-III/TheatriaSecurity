package com.playtheatria.jliii.theatriasecurity.token;

import com.playtheatria.jliii.generalutils.utils.CustomLogger;
import org.bukkit.plugin.Plugin;

public class TokenManager {
    private Plugin plugin;
    private CustomLogger customLogger;
    private int timestamp;
    private boolean hasBeenConsumed;

    public TokenManager(Plugin plugin, CustomLogger customLogger) {
        this.plugin = plugin;
        this.customLogger = customLogger;
        generateToken();
    }

    public boolean isTokenValid(String token) {
        if (timestamp + 60 < (int) (System.currentTimeMillis() / 1000L) || hasBeenConsumed) {
            customLogger.sendLog("Token has expired.");
            return false;
        }
        if (token.equals(plugin.getConfig().get("token"))) {
            hasBeenConsumed = true;
            return true;
        } else {
            customLogger.sendLog("Token is invalid.");
            return false;
        }
    }

    public void generateToken() {
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            token.append((char) (Math.random() * 26 + 'a'));
        }
        plugin.getConfig().set("token", token.toString());
        plugin.saveConfig();
        customLogger.sendLog("Token has been generated.");
        timestamp = (int) (System.currentTimeMillis() / 1000L);
        hasBeenConsumed = false;
    }

}
