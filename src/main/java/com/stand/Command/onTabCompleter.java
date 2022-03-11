package com.stand.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class onTabCompleter implements org.bukkit.command.TabCompleter {

	private List<String> arguments = new ArrayList<>();

	@Override
	public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
		if (arguments.isEmpty()) {
			arguments.add("reload");
			arguments.add("help");
		}

		final List<String> tabList = new ArrayList<>();
		if(args.length == 1) {
			for (final String argu : arguments) {
				if(argu.toLowerCase().startsWith(args[0].toLowerCase())) {
					tabList.add(argu);
				}
			}
			return tabList;
		}

		return null;
	}
}
