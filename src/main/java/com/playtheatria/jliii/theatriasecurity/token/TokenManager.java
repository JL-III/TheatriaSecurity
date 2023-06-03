package com.playtheatria.jliii.theatriasecurity.token;

import com.playtheatria.jliii.theatriasecurity.utils.GeneralUtils;
import org.bukkit.plugin.Plugin;

public class TokenManager {
    private Plugin plugin;
    private int timestamp;

    public TokenManager(Plugin plugin) {
        this.plugin = plugin;
        generateToken();
    }

    public boolean isTokenValid(String token) {
        if (timestamp + 60 < (int) (System.currentTimeMillis() / 1000L)) {
            GeneralUtils.sendLog("Token has expired.");
            return false;
        }
        if (token.equals(plugin.getConfig().get("token"))) {
            return true;
        } else {
            GeneralUtils.sendLog("Token is invalid.");
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
        GeneralUtils.sendLog("Token has been generated.");
        timestamp = (int) (System.currentTimeMillis() / 1000L);
    }

}
