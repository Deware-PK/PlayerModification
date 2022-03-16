package com.stand;

import com.stand.Command.PlayerModificationCommand;
import com.stand.Command.onTabCompleter;
import com.stand.Listener.PlayerMainListener;
import com.stand.Listener.PlayerOptionalListener;
import com.stand.Utility.Common;
import de.leonhard.storage.Config;
import de.leonhard.storage.Yaml;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class PlayerModification extends JavaPlugin {

	/**
	 * To add more settings edit following:
	 * 1. OnEnable
	 * 2. PlayerModificationCommand
	 * 3. PlayerMainListener
	 */

	private Config config;
	private static PlayerModification instance;

	public static PlayerModification getInstance() {
		return instance;
	}

	@Override @SneakyThrows
	public void onEnable() {

		instance = this;
		config = new Config("Settings", "plugins/PlayerModification");
		final Yaml yaml = new Yaml("Black-list-world", "plugins/PlayerModification");

		Bukkit.getConsoleSender().sendMessage(Common.colorize("&aPlayerModification_2.2.2 -> Enabled"));

		getServer().getPluginManager().registerEvents(new PlayerMainListener() , this);
		getServer().getPluginManager().registerEvents(new PlayerOptionalListener(), this);

		getCommand("playermodification").setExecutor(new PlayerModificationCommand());
		getCommand("playermodification").setTabCompleter(new onTabCompleter());

		for (final String worldName : Common.getWorldNames()) {
			config.setDefault(worldName + ".Health", 20.0D);
			config.setDefault(worldName + ".Health_Scale" , 20.0D);
			config.setDefault(worldName + ".Attack_Damage", 1.0D);
			config.setDefault(worldName + ".Movement_Speed", 0.2F);
			config.setDefault(worldName + ".Flying_Speed", 0.1F);
			config.setDefault(worldName + ".Enabled_Pick_Up_Item", true);
			config.setDefault(worldName + ".Enabled_Old_Pvp_Mechanics" , false);
			config.setDefault(worldName + ".Allow_PVP" , true);
		}


		config.setDefault("Your_Custom_World_Name" + ".Health", 20.0D);
		config.setDefault("Your_Custom_World_Name" + ".Health_Scale" , 20.0D);
		config.setDefault("Your_Custom_World_Name" + ".Attack_Damage", 1.0D);
		config.setDefault("Your_Custom_World_Name" + ".Movement_Speed", 0.2F);
		config.setDefault("Your_Custom_World_Name" + ".Flying_Speed", 0.1F);
		config.setDefault("Your_Custom_World_Name" + ".Enabled_Pick_Up_Item", true);
		config.setDefault("Your_Custom_World_Name" + ".Enabled_Old_Pvp_Mechanics" , false);
		config.setDefault("Your_Custom_World_Name" + ".Allow_PVP" , true);

		// -- Black-list -- //

		final List<String> worlds = new ArrayList<>();
		yaml.setDefault("Black_List", worlds);

		final List<String> BlackListWorldName = yaml.getStringList("Black_List");
		for (final String world : BlackListWorldName) {
			WorldManager.blackListedWorlds.add(Bukkit.getWorld(world));
		}



	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(Common.colorize("&cPlayerModification_2.2.2 -> Disabled"));
	}

}