package com.stand.command;

import com.stand.PlayerModification;
import com.stand.PluginCollection;
import com.stand.WorldManager;
import com.stand.utility.Common;
import com.stand.utility.PlayerUtil;
import de.leonhard.storage.Config;
import de.leonhard.storage.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerModificationConsoleCommand implements CommandExecutor {
	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		final Config config = new Config("Settings", "plugins/PlayerModification");
		final Yaml yaml = new Yaml("Black-list-world", "plugins/PlayerModification");

		if (sender instanceof Player) {
			final Player player = (Player) sender;
			if (!PlayerUtil.hasPerm(player, "PlayerModification.access")) {
				Common.sendMessage(player, "&cFor the player using /pm instead.");
				Common.sendMessage(player,"&cYou don't have permission to access PlayerModification plugin!");
				return true;
			}
			return true;
		}

		if (args.length < 1) {
			sendHelp();
		}

		if (args.length == 1) {
			final String param = args[0].toLowerCase();

			if ("reload".equals(param)) {

				WorldManager.blackListedWorlds.clear();
				Common.clearBlackListWorld();

				final List<String> bl = yaml.getStringList("Black_List");

				if (yaml.getStringList("Black_List").isEmpty()) {
					Common.fixBlackListNull();
				} else {
					for (final String world : bl) {
						Common.removeNullFixer();
						WorldManager.blackListedWorlds.add(Bukkit.getWorld(world));
					}
				}

				Common.sendConsoleMessage("&7&l------ &9&l[&fReloaded List&9&l]&7&l ------");
				Common.sendConsoleMessage("&9Blacklisted-world&7:&f [&aPassed&f]");
				Common.sendConsoleMessage("&9Player's value:&f [&aPassed&f]");
				Common.sendConsoleMessage("&9[&f&l!&9] &cIn case of don't need to auto reset, ");
				Common.sendConsoleMessage("&cput permission &e'PlayerModification.dontreset'.");
				Common.sendConsoleMessage("&cBecause some user have other plugin that modify player's value.");
				Common.sendConsoleMessage("&7&l-------------------------");

				for (final String worldName : Common.getWorldNames()) {
					for (final String BlackListWorldName : Common.getBlackListWorldNames()) {
						for (final Player plr : Bukkit.getOnlinePlayers()) {
							if (plr.getWorld().getName().equals(BlackListWorldName) && plr.hasPermission("PlayerModification.dontreset")) {

								plr.setCanPickupItems(true);
								plr.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
								plr.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
								PluginCollection.removePvpPlayer(plr);
								PluginCollection.removeAntiBuildPlayer(plr);
								return true;

							} else if (plr.getWorld().getName().equals(BlackListWorldName) && !plr.hasPermission("PlayerModification.dontreset")) {

								plr.resetMaxHealth();
								plr.setHealthScale(20.0D);
								plr.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
								plr.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
								plr.setCanPickupItems(true);
								plr.setWalkSpeed(0.2F);
								plr.setFlySpeed(0.1F);
								PluginCollection.removePvpPlayer(plr);
								PluginCollection.removeAntiBuildPlayer(plr);
								return true;

							} else if (plr.getWorld().getName().equals(worldName)) {

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

								if (config.getSection(worldName).getBoolean("Anti_Build")) {
									PluginCollection.addAntiBuildingList(plr);
								} else {
									PluginCollection.removeAntiBuildPlayer(plr);
								}

								if (config.getSection(worldName).getBoolean("Anti_Build")) {
									PluginCollection.addAntiBuildingList(plr);
								} else {
									PluginCollection.removeAntiBuildPlayer(plr);
								}


								if (!plr.getWorld().getName().equals(worldName)) {

									config.setDefault(plr.getWorld().getName() + ".Health", 20.0D);
									config.setDefault(plr.getWorld().getName() + ".Health_Scale", 20.0D);
									config.setDefault(plr.getWorld().getName() + ".Attack_Damage", 1.0D);
									config.setDefault(plr.getWorld().getName() + ".Movement_Speed", 0.2F);
									config.setDefault(plr.getWorld().getName() + ".Flying_Speed", 0.1F);
									config.setDefault(plr.getWorld().getName() + ".Enabled_Pick_Up_Item", true);
									config.setDefault(plr.getWorld().getName() + ".Enabled_Old_Pvp_Mechanics", false);
									config.setDefault(plr.getWorld().getName() + ".Allow_PVP", true);
									config.setDefault(plr.getWorld().getName() + ".Anti_Build", false);
								}

							}
						}
					}
				}
			} else if ("help".equals(param)) {

				sendHelp();

			} else if ("reset".equals(param)) {

				Common.sendConsoleMessage("&aAll player in blacklisted world has been reset to default.");

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
							PluginCollection.removeAntiBuildPlayer(plr);
						}
					}
				}

			} else if ("disable".equals(param)) {
				for (final Player plr : Bukkit.getOnlinePlayers()) {
					plr.resetMaxHealth();
					plr.setHealthScale(20.0D);
					plr.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
					plr.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
					plr.setCanPickupItems(true);
					plr.setWalkSpeed(0.2F);
					plr.setFlySpeed(0.1F);
					PluginCollection.removePvpPlayer(plr);
					PluginCollection.removeAntiBuildPlayer(plr);
				}


				PlayerModification.getInstance().disableThisPlugin();
				sendGoodbye();

			} else {
				sendHelp();
			}
		}



		return true;
	}

	private void sendHelp() {
		Common.sendConsoleMessage("&5-----------&f[&ePlayerModification Console 2.2.6&f]&5-----------");
		Common.sendConsoleMessage("&cAliases: /pmc&f,&c /pmconsole");
		Common.sendConsoleMessage("&6/pmc reload &f- Reload the configuration.");
		Common.sendConsoleMessage("&6/pmc reset &f- Resetting all player who in the blacklisted world");
		Common.sendConsoleMessage("&6/pmc disable &f- Disable this plugin &4(If you want to remove)");
		Common.sendConsoleMessage("&6/pmc help &f- Getting help of this plugin.");

	}

	private void sendGoodbye() {
		Common.sendConsoleMessage("&7&l------ &9&l[&fPlayerModification v2.2.6&9&l]&7&l ------");
		Common.sendConsoleMessage("&cThank you for using my plugins");
		Common.sendConsoleMessage("&cDid you get bad experience with this plugin?");
		Common.sendConsoleMessage("&cI can help you on my plugin homepage in discussion room");
		Common.sendConsoleMessage("&cor contact me via SpigotMC. (MrDefault)");
		Common.sendConsoleMessage("&eThank you and I hope to see you again.");
		Common.sendConsoleMessage("&7&l--------------------------------");
	}
}
