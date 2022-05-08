package com.stand.listener;

import com.stand.utility.Common;
import com.stand.utility.ListUtil;
import de.leonhard.storage.Config;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerMainListener implements Listener {

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		core(player);
	}

	@EventHandler
	public void onPlayerChangedWorld(final PlayerChangedWorldEvent event) {

		final Player player = event.getPlayer();
		core(player);
		worldScanner(player);

	}

	@EventHandler
	public void onPlayerRespawn(final PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		final Config config = new Config("Settings", "plugins/PlayerModification");

		for (final String worldName : Common.getWorldNames()) {

			for (final String BlackListWorldName : Common.getBlackListWorldNames()) {

				if (player.getWorld().getName().equals(BlackListWorldName) && player.hasPermission("PlayerModification.dontreset")) {

					ListUtil.removePvpPlayer(player);
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
					player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
					player.setCanPickupItems(true);
					ListUtil.removeAntiBuildPlayer(player);
					return;

				} else if (player.getWorld().getName().equals(BlackListWorldName) && !player.hasPermission("PlayerModification.dontreset")) {

					player.resetMaxHealth();
					player.setHealthScale(20.0D);
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
					player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
					player.setCanPickupItems(true);
					player.setWalkSpeed(0.2F);
					player.setFlySpeed(0.1F);
					ListUtil.removePvpPlayer(player);
					ListUtil.removeAntiBuildPlayer(player);
					return;

				} else if (player.getWorld().getName().equals(worldName)) {

					player.setHealth(config.getSection(worldName).getDouble("Health"));
					player.setHealthScale(config.getSection(worldName).getDouble("Health_Scale"));
					player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(config.getSection(worldName).getDouble("Health"));
					player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(config.getSection(worldName).getDouble("Attack_Damage"));
					player.setWalkSpeed(config.getSection(worldName).getFloat("Movement_Speed"));
					player.setFlySpeed(config.getSection(worldName).getFloat("Flying_Speed"));
					player.setCanPickupItems(config.getSection(worldName).getBoolean("Enabled_Pick_Up_Item"));

					if (config.getSection(worldName).getBoolean("Enabled_Old_Pvp_Mechanics")) {
						player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
					} else {
						player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
					}

					if (!config.getSection(worldName).getBoolean("Allow_PVP")) {
						ListUtil.addPvpPlayer(player);
					} else {
						ListUtil.removePvpPlayer(player);
					}

					if (config.getSection(worldName).getBoolean("Anti_Build")) {
						ListUtil.addAntiBuildingList(player);
					} else {
						ListUtil.removeAntiBuildPlayer(player);
					}
				}
			}


		}

	}

	public void worldScanner(final Player player) {
		final Config config = new Config("Settings", "plugins/PlayerModification");

		for (final String worldName : Common.getWorldNames()) {
			if (!player.getWorld().getName().equals(worldName)) {

				config.setDefault(player.getWorld().getName() + ".Health", 20.0D);
				config.setDefault(player.getWorld().getName() + ".Health_Scale", 20.0D);
				config.setDefault(player.getWorld().getName() + ".Movement_Speed", 0.2F);
				config.setDefault(player.getWorld().getName() + ".Attack_Damage", 1.0D);
				config.setDefault(player.getWorld().getName() + ".Flying_Speed", 0.1F);
				config.setDefault(player.getWorld().getName() + ".Enabled_Pick_Up_Item", true);
				config.setDefault(player.getWorld().getName() + ".Enabled_Old_Pvp_Mechanics", false);
				config.setDefault(player.getWorld().getName() + ".Allow_PVP", true);
				config.setDefault(player.getWorld().getName() + ".Anti_Build", false);

			}
		}
	}

	public void core(final Player player) {
		final Config config = new Config("Settings", "plugins/PlayerModification");


		for (final String worldName : Common.getWorldNames()) {

			for (final String BlackListWorldName : Common.getBlackListWorldNames()) {

				if (player.getWorld().getName().equals(BlackListWorldName) && player.hasPermission("PlayerModification.dontreset")) {

					ListUtil.removePvpPlayer(player);
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
					player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
					player.setCanPickupItems(true);
					ListUtil.removeAntiBuildPlayer(player);
					return;

				} else if (player.getWorld().getName().equals(BlackListWorldName) && !player.hasPermission("PlayerModification.dontreset")) {

					player.resetMaxHealth();
					player.setHealthScale(20.0D);
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
					player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
					player.setCanPickupItems(true);
					player.setWalkSpeed(0.2F);
					player.setFlySpeed(0.1F);
					ListUtil.removePvpPlayer(player);
					ListUtil.removeAntiBuildPlayer(player);
					return;

				} else if (player.getWorld().getName().equals(worldName)) {
					player.setHealthScale(config.getSection(worldName).getDouble("Health_Scale"));
					player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(config.getSection(worldName).getDouble("Health"));
					player.setWalkSpeed(config.getSection(worldName).getFloat("Movement_Speed"));
					player.setFlySpeed(config.getSection(worldName).getFloat("Flying_Speed"));
					player.setCanPickupItems(config.getSection(worldName).getBoolean("Enabled_Pick_Up_Item"));
					player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(config.getSection(worldName).getDouble("Attack_Damage"));

					if (config.getSection(worldName).getBoolean("Enabled_Old_Pvp_Mechanics")) {
						player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
					} else {
						player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
					}

					if (!config.getSection(worldName).getBoolean("Allow_PVP")) {
						ListUtil.addPvpPlayer(player);
					} else {
						ListUtil.removePvpPlayer(player);
					}

					if (config.getSection(worldName).getBoolean("Anti_Build")) {
						ListUtil.addAntiBuildingList(player);
					} else {
						ListUtil.removeAntiBuildPlayer(player);
					}

				}
			}
		}
	}
}