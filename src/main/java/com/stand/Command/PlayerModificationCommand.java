package com.stand.Command;

import com.stand.PluginCollection;
import com.stand.Utility.Common;
import com.stand.Utility.PlayerUtil;
import de.leonhard.storage.Config;
import de.leonhard.storage.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

				player.sendMessage(Common.colorize("&aPlayerModification configuration has been reloaded!!"));

				for (int i = 0; i < PluginCollection.getBlackListWorldList().size(); i++) {
					final String BlackListWorldName = PluginCollection.getBlackListWorldList().get(i);
					for (final String worldName : Common.getWorldNames()) {
						for (final Player plr : Bukkit.getOnlinePlayers()) {

							PluginCollection.clearBlackListWorlds();
							PluginCollection.addAllBlackListWorlds(yaml.getStringList("Black_List"));

							if (!plr.getWorld().getName().equals(worldName) && !PluginCollection.containsBlackListWorld(worldName)) {
								config.setDefault(player.getWorld().getName() + ".Health", 20.0D);
								config.setDefault(player.getWorld().getName() + ".Health_Scale", 20.0D);
								config.setDefault(player.getWorld().getName() + ".Movement_Speed", 0.2F);
								config.setDefault(player.getWorld().getName() + ".Flying_Speed", 0.1F);
								config.setDefault(player.getWorld().getName() + ".Enabled_Pick_Up_Item", true);
								config.setDefault(player.getWorld().getName() + ".Enabled_Old_Pvp_Mechanics", false);
								config.setDefault(player.getWorld().getName() + ".Allow_PVP", true);
							}

							if (plr.getWorld().getName().equals(worldName) && !plr.getWorld().getName().equals(BlackListWorldName)) {
								try {
									plr.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(config.getSection(worldName).getDouble("Health"));
								} catch (final IllegalArgumentException ex) {
									plr.resetMaxHealth();
									plr.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(config.getSection(worldName).getDouble("Health"));
								}

								plr.setHealthScale(config.getSection(worldName).getDouble("Health_Scale"));
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

							}

							if (plr.getWorld().getName().equals(BlackListWorldName)) {
								player.setHealth(20.0D);
								player.resetMaxHealth();
								player.setHealthScale(20.0D);
								player.setWalkSpeed(0.2F);
								player.setFlySpeed(0.1F);
								player.setCanPickupItems(true);
								PluginCollection.clear();
							}

						}
					}
				}

			} else if ("help".equals(param)) {

				if ("help".equalsIgnoreCase(args[0])) {
					sendHelp(player);
				}

			} else {
				sendHelp(player);
			}
		}

		return true;
	}

	private void sendHelp(final Player player) {
		Common.sendMessage(player, "&5-----------&f[&ePlayerModification 2.2.0&f]&5-----------");
		Common.sendMessage(player , "&4<> = Require &f,&c [] = Optional");
		Common.sendMessage(player , "&cAliases: /pm&f,&c /pmd");
		Common.sendMessage(player , "&6/playermodification reload &f- Reload the configuration.");
		Common.sendMessage(player , "&6/playermodification help &f- Getting help of this plugin.");
	}
}