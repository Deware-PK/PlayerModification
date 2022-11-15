package com.stand;

import com.stand.command.OnTabCompleter;
import com.stand.command.PlayerModificationCommand;
import com.stand.command.PlayerModificationConsoleCommand;
import com.stand.listener.ExperienceDropListener;
import com.stand.listener.PlayerAntiBuildEvent;
import com.stand.listener.PlayerMainListener;
import com.stand.listener.PlayerOptionalListener;
import com.stand.utility.Common;
import com.stand.utility.Language;
import com.stand.utility.ListUtil;
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

	private static PlayerModification instance;

	public static PlayerModification getInstance() {
		return instance;
	}

	@Override @SneakyThrows
	public void onEnable() {

		instance = this;
		Language.init();
		final Config config = new Config("Settings", "plugins/PlayerModification");
		final Metrics metrics = new Metrics(this , 14648);

		Bukkit.getConsoleSender().sendMessage(Common.colorize("&aPlayerModification_" + getVersion() + " -> Enabled"));

		getServer().getPluginManager().registerEvents(new PlayerMainListener() , this);
		getServer().getPluginManager().registerEvents(new PlayerOptionalListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerAntiBuildEvent(), this);
		getServer().getPluginManager().registerEvents(new ExperienceDropListener() , this);

		getCommand("playermodification").setExecutor(new PlayerModificationCommand());
		getCommand("playermodificationconsole").setExecutor(new PlayerModificationConsoleCommand());
		getCommand("playermodification").setTabCompleter(new OnTabCompleter());


		for (final String worldName : Common.getWorldNames()) {
			config.setDefault(worldName + ".Health", 20.0D);
			config.setDefault(worldName + ".Health_Scale" , 20.0D);
			config.setDefault(worldName + ".Attack_Damage", 1.0D);
			config.setDefault(worldName + ".Movement_Speed", 0.2F);
			config.setDefault(worldName + ".Flying_Speed", 0.1F);
			config.setDefault(worldName + ".Enabled_Pick_Up_Item", true);
			config.setDefault(worldName + ".Enabled_Old_Pvp_Mechanics" , false);
			config.setDefault(worldName + ".Allow_PVP" , true);
			config.setDefault(worldName + ".Anti_Build", false);
			config.setDefault(worldName + ".Enabled_Mob_To_Drop_Exp", true);
		}


		config.setDefault("Your_Custom_World_Name" + ".Health", 20.0D);
		config.setDefault("Your_Custom_World_Name" + ".Health_Scale" , 20.0D);
		config.setDefault("Your_Custom_World_Name" + ".Attack_Damage", 1.0D);
		config.setDefault("Your_Custom_World_Name" + ".Movement_Speed", 0.2F);
		config.setDefault("Your_Custom_World_Name" + ".Flying_Speed", 0.1F);
		config.setDefault("Your_Custom_World_Name" + ".Enabled_Pick_Up_Item", true);
		config.setDefault("Your_Custom_World_Name" + ".Enabled_Old_Pvp_Mechanics" , false);
		config.setDefault("Your_Custom_World_Name" + ".Allow_PVP" , true);
		config.setDefault("Your_Custom_World_Name" + ".Anti_Build", false);
		config.setDefault("Your_Custom_World_Name" + ".Enabled_Mob_To_Drop_Exp", true);

	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(Common.colorize("&cPlayerModification_" + getVersion() + " -> Disabled"));
	}

	public void disableThisPlugin() {
		getServer().getPluginManager().disablePlugin(this);
	}

	@Override
	public void onLoad() {
		// -- Black-list -- //

		final Yaml yaml = new Yaml("Black-list-world", "plugins/PlayerModification");
		final List<String> worlds = new ArrayList<>();
		yaml.setDefault("Black_List", worlds);

		final List<String> BlackListWorldName = yaml.getStringList("Black_List");

		if (yaml.getStringList("Black_List").isEmpty()) {
			Common.fixBlackListNull();
		} else {
			for (final String world : BlackListWorldName) {
				ListUtil.blackListedWorlds.add(Bukkit.getWorld(world));
			}
		}
	}


	public String getVersion() {
		return "2.2.9";
	}
}