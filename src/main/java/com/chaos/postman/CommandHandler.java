package com.chaos.postman;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandHandler extends JavaPlugin implements Listener {
  FileConfiguration config = getConfig();
  public static CommandHandler plugin; // Dependency Injection to access config
  @Override
  public void onEnable() {
    plugin = this; //Dependency Injection
    config.addDefault("server-url", "");
    config.addDefault("include-username", false);
    config.options().copyDefaults(true);
    saveConfig();

    getCommand("config").setExecutor(new PrintConfig());
    getCommand("post").setExecutor(new Post());
  }
}
