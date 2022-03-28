package com.chaos.postman;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PrintConfig implements CommandExecutor {
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
    if (sender instanceof Player player) {
      player.sendMessage(CommandHandler.plugin.config.getString("server-url") + "\n" + CommandHandler.plugin.config.getString("include-username"));
    }
    return false;
  }
}
