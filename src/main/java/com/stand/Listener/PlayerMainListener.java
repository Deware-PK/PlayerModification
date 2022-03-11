package com.stand.Listener;

import com.stand.PluginCollection;
import com.stand.Utility.Common;
import de.leonhard.storage.Config;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerMainListener implements Listener {

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		final Config config = new Config("Settings", "plugins/PlayerModification");
		final double currentHealth = player.getHealth();

		for (final String worldName : Common.getWorldNames()) {
			if (player.getWorld().getName().equals(worldName)) {
				try {
					player.setHealth(currentHealth);
					player.setMaxHealth(config.getSection(worldName).getDouble("Health"));
				} catch (final IllegalArgumentException ex) {
					if (player.getMaxHealth() > player.getHealth()) {
						player.setMaxHealth(player.getHealth());
					}
				}

				player.setHealthScale(config.getSection(worldName).getDouble("Health_Scale"));
				player.setWalkSpeed(config.getSection(worldName).getFloat("Movement_Speed"));
				player.setFlySpeed(config.getSection(worldName).getFloat("Flying_Speed"));
				player.setCanPickupItems(config.getSection(worldName).getBoolean("Enabled_Pick_Up_Item"));
				if(config.getSection(worldName).getBoolean("Enabled_Old_Pvp_Mechanics")) {
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
				} else {
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
				}

				if (!config.getSection(worldName).getBoolean("Allow_PVP")) {
					PluginCollection.addPvpPlayer(player);
				} else {
					PluginCollection.removePvpPlayer(player);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerChangedWorld(final PlayerChangedWorldEvent event) {

		final Player player = event.getPlayer();
		final Config config = new Config("Settings", "plugins/PlayerModification");
		final double currentHealth = player.getHealth();

		for (final String worldName : Common.getWorldNames()) {

			if (!player.getWorld().getName().equals(worldName)) {
				config.setDefault(player.getWorld().getName() + ".Health", 20.0D);
				config.setDefault(player.getWorld().getName() + ".Health_Scale" , 20.0D);
				config.setDefault(player.getWorld().getName() + ".Movement_Speed", 0.2F);
				config.setDefault(player.getWorld().getName() + ".Flying_Speed", 0.1F);
				config.setDefault(player.getWorld().getName() + ".Enabled_Pick_Up_Item", true);
				config.setDefault(player.getWorld().getName() + ".Enabled_Old_Pvp_Mechanics" , false);
				config.setDefault(player.getWorld().getName() + ".Allow_PVP" , true);

			}

			if (player.getWorld().getName().equals(worldName)) {
				try {

					player.setHealth(currentHealth);
					player.setMaxHealth(config.getSection(worldName).getDouble("Health"));

				} catch (final IllegalArgumentException ex) {

					if (player.getMaxHealth() > player.getHealth()) {
						player.setMaxHealth(player.getHealth());
					}

				}

				player.setHealthScale(config.getSection(worldName).getDouble("Health_Scale"));
				player.setWalkSpeed(config.getSection(worldName).getFloat("Movement_Speed"));
				player.setFlySpeed(config.getSection(worldName).getFloat("Flying_Speed"));
				player.setCanPickupItems(config.getSection(worldName).getBoolean("Enabled_Pick_Up_Item"));
				if(config.getSection(worldName).getBoolean("Enabled_Old_Pvp_Mechanics")) {
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
				} else {
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
				}

				if (!config.getSection(worldName).getBoolean("Allow_PVP")) {
					PluginCollection.addPvpPlayer(player);
				} else {
					PluginCollection.removePvpPlayer(player);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerRespawn(final PlayerRespawnEvent event) {

		final Player player = event.getPlayer();
		final Config config = new Config("Settings", "plugins/PlayerModification");
		final double currentHealth = player.getHealth();

		for (final String worldName : Common.getWorldNames()) {

			if (player.getWorld().getName().equals(worldName)) {
				try {
					player.setHealth(currentHealth);
					player.setMaxHealth(config.getSection(worldName).getDouble("Health"));
				} catch (final IllegalArgumentException ex) {
					if (player.getMaxHealth() > player.getHealth()) {
						player.setMaxHealth(player.getHealth());
					}
				}

				player.setHealthScale(config.getSection(worldName).getDouble("Health_Scale"));
				player.setWalkSpeed(config.getSection(worldName).getFloat("Movement_Speed"));
				player.setFlySpeed(config.getSection(worldName).getFloat("Flying_Speed"));
				player.setCanPickupItems(config.getSection(worldName).getBoolean("Enabled_Pick_Up_Item"));
				if(config.getSection(worldName).getBoolean("Enabled_Old_Pvp_Mechanics")) {
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
				} else {
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
				}

				if (!config.getSection(worldName).getBoolean("Allow_PVP")) {
					PluginCollection.addPvpPlayer(player);
				} else {
					PluginCollection.removePvpPlayer(player);
				}
			}
		}
	}


	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		final double currentHealth = player.getHealth();

		player.setHealth(currentHealth);
		player.setMaxHealth(20.0D);
		player.setWalkSpeed(0.2F);
		player.setFlySpeed(0.1F);
		player.setFallDistance(3.7F);
		player.setCanPickupItems(true);
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
		PluginCollection.removePvpPlayer(player);
	}
}
