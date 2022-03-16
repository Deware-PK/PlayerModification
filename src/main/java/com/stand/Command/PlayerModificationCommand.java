package com.stand.Command;

import com.stand.PluginCollection;
import com.stand.Utility.Common;
import com.stand.Utility.PlayerUtil;
import com.stand.WorldManager;
import de.leonhard.storage.Config;
import de.leonhard.storage.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerModificationCommand implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		final Config config = new Config("Settings", "plugins/PlayerModification");
		final Yaml yaml = new Yaml("Black-list-world", "plugins/PlayerModification");
		final Player player = (Player) sender;

		if (!PlayerUtil.hasPerm(player , "PlayerModification.access")) {
			Common.sendMessage(player,"&cYou don't have permission to access PlayerModification plugin!");
			return true;
		}

		if (args.length < 1) {
			sendHelp(player);
		}

		if (args.length == 1) {
			final String param = args[0].toLowerCase();

			if ("reload".equals(param)) {

				WorldManager.blackListedWorlds.clear();

				final List<String> bl = yaml.getStringList("Black_List");
				for (final String world : bl) {
					WorldManager.blackListedWorlds.add(Bukkit.getWorld(world));
				}

				Common.sendMessage(player, "&7&l------ &9&l[&fReloaded List&9&l]&7&l ------");
				Common.sendMessage(player, "&9Blacklisted-world&7:&f [&a✔&f]");
				Common.sendMessage(player, "&9Player's value:&f [&a✔&f]");
				Common.sendMessage(player, "&9[&f&l!&9] &4Using /pm reset to resetting all player which in blacklisted world.");
				Common.sendMessage(player, "&cIn case of need to auto reset, put permission &e'PlayerModification.autoreset'.");
				Common.sendMessage(player, "&cBecause some of user have other plugin that modify player's value.");
				Common.sendMessage(player, "&7&l-------------------------");

				for (final String worldName : Common.getWorldNames()) {
					for (final String BlackListWorldName : Common.getBlackListWorldNames()) {
						for (final Player plr : Bukkit.getOnlinePlayers()) {

							if (player.getWorld().getName().equals(BlackListWorldName) && plr.hasPermission("PlayerModification.autoreset")) {
								plr.resetMaxHealth();
								plr.setHealthScale(20.0D);
								plr.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
								plr.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
								plr.setCanPickupItems(true);
								plr.setWalkSpeed(0.2F);
								plr.setFlySpeed(0.1F);
								PluginCollection.removePvpPlayer(plr);
								return true;

							} else if (player.getWorld().getName().equals(BlackListWorldName) && !plr.hasPermission("PlayerModification.autoreset")) {

								plr.setCanPickupItems(true);
								plr.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
								plr.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
								PluginCollection.removePvpPlayer(plr);
								return true;

							} else if (player.getWorld().getName().equals(worldName)) {

								plr.setHealthScale(config.getSection(worldName).getDouble("Health_Scale"));
								plr.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(config.getSection(worldName).getDouble("Health"));
								plr.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(config.getSection(worldName).getDouble("Attack_Damage"));
								plr.setWalkSpeed(config.getSection(worldName).getFloat("Movement_Speed"));
								plr.setFlySpeed(config.getSection(worldName).getFloat("Flying_Speed"));
								plr.setCanPickupItems(config.getSection(worldName).getBoolean("Enabled_Pick_Up_Item"));

								if (config.getSection(worldName).getBoolean("Enabled_Old_Pvp_Mechanics")) {
									plr.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
								} else {
									plr.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
								}

								if (!config.getSection(worldName).getBoolean("Allow_PVP")) {
									PluginCollection.addPvpPlayer(plr);
								} else {
									PluginCollection.removePvpPlayer(plr);
								}


								if (!plr.getWorld().getName().equals(worldName)) {

									config.setDefault(player.getWorld().getName() + ".Health", 20.0D);
									config.setDefault(player.getWorld().getName() + ".Health_Scale", 20.0D);
									config.setDefault(player.getWorld().getName() + ".Attack_Damage", 1.0D);
									config.setDefault(player.getWorld().getName() + ".Movement_Speed", 0.2F);
									config.setDefault(player.getWorld().getName() + ".Flying_Speed", 0.1F);
									config.setDefault(player.getWorld().getName() + ".Enabled_Pick_Up_Item", true);
									config.setDefault(player.getWorld().getName() + ".Enabled_Old_Pvp_Mechanics", false);
									config.setDefault(player.getWorld().getName() + ".Allow_PVP", true);
								}

							}
						}
					}
				}
			} else if ("help".equals(param)) {

				sendHelp(player);

			} else if ("reset".equals(param)) {

				Common.sendMessage(player, "&aAll player in blacklisted world has been reset to default.");

				for (final String BlackListWorldName : Common.getBlackListWorldNames()) {
						for (final Player plr : Bukkit.getOnlinePlayers()) {
							if (plr.getWorld().getName().equals(BlackListWorldName)) {
							plr.resetMaxHealth();
							plr.setHealthScale(20.0D);
							plr.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
							plr.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
							plr.setCanPickupItems(true);
							plr.setWalkSpeed(0.2F);
							plr.setFlySpeed(0.1F);
							PluginCollection.removePvpPlayer(plr);
						}
					}
				}

			} else {
				sendHelp(player);
			}
		}

		return true;
	}

	private void sendHelp(final Player player) {
		Common.sendMessage(player, "&5-----------&f[&ePlayerModification 2.2.2&f]&5-----------");
		Common.sendMessage(player , "&4<> = Require &f,&c [] = Optional");
		Common.sendMessage(player , "&cAliases: /pm&f,&c /pmd");
		Common.sendMessage(player , "&6/playermodification reload &f- Reload the configuration.");
		Common.sendMessage(player , "&6/playermodification reset &f- Resetting all player who in the blacklisted world");
		Common.sendMessage(player , "&6/playermodification help &f- Getting help of this plugin.");
	}
}