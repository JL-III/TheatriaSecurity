package com.playtheatria.jliii.theatriasecurity.commands;

import com.playtheatria.jliii.theatriasecurity.token.TokenManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

public class ConsoleCommand implements CommandExecutor {
    private TokenManager tokenManager;

    public ConsoleCommand(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("generate")) {
                    tokenManager.generateToken();
                    return true;
                }
            }
        }
        return false;
    }
}
