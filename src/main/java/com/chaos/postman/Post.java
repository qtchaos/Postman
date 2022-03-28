package com.chaos.postman;


import net.minecraft.ChatFormatting;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

import static org.bukkit.Bukkit.getLogger;

public class Post implements CommandExecutor {
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
    if (sender instanceof Player player) {
      if (Objects.requireNonNull(CommandHandler.plugin.config.getString("server-url")).isEmpty()) {
        player.sendMessage(ChatFormatting.RED + "Postman server URL not set!");
      } else {
        try {
          URL url = new URL(CommandHandler.plugin.config.getString("server-url"));
          HttpURLConnection http = (HttpURLConnection) url.openConnection();
          http.setRequestMethod("POST");
          http.setDoOutput(true);
          http.setRequestProperty("Content-Type", "plain/text");

          String data;
          if (CommandHandler.plugin.config.getBoolean("include-username")) {
            data = player.getName() + ": " + Arrays.toString(args).replace("[", "").replace("]", "");
          } else {
            data = Arrays.toString(args).replace("[", "").replace("]", "");
          }
          player.sendMessage(ChatFormatting.GREEN + "Sending message: \"" + data + "\"");

          byte[] out = data.getBytes(StandardCharsets.UTF_8);
          OutputStream stream = http.getOutputStream();
          stream.write(out);
          if (http.getResponseCode() == 200) {
            getLogger().info("Sent POST to server!");
            player.sendMessage(ChatFormatting.GREEN + "Sent POST to server!");
          } else if (http.getResponseCode() == 400) {
            getLogger().warning("Bad request!");
            player.sendMessage(ChatFormatting.RED + "Bad request!");
          } else if (http.getResponseCode() == 401) {
            getLogger().warning("Unauthorized!");
            player.sendMessage(ChatFormatting.RED + "Unauthorized!");
          } else if (http.getResponseCode() == 403) {
            getLogger().warning("Forbidden!");
            player.sendMessage(ChatFormatting.RED + "Forbidden!");
          } else {
            getLogger().warning("Unknown error!");
            player.sendMessage(ChatFormatting.RED + "Unknown error!");
          }
        } catch (IOException e) {
          player.sendMessage(ChatFormatting.RED + "Error sending POST!");
          getLogger().warning("Error sending POST!");
          e.printStackTrace();
        }
      }
    }
    return false;
  }
}
